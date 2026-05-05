package de.thk.gm.fddw.lecturefaq.models.user_dtos

import java.util.UUID

// Mapping: UserResponseDTO mit zusätzlichem Feld "subscribed"
data class UserSubscriptionResponseDTO(
    var userId: UUID,
    var firstName: String,
    var lastName: String,
    var subscribed: Boolean
)