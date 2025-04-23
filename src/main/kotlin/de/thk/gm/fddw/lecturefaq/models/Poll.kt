package de.thk.gm.fddw.lecturefaq.models

import jakarta.persistence.*
import java.util.UUID

@Entity
class Poll(
    @Id
    @Column(name = "poll_id", nullable = false)
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @OneToMany(mappedBy = "poll", cascade = [CascadeType.ALL], orphanRemoval = true)
    var answers: MutableList<Answer> = mutableListOf(),

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var description: String
)
