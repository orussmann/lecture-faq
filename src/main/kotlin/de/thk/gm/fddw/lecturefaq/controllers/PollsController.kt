package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.models.poll_dtos.CreatePollRequestDTO
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.UpdatePollRequestDTO
import de.thk.gm.fddw.lecturefaq.services.PollsService
import de.thk.gm.fddw.lecturefaq.services.UsersServiceImpl
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*


@Controller
@RequestMapping(produces = [MediaType.TEXT_HTML_VALUE])
class PollsController(
    private val pollsService: PollsService,
    private val usersService: UsersServiceImpl
) {

    private val logger = LoggerFactory.getLogger(PollsController::class.java)

    @GetMapping("/users/{userId}/polls")
    @ResponseStatus(HttpStatus.OK)
    fun getAllPollsFromUser(
        @PathVariable userId: UUID,
        model: Model
    ): String {
        try {
            val usersPolls = pollsService.findAllByUserId(userId)
            model.addAttribute("polls", usersPolls)
            model.addAttribute("userId", userId)
            return "polls/showPolls"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch polls")
        }
    }

    //TODO: Consider removing this
    /*@GetMapping("/polls")
    @ResponseStatus(HttpStatus.OK)
    fun getAllPolls(): MutableIterable<PollResponseDTO> {
        try {
            val allPolls = pollsService.findAll()
            return allPolls
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch polls")
        }
    }*/

    @PostMapping("/users/{userId}/polls")
    fun createPoll(
        @PathVariable userId: UUID,
        @Validated @ModelAttribute poll: CreatePollRequestDTO,
        bindingResult: BindingResult,
        model: Model
    ): String {
        try {
            if (bindingResult.hasErrors()) {
                val polls = pollsService.findAllByUserId(userId)
                model.addAttribute("errors", bindingResult)
                model.addAttribute("userId", userId)
                model.addAttribute("polls", polls)
                logger.info("There are >>Errors: " + bindingResult.hasErrors())
                println("There are >>Errors: " + bindingResult.hasErrors())
                logger.info(">>Errors: " + bindingResult.allErrors.toString())
                println(">>Errors: " + bindingResult.allErrors.toString())
                return "polls/showPolls"
            } else {
                logger.info("There are no >>Errors.")
                println("There are no >>Errors.")
                pollsService.save(poll, userId)
                return "redirect:/app/users/${userId}/polls"
            }
        } catch (e: ResponseStatusException) {
            throw e
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create poll")
        }
    }


    //TODO: Get mapping for one poll
    @GetMapping("/users/{userId}/polls/{pollId}")
    fun getPollById(
        @PathVariable pollId: UUID,
        @PathVariable userId: String,
        model: Model
    ): String {
        try {
            val poll = pollsService.findById(pollId)
            model.addAttribute("poll", poll)
            return "polls/showPoll"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch poll")
        }
    }

    @DeleteMapping("/users/{userId}/polls/{pollId}")
    fun deletePoll(
        @PathVariable pollId: UUID,
        @PathVariable userId: UUID
    ): String {
        try {
            pollsService.removeById(pollId)
            return "redirect:/app/users/${userId}/polls"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete poll")
        }
    }

    @PutMapping("/users/{userId}/polls/{pollId}")
    fun updatePoll(
        @PathVariable pollId: UUID,
        @PathVariable userId: UUID,
        @Valid poll: UpdatePollRequestDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("errors", bindingResult)
            } else {
                pollsService.updateById(pollId, poll)
            }
            return "redirect:/app/users/${userId}/polls"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update poll")
        }
    }
}
