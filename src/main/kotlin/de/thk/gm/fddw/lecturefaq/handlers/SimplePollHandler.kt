package de.thk.gm.fddw.lecturefaq.handlers

import de.thk.gm.fddw.lecturefaq.models.answer_dtos.UpdateAnswerRequestDTO
import de.thk.gm.fddw.lecturefaq.models.enums.Role
import de.thk.gm.fddw.lecturefaq.services.AnswersService
import org.json.JSONArray
import org.json.JSONObject
import org.slf4j.LoggerFactory
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

class SimplePollHandler(private val answersService: AnswersService) : TextWebSocketHandler() {

    private val logger = LoggerFactory.getLogger(SimplePollHandler::class.java)

    private val hashMapOfSessions: HashMap<UUID, ArrayList<WebSocketSession>> = HashMap()

    override fun afterConnectionEstablished(session: WebSocketSession) {

        logger.info("SimplePollHandler: sessions -> " + hashMapOfSessions.size)
        logger.info("SimplePollHandler: Connection established")
        val uri: UriComponents = UriComponentsBuilder.fromUri(session.uri!!).build()
        val role = uri.queryParams.getFirst("role")
        if (role != null) {
            logger.info("SimplePollHandler (1): role != null")
            val pollId = UUID.fromString(uri.queryParams.getFirst("pollId"))
            if (pollId != null) {
                logger.info("SimplePollHandler: pollId != null")
                var sessions = hashMapOfSessions[pollId]
                if (sessions == null) {
                    sessions = ArrayList()
                }
                sessions.add(session)
                hashMapOfSessions[pollId] = sessions
            }
        }
    }

    //TODO: Persist answer count
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        logger.info("SimplePollHandler: Handling message")
        val uri: UriComponents = UriComponentsBuilder.fromUri(session.uri!!).build()
        val role = uri.queryParams.getFirst("role")
        val pollId = UUID.fromString(uri.queryParams.getFirst("pollId"))
        val jsonOfObjects = JSONObject(message.payload)
        logger.info("SimplePollHandler: role = $role")
        logger.info("SimplePollHandler: pollId = $pollId")
        logger.info("SimplePollHandler: jsonOfObjects = $jsonOfObjects")

        val answerIdStr = jsonOfObjects.getString("answerId")
        val answerId = UUID.fromString(answerIdStr)
        logger.info("SimplePollHandler: answerId = $answerId")
        val newCount = (answersService.findById(answerId).count + 1).toShort()  // Refactor!! Too many queries!
        logger.info("SimplePollHandler: newCount = $newCount")
        answersService.updateById(answerId, UpdateAnswerRequestDTO(count = newCount))
        // Hole alle Antworten aus der Datenbank
        val answers = answersService.findAllByPollId(pollId)

        // Mappe die Antworten zu einer Liste von ChosenAnswer-Objekten
        val answersObj = answers.map { ChosenAnswer(it.text, it.count) }

        // Wandle die Liste der Antworten in ein JSON-Array um
        val answersJsonArray = JSONArray()
        answersObj.forEach { answer ->
            val answerJson = JSONObject()
            answerJson.put("text", answer.text)
            answerJson.put("count", answer.count)
            answersJsonArray.put(answerJson)
        }

        logger.info("SimplePollHandler: answersJsonArray = $answersJsonArray")
        // Erstelle die TextMessage, die das JSON-Array enthält
        val answersTextMessage = TextMessage(answersJsonArray.toString())


        if (role != null) {
            logger.info("SimplePollHandler: role != null")
            val pollId = UUID.fromString(uri.queryParams.getFirst("pollId"))
            val sessions = hashMapOfSessions[pollId]
            if (sessions != null) {
                logger.info("SimplePollHandler: sessions != null")
                for (pollSession in sessions) {
                    pollSession.sendMessage(answersTextMessage)
                }
            }
        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        logger.info("SimplePollHandler: Connection closed")
        val uri: UriComponents = UriComponentsBuilder.fromUri(session.uri!!).build()
        val role = uri.queryParams.getFirst("role")
        val pollUser = listOf(Role.LECTURER.toString(), Role.STUDENT.toString())
        if (role != null && role in pollUser) {
            val pollId = UUID.fromString(uri.queryParams.getFirst("pollId"))
            val sessions = hashMapOfSessions[pollId]
            sessions?.remove(session)
        }
    }
}

class ChosenAnswer(
    var text: String? = null,
    var count: Short? = null
)