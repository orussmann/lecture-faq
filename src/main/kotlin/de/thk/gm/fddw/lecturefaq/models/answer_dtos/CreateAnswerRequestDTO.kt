package de.thk.gm.fddw.lecturefaq.models.answer_dtos

import de.thk.gm.fddw.lecturefaq.constants.MAXIMUM_TEXT_LENGTH
import jakarta.validation.constraints.Size
import java.util.*

class CreateAnswerRequestDTO {
    var pollId: UUID? = null

    @field:Size(max = MAXIMUM_TEXT_LENGTH)
    var text: String = ""
}