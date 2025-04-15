package de.thk.gm.fddw.lecturefaq.repositories

import de.thk.gm.fddw.lecturefaq.models.Lecture
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID


@Repository
interface LecturesRepository : CrudRepository<Lecture, UUID> {
    fun findAllByUserId(userId: UUID): List<Lecture>
}