package de.thk.gm.fddw.lecturefaq.models

import de.thk.gm.fddw.lecturefaq.constants.Type
import jakarta.persistence.*
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

    @OneToMany(mappedBy = "lecture", cascade = [CascadeType.ALL], orphanRemoval = true)
    val questions: MutableList<Question> = mutableListOf(),

    @Column(nullable = false)
    val code: Short
)
