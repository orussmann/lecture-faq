package de.thk.gm.fddw.lecturefaq.models.dtos

data class UpdateUserRequestDTO(
    val email: String?,
    val firstName: String?,
    val lastName: String?
)