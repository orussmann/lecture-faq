package de.thk.gm.fddw.lecturefaq.loginHandlers


import de.thk.gm.fddw.lecturefaq.models.enums.Role
import de.thk.gm.fddw.lecturefaq.services.LecturesServiceImpl
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

private val logger = LoggerFactory.getLogger(LecturesServiceImpl::class.java)

@Component
class LoginSuccessHandler : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val roles = AuthorityUtils.authorityListToSet(authentication!!.authorities)
        logger.info("LoginSuccessHandler roles: {}", roles)
        if(roles.contains("ROLE_${Role.LECTURER}")) {
            response!!.sendRedirect("/app/user/lecturer/profile")
        } else if(roles.contains("ROLE_${Role.STUDENT}")) {
            response!!.sendRedirect("/app/user/student/profile")
        }
    }
}
