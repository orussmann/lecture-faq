package de.thk.gm.fddw.lecturefaq.models.lecture_dtos

import de.thk.gm.fddw.lecturefaq.constants.MAXIMUM_DESCRIPTION_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MAXIMUM_LINK_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MAXIMUM_TITLE_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_DESCRIPTION_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_LINK_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_TITLE_LENGTH
import de.thk.gm.fddw.lecturefaq.models.enums.Type
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size
import java.util.*

class UpdateLectureRequestDTO(
    @field:NotBlank
    @field:Size(min = MINIMUM_TITLE_LENGTH, max = MAXIMUM_TITLE_LENGTH)
    val title: String?,

    @field:NotBlank
    @field:Size(min = MINIMUM_DESCRIPTION_LENGTH, max = MAXIMUM_DESCRIPTION_LENGTH)
    val description: String?,

    val type: Type?,

    @field:Size(min = MINIMUM_LINK_LENGTH, max = MAXIMUM_LINK_LENGTH)
    @field:Pattern(regexp = "^https?://(?:www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b[-a-zA-Z0-9()@:%_+.~#?&/=]*$")
    val link: String?,

    val userId: UUID?,

    @PositiveOrZero
    val code: Short?
)