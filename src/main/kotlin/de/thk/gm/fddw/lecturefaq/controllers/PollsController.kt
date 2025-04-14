package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.models.poll_dtos.CreatePollRequestDTO
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.PollResponseDTO
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.UpdatePollRequestDTO
import de.thk.gm.fddw.lecturefaq.services.PollsService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
class PollsController(
    private val pollsService: PollsService
) {

    @GetMapping("/users/{userId}/polls")
    @ResponseStatus(HttpStatus.OK)
    fun getAllPollsFromUser(@PathVariable userId: UUID): List<PollResponseDTO> {
        try {
            val usersPolls = pollsService.findAllByUserId(userId)
            return usersPolls
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch polls")
        }
    }

    @GetMapping("/polls")
    @ResponseStatus(HttpStatus.OK)
    fun getAllPolls(): MutableIterable<PollResponseDTO> {
        try {
            val allPolls = pollsService.findAll()
            return allPolls
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch polls")
        }
    }

    @PostMapping("/users/{userId}/polls")
    @ResponseStatus(HttpStatus.CREATED)
    fun createPoll(
        @RequestBody poll: CreatePollRequestDTO,
        @PathVariable userId: String
    ): PollResponseDTO {
        try {
            val createdPoll = pollsService.save(poll)
            return createdPoll
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.CREATED)
        }
    }

    @DeleteMapping("/users/{userId}/polls/{pollId}")
    @ResponseStatus(HttpStatus.OK)
    fun deletePoll(
        @PathVariable pollId: UUID,
        @PathVariable userId: String
    ) {
        try {
            pollsService.removeById(pollId)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete poll")
        }
    }

    @PutMapping("/users/{userId}/polls/{pollId}")
    @ResponseStatus(HttpStatus.OK)
    fun updatePoll(
        @PathVariable pollId: UUID,
        @PathVariable userId: UUID,
        @RequestBody poll: UpdatePollRequestDTO
    ): PollResponseDTO {
        try {
            val updatedPoll = pollsService.updateById(pollId, poll)
            return updatedPoll
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update poll")
        }
    }
}
