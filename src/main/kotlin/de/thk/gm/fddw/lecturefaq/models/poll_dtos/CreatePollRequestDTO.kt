package de.thk.gm.fddw.lecturefaq.models.poll_dtos

import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_ANSWERS_COUNT
import de.thk.gm.fddw.lecturefaq.models.answer_dto.CreateAnswerRequestDTO
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

class CreatePollRequestDTO(
    @field:NotNull
    @field:NotBlank
    @field:Size(min = 1, max = 100)
    val title: String,

    @field:NotNull
    @field:NotBlank
    @field:Size(min = 1, max = 1_000)
    val description: String,

    //@field:Valid
    @field:NotNull
    @field:Size(min = MINIMUM_ANSWERS_COUNT, max = 5)
    var answers: MutableList< /*@Valid*/ /*@NotBlank @Size(max = 10)*/ CreateAnswerRequestDTO> = mutableListOf()
)