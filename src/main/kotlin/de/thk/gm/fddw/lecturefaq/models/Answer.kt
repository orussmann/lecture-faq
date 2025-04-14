package de.thk.gm.fddw.lecturefaq.models

import jakarta.persistence.*
import java.util.*

@Entity
data class Answer(
    @Id
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "poll_id", nullable = false)
    val poll: Poll,

    @Column(nullable = false)
    val text: String,

    @Column(nullable = false)
    val count: Short = 0
)
