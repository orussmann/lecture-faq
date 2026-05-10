package de.thk.gm.fddw.lecturefaq.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import de.thk.gm.fddw.lecturefaq.models.enums.Role
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import java.util.*

//TODO: Consider having a bidirectional 1-n-relationship
// TODO: Utility methods for add() and remove(), for Users and Lectures
@Entity
@Table(name = "Lecture_User")
class User(
    //TODO: Consider additionally doing validation on the persistence layer
    // -> counter errors on the service layer
    @Id
    @Column(name = "user_id", nullable = false)
    val id: UUID = UUID.randomUUID(),

    @field:Email
    @Column(name = "email", nullable = false, unique = true)
    var email: String = "",

    @Column(name = "first_name", nullable = false)
    var firstName: String = "",

    @Column(name = "last_name", nullable = false)
    var lastName: String = "",

    @Column
    var role: Role = Role.STUDENT,

    @Column
    var password: String = "",

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val polls: MutableList<Poll> = mutableListOf(),

    @ManyToMany
    @JoinTable(
        name = "user_attends_lecture",
        joinColumns = [JoinColumn(name = "user_id_fk")],
        inverseJoinColumns = [JoinColumn(name = "lecture_id_fk")]
    )
    //TODO: Change to Set (likewise for User) (https://thorben-janssen.com/ultimate-guide-association-mappings-jpa-hibernate/#manyToMany)
    @JsonIgnoreProperties("users")
    val lectures: MutableList<Lecture> = mutableListOf(),

    @ElementCollection
    var subscriptions: MutableList<UUID> = mutableListOf(),

    @Column
    var lastVisited: Date = Date()
) {
    //TODO: Is this an atomic operation?
    fun addLecture(lecture: Lecture) {
        lectures.add(lecture)
        lecture.users.add(this)
    }

    fun removeLecture(lecture: Lecture) {
        lectures.remove(lecture)
        lecture.users.remove(this)
    }
}