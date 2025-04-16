package de.thk.gm.fddw.lecturefaq.services

import de.thk.gm.fddw.lecturefaq.models.questions_dto.CreateQuestionRequestDTO
import de.thk.gm.fddw.lecturefaq.models.questions_dto.QuestionResponseDTO
import de.thk.gm.fddw.lecturefaq.models.questions_dto.UpdateQuestionRequestDTO
import java.util.*


interface QuestionsService {
    fun save(question: CreateQuestionRequestDTO): QuestionResponseDTO
    fun findAll(): MutableIterable<QuestionResponseDTO>
    fun findById(questionId: UUID): QuestionResponseDTO
    fun findAllByUserId(userId: UUID): List<QuestionResponseDTO>
    fun findAllByLectureId(lectureId: UUID): List<QuestionResponseDTO>
    fun removeById(questionId: UUID)
    fun updateById(questionId: UUID, questionDTO: UpdateQuestionRequestDTO): QuestionResponseDTO
}