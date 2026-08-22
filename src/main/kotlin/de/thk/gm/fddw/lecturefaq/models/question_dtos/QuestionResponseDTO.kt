package de.thk.gm.fddw.lecturefaq.models.question_dtos

import java.util.*

class QuestionResponseDTO(
    val id: UUID,
    val lectureId: UUID,
    val userId: UUID,
    val text: String,
    val createdAt: Date,
    val chatUserName: String,
    val likesCount: Int
)
