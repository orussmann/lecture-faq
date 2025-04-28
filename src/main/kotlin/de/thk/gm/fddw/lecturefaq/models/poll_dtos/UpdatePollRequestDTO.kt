package de.thk.gm.fddw.lecturefaq.models.poll_dtos

import de.thk.gm.fddw.lecturefaq.constants.MAXIMUM_DESCRIPTION_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MAXIMUM_TITLE_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_DESCRIPTION_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_TITLE_LENGTH
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class UpdatePollRequestDTO(
    @field:NotBlank
    @field:Size(min = MINIMUM_TITLE_LENGTH, max = MAXIMUM_TITLE_LENGTH)
    val title: String?,

    @field:NotBlank
    @field:Size(min = MINIMUM_DESCRIPTION_LENGTH, max = MAXIMUM_DESCRIPTION_LENGTH)
    val description: String?,
)
