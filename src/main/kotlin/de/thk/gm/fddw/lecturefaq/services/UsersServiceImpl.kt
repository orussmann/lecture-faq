package de.thk.gm.fddw.lecturefaq.services

import de.thk.gm.fddw.lecturefaq.models.user_dtos.CreateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.user_dtos.UpdateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.user_dtos.UserResponseDTO
import de.thk.gm.fddw.lecturefaq.repositories.UsersRepository
import de.thk.gm.fddw.lecturefaq.util.UsersDTOMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UsersServiceImpl(
    private val usersRepository: UsersRepository,
    private val usersDTOMapper: UsersDTOMapper
) : UsersService {
    override fun save(userDTO: CreateUserRequestDTO): UserResponseDTO {
        val newUser = usersDTOMapper.mapToNewUser(userDTO)
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
}