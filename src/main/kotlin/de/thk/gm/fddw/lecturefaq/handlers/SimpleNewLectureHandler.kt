package de.thk.gm.fddw.lecturefaq.handlers

import de.thk.gm.fddw.lecturefaq.controllers.PollsController
import de.thk.gm.fddw.lecturefaq.models.enums.Role
import de.thk.gm.fddw.lecturefaq.services.UsersService
import org.slf4j.LoggerFactory
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import kotlin.collections.ArrayList

class SimpleNewLectureHandler(private val usersService: UsersService) : TextWebSocketHandler() {

    private val logger = LoggerFactory.getLogger(PollsController::class.java)

    private val hashMapOfSessions: HashMap<UUID, ArrayList<WebSocketSession>> = HashMap()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val uri: UriComponents = UriComponentsBuilder.fromUri(session.uri!!).build()
        val role = uri.queryParams.getFirst("role")
        logger.debug("SimpleNewLectureHandler, new connection established : {}", session)
        if (role == Role.STUDENT.toString()) {
            val lecturerId = UUID.fromString(uri.queryParams.getFirst("lecturerId"))
            val studentId = UUID.fromString(uri.queryParams.getFirst("studentId"))
            var sessions = hashMapOfSessions[lecturerId]
            if (sessions == null) {
                sessions = ArrayList()
            }
            sessions.add(session)
            hashMapOfSessions[lecturerId] = sessions
        }
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val uri: UriComponents = UriComponentsBuilder.fromUri(session.uri!!).build()
        val lecturerId: UUID = UUID.fromString(uri.queryParams.getFirst("lecturerId"))
        val sessions: ArrayList<WebSocketSession>? = hashMapOfSessions[lecturerId]
        if (sessions != null) {
            logger.debug("SimpleNewLectureHandler, Anzahl sessions : {}{}", sessions.size, " test")
            for (chatSession in sessions) {
                chatSession.sendMessage(message)
            }
        }
    }


    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val uri: UriComponents = UriComponentsBuilder.fromUri(session.uri!!).build()
        val lecturerId: UUID? = UUID.fromString(uri.queryParams.getFirst("lecturerId"))
        val sessions: ArrayList<WebSocketSession>? = hashMapOfSessions[lecturerId]
        if (sessions != null && lecturerId != null) {
            sessions.removeIf { it.id == session.id }
            hashMapOfSessions[lecturerId] = sessions
        }
    }
}