package de.thk.gm.fddw.lecturefaq.models.user_dtos

import de.thk.gm.fddw.lecturefaq.constants.MAXIMUM_NAME_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MAXIMUM_PASSWORD_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_NAME_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_PASSWORD_LENGTH
import de.thk.gm.fddw.lecturefaq.models.enums.Role
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

class CreateUserRequestDTO(
    @field:Email(message = "You must provide a valid e-mail address, like 'tom123@gmail.com'")
    val email: String = "",

    @field:NotBlank(message = "Input can't be empty or a bunch of whitespace characters")
    @field:Size(min = MINIMUM_NAME_LENGTH, max = MAXIMUM_NAME_LENGTH, message = "The input should be min. 1 and max. 100 characters")
    val firstName: String = "",

    @field:NotBlank(message = "Input can't be empty or a bunch of whitespace characters")
    @field:Size(min = MINIMUM_NAME_LENGTH, max = MAXIMUM_NAME_LENGTH, message = "The input should be min. 1 and max. 100 characters")
    val lastName: String = "",

    val role: Role = Role.STUDENT,

    @field:NotNull
    @field:Size(min = MINIMUM_PASSWORD_LENGTH, max = MAXIMUM_PASSWORD_LENGTH)
    val password: String = "",

    @field:NotNull
    @field:Size(min = MINIMUM_PASSWORD_LENGTH, max = MAXIMUM_PASSWORD_LENGTH)
    val passwordConfirmation: String = ""
)