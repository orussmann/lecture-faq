package de.thk.gm.fddw.lecturefaq.services

import de.thk.gm.fddw.lecturefaq.models.dtos.CreateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.dtos.UpdateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.dtos.UserResponseDTO
import java.util.*


interface UserService {
    fun save(userDTO: CreateUserRequestDTO): UserResponseDTO
    fun findAll(): MutableIterable<UserResponseDTO>
    fun findById(userId: UUID): UserResponseDTO
    fun removeById(userId: UUID)
    fun updateById(userId: UUID, userDTO: UpdateUserRequestDTO): UserResponseDTO
}