package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.constants.Role
import de.thk.gm.fddw.lecturefaq.models.user_dtos.CreateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.user_dtos.UpdateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.services.UsersService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import kotlin.Exception

@Controller
@RequestMapping(produces = [MediaType.TEXT_HTML_VALUE])
class UsersController(private val usersService: UsersService, private val usersRestController: UsersRestController) {

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getUser(
        @PathVariable id: UUID,
        model: Model
    ): String {
        try {
            val user = usersService.findById(id)
            model.addAttribute("user", user)
            return "users/showUser"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch user")
        }
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    fun getAllUsers(model: Model): String {
        try {
            val users = usersService.findAll()
            model.addAttribute("users", users)
            return "users/showUsers"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch users")
        }
    }

    @PostMapping("/users")
    fun createUser(
        @RequestParam email: String,
        @RequestParam firstName: String,
        @RequestParam lastName: String,
        @RequestParam role: Role
    ): String {
        try {
            val user = CreateUserRequestDTO(email, firstName, lastName, role)
            usersService.save(user)
            return "redirect:/app/users"
        } catch (e: java.lang.Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create user")
        }
    }

    @DeleteMapping("/users/{id}")   //TODO: Handln, wenn nichts gelöscht wird
    fun deleteUser(@PathVariable id: UUID): String {
        try {
            usersService.removeById(id)
            return "redirect:/app/users"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete user")
        }
    }

    @PutMapping("/users/{id}")
    fun updateUser(
        @PathVariable id: UUID,
        @RequestParam(required = false) email: String?,
        @RequestParam(required = false) firstName: String?,
        @RequestParam(required = false) lastName: String?,
        @RequestParam(required = false) role: Role?
    ): String {
        try {
            val updatedUser = UpdateUserRequestDTO(email, firstName, lastName, role)
            usersService.updateById(id, updatedUser)
            return "redirect:/app/users"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update user")
        }
    }
}
