package de.thk.gm.fddw.lecturefaq.models.poll_dtos

import de.thk.gm.fddw.lecturefaq.constants.*
import de.thk.gm.fddw.lecturefaq.custoValidation.AnswersConstraint
import de.thk.gm.fddw.lecturefaq.models.answer_dtos.CreateAnswerRequestDTO
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class CreatePollRequestDTO {
    @field:NotBlank
    @field:Size(min = MINIMUM_TITLE_LENGTH, max = MAXIMUM_TITLE_LENGTH)
    var title: String = ""

    @field:NotBlank
    @field:Size(min = MINIMUM_DESCRIPTION_LENGTH, max = MAXIMUM_DESCRIPTION_LENGTH)
    var description: String = ""

    @field:Valid
    @field:AnswersConstraint
    var answers: MutableList<CreateAnswerRequestDTO> = mutableListOf()
}