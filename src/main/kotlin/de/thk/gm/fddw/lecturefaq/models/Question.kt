package de.thk.gm.fddw.lecturefaq.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.UniqueConstraint
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
    var chatUserName: String = "",

    @ManyToMany
    @JoinTable(
        name = "question_likes",
        joinColumns = [JoinColumn(name = "question_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")],
        uniqueConstraints = [
            UniqueConstraint(columnNames = ["question_id", "user_id"])
        ]
    )
    var likedBy: MutableSet<User> = mutableSetOf()
)
