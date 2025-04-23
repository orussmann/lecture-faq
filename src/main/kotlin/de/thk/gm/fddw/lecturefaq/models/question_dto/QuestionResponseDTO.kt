package de.thk.gm.fddw.lecturefaq.models.question_dto

import java.util.UUID

class QuestionResponseDTO(
    val id: UUID,
    val lectureId: UUID,
    val userId: UUID,
    val text: String
)
