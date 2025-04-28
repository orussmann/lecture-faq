package de.thk.gm.fddw.lecturefaq.models.answer_dtos

import de.thk.gm.fddw.lecturefaq.constants.MAXIMUM_TEXT_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_TEXT_LENGTH
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size

class UpdateAnswerRequestDTO(
    @field:NotBlank
    @field:Size(min = MINIMUM_TEXT_LENGTH, max = MAXIMUM_TEXT_LENGTH)
    val text: String?,
    @field:PositiveOrZero
    val count: Short?
)
