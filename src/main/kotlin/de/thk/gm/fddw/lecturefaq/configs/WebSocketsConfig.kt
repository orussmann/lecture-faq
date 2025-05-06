package de.thk.gm.fddw.lecturefaq.configs

import de.thk.gm.fddw.lecturefaq.handlers.SimpleChatHandler
import de.thk.gm.fddw.lecturefaq.handlers.SimplePollHandler
import de.thk.gm.fddw.lecturefaq.services.AnswersService
import de.thk.gm.fddw.lecturefaq.services.QuestionsService
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketsConfig(private val questionsService: QuestionsService, private val answersService: AnswersService) : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(SimpleChatHandler(questionsService), "/chat").setAllowedOrigins("*")
        registry.addHandler(SimplePollHandler(answersService), "/poll-completion").setAllowedOrigins("*")
    }
}