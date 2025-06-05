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
class SecurityConfig(private val loginSuccessHandler: LoginSuccessHandler) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            authorizeHttpRequests {
                // TODO: Adjust to security constraints
                // TODO: Implement logout
                authorize("/app/", permitAll)
                authorize("/app/login", permitAll)
                authorize("/app/register", permitAll)
                authorize("/app/chat", permitAll)
                authorize("/chat", permitAll)
                authorize("/public-lectures/**", permitAll)
                authorize("/app/poll-completion", permitAll)
                authorize("/public-polls/**", permitAll)
                // TODO: If POST is not allowed, then PUT shouldn't be either
                // TODO: Divide the Controller into several Controllers -> at this point the separation of views happens in one Controller (for each ressource)
                authorize(HttpMethod.POST, "/app/user/lecturer/**", hasAuthority("ROLE_${Role.LECTURER}"))
                authorize(HttpMethod.POST, "/app/user/student/**", hasAuthority("ROLE_${Role.STUDENT}"))    // Prefix student -> Controller for student view
                authorize(HttpMethod.POST, "/app/api/v1/users/lecturers/**", hasAuthority("ROLE_${Role.LECTURER}"))
                authorize(HttpMethod.POST, "/app/api/v1/users/students/**", hasAuthority("ROLE_${Role.STUDENT}"))

                authorize(anyRequest, authenticated)

//                authorize("/app/api/v1/users/**", hasAnyRole(Role.LECTURER.name, Role.STUDENT.name))
//                authorize("/app/api/v1/users", hasAnyRole(Role.LECTURER.name, Role.STUDENT.name))
//
//                authorize(HttpMethod.GET, "/app/api/v1/lectures/**", hasAnyRole(Role.LECTURER.name, Role.STUDENT.name))
//                authorize(HttpMethod.POST, "/app/api/v1/lectures/**", hasAnyRole(Role.LECTURER.name))
//                authorize(HttpMethod.PUT, "/app/api/v1/lectures/**", hasAnyRole(Role.LECTURER.name))
//                authorize(HttpMethod.GET, "/app/api/v1/questions", hasAnyRole(Role.LECTURER.name, Role.STUDENT.name))
//
//                authorize(HttpMethod.GET, "/app/api/v1/polls", hasAnyRole(Role.LECTURER.name, Role.STUDENT.name))
//                authorize(HttpMethod.GET, "/app/api/v1/users/**", hasAnyRole(Role.LECTURER.name, Role.STUDENT.name))
//                authorize(HttpMethod.PUT, "/app/api/v1/users/**", hasAnyRole(Role.LECTURER.name, Role.STUDENT.name))
//                authorize("/app/api/v1/lecturers/**", hasAnyRole(Role.LECTURER.name))
//
//                authorize(HttpMethod.GET, "/app/api/v1/lectures", hasAnyRole(Role.LECTURER.name, Role.STUDENT.name))
//                authorize(HttpMethod.GET, "/app/api/v1/polls/**", hasAnyRole(Role.LECTURER.name, Role.STUDENT.name))
//                authorize(HttpMethod.DELETE, "/app/api/v1/polls/**", hasAnyRole(Role.LECTURER.name))
//                authorize(HttpMethod.PUT, "/app/api/v1/polls/**", hasAnyRole(Role.LECTURER.name))
//                authorize(HttpMethod.GET, "/app/api/v1/answers", hasAnyRole(Role.LECTURER.name))
//                authorize(HttpMethod.GET, "/app/api/v1/lecturers/**", hasAnyRole(Role.LECTURER.name))
//
//                authorize(anyRequest, denyAll)
            }
            formLogin {
                permitAll()
                loginPage = "/"
                loginProcessingUrl = "/login"   //TODO: Implement logout
                defaultSuccessUrl("/app/user/profile", true)
                authenticationSuccessHandler = loginSuccessHandler
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
