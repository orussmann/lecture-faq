package de.thk.gm.fddw.lecturefaq.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.util.UUID

@Entity
data class Question(
    @Id
    @Column
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "lecture_id", nullable = false)
    val lecture: Lecture,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false)
    val text: String
)
