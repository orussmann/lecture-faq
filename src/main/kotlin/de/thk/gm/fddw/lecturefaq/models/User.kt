package de.thk.gm.fddw.lecturefaq.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.util.*
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Email

@Entity
@Table(name = "Lecture_User")
data class User(

    @Id
    @Column(name = "user_id", nullable = false)
    val id: UUID = UUID.randomUUID(),

    @field:Email
    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "first_name", nullable = false)
    val firstName: String,

    @Column(name = "last_name", nullable = false)
    val lastName: String
)
