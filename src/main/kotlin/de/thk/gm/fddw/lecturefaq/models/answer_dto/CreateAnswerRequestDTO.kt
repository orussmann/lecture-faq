package de.thk.gm.fddw.lecturefaq.models.answer_dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID

class CreateAnswerRequestDTO(
    val pollId: UUID? = null,

    @field:NotNull
    @field:NotBlank
    @field:Size(max = 100)
    val text: String = ""
)