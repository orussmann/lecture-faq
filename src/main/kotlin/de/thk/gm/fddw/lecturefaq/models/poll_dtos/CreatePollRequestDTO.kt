package de.thk.gm.fddw.lecturefaq.models.poll_dtos

import de.thk.gm.fddw.lecturefaq.constants.*
import de.thk.gm.fddw.lecturefaq.models.answer_dtos.CreateAnswerRequestDTO
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

class CreatePollRequestDTO {
    @field:NotBlank
    @field:Size(min = MINIMUM_TITLE_LENGTH, max = MAXIMUM_TITLE_LENGTH)
    val title: String = ""


    @field:NotBlank
    @field:Size(min = MINIMUM_DESCRIPTION_LENGTH, max = MAXIMUM_DESCRIPTION_LENGTH)
    val description: String = ""

    @field:NotEmpty(message = "answers must not be empty")
    @field:Size(min = MINIMUM_ANSWERS_COUNT, max = MAXIMUM_ANSWERS_COUNT)
    var answers: MutableList<CreateAnswerRequestDTO> = mutableListOf()
}