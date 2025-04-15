package de.thk.gm.fddw.lecturefaq.models

import de.thk.gm.fddw.lecturefaq.constants.Type
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.util.*

@Entity
data class Lecture(
    @Id
    @Column
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val type: Type,

    @Column(nullable = false)
    val link: String,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false)
    val code: Short
)
