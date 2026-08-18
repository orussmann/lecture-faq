package de.thk.gm.fddw.lecturefaq.models.lecture_dtos

import de.thk.gm.fddw.lecturefaq.constants.MAXIMUM_CODE_SIZE
import de.thk.gm.fddw.lecturefaq.constants.MAXIMUM_DESCRIPTION_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MAXIMUM_LINK_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MAXIMUM_TITLE_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_CODE_SIZE
import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_DESCRIPTION_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_LINK_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_TITLE_LENGTH
import de.thk.gm.fddw.lecturefaq.models.enums.Type
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

class CreateLectureRequestDTO(
    @field:NotNull
    @field:NotBlank
    @field:Size(
        message = "The title length must be greater at least $MINIMUM_TITLE_LENGTH and at most $MAXIMUM_TITLE_LENGTH",
        min = MINIMUM_TITLE_LENGTH,
        max = MAXIMUM_TITLE_LENGTH
    )
    val title: String,

    @field:NotNull
    @field:NotBlank
    @field:Size(
        message = "The description length must be at least $MINIMUM_DESCRIPTION_LENGTH and at most $MAXIMUM_DESCRIPTION_LENGTH",
        min = MINIMUM_DESCRIPTION_LENGTH,
        max = MAXIMUM_DESCRIPTION_LENGTH
    )
    val description: String,

    @field:NotNull
    val type: Type,

    @field:NotNull
    @field:NotBlank
    @field:Size(
        message = "The email length is invalid.",
        min = MINIMUM_LINK_LENGTH,
        max = MAXIMUM_LINK_LENGTH
    )   //TODO: Protocol can be optional(?)
    @field:Pattern(
        regexp = "^https?://(?:www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b[-a-zA-Z0-9()@:%_+.~#?&/=]*$",
        message = "You must provide a valid URL, like 'https://google.com'"
    )
    val link: String,

    @field:Min(value = MINIMUM_CODE_SIZE.toLong(), message = "The code value must be at least $MINIMUM_CODE_SIZE")
    @field:Max(value = MAXIMUM_CODE_SIZE.toLong(), message = "The code value must be at most $MAXIMUM_CODE_SIZE")
    val code: Short
)
