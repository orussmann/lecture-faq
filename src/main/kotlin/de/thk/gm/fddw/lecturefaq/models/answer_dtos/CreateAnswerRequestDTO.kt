package de.thk.gm.fddw.lecturefaq.models.answer_dtos

import de.thk.gm.fddw.lecturefaq.constants.MAXIMUM_TEXT_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_TEXT_LENGTH
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.*

class CreateAnswerRequestDTO {
    var pollId: UUID? = null

    @field:NotBlank
    @field:Size(min = MINIMUM_TEXT_LENGTH, max = MAXIMUM_TEXT_LENGTH)
    var text: String = ""
}