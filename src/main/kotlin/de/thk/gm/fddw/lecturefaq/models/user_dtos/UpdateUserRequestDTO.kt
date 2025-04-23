package de.thk.gm.fddw.lecturefaq.models.user_dtos

import de.thk.gm.fddw.lecturefaq.constants.Role
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class UpdateUserRequestDTO(
    @field:Email(message = "You must provide a valid e-mail address, like 'tom123@gmail.com'")
    var email: String? = null,

    @field:NotBlank(message = "Input can't be empty or a bunch of whitespace characters")
    @field:Size(min = 5, max = 100, message = "The input should be min. 1 and max. 100 characters")
    var firstName: String? = null,

    @field:NotBlank(message = "Input can't be empty or a bunch of whitespace characters")
    @field:Size(min = 5, max = 100, message = "The input should be min. 1 and max. 100 characters")
    var lastName: String? = null,

    val role: Role? = null
)
