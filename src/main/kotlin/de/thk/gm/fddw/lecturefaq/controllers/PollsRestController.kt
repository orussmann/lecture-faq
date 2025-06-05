package de.thk.gm.fddw.lecturefaq.controllers

import de.thk.gm.fddw.lecturefaq.models.enums.Role
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.CreatePollRequestDTO
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.PollResponseDTO
import de.thk.gm.fddw.lecturefaq.models.poll_dtos.UpdatePollRequestDTO
import de.thk.gm.fddw.lecturefaq.services.PollsService
import de.thk.gm.fddw.lecturefaq.services.UsersService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/v1")
class PollsRestController(
    private val pollsService: PollsService,
    private val usersService: UsersService
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

    @PostMapping("/lecturers/users/{userId}/polls")
    @ResponseStatus(HttpStatus.CREATED)
    fun createPoll(
        @PathVariable userId: UUID,
        @Validated @RequestBody poll: CreatePollRequestDTO,
    ): Any {
        try {
            val user = usersService.findById(userId)
            if (user.role == Role.LECTURER) {
                val createdPoll = pollsService.save(poll, userId)
                return createdPoll
            } else {    // TODO: Use ResponseEntity<PollResponseDTO> as return value
                return ResponseEntity("Only lecturers can crete new polls", HttpStatus.UNAUTHORIZED)
            }
        } catch (e: ResponseStatusException) {
            throw e
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create poll")
        }
    }

    @DeleteMapping("/users/{userId}/polls/{pollId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
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
