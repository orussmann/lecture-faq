package de.thk.gm.fddw.lecturefaq.repositories

import de.thk.gm.fddw.lecturefaq.models.Lecture
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID


@Repository
interface LecturesRepository : CrudRepository<Lecture, UUID> {
    @Query(
        """
        SELECT l
        FROM Lecture l JOIN l.users u
        WHERE  l.id = :userId
    """
    )
    fun findAllByUserId(@Param("userId") userId: UUID): List<Lecture>
}