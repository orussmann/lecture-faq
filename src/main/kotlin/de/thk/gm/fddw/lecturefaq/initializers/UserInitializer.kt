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
    private val usersDTOMapper: UsersDTOMapper,
    private val usersRepository: UsersRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val userLecturer = CreateUserRequestDTO(
            email = "v.n@mail.de",
            firstName = "Viet",
            lastName = "Nguyen",
            role = Role.LECTURER,
            password = "password"
        )
        val userStudent = CreateUserRequestDTO(
            email = "o.r@mail.de",
            firstName = "Oliver",
            lastName = "Russmann",
            role = Role.STUDENT,
            password = "password"
        )

        // Dummy-User-Instanz
        val dummyUser = User(
            id = UUID.fromString(DUMMY_USER_ID),
            email = "dummy@lecturefaq.com",
            firstName = "Anonymer",
            lastName = "Benutzer",
            role = Role.STUDENT  // oder ROLE_ANONYMOUS, falls du eine spezielle Rolle für den Dummy-User verwendest
        )

        //usersService.save(userLecturer)
        //usersService.save(userStudent)
        //usersRepository.save(dummyUser)
    }
}