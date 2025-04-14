package de.thk.gm.fddw.lecturefaq.models.user_dtos

import de.thk.gm.fddw.lecturefaq.constants.Role

data class CreateUserRequestDTO(
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: Role
)