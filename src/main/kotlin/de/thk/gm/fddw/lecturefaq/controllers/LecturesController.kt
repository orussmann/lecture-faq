package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.models.enums.Role
import de.thk.gm.fddw.lecturefaq.models.lecture_dtos.CreateLectureRequestDTO
import de.thk.gm.fddw.lecturefaq.models.lecture_dtos.UpdateLectureRequestDTO
import de.thk.gm.fddw.lecturefaq.services.LecturesService
import de.thk.gm.fddw.lecturefaq.services.QuestionsService
import de.thk.gm.fddw.lecturefaq.services.UsersService
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
class LecturesController(
    private val lecturesService: LecturesService,
    private val questionsService: QuestionsService,
    private val usersService: UsersService
) {

    @GetMapping("/users/{userId}/lectures/{lectureId}")
    @ResponseStatus(HttpStatus.OK)
    fun getLecture(
        @PathVariable userId: UUID,
        @PathVariable lectureId: UUID,
        model: Model
    ): String {
        try {
            val lecture = lecturesService.findById(lectureId)
            val chatMessages = questionsService.findAllByLectureIdOrderByCreatedAt(lecture.id)
            model.addAttribute("lecture", lecture)
            model.addAttribute("chatMessages", chatMessages)
            return "lectures/showLecture"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch lecture")
        }
    }

    @GetMapping("/users/{userId}/notification")
    fun showNotification(
        @PathVariable userId: UUID,
        model: Model
    ): String {
        try {
            model.addAttribute("studentId", userId)
            return "student-view/showNotification"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    /*
    @GetMapping("/lectures")
    @ResponseStatus(HttpStatus.OK)
    fun getAllLectures(): MutableIterable<LectureResponseDTO> {
        try {
            val lectures = lecturesService.findAll()
            return lectures
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch lectures")
        }
    }
     */

    /*@GetMapping("/users/{userId}/lectures")
    @ResponseStatus(HttpStatus.OK)
    fun getAllLecturesFromUser(
        @PathVariable userId: UUID,
        model: Model
    ): String {
        try {
            val lectures = lecturesService.findAllByUserId(userId)
            model.addAttribute("lectures", lectures)
            model.addAttribute("userId", userId)
            return "lectures/showLectures"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch lectures")
        }
    }*/


    @GetMapping("/users/{userId}/lectures")
    @ResponseStatus(HttpStatus.OK)
    fun getAllLecturesFromUser(
        @PathVariable userId: UUID,
        model: Model
    ): String {
        try {
            val lectures = lecturesService.findAllByUserId(userId)
//            val lecturers = usersService.findAll().filter { it.role == Role.LECTURER }.map { it.userId }
//            val lecturersForResponse = lectures.filter { it.userId in lecturers }
            model.addAttribute("lectures", lectures)
            model.addAttribute("userId", userId)
            val user = usersService.findById(userId)
            return if (user.role == Role.LECTURER) {
                "lecturer-view/showLectures"
            } else {
                "student-view/showLectures"
            }
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch lectures")
        }
    }


    @PostMapping("/users/{userId}/lectures")
    fun createLecture(
        @PathVariable userId: UUID,
        @Valid lecture: CreateLectureRequestDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("errors", bindingResult)
            } else {
                lecturesService.save(lecture)
            }
            return "redirect:/app/users/$userId/lectures"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create lecture")
        }
    }

    @DeleteMapping("/users/{userId}/lectures/{lectureId}")   //TODO: Handln, wenn nichts gelöscht wird
    fun deleteLecture(
        @PathVariable lectureId: UUID,
        @PathVariable userId: String
    ): String {
        try {
            lecturesService.removeById(lectureId)
            return "redirect:/app/users/$userId/lectures"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete lecture")
        }
    }

    @PutMapping("/users/{userId}/lectures/{lectureId}")
    fun updateLecture(
        @PathVariable userId: UUID,
        @PathVariable lectureId: UUID,
        @Valid lecture: UpdateLectureRequestDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("errors", bindingResult)
            } else {
                lecturesService.updateById(lectureId, lecture)
            }
            return "redirect:/app/users/$userId/lectures"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update lecture")
        }
    }
}