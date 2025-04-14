package de.thk.gm.fddw.lecturefaq.services

import de.thk.gm.fddw.lecturefaq.models.answers_dto.AnswerResponseDTO
import de.thk.gm.fddw.lecturefaq.models.answers_dto.CreateAnswerRequestDTO
import de.thk.gm.fddw.lecturefaq.models.answers_dto.UpdateAnswerRequestDTO
import java.util.*

interface AnswersService {
    fun save(answer: CreateAnswerRequestDTO): AnswerResponseDTO
    fun findAll(): MutableIterable<AnswerResponseDTO>
    fun findById(answerId: UUID): AnswerResponseDTO
    fun findAllByPollId(pollId: UUID): List<AnswerResponseDTO>
    fun removeById(answerId: UUID)
    fun updateById(answerId: UUID, answerDTO: UpdateAnswerRequestDTO): AnswerResponseDTO

}