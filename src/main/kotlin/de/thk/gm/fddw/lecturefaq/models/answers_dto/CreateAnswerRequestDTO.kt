package de.thk.gm.fddw.lecturefaq.models.answers_dto

import java.util.UUID

data class CreateAnswerRequestDTO(
    val pollId: UUID?,
    val text: String
)