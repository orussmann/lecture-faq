package de.thk.gm.fddw.lecturefaq.models.user_dtos

data class UpdateUserRequestDTO(
    val email: String?,
    val firstName: String?,
    val lastName: String?
)