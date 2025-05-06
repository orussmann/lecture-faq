package de.thk.gm.fddw.lecturefaq.models

import de.thk.gm.fddw.lecturefaq.models.enums.Role
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import java.util.*

//TODO: Consider having a bidirectional 1-n-relationship
@Entity
@Table(name = "Lecture_User")
class User(
    //TODO: Consider additionally doing validation on the persistence layer
    // -> counter errors on the service layer
    @Id
    @Column(name = "user_id", nullable = false)
    val id: UUID = UUID.randomUUID(),

    @field:Email
    @Column(name = "email", nullable = false)
    var email: String = "",

    @Column(name = "first_name", nullable = false)
    var firstName: String = "",

    @Column(name = "last_name", nullable = false)
    var lastName: String = "",

    @Column
    var role: Role = Role.STUDENT,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val polls: MutableList<Poll> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val lectures: MutableList<Lecture> = mutableListOf(),

    @ElementCollection
    var subscriptions: MutableList<UUID> = mutableListOf()
)