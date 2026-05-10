package de.thk.gm.fddw.lecturefaq.configs

import de.thk.gm.fddw.lecturefaq.loginHandlers.LoginSuccessHandler
import de.thk.gm.fddw.lecturefaq.models.enums.Role
import de.thk.gm.fddw.lecturefaq.services.LecturesServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

private val logger = LoggerFactory.getLogger(LecturesServiceImpl::class.java)

@EnableWebSecurity
@Configuration
class SecurityConfig(
    private val loginSuccessHandler: LoginSuccessHandler) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            authorizeHttpRequests {
                // TODO: Adjust to security constraints
                // TODO: Implement logout
                authorize("/", permitAll)
                authorize("/login", permitAll)
                authorize("/register", permitAll)
                authorize("/chat", permitAll)
                authorize("/public-lectures/**", permitAll)
                authorize("/poll-completion", permitAll)
                authorize("/public-polls/**", permitAll)
                // TODO: If POST is not allowed, then PUT shouldn't be either
                // TODO: Divide the Controller into several Controllers -> at this point the separation of views happens in one Controller (for each ressource)
                authorize("/user/lecturer/**", hasAuthority("ROLE_${Role.LECTURER}"))
                authorize("/user/student/**", hasAuthority("ROLE_${Role.STUDENT}"))    // Prefix student -> Controller for student view

                authorize("/user/**", authenticated)





                authorize("/api/v1/user**", hasAnyRole(Role.LECTURER.name, Role.STUDENT.name))

                authorize(HttpMethod.GET, "/api/v1/lectures/**", hasAnyRole(Role.LECTURER.name, Role.STUDENT.name))
                authorize(HttpMethod.POST, "/api/v1/lectures/**", hasAnyRole(Role.LECTURER.name))
                authorize(HttpMethod.PUT, "/api/v1/lectures/**", hasAnyRole(Role.LECTURER.name))
                authorize(HttpMethod.GET, "/api/v1/questions", hasAnyRole(Role.LECTURER.name, Role.STUDENT.name))

                authorize(HttpMethod.GET, "/api/v1/polls", hasAnyRole(Role.LECTURER.name, Role.STUDENT.name))
                authorize(HttpMethod.GET, "/api/v1/user/**", hasAnyRole(Role.LECTURER.name, Role.STUDENT.name))
                authorize(HttpMethod.PUT, "/api/v1/user/**", hasAnyRole(Role.LECTURER.name, Role.STUDENT.name))
                authorize("/api/v1/lecturers/**", hasAnyRole(Role.LECTURER.name))

                authorize(HttpMethod.GET, "/api/v1/lectures", hasAnyRole(Role.LECTURER.name, Role.STUDENT.name))
                authorize(HttpMethod.GET, "/api/v1/polls/**", hasAnyRole(Role.LECTURER.name, Role.STUDENT.name))
                authorize(HttpMethod.DELETE, "/api/v1/polls/**", hasAnyRole(Role.LECTURER.name))
                authorize(HttpMethod.PUT, "/api/v1/polls/**", hasAnyRole(Role.LECTURER.name))
                authorize(HttpMethod.GET, "/api/v1/answers", hasAnyRole(Role.LECTURER.name))
                authorize(HttpMethod.GET, "/api/v1/lecturers/**", hasAnyRole(Role.LECTURER.name))

                authorize(anyRequest, denyAll)
            }
            formLogin {
                permitAll()
                loginPage = "/"
                loginProcessingUrl = "/login"   //TODO: Implement logout
                failureUrl = "/login-error"
                defaultSuccessUrl("/user/profile", true)
                authenticationSuccessHandler = loginSuccessHandler
            }
            logout {
                permitAll()
                logoutUrl = "/logout"
                logoutSuccessUrl = "/"
            }
            httpBasic { }
        }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
