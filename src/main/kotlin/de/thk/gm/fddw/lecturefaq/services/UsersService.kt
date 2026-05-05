package de.thk.gm.fddw.lecturefaq.services

import de.thk.gm.fddw.lecturefaq.models.User
import de.thk.gm.fddw.lecturefaq.models.user_dtos.CreateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.user_dtos.UpdateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.user_dtos.UserResponseDTO
import de.thk.gm.fddw.lecturefaq.models.user_dtos.UserSubscriptionResponseDTO
import java.util.*


interface UsersService {
    fun save(userDTO: CreateUserRequestDTO): UserResponseDTO
    fun findAll(): MutableIterable<UserResponseDTO>
    fun findById(userId: UUID): UserResponseDTO
    fun removeById(userId: UUID)
    fun updateById(userId: UUID, userDTO: UpdateUserRequestDTO): UserResponseDTO
    fun findByEmail(email: String): User?
    fun findSubscriptions(student: User, lecturers: List<UserResponseDTO>): List<UserSubscriptionResponseDTO>
}