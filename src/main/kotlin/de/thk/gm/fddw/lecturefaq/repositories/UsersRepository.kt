package de.thk.gm.fddw.lecturefaq.repositories

import de.thk.gm.fddw.lecturefaq.models.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface UsersRepository: CrudRepository<User, java.util.UUID> {
    fun findByEmail(email: String): User?
}
