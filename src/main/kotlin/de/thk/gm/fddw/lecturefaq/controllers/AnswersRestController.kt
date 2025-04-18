package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.models.answer_dto.AnswerResponseDTO
import de.thk.gm.fddw.lecturefaq.models.answer_dto.CreateAnswerRequestDTO
import de.thk.gm.fddw.lecturefaq.models.answer_dto.UpdateAnswerRequestDTO
import de.thk.gm.fddw.lecturefaq.services.AnswersService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/v1")
class AnswersRestController(
    private val answersService: AnswersService
) {
    //TODO: Consider using ResponseEntity


    @GetMapping("/polls/{pollId}/answers")
    @ResponseStatus(HttpStatus.OK)
    fun getAllAnswersForPoll(@PathVariable pollId: UUID): List<AnswerResponseDTO> {
        try {
            val pollsAnswers = answersService.findAllByPollId(pollId)
            return pollsAnswers
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch answers")
        }
    }

    @GetMapping("/answers")
    @ResponseStatus(HttpStatus.OK)
    fun getAllAnswers(): MutableIterable<AnswerResponseDTO> {
        try {
            val allAnswers = answersService.findAll()
            return allAnswers
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch answers")
        }
    }

    @PostMapping("/polls/{pollId}/answers")
    @ResponseStatus(HttpStatus.CREATED)
    fun createAnswer(
        @RequestBody answer: CreateAnswerRequestDTO,
        @PathVariable pollId: UUID
    ): AnswerResponseDTO {
        try {
            if (answer.pollId == null) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Poll ID is missing")
            }
            val createdAnswer = answersService.save(answer)
            return createdAnswer
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create answer")
        }
    }

    @GetMapping("/polls/{pollId}/answers/{answerId}")
    @ResponseStatus(HttpStatus.OK)
    fun getAnswer(
        @PathVariable pollId: UUID,
        @PathVariable answerId: UUID
    ): AnswerResponseDTO {
        try {
            val answer = answersService.findById(answerId)
            return answer
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete answer")
        }
    }

    @DeleteMapping("/polls/{pollId}/answers/{answerId}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteAnswer(
        @PathVariable pollId: UUID,
        @PathVariable answerId: UUID
    ) {
        try {
            answersService.removeById(answerId)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete answer")
        }
    }

    @PutMapping("/polls/{pollId}/answers/{answerId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateAnswer(
        @PathVariable pollId: UUID,
        @PathVariable answerId: UUID,
        @RequestBody answer: UpdateAnswerRequestDTO
    ): AnswerResponseDTO {
        try {
            val updatedAnswer = answersService.updateById(answerId, answer)
            return updatedAnswer
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update answer")
        }
    }

}