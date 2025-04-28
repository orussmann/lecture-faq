package de.thk.gm.fddw.lecturefaq.services

import de.thk.gm.fddw.lecturefaq.models.Answer
import de.thk.gm.fddw.lecturefaq.models.Poll
import de.thk.gm.fddw.lecturefaq.models.answer_dtos.AnswerResponseDTO
import de.thk.gm.fddw.lecturefaq.models.answer_dtos.CreateAnswerRequestDTO
import de.thk.gm.fddw.lecturefaq.models.answer_dtos.UpdateAnswerRequestDTO
import de.thk.gm.fddw.lecturefaq.repositories.AnswersRepository
import de.thk.gm.fddw.lecturefaq.repositories.PollsRepository
import de.thk.gm.fddw.lecturefaq.util.AnswersDTOMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AnswersServiceImpl(
    private val pollsRepository: PollsRepository,
    private val answersRepository: AnswersRepository,
    private val answersDTOMapper: AnswersDTOMapper
) : AnswersService {
    @Transactional
    override fun save(answer: CreateAnswerRequestDTO): AnswerResponseDTO {
        val poll = pollsRepository.findById(answer.pollId!!)
            .orElseThrow { NoSuchElementException("Poll for this answer not found") }
        val newAnswer = answersDTOMapper.mapToNewAnswer(answer, poll)
        val savedAnswer = answersRepository.save(newAnswer)
        return answersDTOMapper.mapToAnswerResponse(savedAnswer)
    }

    override fun findAll(): MutableIterable<AnswerResponseDTO> {
        val savedAnswers = answersRepository.findAll()
        return savedAnswers
            .map(answersDTOMapper::mapToAnswerResponse)
            .toMutableList()
    }

    override fun findById(answerId: UUID): AnswerResponseDTO {
        val foundAnswer = answersRepository
            .findById(answerId)
            .orElseThrow { NoSuchElementException("Answer not found") }
        return answersDTOMapper.mapToAnswerResponse(foundAnswer)
    }

    override fun findAllByPollId(pollId: UUID): List<AnswerResponseDTO> {
        val pollsAnswers = answersRepository.findAllByPollId(pollId)
        return pollsAnswers.map(answersDTOMapper::mapToAnswerResponse)
    }

    override fun removeById(answerId: UUID) {
        answersRepository.deleteById(answerId)
    }

    @Transactional
    override fun updateById(answerId: UUID, answerDTO: UpdateAnswerRequestDTO): AnswerResponseDTO {
        val existingAnswer = answersRepository
            .findById(answerId)
            .orElseThrow { NoSuchElementException("Answer not found") }
        val updatedAnswer = answersDTOMapper.mapToUpdatedAnswer(answerDTO, existingAnswer)
        val savedAnswer = answersRepository.save(updatedAnswer)
        return answersDTOMapper.mapToAnswerResponse(savedAnswer)
    }
}