package de.thk.gm.fddw.lecturefaq.repositories

import de.thk.gm.fddw.lecturefaq.models.Poll
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PollsRepository : CrudRepository<Poll, UUID> {
    fun findAllByUserId(userId: UUID): List<Poll>
    fun findByUserId(userId: UUID): Poll
}