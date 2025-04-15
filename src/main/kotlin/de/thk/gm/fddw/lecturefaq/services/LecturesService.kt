package de.thk.gm.fddw.lecturefaq.services

import de.thk.gm.fddw.lecturefaq.models.answers_dto.AnswerResponseDTO
import de.thk.gm.fddw.lecturefaq.models.answers_dto.CreateAnswerRequestDTO
import de.thk.gm.fddw.lecturefaq.models.answers_dto.UpdateAnswerRequestDTO
import de.thk.gm.fddw.lecturefaq.models.lectures_dtos.CreateLectureRequestDTO
import de.thk.gm.fddw.lecturefaq.models.lectures_dtos.LectureResponseDTO
import de.thk.gm.fddw.lecturefaq.models.lectures_dtos.UpdateLectureRequestDTO
import java.util.*

interface LecturesService {
    fun save(lecture: CreateLectureRequestDTO): LectureResponseDTO
    fun findAll(): MutableIterable<LectureResponseDTO>
    fun findById(lectureId: UUID): LectureResponseDTO
    fun findAllByUserId(userId: UUID): List<LectureResponseDTO>
    fun removeById(lectureId: UUID)
    fun updateById(lectureId: UUID, lectureDTO: UpdateLectureRequestDTO): LectureResponseDTO
}