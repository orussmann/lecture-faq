package de.thk.gm.fddw.lecturefaq.util

import de.thk.gm.fddw.lecturefaq.models.User
import de.thk.gm.fddw.lecturefaq.models.user_dtos.CreateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.user_dtos.UpdateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.user_dtos.UserResponseDTO
import org.springframework.stereotype.Component

@Component
class UsersDTOMapper {  //TODO: Find better name

    fun mapToUserResponse(user: User): UserResponseDTO {
        return UserResponseDTO(
            userId = user.id,
            email = user.email,
            firstName = user.firstName,
            lastName = user.lastName,
            role = user.role
        )
    }

    fun mapToNewUser(
        createUserRequestDTO: CreateUserRequestDTO
    ): User {
        return User(
            email = createUserRequestDTO.email,
            firstName = createUserRequestDTO.firstName,
            lastName = createUserRequestDTO.lastName,
            role = createUserRequestDTO.role
        )
    }

    fun updateUserFromTo(
        updateUserRequestDTO: UpdateUserRequestDTO,
        user: User
    ): User {
        return user.copy(
            email = updateUserRequestDTO.email ?: user.email,
            firstName = updateUserRequestDTO.firstName ?: user.firstName,
            lastName = updateUserRequestDTO.lastName ?: user.lastName,
            role = updateUserRequestDTO.role ?: user.role
        )
    }
}