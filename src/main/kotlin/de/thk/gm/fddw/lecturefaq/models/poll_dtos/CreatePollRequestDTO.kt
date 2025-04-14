package de.thk.gm.fddw.lecturefaq.models.poll_dtos

import de.thk.gm.fddw.lecturefaq.models.answers_dto.CreateAnswerRequestDTO
import java.util.*

data class CreatePollRequestDTO(
    val userId: UUID,
    val title: String,
    val description: String,
    val answers: List<CreateAnswerRequestDTO>
)