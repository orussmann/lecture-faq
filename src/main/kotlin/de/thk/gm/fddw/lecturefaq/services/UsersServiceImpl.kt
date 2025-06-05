package de.thk.gm.fddw.lecturefaq.services

import de.thk.gm.fddw.lecturefaq.models.User
import de.thk.gm.fddw.lecturefaq.models.UserDetailsModel
import de.thk.gm.fddw.lecturefaq.models.user_dtos.CreateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.user_dtos.UpdateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.user_dtos.UserResponseDTO
import de.thk.gm.fddw.lecturefaq.repositories.UsersRepository
import de.thk.gm.fddw.lecturefaq.util.UsersDTOMapper
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.NoSuchElementException

@Service
class UsersServiceImpl(
    private val usersRepository: UsersRepository,
    private val usersDTOMapper: UsersDTOMapper,
    private val passwordEncoder: PasswordEncoder
) : UsersService, UserDetailsService {
    override fun save(userDTO: CreateUserRequestDTO): UserResponseDTO {
        val newUser = usersDTOMapper.mapToNewUser(userDTO)
        newUser.password = passwordEncoder.encode(newUser.password)
        val savedUser = usersRepository.save(newUser)
        return usersDTOMapper.mapToUserResponse(savedUser)
    }

    override fun findAll(): MutableIterable<UserResponseDTO> {
        val savedUsers = usersRepository.findAll()
        return savedUsers
            .map(usersDTOMapper::mapToUserResponse)
            .toMutableList()
    }

    override fun findById(userId: UUID): UserResponseDTO {
        val foundUser = usersRepository
            .findById(userId)
            .orElseThrow {
                NoSuchElementException("User not found")
            }
        return usersDTOMapper.mapToUserResponse(foundUser)
    }

    override fun removeById(userId: UUID) {
        usersRepository.deleteById(userId)
    }

    @Transactional
    override fun updateById(userId: UUID, userDTO: UpdateUserRequestDTO): UserResponseDTO {
        val existingUser = usersRepository
            .findById(userId)
            .orElseThrow {
                NoSuchElementException("User not found")
            }
        val updatedUser = usersDTOMapper.mapToUpdatedUser(userDTO, existingUser)
        val savedUser = usersRepository.save(updatedUser)
        return usersDTOMapper.mapToUserResponse(savedUser)
    }

    // INFO: Doesn't use DTO, because it's not used for requests
    override fun findByEmail(email: String): User {
        val user = usersRepository.findByEmail(email) ?: throw NoSuchElementException("User not found")
        return user
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = findByEmail(username!!)
        val userDetailsModel = UserDetailsModel(user.role, user.password, user.email)
        return userDetailsModel
    }
}