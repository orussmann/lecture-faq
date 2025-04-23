package de.thk.gm.fddw.lecturefaq.models.poll_dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class UpdatePollRequestDTO(
    @field:NotBlank
    @field:Size(min = 1, max = 100)
    val title: String?,

    @field:NotBlank
    @field:Size(min = 1, max = 1_000)
    val description: String?,
)
