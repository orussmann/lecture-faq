package de.thk.gm.fddw.lecturefaq.models.lectures_dtos

import de.thk.gm.fddw.lecturefaq.constants.Type
import java.util.*

data class CreateLectureRequestDTO(
    val title: String,
    val description: String,
    val type: Type,
    val link: String,
    val userId: UUID,
    val code: Short
)
