package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.models.user_dtos.CreateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.user_dtos.UpdateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.user_dtos.UserResponseDTO
import de.thk.gm.fddw.lecturefaq.services.UsersService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/v1")
class UsersRestController(private val usersService: UsersService) {

    private val logger = LoggerFactory.getLogger(PollsController::class.java)

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getUser(@PathVariable id: UUID): UserResponseDTO {
        try {
            val user = usersService.findById(id)
            return user
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch user")
        }
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    fun getAllUsers(): MutableIterable<UserResponseDTO> {
        try {
            val users = usersService.findAll()
            return users
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch users")
        }
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(
        @Valid @RequestBody userDTO: CreateUserRequestDTO
    ): UserResponseDTO {
        try {
            if (userDTO.password != userDTO.passwordConfirmation) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match")
            }
            return usersService.save(userDTO)
        } catch (e: java.lang.Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/user/{id}")   //TODO: Handln, wenn nichts gelöscht wird
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable id: UUID) {
        try {
            usersService.removeById(id)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete user")
        }
    }

    @PutMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateUser(
        @PathVariable id: UUID,
        @Valid @RequestBody updatedUserDTO: UpdateUserRequestDTO
    ): UserResponseDTO {
        try {
            return usersService.updateById(id, updatedUserDTO)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update user")
        }
    }

    //TODO: Check if all endpoints follow best practices
    // TODO: Should there be a @Controller method as well?
    @PutMapping("/user/students/{studentId}/subscriptions/{lecturerId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateLecturersSubscriptions(
        @PathVariable studentId: UUID,
        @PathVariable lecturerId: UUID,
        @RequestBody updateUserRequestDTO: UpdateUserRequestDTO
    ) {
        try {
            logger.debug("UserRestController, studentId: {}", studentId)
            logger.debug("UserRestController, lectureId: {}", lecturerId)
            val subscriptions = usersService.findById(studentId).subscriptions
            val studentSubscribed = subscriptions.contains(lecturerId)
            logger.debug("UserRestController, studentSubscribed: {}", studentSubscribed)
            if (studentSubscribed) {
                subscriptions.remove(lecturerId)
            } else {
                subscriptions.add(lecturerId)
            }
            usersService.updateById(
                studentId,
                UpdateUserRequestDTO(subscriptions = subscriptions)
            ) //TODO: Redundancy -> see @RequestBody.. and here all values are updated with default values (null) -> Wrong!
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
