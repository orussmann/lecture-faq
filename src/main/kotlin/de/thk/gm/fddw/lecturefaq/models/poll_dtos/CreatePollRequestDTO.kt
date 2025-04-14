package de.thk.gm.fddw.lecturefaq.models.poll_dtos

import java.util.*

data class CreatePollRequestDTO(
    val userId: UUID,
    val title: String,
    val description: String
)