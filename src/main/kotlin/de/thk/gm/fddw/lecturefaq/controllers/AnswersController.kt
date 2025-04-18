package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.models.answer_dto.CreateAnswerRequestDTO
import de.thk.gm.fddw.lecturefaq.models.answer_dto.UpdateAnswerRequestDTO
import de.thk.gm.fddw.lecturefaq.services.AnswersService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

//TODO: Consider using ResponseEntity

@Controller
@RequestMapping(produces = [MediaType.TEXT_HTML_VALUE])
class AnswersController(
    private val answersService: AnswersService
) {
    @GetMapping("/polls/{pollId}/answers")
    @ResponseStatus(HttpStatus.OK)
    fun getAllAnswersForPoll(
        @PathVariable pollId: UUID,
        model: Model
    ): String {
        try {
            val pollsAnswers = answersService.findAllByPollId(pollId)
            model.addAttribute("pollId", pollId)
            model.addAttribute("answers", pollsAnswers)
            return "answers/showAnswers"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch answers")
        }
    }

    @GetMapping("/answers")
    @ResponseStatus(HttpStatus.OK)
    fun getAllAnswers(model: Model): String {
        try {
            val allAnswers = answersService.findAll()
            model.addAttribute("answers", allAnswers)
            return "answers/showAnswers"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch answers")
        }
    }

    @PostMapping("/polls/{pollId}/answers")
    fun createAnswer(
        @PathVariable pollId: UUID,
        @RequestParam text: String,
        model: Model
    ): String {
        try {
            val answer = CreateAnswerRequestDTO(pollId, text)
            answersService.save(answer)
            return "redirect:/app/polls/${pollId}/answers"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create answer")
        }
    }

    @GetMapping("/polls/{pollId}/answers/{answerId}")
    @ResponseStatus(HttpStatus.OK)
    fun getAnswer(
        @PathVariable pollId: UUID,
        @PathVariable answerId: UUID,
        model: Model
    ): String {
        try {
            val answer = answersService.findById(answerId)
            model.addAttribute("pollId", pollId)
            model.addAttribute("answer", answer)
            return "answers/showAnswer"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete answer")
        }
    }

    @DeleteMapping("/polls/{pollId}/answers/{answerId}")
    fun deleteAnswer(
        @PathVariable pollId: UUID,
        @PathVariable answerId: UUID
    ): String {
        try {
            answersService.removeById(answerId)
            return "redirect:/app/polls/${pollId}/answers"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete answer")
        }
    }

    @PutMapping("/polls/{pollId}/answers/{answerId}")
    fun updateAnswer(
        @PathVariable pollId: UUID,
        @PathVariable answerId: UUID,
        @RequestParam text: String,
        @RequestParam count: Short,
        model: Model
    ): String {
        try {
            answersService.updateById(answerId, UpdateAnswerRequestDTO(text, count))
            return "redirect:/app/polls/${pollId}/answers"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update answer")
        }
    }
}