package de.thk.gm.fddw.lecturefaq.services

import de.thk.gm.fddw.lecturefaq.models.poll_dtos.CreatePollRequestDTO
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.PollResponseDTO
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.UpdatePollRequestDTO
import java.util.UUID


interface PollsService {
    fun save(poll: CreatePollRequestDTO, userId: UUID): PollResponseDTO
    fun findAll(): MutableIterable<PollResponseDTO>
    fun findById(pollId: UUID): PollResponseDTO
    fun findAllByUserId(userId: UUID): List<PollResponseDTO>
    fun removeById(pollId: UUID)
    fun updateById(pollId: UUID, pollDTO: UpdatePollRequestDTO): PollResponseDTO
}