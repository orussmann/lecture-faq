package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.models.dtos.CreateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.dtos.UpdateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.dtos.UserResponseDTO
import de.thk.gm.fddw.lecturefaq.services.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import kotlin.Exception

@RestController
class UsersController(private val userService: UserService) {

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getUser(@PathVariable id: UUID): UserResponseDTO {
        try {
            val user = userService.findById(id)
            return user
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch user")
        }
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    fun getAllUsers(): MutableIterable<UserResponseDTO> {
        try {
            val users = userService.findAll()
            return users
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch users")
        }
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(
        @RequestBody userDTO: CreateUserRequestDTO
    ): UserResponseDTO {
        try {
            return userService.save(userDTO)
        } catch (e: java.lang.Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create user")
        }
    }

    @DeleteMapping("/users/{id}")   //TODO: Handln, wenn nichts gelöscht wird
    @ResponseStatus(HttpStatus.OK)
    fun deleteUser(@PathVariable id: UUID) {
        try {
            userService.removeById(id)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete user")
        }
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateUser(
        @PathVariable id: UUID,
        @RequestBody updatedUserDTO: UpdateUserRequestDTO
    ): UserResponseDTO {
        try {
            return userService.updateById(id, updatedUserDTO)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update user")
        }
    }
}
