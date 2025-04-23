package de.thk.gm.fddw.lecturefaq.models

import de.thk.gm.fddw.lecturefaq.constants.Type
import jakarta.persistence.*
import java.util.*

@Entity
class Lecture(
    @Id
    @Column
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var type: Type,

    @Column(nullable = false)
    var link: String,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @OneToMany(mappedBy = "lecture", cascade = [CascadeType.ALL], orphanRemoval = true)
    val questions: MutableList<Question> = mutableListOf(),

    @Column(nullable = false)
    var code: Short
)
