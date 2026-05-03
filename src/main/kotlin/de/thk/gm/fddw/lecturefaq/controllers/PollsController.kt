package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.models.Answer
import de.thk.gm.fddw.lecturefaq.models.Poll
import de.thk.gm.fddw.lecturefaq.models.enums.Role
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.CreatePollRequestDTO
import de.thk.gm.fddw.lecturefaq.services.AnswersService
import de.thk.gm.fddw.lecturefaq.services.PollsService
import de.thk.gm.fddw.lecturefaq.services.UsersService
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
import java.security.Principal
import java.util.*

// TODO: Decision for

@Controller
@RequestMapping(produces = [MediaType.TEXT_HTML_VALUE])
class PollsController(
    private val pollsService: PollsService,
    private val usersService: UsersService,
    private val answersService: AnswersService
) {

    private val logger = LoggerFactory.getLogger(PollsController::class.java)

    /*@GetMapping("/users/{userId}/polls")
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
    }*/

    //TODO: Consider removing this


    // Original implementation, using the test views

    /*@PostMapping("/users/{userId}/polls")
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
                val filteredPoll = poll.apply { answers = answers.filter { it.text.isNotBlank() }.toMutableList() }
                pollsService.save(filteredPoll, userId)
                return "redirect:/app/users/${userId}/polls"
            }
        } catch (e: ResponseStatusException) {
            throw e
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create poll")
        }
    }*/
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
    // TODO: Redesign URIs
    @GetMapping("/user/lecturer/poll-form")
    fun getPollForm(
        principal: Principal,
        model: Model
    ): String {
        try {
            val userId = usersService.findByEmail(principal.name)?.id ?: throw NoSuchElementException("User not found")
            model.addAttribute("userId", userId)
            val polls = pollsService.findAllByUserId(userId)
            model.addAttribute("polls", polls)
            return "lecturer-view/createPollForm"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch polls")
        }
    }

    //TODO: Fix -> Currently, when there are errors, the poll is not saved, but the user is redirected to the poll form without any error messages.
    // The error messages should be displayed on the poll form.
    @PostMapping("/user/lecturer/poll-form")
    fun createPoll(
        principal: Principal,
        @Validated @ModelAttribute poll: CreatePollRequestDTO,
        bindingResult: BindingResult,
        model: Model
    ): String {
        try {
            val userId = usersService.findByEmail(principal.name)?.id ?: throw NoSuchElementException("User not found")
            if (bindingResult.hasErrors()) {
                logger.info("There are >>Errors: " + bindingResult.hasErrors())
                logger.info(">>Errors: " + bindingResult.allErrors.toString())
                model.addAttribute("errors", bindingResult)
                model.addAttribute("userId", userId)
                val polls = pollsService.findAllByUserId(userId)
                model.addAttribute("polls", polls)
                return "lecturer-view/createPollForm"
            } else {
                logger.info("There are no >>Errors.")
                println("There are no >>Errors.")
                val filteredPoll = poll.apply { answers = answers.filter { it.text.isNotBlank() }.toMutableList() }
                pollsService.save(filteredPoll, userId)
            }
            val polls = pollsService.findAllByUserId(userId)
            model.addAttribute("userId", userId)
            model.addAttribute("polls", polls)
            logger.info("Poll created successfully.")
            return "redirect:/app/user/lecturer/poll-form"
        } catch (e: ResponseStatusException) {
            throw e
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create poll")
        }
    }

    @GetMapping("/user/polls")
    fun getAllPolls(
        principal: Principal,
        model: Model
    ): String {
        try {
            //TODO: Should a user see all polls from all lectures, from all lectures he is attending or from one selected lecture?
            val polls = pollsService.findAll() // TODO: Replace
            val userId = usersService.findByEmail(principal.name)?.id ?: throw NoSuchElementException("User not found")
            model.addAttribute("polls", polls)
            model.addAttribute("userId", userId)
            val lecturerIds = usersService.findById(userId).subscriptions
            model.addAttribute("lecturerIds", lecturerIds)
            return "student-view/showPollsOverview"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/public-polls")
    fun getAllPublicPolls(
        model: Model
    ): String {
        try {
            //TODO: Should a user see all polls from all lectures, from all lectures he is attending or from one selected lecture?
            val polls = pollsService.findAll() // TODO: Replace
            model.addAttribute("polls", polls)
            return "public-view/showPublicPollsOverview"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    //TODO: Get mapping for one poll
    /*@GetMapping("/users/{userId}/polls/{pollId}")
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
    }*/


    @GetMapping("/user/lecturer/polls/{pollId}")
    fun getPollByIdLecturerView(
        @PathVariable pollId: UUID,
        model: Model
    ): String {
        try {
            val poll = pollsService.findById(pollId)
            model.addAttribute("poll", poll)
            val answers = answersService.findAllByPollId(pollId)
            model.addAttribute("answers", answers)
            return "lecturer-view/showPoll"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch poll")
        }
    }

    @GetMapping("/user/student/polls/{pollId}")
    fun getPollByIdStudentView(
        @PathVariable pollId: UUID,
        model: Model
    ): String {
        try {
            val poll = pollsService.findById(pollId)
            model.addAttribute("poll", poll)
            val answers = answersService.findAllByPollId(pollId)
            model.addAttribute("answers", answers)
            return "student-view/showPoll"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch poll")
        }
    }


    @GetMapping("/public-polls/{pollId}")
    fun getPublicPollByIdLecturerView(
        @PathVariable pollId: UUID,
        model: Model
    ): String {
        try {
            val poll = pollsService.findById(pollId)
            model.addAttribute("poll", poll)
            val answers = answersService.findAllByPollId(pollId)
            model.addAttribute("answers", answers)
            return "public-view/showPublicPoll"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch poll")
        }
    }

    @GetMapping("/user/lecturer/polls/{pollId}/results")
    fun getPollResultsLecturerView(
        @PathVariable pollId: UUID,
        model: Model
    ): String {
        try {
            val title = pollsService.findById(pollId).title
            model.addAttribute("title", title)  //TODO: Refactor, if views stay identical
            model.addAttribute("pollId", pollId)
            val answers = answersService.findAllByPollId(pollId)
            model.addAttribute("answers", answers)
            return "lecturer-view/showPollResults"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/user/student/polls/{pollId}/results")
    fun getPollResultsStudentView(
        principal: Principal,
        @PathVariable pollId: UUID,
        model: Model
    ): String {
        try {
            val title = pollsService.findById(pollId).title
            model.addAttribute("title", title)  //TODO: Refactor, if views stay identical
            model.addAttribute("pollId", pollId)
            val answers = answersService.findAllByPollId(pollId)
            model.addAttribute("answers", answers)
            return "student-view/showPollResults"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/public-polls/{pollId}/results")
    fun getPublicPollResults(
        @PathVariable pollId: UUID,
        model: Model
    ): String {
        try {
            val title = pollsService.findById(pollId).title
            model.addAttribute("title", title)  //TODO: Refactor, if views stay identical
            model.addAttribute("pollId", pollId)
            val answers = answersService.findAllByPollId(pollId)
            model.addAttribute("answers", answers)
            return "public-view/showPublicPollResults"
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    /*@DeleteMapping("/users/{userId}/polls/{pollId}")
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
    }*/
    /*
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
        }*/
}
