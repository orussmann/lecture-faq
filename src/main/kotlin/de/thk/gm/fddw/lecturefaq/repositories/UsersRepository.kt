package de.thk.gm.fddw.lecturefaq.repositories

import de.thk.gm.fddw.lecturefaq.models.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface UsersRepository: CrudRepository<User, UUID> {

}
