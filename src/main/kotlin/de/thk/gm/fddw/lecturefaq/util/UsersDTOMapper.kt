package de.thk.gm.fddw.lecturefaq.util

import de.thk.gm.fddw.lecturefaq.models.User
import de.thk.gm.fddw.lecturefaq.models.user_dtos.CreateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.user_dtos.UpdateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.user_dtos.UserResponseDTO
import de.thk.gm.fddw.lecturefaq.models.user_dtos.UserSubscriptionResponseDTO
import org.springframework.stereotype.Component

@Component
class UsersDTOMapper {  //TODO: Find better name

    fun mapToUserResponse(user: User): UserResponseDTO {
        return UserResponseDTO(
            userId = user.id,
            email = user.email,
            firstName = user.firstName,
            lastName = user.lastName,
            role = user.role,
            subscriptions = user.subscriptions
        )
    }

    fun mapToNewUser(
        createUserRequestDTO: CreateUserRequestDTO
    ): User {
        return User(
            email = createUserRequestDTO.email,
            firstName = createUserRequestDTO.firstName,
            lastName = createUserRequestDTO.lastName,
            role = createUserRequestDTO.role,
            password = createUserRequestDTO.password
        )
    }

    fun mapToUpdatedUser(
        updateUserRequestDTO: UpdateUserRequestDTO,
        user: User
    ): User {
        user.email = updateUserRequestDTO.email ?: user.email
        user.firstName = updateUserRequestDTO.firstName ?: user.firstName
        user.lastName = updateUserRequestDTO.lastName ?: user.lastName
        user.role = updateUserRequestDTO.role ?: user.role
        user.password = updateUserRequestDTO.password ?: user.password
        user.subscriptions = updateUserRequestDTO.subscriptions ?: user.subscriptions
        return user
    }

    fun mapToUserSubscriptionsResponse(student: User, lecturers: List<UserResponseDTO>): List<UserSubscriptionResponseDTO> {
        return lecturers.map { lecturer ->
            UserSubscriptionResponseDTO(
                userId = lecturer.userId,
                firstName = lecturer.firstName,
                lastName = lecturer.lastName,
                subscribed = student.subscriptions.contains(lecturer.userId)
            )
        }
    }
}