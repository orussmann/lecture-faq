package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.constants.MINIMUM_ANSWERS_COUNT
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.CreatePollRequestDTO
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.PollResponseDTO
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.UpdatePollRequestDTO
import de.thk.gm.fddw.lecturefaq.services.PollsService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/v1")
class PollsRestController(
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

    //TODO: Add GET for one poll


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
        @PathVariable userId: UUID,
        @Valid @RequestBody poll: CreatePollRequestDTO,
    ): PollResponseDTO {
        try {
            val createdPoll = pollsService.save(poll, userId)
            return createdPoll
        } catch (e: ResponseStatusException) {
            throw e
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create poll")
        }
    }

    @DeleteMapping("/users/{userId}/polls/{pollId}")
    @ResponseStatus(HttpStatus.OK)
    fun deletePoll(
        @PathVariable pollId: UUID,
        @PathVariable userId: UUID
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
        @Valid @RequestBody poll: UpdatePollRequestDTO
    ): PollResponseDTO {
        try {
            val updatedPoll = pollsService.updateById(pollId, poll)
            return updatedPoll
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update poll")
        }
    }
}
