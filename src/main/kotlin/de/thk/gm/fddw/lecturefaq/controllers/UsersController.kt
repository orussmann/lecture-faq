package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.models.enums.Role
import de.thk.gm.fddw.lecturefaq.models.user_dtos.CreateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.user_dtos.UpdateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.repositories.LecturesRepository
import de.thk.gm.fddw.lecturefaq.repositories.UsersRepository
import de.thk.gm.fddw.lecturefaq.services.UsersService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.security.Principal
import java.util.*
import kotlin.NoSuchElementException

//TODO: Redesign URIs -> /app/user/lectures (decouple user from lectures, because the URi doesn't contain User ID anymore)
@Controller
@RequestMapping(produces = [MediaType.TEXT_HTML_VALUE])
class UsersController(
    private val usersService: UsersService,
    private val lecturesRepository: LecturesRepository,
    private val usersRepository: UsersRepository
) {

    private val logger = LoggerFactory.getLogger(PollsController::class.java)

    /*@GetMapping("/users/{id}")
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
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getUser(
        @PathVariable id: UUID,
        model: Model
    ): String {
        try {
            val user = usersService.findById(id)
            model.addAttribute("user", user)
            model.addAttribute("userId", user.userId)
            return if (user.role == Role.STUDENT) {
                val lecturerIds = usersService.findById(user.userId).subscriptions
                model.addAttribute("lecturerIds", lecturerIds)
                "student-view/showProfile"
            } else {
                "lecturer-view/showProfile"
            }
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch user")
        }
    }
     */
    @GetMapping("/user/student/profile")
    @ResponseStatus(HttpStatus.OK)
    fun showStudentProfile(
        principal: Principal,
        model: Model
    ): String {
        try {
            // TODO: Move all the business logic from Controller to Service
            val user = usersService.findByEmail(principal.name) ?: throw NoSuchElementException("User not found")
            val subscriptions = user.subscriptions
            val allLectures = lecturesRepository.findAll().toList()
            val newLectures =
                allLectures.filter { subscriptions.contains(it.creatorId) && it.createdAt >= user.lastVisited }
            val allLecturers = usersService.findAll().filter { it.role == Role.LECTURER }
            val newLecturesMessages =
                newLectures.map { l ->
                    "${
                        allLecturers.find { it.userId == l.creatorId }.let { it?.firstName + it?.lastName }
                    } hat eine neue Vorlesung ${l.title} erstellt!"
                }
            user.lastVisited = Date()
            usersRepository.save(user)
            model.addAttribute("newLectures", newLecturesMessages)
            model.addAttribute("user", user)
            val lecturerIds = usersService.findById(user.id).subscriptions
            model.addAttribute("lecturerIds", lecturerIds)
            return "student-view/showProfile"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch user")
        }
    }

    @GetMapping("/user/lecturer/profile")
    @ResponseStatus(HttpStatus.OK)
    fun showLecturerProfile(
        principal: Principal,
        model: Model
    ): String {
        try {
            val user = usersService.findByEmail(principal.name) ?: throw NoSuchElementException("User not found")
            model.addAttribute("user", user)
            return "lecturer-view/showProfile"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch user")
        }
    }

    // TODO: Not needed for now
    /* @GetMapping("/users")
     @ResponseStatus(HttpStatus.OK)
     fun getAllUsers(model: Model): String {
         try {
             val users = usersService.findAll()
             model.addAttribute("users", users)
             return "users/showUsers"
         } catch (e: Exception) {
             throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch users")
         }
     }*/

    /*@PostMapping("/users")
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
    }*/

    @PostMapping("/register")
    fun registerUser(
        @Valid user: CreateUserRequestDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
        model: Model
    ): String {
        try {   // TODO: Fix redirect not showing any flash attributes. Current solution, not following the PRG pattern causes duplicate POST on page reload
            if (bindingResult.hasErrors()) {
//                redirectAttributes.addFlashAttribute("errors", bindingResult)
                logger.info("UsersController hasErrors()")
                model.addAttribute("errors", bindingResult)
            } else if (user.password != user.passwordConfirmation) {
//                redirectAttributes.addFlashAttribute("error", "Passwords do not match")
                logger.info("UsersController error")
                model.addAttribute("error", "Passwords do not match")
            } else {
//                redirectAttributes.addFlashAttribute("success", "User registered successfully")
                logger.info("UsersController success")
                model.addAttribute("success", "User registered successfully")
                usersService.save(user)
            }
//            return "redirect:/app/"
            return "index"
        } catch (e: DataIntegrityViolationException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        } catch (e: java.lang.Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    // INFO: Not needed now
    @DeleteMapping("/users/{id}")   //TODO: Handln, wenn nichts gelöscht wird
    fun deleteUser(@PathVariable id: UUID): String {
        try {
            usersService.removeById(id)
            return "redirect:/app/users"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete user")
        }
    }

    @PutMapping("/user")
    fun updateUser(
        principal: Principal,
        @Valid user: UpdateUserRequestDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("errors", bindingResult)
            } else {
                val userId =
                    usersService.findByEmail(principal.name)?.id ?: throw NoSuchElementException("User not found")
                usersService.updateById(userId, user)
            }
            return "redirect:/app/user/profile"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update user")
        }
    }

    // TODO: Should have its own Controller (?)
    // TODO: Wouldn't that make more sense? -> /users/{studentId}/subscriptions
    @GetMapping("/user/students/subscriptions")
    fun getAllLecturers(
        principal: Principal,
        model: Model
    ): String {
        try {
            val student = usersService.findByEmail(principal.name)
                ?: throw NoSuchElementException("User not found")
            val studentsSubscriptions = student.subscriptions
            val studentId = student.id
            val lecturers = usersService.findAll().filter { it.role == Role.LECTURER }


            // Mapping: UserResponseDTO mit zusätzlichem Feld "subscribed"
            data class UserSubscriptionResponse(
                var userId: UUID,
                var firstName: String,
                var lastName: String,
                var subscribed: Boolean
            )
            // TODO: Move logic out of controller
            val lecturersWithSubscriptionInfo = lecturers.map { lecturer ->
                UserSubscriptionResponse(
                    lecturer.userId,
                    lecturer.firstName,
                    lecturer.lastName,
                    studentsSubscriptions.contains(lecturer.userId)
                )
            }

            model.addAttribute("lecturers", lecturersWithSubscriptionInfo)
            model.addAttribute("studentId", studentId) // TODO: Should be unified to userId
            model.addAttribute("userId", studentId)
            val lecturerIds = usersService.findById(studentId).subscriptions
            model.addAttribute("lecturerIds", lecturerIds)
            return "student-view/showLecturers"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
