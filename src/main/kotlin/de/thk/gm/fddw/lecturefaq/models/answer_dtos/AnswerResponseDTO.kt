package de.thk.gm.fddw.lecturefaq.models.answer_dtos

import java.util.UUID

class AnswerResponseDTO(
    val id: UUID,
    val pollId: UUID,
    val text: String,
    val count: Short
)
