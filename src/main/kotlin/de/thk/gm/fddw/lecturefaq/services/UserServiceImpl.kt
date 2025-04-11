package de.thk.gm.fddw.lecturefaq.services

import de.thk.gm.fddw.lecturefaq.models.dtos.CreateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.dtos.UpdateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.dtos.UserResponseDTO
import de.thk.gm.fddw.lecturefaq.repositories.UserRepository
import de.thk.gm.fddw.lecturefaq.util.UserDTOMapper
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userDTOMapper: UserDTOMapper
) : UserService {
    override fun save(userDTO: CreateUserRequestDTO): UserResponseDTO {
        val newUser = userDTOMapper.mapToNewUser(userDTO)
        val savedUser = userRepository.save(newUser)
        return userDTOMapper.mapToUserResponse(savedUser)
    }

    override fun findAll(): MutableIterable<UserResponseDTO> {
        val savedUsers = userRepository.findAll()
        return savedUsers
            .map(userDTOMapper::mapToUserResponse)
            .toMutableList()
    }

    override fun findById(userId: UUID): UserResponseDTO {
        val foundUser = userRepository
            .findById(userId)
            .orElseThrow {
                NoSuchElementException("User not found")
            }
        return userDTOMapper.mapToUserResponse(foundUser)
    }

    override fun removeById(userId: UUID) {
        userRepository.deleteById(userId)
    }

    override fun updateById(userId: UUID, userDTO: UpdateUserRequestDTO): UserResponseDTO {
        val existingUser = userRepository
            .findById(userId)
            .orElseThrow {
                NoSuchElementException("User not found")
            }
        val updatedUser = userDTOMapper.mapToUpdatedUser(userDTO, existingUser)
        val savedUser = userRepository.save(updatedUser)
        return userDTOMapper.mapToUserResponse(savedUser)
    }
}