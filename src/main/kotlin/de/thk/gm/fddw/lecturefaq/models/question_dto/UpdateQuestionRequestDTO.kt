package de.thk.gm.fddw.lecturefaq.models.question_dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.*

class UpdateQuestionRequestDTO(
    val lectureId: UUID?,
    val userId: UUID?,

    @field:NotBlank
    @field:Size(min = 1, max = 100)
    val text: String?
)
