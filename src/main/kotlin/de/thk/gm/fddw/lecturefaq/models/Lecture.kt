package de.thk.gm.fddw.lecturefaq.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import de.thk.gm.fddw.lecturefaq.models.enums.Type
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

    @ManyToMany(mappedBy = "lectures")
    @JsonIgnoreProperties("lectures")
    var users: MutableList<User> = mutableListOf(),

    @OneToMany(mappedBy = "lecture", cascade = [CascadeType.ALL], orphanRemoval = true)
    val questions: MutableList<Question> = mutableListOf(),

    @Column(nullable = false)
    var code: Short,

    @Column(nullable = true)    //TODO: Should be false
    var createdAt: Date = Date(),

    @Column(nullable = true)
    var creatorId: UUID
) {
    fun addUser(user: User) {
        users.add(user)
        user.lectures.add(this)
    }

    fun removeUser(user: User) {
        users.remove(user)
        user.lectures.remove(this)
    }
}
