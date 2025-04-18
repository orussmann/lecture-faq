package de.thk.gm.fddw.lecturefaq.models.answer_dto

import java.util.UUID

data class CreateAnswerRequestDTO(
    val pollId: UUID? = null,
    val text: String
)