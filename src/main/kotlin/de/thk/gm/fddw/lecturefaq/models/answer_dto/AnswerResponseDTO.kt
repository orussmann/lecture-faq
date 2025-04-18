package de.thk.gm.fddw.lecturefaq.models.answer_dto

import java.util.UUID

data class AnswerResponseDTO(
    val id: UUID,
    val pollId: UUID,
    val text: String,
    val count: Short
)
