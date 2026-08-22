package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.models.question_dtos.CreateQuestionRequestDTO
import de.thk.gm.fddw.lecturefaq.models.question_dtos.LikeQuestionRequestDTO
import de.thk.gm.fddw.lecturefaq.models.question_dtos.QuestionResponseDTO
import de.thk.gm.fddw.lecturefaq.models.question_dtos.UpdateQuestionRequestDTO
import de.thk.gm.fddw.lecturefaq.services.QuestionsService
import de.thk.gm.fddw.lecturefaq.services.UsersService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.security.Principal
import java.util.*

@RestController
@RequestMapping("/api/v1")
class QuestionsRestController(
    private val questionsService: QuestionsService,
    private val usersService: UsersService
) {


    @GetMapping("/lectures/{lectureId}/questions/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    fun getQuestion(
        @PathVariable questionId: UUID,
        @PathVariable lectureId: UUID
    ): QuestionResponseDTO {
        try {
            val question = questionsService.findById(questionId)
            return question
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch question")
        }
    }
    //TODO: GET mapping for one question

    @GetMapping("/questions")
    @ResponseStatus(HttpStatus.OK)
    fun getAllQuestions(): MutableIterable<QuestionResponseDTO> {
        try {
            val questions = questionsService.findAll()
            return questions
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch question")
        }
    }

    @GetMapping("/lectures/{lectureId}/questions")
    @ResponseStatus(HttpStatus.OK)
    fun getAllQuestionsForLecture(@PathVariable lectureId: UUID): List<QuestionResponseDTO> {
        try {
            val questions = questionsService.findAllByLectureId(lectureId)
            return questions
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch questions")
        }
    }

    @PostMapping("/lectures/{lectureId}/questions")
    @ResponseStatus(HttpStatus.CREATED)
    fun createQuestion(
        @PathVariable lectureId: UUID,
        @Valid @RequestBody questionDTO: CreateQuestionRequestDTO
    ): QuestionResponseDTO {
        try {
            return questionsService.save(questionDTO)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create question")
        }
    }

    @DeleteMapping("/lectures/{lectureId}/questions/{questionId}")   //TODO: Handln, wenn nichts gelöscht wird
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteQuestion(
        @PathVariable lectureId: UUID,
        @PathVariable questionId: UUID
    ) {
        try {
            questionsService.removeById(questionId)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete question")
        }
    }

    @PutMapping("/lectures/{lectureId}/questions/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateQuestion(
        @PathVariable questionId: UUID,
        @PathVariable lectureId: UUID,
        @Valid @RequestBody updatedQuestionDTO: UpdateQuestionRequestDTO
    ): QuestionResponseDTO {
        try {
            return questionsService.updateById(questionId, updatedQuestionDTO)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update question")
        }
    }

    @PatchMapping("/lectures/{lectureId}/questions/{questionId}/likes")
    fun likeQuestion(
        @PathVariable questionId: UUID,
        @RequestBody likeQuestionRequestDTO: LikeQuestionRequestDTO,
        principal: Principal
    ): Map<String, Int> {

        val user = usersService.findByEmail(principal.name)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

        val likes = questionsService.updateLikes(
            questionId,
            user.id,
            likeQuestionRequestDTO.liked
        )

        return mapOf("likes" to likes)
    }
}