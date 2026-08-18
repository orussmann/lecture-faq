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

@Controller
@RequestMapping(produces = [MediaType.TEXT_HTML_VALUE])
class UsersController(
    private val usersService: UsersService,
    private val lecturesRepository: LecturesRepository,
    private val usersRepository: UsersRepository
) {

    private val logger = LoggerFactory.getLogger(PollsController::class.java)

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

    @PostMapping("/register")
    fun registerUser(
        @Valid user: CreateUserRequestDTO,
        bindingResult: BindingResult,
        model: Model
    ): String {
        try {
            if (bindingResult.hasErrors()) {
                logger.info("UsersController hasErrors()")
                model.addAttribute("errors", bindingResult)
            } else {
                logger.info("UsersController success")
                model.addAttribute("success", "User registered successfully")
                usersService.save(user)
            }
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

    @PutMapping("/user/lecturer/profile")
    fun updateLecturer(
        principal: Principal,
        @Valid userDTO: UpdateUserRequestDTO,
        bindingResult: BindingResult,
        model: Model
    ): String {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("user", userDTO)
                model.addAttribute("errors", bindingResult)
                return "lecturer-view/showProfile"
            }
            val user =
                usersService.findByEmail(principal.name) ?: throw NoSuchElementException("User not found")
            val emailChanged = user.email != userDTO.email
            usersService.updateById(user.id, userDTO)

            return if (emailChanged) {
                "redirect:/app/logout?emailChanged=true"
            } else {
                "redirect:/app/user/lecturer/profile"
            }
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update user")
        }
    }

    @GetMapping("/user/student/subscriptions")
    fun getAllSubscriptions(
        principal: Principal,
        model: Model
    ): String {
        try {
            val student = usersService.findByEmail(principal.name)
                ?: throw NoSuchElementException("User not found")
            val studentId = student.id
            val lecturers = usersService.findAll().filter { it.role == Role.LECTURER }

            val lecturersWithSubscriptionInfo = usersService.findSubscriptions(student, lecturers)

            model.addAttribute("lecturers", lecturersWithSubscriptionInfo)
            model.addAttribute("studentId", studentId)
            val lecturerIds = usersService.findById(studentId).subscriptions
            model.addAttribute("lecturerIds", lecturerIds)
            return "student-view/showSubscriptions"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
