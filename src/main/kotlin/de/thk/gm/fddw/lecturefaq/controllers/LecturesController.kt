package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.constants.Type
import de.thk.gm.fddw.lecturefaq.models.lecture_dtos.CreateLectureRequestDTO
import de.thk.gm.fddw.lecturefaq.models.lecture_dtos.UpdateLectureRequestDTO
import de.thk.gm.fddw.lecturefaq.services.LecturesService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Controller
@RequestMapping(produces = [MediaType.TEXT_HTML_VALUE])
class LecturesController(private val lecturesService: LecturesService) {

    @GetMapping("/users/{userId}/lectures/{lectureId}")
    @ResponseStatus(HttpStatus.OK)
    fun getLecture(
        @PathVariable userId: UUID,
        @PathVariable lectureId: UUID,
        model: Model
    ): String {
        try {
            val lecture = lecturesService.findById(lectureId)
            model.addAttribute("lecture", lecture)
            return "lectures/showLecture"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch lecture")
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

    @GetMapping("/users/{userId}/lectures")
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
    }

    @PostMapping("/users/{userId}/lectures")
    fun createLecture(
        @PathVariable userId: UUID,
        @RequestParam title: String,
        @RequestParam description: String,
        @RequestParam type: Type,
        @RequestParam link: String,
        @RequestParam code: Short
    ): String {
        try {
            val lecture = CreateLectureRequestDTO(title, description, type, link, userId, code)
            lecturesService.save(lecture)
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
        @RequestParam title: String,
        @RequestParam description: String,
        @RequestParam type: Type,
        @RequestParam link: String,
        @RequestParam code: Short
    ): String {
        try {
            val lecture = UpdateLectureRequestDTO(
                title = title,
                description = description,
                type = type,
                link = link,
                code = code,
                userId = userId
            )
            lecturesService.updateById(lectureId, lecture)
            return "redirect:/app/users/$userId/lectures"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update lecture")
        }
    }
}