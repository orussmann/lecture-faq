package de.thk.gm.fddw.lecturefaq.services

import de.thk.gm.fddw.lecturefaq.models.question_dtos.CreateQuestionRequestDTO
import de.thk.gm.fddw.lecturefaq.models.question_dtos.QuestionResponseDTO
import de.thk.gm.fddw.lecturefaq.models.question_dtos.UpdateQuestionRequestDTO
import java.util.*


interface QuestionsService {
    fun save(question: CreateQuestionRequestDTO): QuestionResponseDTO
    fun findAll(): MutableIterable<QuestionResponseDTO>
    fun findById(questionId: UUID): QuestionResponseDTO
    fun findAllByUserId(userId: UUID): List<QuestionResponseDTO>
    fun findAllByLectureId(lectureId: UUID): List<QuestionResponseDTO>
    fun removeById(questionId: UUID)
    fun updateById(questionId: UUID, questionDTO: UpdateQuestionRequestDTO): QuestionResponseDTO
    fun findAllByLectureIdOrderByCreatedAt(lectureId: UUID): List<QuestionResponseDTO>
    fun updateLikes(questionId: UUID, userId: UUID, liked: Boolean): Int
    fun findLikedQuestionIds(lectureId: UUID, userId: UUID): Set<UUID>
}