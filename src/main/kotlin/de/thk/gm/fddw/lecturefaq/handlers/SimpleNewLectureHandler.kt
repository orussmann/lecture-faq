package de.thk.gm.fddw.lecturefaq.handlers

import de.thk.gm.fddw.lecturefaq.services.UsersService
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

class SimpleNewLectureHandler(private val usersService: UsersService) : TextWebSocketHandler() {

    private val hashMapOfSessions: HashMap<UUID, ArrayList<WebSocketSession>> = HashMap()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val uri: UriComponents = UriComponentsBuilder.fromUri(session.uri!!).build()
        val lecturerId = UUID.fromString(uri.queryParams.getFirst("lecturerId"))
        val studentId = UUID.fromString(uri.queryParams.getFirst("studentId"))
        if (studentId != null && lecturerId != null) {
            val studentAssociatedWithLecturer = usersService.findById(lecturerId).subscriptions.contains(studentId)
            if (studentAssociatedWithLecturer) {
                var sessions = hashMapOfSessions[lecturerId]
                if (sessions == null) {
                    sessions = ArrayList()
                }
                sessions.add(session)
                hashMapOfSessions[lecturerId] = sessions
            }
        }
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val uri: UriComponents = UriComponentsBuilder.fromUri(session.uri!!).build()
        val lecturerId: UUID = UUID.fromString(uri.queryParams.getFirst("lecturerId"))
        val sessions: ArrayList<WebSocketSession>? = hashMapOfSessions[lecturerId]
        if (sessions != null) {
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
            for (chatSession in sessions) {
                if (chatSession.id == session.id) {
                    sessions.remove(chatSession)
                    hashMapOfSessions[lecturerId] = sessions
                }
            }
        }
    }
}