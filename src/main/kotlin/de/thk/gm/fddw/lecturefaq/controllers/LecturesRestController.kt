package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.models.lecture_dtos.CreateLectureRequestDTO
import de.thk.gm.fddw.lecturefaq.models.lecture_dtos.LectureResponseDTO
import de.thk.gm.fddw.lecturefaq.models.lecture_dtos.UpdateLectureRequestDTO
import de.thk.gm.fddw.lecturefaq.services.LecturesService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/v1")
class LecturesRestController(private val lecturesService: LecturesService) {


    @GetMapping("/users/{userId}/lectures/{lectureId}")
    @ResponseStatus(HttpStatus.OK)
    fun getLecture(
        @PathVariable userId: UUID,
        @PathVariable lectureId: UUID
    ): LectureResponseDTO {
        try {
            val lecture = lecturesService.findById(lectureId)
            return lecture
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch lecture")
        }
    }

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

    //TODO: GET mapping for one lecture

    @GetMapping("/users/{userId}/lectures")
    @ResponseStatus(HttpStatus.OK)
    fun getAllLecturesFromUser(@PathVariable userId: UUID): List<LectureResponseDTO> {
        try {
            val lectures = lecturesService.findAllByUserId(userId)
            return lectures
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch lectures")
        }
    }

    @PostMapping("/users/{userId}/lectures")
    @ResponseStatus(HttpStatus.CREATED)
    fun createLecture(
        @PathVariable userId: UUID,
        @Valid @RequestBody lectureDTO: CreateLectureRequestDTO
    ): LectureResponseDTO {
        try {
            return lecturesService.save(lectureDTO)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create lecture")
        }
    }

    @DeleteMapping("/users/{userId}/lectures/{lectureId}")   //TODO: Handln, wenn nichts gelöscht wird
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteLecture(
        @PathVariable lectureId: UUID,
        @PathVariable userId: UUID
    ) {
        try {
            lecturesService.removeById(lectureId, userId)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete lecture")
        }
    }

    @PutMapping("/users/{userId}/lectures/{lectureId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateLecture(
        @PathVariable userId: UUID,
        @PathVariable lectureId: UUID,
        @Valid @RequestBody updatedLectureDTO: UpdateLectureRequestDTO
    ): LectureResponseDTO {
        try {
            return lecturesService.updateById(lectureId, userId, updatedLectureDTO)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update lecture")
        }
    }

}