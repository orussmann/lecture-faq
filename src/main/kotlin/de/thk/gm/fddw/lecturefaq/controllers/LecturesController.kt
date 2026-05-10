package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.constants.DUMMY_USER_ID
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
import java.security.Principal
import java.util.*
import kotlin.NoSuchElementException

@Controller
@RequestMapping(produces = [MediaType.TEXT_HTML_VALUE])
class LecturesController(
    private val lecturesService: LecturesService,
    private val questionsService: QuestionsService,
    private val usersService: UsersService
) {

    @GetMapping("/user/lecturer/lectures/{lectureId}")
    @ResponseStatus(HttpStatus.OK)
    fun getLectureLecturerView(
        principal: Principal,
        @PathVariable lectureId: UUID,
        model: Model
    ): String {
        try {
            val lecture = lecturesService.findById(lectureId)
            val chatMessages = questionsService.findAllByLectureIdOrderByCreatedAt(lecture.id)
            val user = usersService.findByEmail(principal.name) ?: throw NoSuchElementException("User not found")
            val userId = user.id
            val userName = "${user.firstName} ${user.lastName}"
            model.addAttribute("lecture", lecture)
            model.addAttribute("userId", userId)
            model.addAttribute("chatMessages", chatMessages)
            model.addAttribute("userName", userName)
            return "lecturer-view/showLecture"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch lecture")
        }
    }

    @GetMapping("/user/student/lectures/{lectureId}")
    @ResponseStatus(HttpStatus.OK)
    fun getLectureStudentView(
        principal: Principal,
        @PathVariable lectureId: UUID,
        model: Model
    ): String {
        try {
            val lecture = lecturesService.findById(lectureId)
            val chatMessages = questionsService.findAllByLectureIdOrderByCreatedAt(lecture.id)
            val user = usersService.findByEmail(principal.name) ?: throw NoSuchElementException("User not found")
            val userId = user.id
            val userName = "${user.firstName} ${user.lastName}"
            model.addAttribute("lecture", lecture)
            model.addAttribute("userId", userId)
            model.addAttribute("chatMessages", chatMessages)
            model.addAttribute("userName", userName)
            return "student-view/showLecture"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch lecture")
        }
    }

    @GetMapping("/public-lectures/{lectureId}")
    @ResponseStatus(HttpStatus.OK)
    fun getPublicLecture(
        @PathVariable lectureId: UUID,
        model: Model
    ): String {
        try {
            val lecture = lecturesService.findById(lectureId)
            val chatMessages = questionsService.findAllByLectureIdOrderByCreatedAt(lecture.id)
            val userId = usersService.findById(UUID.fromString(DUMMY_USER_ID))
            model.addAttribute("lecture", lecture)
            model.addAttribute("chatMessages", chatMessages)
            model.addAttribute("userId", userId)
            return "public-view/showPublicLecture"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch lecture")
        }
    }

    @GetMapping("/user/notification")
    fun showNotification(
        principal: Principal,
        model: Model
    ): String {
        try {
            val userId = usersService.findByEmail(principal.name)?.id ?: throw NoSuchElementException("User not found")
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


    @GetMapping("/user/student/lectures")
    @ResponseStatus(HttpStatus.OK)
    fun getAllLecturesStudentView(
        principal: Principal,
        model: Model
    ): String {
        try {
//            val lecturers = usersService.findAll().filter { it.role == Role.LECTURER }.map { it.userId }
//            val lecturersForResponse = lectures.filter { it.userId in lecturers }
            val allLectures = lecturesService.findAll()
            val userId = usersService.findByEmail(principal.name)?.id ?: throw NoSuchElementException("User not found")
            model.addAttribute("allLectures", allLectures)
            model.addAttribute("userId", userId)
            val lecturerIds = usersService.findById(userId).subscriptions
            model.addAttribute("lecturerIds", lecturerIds)
            return "student-view/showLectures"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch lectures")
        }
    }

    @GetMapping("/user/lecturer/lectures")
    @ResponseStatus(HttpStatus.OK)
    fun getAllLecturesLecturerView(
        principal: Principal,
        model: Model
    ): String {
        try {
//            val lecturers = usersService.findAll().filter { it.role == Role.LECTURER }.map { it.userId }
//            val lecturersForResponse = lectures.filter { it.userId in lecturers }
            val allLectures = lecturesService.findAll()
            val userId = usersService.findByEmail(principal.name)?.id ?: throw NoSuchElementException("User not found")
            model.addAttribute("allLectures", allLectures)
            model.addAttribute("userId", userId)
            return "lecturer-view/showLectures"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch lectures")
        }
    }


    @GetMapping("/public-lectures")
    @ResponseStatus(HttpStatus.OK)
    fun getAllPublicLectures(
        model: Model
    ): String {
        try {
            val allLectures = lecturesService.findAll()
            model.addAttribute("allLectures", allLectures)
            return "public-view/showPublicLectures"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch lectures")
        }
    }


    @PostMapping("/user/lectures")
    fun createLecture(
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
            return "redirect:/app/user/lectures"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create lecture")
        }
    }

    @DeleteMapping("/user/lectures/{lectureId}")   //TODO: Handln, wenn nichts gelöscht wird
    fun deleteLecture(
        @PathVariable lectureId: UUID,
        principal: Principal
    ): String {
        try {
            val userId = usersService.findByEmail(principal.name)?.id
                ?: throw NoSuchElementException("User not found")
            lecturesService.removeById(lectureId, userId)
            return "redirect:/app/user/lectures"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete lecture")
        }
    }

    @PutMapping("/user/lectures/{lectureId}")
    fun updateLecture(
        principal: Principal,
        @PathVariable lectureId: UUID,
        @Valid lecture: UpdateLectureRequestDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            val userId = usersService.findByEmail(principal.name)?.id ?: throw NoSuchElementException("User not found")
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("errors", bindingResult)
            } else {
                lecturesService.updateById(lectureId, userId, lecture)
            }
            return "redirect:/app/user/lectures"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update lecture")
        }
    }
}