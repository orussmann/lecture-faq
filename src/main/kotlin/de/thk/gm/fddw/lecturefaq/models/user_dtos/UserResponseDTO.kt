package de.thk.gm.fddw.lecturefaq.models.user_dtos

import java.util.*

data class UserResponseDTO(
    val userId: UUID,
    val email: String,
    val firstName: String,
    val lastName: String
)