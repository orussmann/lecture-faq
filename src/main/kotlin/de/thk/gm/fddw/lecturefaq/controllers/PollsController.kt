package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_ANSWERS_COUNT
import de.thk.gm.fddw.lecturefaq.models.answer_dto.CreateAnswerRequestDTO
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.CreatePollRequestDTO
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.UpdatePollRequestDTO
import de.thk.gm.fddw.lecturefaq.services.PollsService
import de.thk.gm.fddw.lecturefaq.services.UsersServiceImpl
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.UUID

@Controller
@RequestMapping(produces = [MediaType.TEXT_HTML_VALUE])
class PollsController(
    private val pollsService: PollsService,
    private val usersService: UsersServiceImpl
) {

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
        @Valid @ModelAttribute poll: CreatePollRequestDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("errors", bindingResult)
            } else {
                pollsService.save(poll, userId)
            }
            return "redirect:/app/users/${userId}/polls"
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
