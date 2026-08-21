package de.thk.gm.fddw.lecturefaq.initializers

import de.thk.gm.fddw.lecturefaq.constants.DUMMY_USER_ID
import de.thk.gm.fddw.lecturefaq.models.User
import de.thk.gm.fddw.lecturefaq.models.enums.Role
import de.thk.gm.fddw.lecturefaq.models.user_dtos.CreateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.repositories.UsersRepository
import de.thk.gm.fddw.lecturefaq.services.UsersService
import de.thk.gm.fddw.lecturefaq.util.UsersDTOMapper
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserInitializer(
    private val usersService: UsersService,
    private val usersRepository: UsersRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {

        val dummyId = UUID.fromString(DUMMY_USER_ID)

        if (!usersRepository.existsById(dummyId)) {
            usersRepository.save(
                User(
                    id = dummyId,
                    email = "dummy@lecturefaq.com",
                    firstName = "Anonymer",
                    lastName = "Benutzer",
                    role = Role.STUDENT
                )
            )
        }
    }
}