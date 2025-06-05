package de.thk.gm.fddw.lecturefaq.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import java.util.UUID

@Entity
class Poll(
    @Id
    @Column(name = "poll_id", nullable = false)
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @OneToMany(mappedBy = "poll", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonIgnoreProperties("poll")
    var answers: MutableList<Answer> = mutableListOf(),

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var description: String
)
