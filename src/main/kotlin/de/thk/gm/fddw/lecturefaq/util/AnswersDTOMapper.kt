package de.thk.gm.fddw.lecturefaq.util

import de.thk.gm.fddw.lecturefaq.models.Answer
import de.thk.gm.fddw.lecturefaq.models.Poll
import de.thk.gm.fddw.lecturefaq.models.answers_dto.AnswerResponseDTO
import de.thk.gm.fddw.lecturefaq.models.answers_dto.CreateAnswerRequestDTO
import de.thk.gm.fddw.lecturefaq.models.answers_dto.UpdateAnswerRequestDTO
import org.springframework.stereotype.Component

@Component
class AnswersDTOMapper {

    fun mapToAnswerResponse(answer: Answer): AnswerResponseDTO {
        return AnswerResponseDTO(
            id = answer.id,
            pollId = answer.poll.id,
            text = answer.text,
            count = answer.count
        )
    }

    fun mapToNewAnswer(answer: CreateAnswerRequestDTO, poll: Poll): Answer {
        return Answer(
            poll = poll,
            text = answer.text
        )
    }

    fun updateAnswerFromTo(updateAnswerRequestDTO: UpdateAnswerRequestDTO, answer: Answer): Answer {
        return answer.copy(
            text = updateAnswerRequestDTO.text ?: answer.text,
            count = updateAnswerRequestDTO.count ?: answer.count
        )
    }
}