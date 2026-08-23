package de.thk.gm.fddw.lecturefaq.models.poll_dtos

import de.thk.gm.fddw.lecturefaq.constants.MAXIMUM_DESCRIPTION_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MAXIMUM_TITLE_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_TITLE_LENGTH
import de.thk.gm.fddw.lecturefaq.customValidation.TwoAnswersMinimum
import de.thk.gm.fddw.lecturefaq.models.answer_dtos.CreateAnswerRequestDTO
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class CreatePollRequestDTO {
    @field:NotBlank
    @field:Size(min = MINIMUM_TITLE_LENGTH, max = MAXIMUM_TITLE_LENGTH)
    var title: String = ""
    
    @field:Size(max = MAXIMUM_DESCRIPTION_LENGTH)
    var description: String = ""

    @field:Valid
    @field:TwoAnswersMinimum
    var answers: MutableList<CreateAnswerRequestDTO> = mutableListOf()
}