package de.thk.gm.fddw.lecturefaq.util

import de.thk.gm.fddw.lecturefaq.models.Poll
import de.thk.gm.fddw.lecturefaq.models.User
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.CreatePollRequestDTO
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.PollResponseDTO
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.UpdatePollRequestDTO
import org.springframework.stereotype.Component

@Component
class PollsDTOMapper {

    fun mapToPollResponse(poll: Poll): PollResponseDTO {
        return PollResponseDTO(
            id = poll.id,
            userId = poll.user.id,
            title = poll.title,
            description = poll.description
        )
    }

    fun mapToNewPoll(poll: CreatePollRequestDTO, user: User): Poll {
        return Poll(
            user = user,
            title = poll.title,
            description = poll.description
        )
    }

    fun mapToUpdatedPoll(
        updatePollRequestDTO: UpdatePollRequestDTO,
        poll: Poll
    ): Poll {
        poll.title = updatePollRequestDTO.title ?: poll.title
        poll.description = updatePollRequestDTO.description ?: poll.description
        return poll
    }
}