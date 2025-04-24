package de.thk.gm.fddw.lecturefaq.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.util.Date
import java.util.UUID

@Entity
class Question(
    @Id
    @Column
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "lecture_id", nullable = false)
    var lecture: Lecture,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column(nullable = false)
    var text: String,

    @Column(nullable = true)
    var createdAt: Date = Date(),

    @Column(nullable = true)
    var chatUserName: String = ""
)
