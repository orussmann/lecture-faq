package de.thk.gm.fddw.lecturefaq.models.lecture_dtos

import de.thk.gm.fddw.lecturefaq.constants.Type
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size
import java.util.*

class UpdateLectureRequestDTO(
    @field:NotBlank
    @field:Size(min = 1, max = 100)
    val title: String?,

    @field:NotBlank
    @field:Size(min = 1, max = 1_000)
    val description: String?,

    val type: Type?,

    @field:Size(min = 1, max = 1_000)
    @field:Pattern(regexp = "^https?://(?:www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b[-a-zA-Z0-9()@:%_+.~#?&/=]*$")
    val link: String?,

    val userId: UUID?,

    @PositiveOrZero
    val code: Short?
)