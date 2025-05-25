package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.models.enums.Role
import de.thk.gm.fddw.lecturefaq.models.user_dtos.CreateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.user_dtos.UpdateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.services.UsersService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*

@Controller
@RequestMapping(produces = [MediaType.TEXT_HTML_VALUE])
class UsersController(private val usersService: UsersService, private val usersRestController: UsersRestController) {

    private val logger = LoggerFactory.getLogger(PollsController::class.java)

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
        @Valid user: CreateUserRequestDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("errors", bindingResult)
            } else {
                usersService.save(user)
            }
            return "redirect:/app/users"
        } catch (e: java.lang.Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
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
        @Valid user: UpdateUserRequestDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("errors", bindingResult)
            } else {
                usersService.updateById(id, user)
            }
            return "redirect:/app/users"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update user")
        }
    }

    @GetMapping("/users/students/{studentId}/subscriptions")
    fun getAllLecturers(
        @PathVariable studentId: UUID,
        model: Model
    ): String {
        try {
            val studentsSubscriptions = usersService.findById(studentId).subscriptions
            val lecturers = usersService.findAll().filter { it.role == Role.LECTURER }


            // Mapping: UserResponseDTO mit zusätzlichem Feld "subscribed"
            data class UserSubscriptionResponse(
                var userId: UUID,
                var firstName: String,
                var lastName: String,
                var subscribed: Boolean
            )

            val lecturersWithSubscriptionInfo = lecturers.map { lecturer ->
                UserSubscriptionResponse(
                    lecturer.userId,
                    lecturer.firstName,
                    lecturer.lastName,
                    studentsSubscriptions.contains(lecturer.userId)
                )
            }

            model.addAttribute("lecturers", lecturersWithSubscriptionInfo)
            model.addAttribute("studentId", studentId)
            return "student-view/showLecturers"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
