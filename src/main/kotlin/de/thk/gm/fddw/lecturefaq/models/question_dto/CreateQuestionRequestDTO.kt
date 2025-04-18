package de.thk.gm.fddw.lecturefaq.models.question_dto

import java.util.*

data class CreateQuestionRequestDTO(
    val lectureId: UUID,
    val userId: UUID,
    val text: String
)
