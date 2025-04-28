package de.thk.gm.fddw.lecturefaq.handlers

import com.chargebee.org.json.JSONObject
import de.thk.gm.fddw.lecturefaq.models.question_dtos.CreateQuestionRequestDTO
import de.thk.gm.fddw.lecturefaq.services.QuestionsService
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder
import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class SimpleChatHandler(private val questionsService: QuestionsService) : TextWebSocketHandler() {

    private val hashMapOfSessions: HashMap<UUID, ArrayList<WebSocketSession>> = HashMap()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val uri: UriComponents = UriComponentsBuilder.fromUri(session.uri!!).build()
        val roomId = UUID.fromString(uri.queryParams.getFirst("roomId"))
        if (roomId != null) {
            var sessions = hashMapOfSessions[roomId]
            if (sessions == null) {
                sessions = ArrayList()
            }
            sessions.add(session)
            hashMapOfSessions[roomId] = sessions
        }
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val uri: UriComponents = UriComponentsBuilder.fromUri(session.uri!!).build()
        val roomId: UUID = UUID.fromString(uri.queryParams.getFirst("roomId"))

        val chatMessageJSONObject = JSONObject(message.payload)

        val createdAtString = chatMessageJSONObject.getString("createdAt")
        val createdAt = Date.from(Instant.parse(createdAtString))

        val chatQuestion = CreateQuestionRequestDTO(
            lectureId = UUID.fromString(chatMessageJSONObject.getString("lectureId")),
            userId = UUID.fromString(chatMessageJSONObject.getString("userId")),
            text = chatMessageJSONObject.getString("text"),
            createdAt = createdAt,
            chatUserName = chatMessageJSONObject.getString("chatUserName")
        )

        questionsService.save(chatQuestion)

        val sessions: ArrayList<WebSocketSession>? = hashMapOfSessions[roomId]
        if (sessions != null) {
            for (chatSession in sessions) {
                chatSession.sendMessage(message)
            }
        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val uri: UriComponents = UriComponentsBuilder.fromUri(session.uri!!).build()
        val roomId: UUID? = UUID.fromString(uri.queryParams.getFirst("roomId"))
        val sessions: ArrayList<WebSocketSession>? = hashMapOfSessions[roomId]
        if (sessions != null && roomId != null) {
            for (chatSession in sessions) {
                if (chatSession.id == session.id) {
                    sessions.remove(chatSession)
                    hashMapOfSessions[roomId] = sessions
                }
            }
        }
    }

}