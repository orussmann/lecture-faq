package de.thk.gm.fddw.lecturefaq.models

import de.thk.gm.fddw.lecturefaq.constants.Role
import jakarta.persistence.*
import java.util.*
import jakarta.validation.constraints.Email

//TODO: Consider having a bidirectional 1-n-relationship
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
    val lastName: String,

    @Column
    val role: Role,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val polls: MutableList<Poll> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val lectures: MutableList<Lecture> = mutableListOf()
)
