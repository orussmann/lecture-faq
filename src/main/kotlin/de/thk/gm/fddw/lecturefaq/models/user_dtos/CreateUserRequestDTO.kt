package de.thk.gm.fddw.lecturefaq.models.user_dtos

data class CreateUserRequestDTO(
    val email: String,
    val firstName: String,
    val lastName: String
)