package de.thk.gm.fddw.lecturefaq.repositories

import de.thk.gm.fddw.lecturefaq.models.Question
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface QuestionsRepository: CrudRepository<Question, UUID> {
    fun findAllByUserId(userId: UUID): List<Question>
    fun findAllByLectureId(lectureId: UUID): List<Question>
}