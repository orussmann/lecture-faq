package de.thk.gm.fddw.lecturefaq.services

import de.thk.gm.fddw.lecturefaq.models.poll_dtos.CreatePollRequestDTO
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.PollResponseDTO
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.UpdatePollRequestDTO
import de.thk.gm.fddw.lecturefaq.repositories.PollsRepository
import de.thk.gm.fddw.lecturefaq.repositories.UsersRepository
import de.thk.gm.fddw.lecturefaq.util.AnswersDTOMapper
import de.thk.gm.fddw.lecturefaq.util.PollsDTOMapper
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

@Service
class PollsServiceImpl(
    private val pollsRepository: PollsRepository,
    private val pollsDTOMapper: PollsDTOMapper,
    private val usersRepository: UsersRepository,
    private val answersDTOMapper: AnswersDTOMapper
) : PollsService {
    override fun save(poll: CreatePollRequestDTO): PollResponseDTO {
        val user = usersRepository.findById(poll.userId)
            .orElseThrow { NoSuchElementException("User for this poll not found") }
        val newPoll = pollsDTOMapper.mapToNewPoll(poll, user)
        val answers = poll.answers.map { answer -> answersDTOMapper.mapToNewAnswer(answer, newPoll) }
        newPoll.answers.addAll(answers)
        val savedPoll = pollsRepository.save(newPoll)
        return pollsDTOMapper.mapToPollResponse(savedPoll)
    }

    override fun findAll(): MutableIterable<PollResponseDTO> {
        val savedPolls = pollsRepository.findAll()
        return savedPolls
            .map(pollsDTOMapper::mapToPollResponse)
            .toMutableList()
    }

    override fun findById(pollId: UUID): PollResponseDTO {
        val foundPoll = pollsRepository
            .findById(pollId)
            .orElseThrow { NoSuchElementException("Poll not found") }
        return pollsDTOMapper.mapToPollResponse(foundPoll)
    }

    override fun findAllByUserId(userId: UUID): List<PollResponseDTO> {
        val usersPolls = pollsRepository.findAllByUserId(userId)
        return usersPolls.map(pollsDTOMapper::mapToPollResponse)
    }

    override fun removeById(pollId: UUID) {
        pollsRepository.deleteById(pollId)
    }

    override fun updateById(pollId: UUID, pollDTO: UpdatePollRequestDTO): PollResponseDTO {
        val existingPoll = pollsRepository
            .findById(pollId)
            .orElseThrow {
                NoSuchElementException("Poll not found")
            }
        val updatedPoll = pollsDTOMapper.mapToUpdatedPoll(pollDTO, existingPoll)
        val savedPoll = pollsRepository.save(updatedPoll)
        return pollsDTOMapper.mapToPollResponse(savedPoll)
    }
}