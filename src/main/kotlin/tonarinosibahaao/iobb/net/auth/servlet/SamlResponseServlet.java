package tonarinosibahaao.iobb.net.auth.servlet;

import com.onelogin.saml2.Auth;
import org.apache.commons.lang3.StringUtils;
import tonarinosibahaao.iobb.net.auth.common.EncryptManagerClient;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.lang.System.out;

public class SamlResponseServlet extends HttpServlet {

    private String username = null;
    private String encryptedUsername = null;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try{
            username = validate(req, resp);
            encryptedUsername = EncryptManagerClient.encryptUsername(username).replace("=", "");
            // TalonRealmに平文のユーザ名とタイムスタンプを加えて暗号化したユーザ名を渡す
            req.login(username, encryptedUsername);
            out.println("[LoginSucceeded] user:\"" + username + "\", encryptedUsername:\"" + encryptedUsername + "\"");
        }catch (Exception e){
            e.printStackTrace();
            out.println("[LoginSucceeded] user:\"" + username + "\", encryptedUsername:\"" + encryptedUsername + "\"");
        }
    }

    private String validate(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, LoginException {

        Auth auth = null;
        try {
            auth = new Auth(req, resp);
            auth.processResponse();
        } catch (Exception e) {
            throw new LoginException("broken saml response");
        }

        if (!auth.isAuthenticated()) {
            throw new LoginException("invalid saml response");
        }

        List<String> errors = auth.getErrors();
        if (!errors.isEmpty()) {
            out.println(StringUtils.join(errors, ", "));
            if (auth.isDebugActive()) {
                String errorReason = auth.getLastErrorReason();
                if (errorReason != null && !errorReason.isEmpty()) {
                    out.println(auth.getLastErrorReason());
                }
            }
            throw new LoginException();
        } else {
            return auth.getNameId();
        }
    }
}
