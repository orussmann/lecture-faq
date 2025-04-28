package de.thk.gm.fddw.lecturefaq.models.question_dtos

import de.thk.gm.fddw.lecturefaq.constants.MAXIMUM_TEXT_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_TEXT_LENGTH
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.*

class UpdateQuestionRequestDTO(
    val lectureId: UUID?,
    val userId: UUID?,

    @field:NotBlank
    @field:Size(min = MINIMUM_TEXT_LENGTH, max = MAXIMUM_TEXT_LENGTH)
    val text: String?
)
