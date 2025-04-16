package de.thk.gm.fddw.lecturefaq.models.questions_dto

import java.util.UUID

data class QuestionResponseDTO(
    val id: UUID,
    val lectureId: UUID,
    val userId: UUID,
    val text: String
)
