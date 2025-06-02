package de.thk.gm.fddw.lecturefaq.models.lecture_dtos

import de.thk.gm.fddw.lecturefaq.models.User
import de.thk.gm.fddw.lecturefaq.models.enums.Type
import java.util.*

class LectureResponseDTO(
    val id: UUID,
    val title: String,
    val description: String,
    val type: Type,
    val link: String,
    val users: List<User>,
    val code: Short
)
