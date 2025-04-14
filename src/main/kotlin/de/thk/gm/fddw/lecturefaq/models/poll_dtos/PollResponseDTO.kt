package de.thk.gm.fddw.lecturefaq.models.poll_dtos

import java.util.*

data class PollResponseDTO(
    val id: UUID,
    val userId: UUID,
    val title: String,
    val description: String
)