package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.models.question_dto.CreateQuestionRequestDTO
import de.thk.gm.fddw.lecturefaq.models.question_dto.UpdateQuestionRequestDTO
import de.thk.gm.fddw.lecturefaq.services.QuestionsService
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
class QuestionsController(private val questionsService: QuestionsService) {

    @GetMapping("/lectures/{lectureId}/questions/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    fun getQuestion(
        @PathVariable questionId: UUID,
        @PathVariable lectureId: UUID,
        model: Model
    ): String {
        try {
            val question = questionsService.findById(questionId)
            model.addAttribute("question", question)
            return "questions/showQuestion"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch question")
        }
    }

    /*
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
     */

    @GetMapping("/lectures/{lectureId}/questions")
    @ResponseStatus(HttpStatus.OK)
    fun getAllQuestionsForLecture(
        @PathVariable lectureId: UUID,
        model: Model
    ): String {
        try {
            val questions = questionsService.findAllByLectureId(lectureId)
            model.addAttribute("questions", questions)
            model.addAttribute("lectureId", lectureId)
            return "questions/showQuestions"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch questions")
        }
    }

    @PostMapping("/lectures/{lectureId}/questions")
    fun createQuestion(
        @PathVariable lectureId: UUID,
        @Valid question: CreateQuestionRequestDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("errors", bindingResult)
            } else {
                questionsService.save(question)
            }
            return "redirect:/app/lectures/$lectureId/questions"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create question")
        }
    }

    @DeleteMapping("/lectures/{lectureId}/questions/{questionId}")   //TODO: Handln, wenn nichts gelöscht wird
    fun deleteQuestion(
        @PathVariable lectureId: UUID,
        @PathVariable questionId: UUID
    ): String {
        try {
            questionsService.removeById(questionId)
            return "redirect:/app/lectures/$lectureId/questions"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete question")
        }
    }

    @PutMapping("/lectures/{lectureId}/questions/{questionId}")
    fun updateQuestion(
        @PathVariable questionId: UUID,
        @PathVariable lectureId: UUID,
        @Valid question: UpdateQuestionRequestDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        try {

            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("errors", bindingResult)
            } else {
                questionsService.updateById(questionId, question)
            }
            return "redirect:/app/lectures/$lectureId/questions"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update question")
        }
    }
}