package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.models.answer_dtos.CreateAnswerRequestDTO
import de.thk.gm.fddw.lecturefaq.models.answer_dtos.UpdateAnswerRequestDTO
import de.thk.gm.fddw.lecturefaq.services.AnswersService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*

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

    @PostMapping("/polls/{pollId}/answers")
    fun createAnswer(
        @PathVariable pollId: UUID,
        @Valid answer: CreateAnswerRequestDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("errors", bindingResult)
            } else {
                answersService.save(answer)
            }
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
        @Valid answer: UpdateAnswerRequestDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("errors", bindingResult)
            } else {
                answersService.updateById(answerId, answer)
            }
            return "redirect:/app/polls/${pollId}/answers"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update answer")
        }
    }
}