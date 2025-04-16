package de.thk.gm.fddw.lecturefaq.models.questions_dto

import java.util.*

data class UpdateQuestionRequestDTO(
    val lectureId: UUID?,
    val userId: UUID?,
    val text: String?
)
