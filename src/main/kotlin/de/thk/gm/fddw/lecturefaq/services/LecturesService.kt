package de.thk.gm.fddw.lecturefaq.services

import de.thk.gm.fddw.lecturefaq.models.lecture_dtos.CreateLectureRequestDTO
import de.thk.gm.fddw.lecturefaq.models.lecture_dtos.LectureResponseDTO
import de.thk.gm.fddw.lecturefaq.models.lecture_dtos.UpdateLectureRequestDTO
import java.util.*

interface LecturesService {
    fun save(lecture: CreateLectureRequestDTO): LectureResponseDTO
    fun findAll(): MutableIterable<LectureResponseDTO>
    fun findById(lectureId: UUID): LectureResponseDTO
    fun findAllByUserId(userId: UUID): List<LectureResponseDTO>
    fun removeById(lectureId: UUID, userId: UUID)
    fun updateById(lectureId: UUID, userId: UUID, lectureDTO: UpdateLectureRequestDTO): LectureResponseDTO
}