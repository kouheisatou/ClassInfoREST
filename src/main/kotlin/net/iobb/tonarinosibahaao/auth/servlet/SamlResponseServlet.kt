package net.iobb.tonarinosibahaao.auth.servlet

import com.onelogin.saml2.Auth
import net.iobb.tonarinosibahaao.auth.common.EncryptManagerClient
import org.apache.commons.lang3.StringUtils
import java.io.IOException
import javax.security.auth.login.LoginException
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SamlResponseServlet : HttpServlet() {


    private var username: String? = null
    private var encryptedUsername: String? = null

    @Throws(ServletException::class, IOException::class)
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        try {
            username = validate(req, resp)
            encryptedUsername = EncryptManagerClient.encryptUsername(username).replace("=", "")
            // TalonRealmに平文のユーザ名とタイムスタンプを加えて暗号化したユーザ名を渡す
            req.login(username, encryptedUsername)
            println("[LoginSucceeded] user:\"$username\", encryptedUsername:\"$encryptedUsername\"")
        } catch (e: Exception) {
            e.printStackTrace()
            println("[LoginSucceeded] user:\"$username\", encryptedUsername:\"$encryptedUsername\"")
        }
    }

    @Throws(IOException::class, ServletException::class, LoginException::class)
    private fun validate(req: HttpServletRequest, resp: HttpServletResponse): String? {
        var auth: Auth? = null
        try {
            auth = Auth(req, resp)
            auth.processResponse()
        } catch (e: Exception) {
            throw LoginException("broken saml response")
        }
        if (!auth.isAuthenticated) {
            throw LoginException("invalid saml response")
        }
        val errors = auth.errors
        return if (errors.isNotEmpty()) {
            println(StringUtils.join(errors, ", "))
            if (auth.isDebugActive) {
                val errorReason = auth.lastErrorReason
                if (errorReason != null && !errorReason.isEmpty()) {
                    println(auth.lastErrorReason)
                }
            }
            throw LoginException()
        } else {
            auth.nameId
        }
    }
}