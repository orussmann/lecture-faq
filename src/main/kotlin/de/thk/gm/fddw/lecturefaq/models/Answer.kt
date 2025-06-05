package de.thk.gm.fddw.lecturefaq.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import java.util.*

@Entity
class Answer(
    @Id
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "poll_id", nullable = false)
    @JsonIgnoreProperties("answers")
    val poll: Poll,

    @Column(nullable = false)
    var text: String,

    @Column(nullable = false)
    var count: Short = 0
)
