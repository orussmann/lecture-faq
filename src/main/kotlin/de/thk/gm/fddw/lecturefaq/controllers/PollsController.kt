package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.models.enums.Role
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.CreatePollRequestDTO
import de.thk.gm.fddw.lecturefaq.services.AnswersService
import de.thk.gm.fddw.lecturefaq.services.PollsService
import de.thk.gm.fddw.lecturefaq.services.UsersServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*


@Controller
@RequestMapping(produces = [MediaType.TEXT_HTML_VALUE])
class PollsController(
    private val pollsService: PollsService,
    private val usersService: UsersServiceImpl,
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

    //TODO: Fix -> Refreshing the page causes a form resubmit
    @GetMapping("/users/{userId}/polls/poll-form")
    fun getPollForm(
        @PathVariable userId: UUID,
        model: Model
    ): String {
        try {
            val isAuthorizedToViewPollForm = usersService.findById(userId).role == Role.LECTURER
            if (isAuthorizedToViewPollForm) {
                model.addAttribute("userId", userId)
                val polls = pollsService.findAllByUserId(userId)
                model.addAttribute("polls", polls)
                return "lecturer-view/createPollForm"
            } else {
                return "redirect:/app/users/${userId}/polls"
            }
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

    @PostMapping("/users/{userId}/polls/poll-form")
    fun createPoll(
        @PathVariable userId: UUID,
        @Validated @ModelAttribute poll: CreatePollRequestDTO,
        bindingResult: BindingResult,
        model: Model
    ): String {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("errors", bindingResult)
                logger.info("There are >>Errors: " + bindingResult.hasErrors())
                println("There are >>Errors: " + bindingResult.hasErrors())
                logger.info(">>Errors: " + bindingResult.allErrors.toString())
                println(">>Errors: " + bindingResult.allErrors.toString())
            } else {
                logger.info("There are no >>Errors.")
                println("There are no >>Errors.")
                val filteredPoll = poll.apply { answers = answers.filter { it.text.isNotBlank() }.toMutableList() }
                pollsService.save(filteredPoll, userId)
            }
            val polls = pollsService.findAllByUserId(userId)
            model.addAttribute("userId", userId)
            model.addAttribute("polls", polls)
            return "lecturer-view/createPollForm"
        } catch (e: ResponseStatusException) {
            throw e
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create poll")
        }
    }

    @GetMapping("/users/{userId}/polls")
    fun getAllPolls(
        @PathVariable userId: UUID,
        model: Model
    ): String {
        try {
            //TODO: Should a user see all polls from all lectures, from all lectures he is attending or from one selected lecture?
            val polls = pollsService.findAll() // TODO: Replace
            model.addAttribute("polls", polls)
            return "student-view/showPollsOverview"
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

    //TODO: One endpoint serves multiple different views (lecturer, student, ..)?
    @GetMapping("/users/{userId}/polls/{pollId}")
    fun getPollById(
        @PathVariable pollId: UUID,
        @PathVariable userId: UUID,
        model: Model
    ): String {
        try {
            val isLecturer = usersService.findById(userId).role == Role.LECTURER
            if (isLecturer) {
                val poll = pollsService.findById(pollId)    //TODO: View only needs title
                model.addAttribute("poll", poll)
                val answers = answersService.findAllByPollId(pollId)
                model.addAttribute("answers", answers)
                return "lecturer-view/showPoll"
            } else {
                val poll = pollsService.findById(pollId)    //TODO: View only needs title
                model.addAttribute("poll", poll)
                val answers = answersService.findAllByPollId(pollId)
                model.addAttribute("answers", answers)
                return "student-view/showPoll"
            }
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch poll")
        }
    }

    @GetMapping("/users/{userId}/polls/{pollId}/results")
    fun getPollResults(
        @PathVariable userId: UUID,
        @PathVariable pollId: UUID,
        model: Model
    ): String {
        try {
            val isLecturer = usersService.findById(userId).role == Role.LECTURER
            if (isLecturer) {
                val title = pollsService.findById(pollId).title
                model.addAttribute("title", title)  //TODO: Refactor, if views stay identical
                model.addAttribute("pollId", pollId)
                val answers = answersService.findAllByPollId(pollId)
                model.addAttribute("answers", answers)
                return "lecturer-view/showPollResults"
            } else {
                val title = pollsService.findById(pollId).title
                model.addAttribute("title", title)
                model.addAttribute("pollId", pollId)
                val answers = answersService.findAllByPollId(pollId)
                model.addAttribute("answers", answers)
                return "student-view/showPollResults"
            }
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
