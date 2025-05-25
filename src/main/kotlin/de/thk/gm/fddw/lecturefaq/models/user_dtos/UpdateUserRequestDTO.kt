package de.thk.gm.fddw.lecturefaq.models.user_dtos

import de.thk.gm.fddw.lecturefaq.constants.MAXIMUM_NAME_LENGTH
import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_NAME_LENGTH
import de.thk.gm.fddw.lecturefaq.models.enums.Role
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.*

class UpdateUserRequestDTO(
    @field:Email(message = "You must provide a valid e-mail address, like 'tom123@gmail.com'")
    var email: String? = null,

    @field:NotBlank(message = "Input can't be empty or a bunch of whitespace characters")
    @field:Size(
        min = MINIMUM_NAME_LENGTH,
        max = MAXIMUM_NAME_LENGTH,
        message = "The input should be min. $MINIMUM_NAME_LENGTH and max. $MAXIMUM_NAME_LENGTH characters"
    )
    var firstName: String? = null,

    @field:NotBlank(message = "Input can't be empty or a bunch of whitespace characters")
    @field:Size(
        min = MINIMUM_NAME_LENGTH,
        max = MAXIMUM_NAME_LENGTH,
        message = "The input should be min. $MINIMUM_NAME_LENGTH and max. $MAXIMUM_NAME_LENGTH characters"
    )
    var lastName: String? = null,

    val role: Role? = null,

    var subscriptions: MutableList<UUID>? = null
)
