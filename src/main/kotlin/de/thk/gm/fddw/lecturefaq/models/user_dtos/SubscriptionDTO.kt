package de.thk.gm.fddw.lecturefaq.models.user_dtos

import java.util.UUID

// Mapping: UserResponseDTO mit zusätzlichem Feld "subscribed"
data class SubscriptionDTO(
    var lecturerId: UUID,
    var lecturerFirstName: String,
    var lecturerLastName: String,
    var subscribed: Boolean
)