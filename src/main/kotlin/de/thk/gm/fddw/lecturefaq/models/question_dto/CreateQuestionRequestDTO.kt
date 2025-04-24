package de.thk.gm.fddw.lecturefaq.models.question_dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent
import jakarta.validation.constraints.Size
import org.springframework.format.annotation.DateTimeFormat
import java.util.*

class CreateQuestionRequestDTO(
    @field:NotNull
    val lectureId: UUID,

    @field:NotNull
    val userId: UUID,

    @field:NotNull
    @field:NotBlank
    @field:Size(min = 1, max = 100)
    val text: String,

    @field:NotNull
    @field:DateTimeFormat
    @field:PastOrPresent
    val createdAt: Date = Date(),

    var chatUserName: String = ""
)