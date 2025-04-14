package de.thk.gm.fddw.lecturefaq.services

import de.thk.gm.fddw.lecturefaq.models.answers_dto.AnswerResponseDTO
import de.thk.gm.fddw.lecturefaq.models.answers_dto.CreateAnswerRequestDTO
import de.thk.gm.fddw.lecturefaq.models.answers_dto.UpdateAnswerRequestDTO
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.CreatePollRequestDTO
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.PollResponseDTO
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.UpdatePollRequestDTO
import java.util.*

interface AnswersService {
    fun save(answer: CreateAnswerRequestDTO): AnswerResponseDTO
    fun findAll(): MutableIterable<AnswerResponseDTO>
    fun findById(answerId: UUID): AnswerResponseDTO
    fun findAllByPollId(pollId: UUID): List<AnswerResponseDTO>
    fun removeById(answerId: UUID)
    fun updateById(answerId: UUID, answerDTO: UpdateAnswerRequestDTO): AnswerResponseDTO

}