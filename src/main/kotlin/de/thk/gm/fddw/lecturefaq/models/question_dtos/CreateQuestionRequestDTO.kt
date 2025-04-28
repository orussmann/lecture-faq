package de.thk.gm.fddw.lecturefaq.models.question_dtos

import de.thk.gm.fddw.lecturefaq.constants.MAXIMUM_NAME_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MAXIMUM_TEXT_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_NAME_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_TEXT_LENGTH
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
    @field:Size(min = MINIMUM_TEXT_LENGTH, max = MAXIMUM_TEXT_LENGTH)
    val text: String,

    @field:NotNull
    @field:DateTimeFormat
    @field:PastOrPresent
    val createdAt: Date = Date(),

    @field:NotBlank
    @field:Size(min = MINIMUM_NAME_LENGTH, max = MAXIMUM_NAME_LENGTH)
    var chatUserName: String = ""
)