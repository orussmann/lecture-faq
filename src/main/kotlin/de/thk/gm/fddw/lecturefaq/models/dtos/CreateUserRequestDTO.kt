package de.thk.gm.fddw.lecturefaq.models.dtos

data class CreateUserRequestDTO(
    val email: String,
    val firstName: String,
    val lastName: String
)