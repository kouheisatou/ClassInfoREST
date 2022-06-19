package net.iobb.tonarinosibahaao.auth.servlet

import com.onelogin.saml2.Auth
import java.io.Writer

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SamlRequestServlet : HttpServlet(){

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse) {
        try {
            val w: Writer = resp.writer
            resp.contentType = "text/html"
            w.append(
                "<html>" + "<head></head>"
                        + "<body><h1>Click the button to send the AuthnRequest using HTTP POST</h1> <form method=\"POST\">"
                        + "<input type=\"submit\" value=\"Send\"/>" + "</form>" + "</body>" + "</html>"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val auth = Auth(req, resp)
        auth.login()

    }
}