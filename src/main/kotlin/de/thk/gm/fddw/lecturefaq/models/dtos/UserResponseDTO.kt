package de.thk.gm.fddw.lecturefaq.models.dtos

import java.util.*

data class UserResponseDTO(
    val userId: UUID,
    val email: String,
    val firstName: String,
    val lastName: String
)