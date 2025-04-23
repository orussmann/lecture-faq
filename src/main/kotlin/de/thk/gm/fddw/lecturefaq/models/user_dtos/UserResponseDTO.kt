package de.thk.gm.fddw.lecturefaq.models.user_dtos

import de.thk.gm.fddw.lecturefaq.constants.Role
import java.util.*

class UserResponseDTO(
    val userId: UUID,
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: Role
)