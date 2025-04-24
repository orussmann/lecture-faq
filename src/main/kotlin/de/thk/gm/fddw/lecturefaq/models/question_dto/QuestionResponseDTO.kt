package de.thk.gm.fddw.lecturefaq.models.question_dto

import java.util.*

class QuestionResponseDTO(
    val id: UUID,
    val lectureId: UUID,
    val userId: UUID,
    val text: String,
    val createdAt: Date,
    val chatUserName: String
)
