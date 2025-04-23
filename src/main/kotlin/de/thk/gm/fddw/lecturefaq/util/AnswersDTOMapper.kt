package de.thk.gm.fddw.lecturefaq.util

import de.thk.gm.fddw.lecturefaq.models.Answer
import de.thk.gm.fddw.lecturefaq.models.Poll
import de.thk.gm.fddw.lecturefaq.models.answer_dto.AnswerResponseDTO
import de.thk.gm.fddw.lecturefaq.models.answer_dto.CreateAnswerRequestDTO
import de.thk.gm.fddw.lecturefaq.models.answer_dto.UpdateAnswerRequestDTO
import org.springframework.stereotype.Component
import java.awt.SystemColor.text

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

    fun mapToUpdatedAnswer(
        updateAnswerRequestDTO: UpdateAnswerRequestDTO,
        answer: Answer
    ): Answer {
        answer.text = updateAnswerRequestDTO.text ?: answer.text
        answer.count = updateAnswerRequestDTO.count ?: answer.count
        return answer
    }
}