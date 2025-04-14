package de.thk.gm.fddw.lecturefaq.repositories

import de.thk.gm.fddw.lecturefaq.models.Answer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AnswersRepository : CrudRepository<Answer, UUID> {
    fun findAllByPollId(pollId: UUID): List<Answer>
}