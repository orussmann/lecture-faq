package de.thk.gm.fddw.lecturefaq.models

import jakarta.persistence.*
import java.util.UUID

@Entity
data class Poll(
    @Id
    @Column(name = "poll_id", nullable = false)
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @OneToMany(mappedBy = "poll", cascade = [CascadeType.ALL], orphanRemoval = true)
    val answers: MutableList<Answer> = mutableListOf(),

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val description: String
)
