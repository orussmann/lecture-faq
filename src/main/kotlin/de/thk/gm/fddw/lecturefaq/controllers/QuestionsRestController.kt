package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.models.questions_dto.CreateQuestionRequestDTO
import de.thk.gm.fddw.lecturefaq.models.questions_dto.QuestionResponseDTO
import de.thk.gm.fddw.lecturefaq.models.questions_dto.UpdateQuestionRequestDTO
import de.thk.gm.fddw.lecturefaq.services.QuestionsService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/v1")
class QuestionsRestController(private val questionsService: QuestionsService) {


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
        @RequestBody questionDTO: CreateQuestionRequestDTO,
        @PathVariable lectureId: UUID
    ): QuestionResponseDTO {
        try {
            return questionsService.save(questionDTO)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create question")
        }
    }

    @DeleteMapping("/lectures/{lectureId}/questions/{questionId}")   //TODO: Handln, wenn nichts gelöscht wird
    @ResponseStatus(HttpStatus.OK)
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
        @RequestBody updatedQuestionDTO: UpdateQuestionRequestDTO,
        @PathVariable lectureId: UUID
    ): QuestionResponseDTO {
        try {
            return questionsService.updateById(questionId, updatedQuestionDTO)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update question")
        }
    }
}