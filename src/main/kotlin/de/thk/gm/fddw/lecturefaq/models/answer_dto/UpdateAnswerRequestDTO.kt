package de.thk.gm.fddw.lecturefaq.models.answer_dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size

class UpdateAnswerRequestDTO(
    @field:NotBlank
    @field:Size(min = 1, max = 1_000)
    val text: String?,
    @field:PositiveOrZero
    val count: Short?
)
