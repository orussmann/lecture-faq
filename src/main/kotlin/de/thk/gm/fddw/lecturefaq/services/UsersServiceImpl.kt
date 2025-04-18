package de.thk.gm.fddw.lecturefaq.services

import de.thk.gm.fddw.lecturefaq.models.user_dtos.CreateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.user_dtos.UpdateUserRequestDTO
import de.thk.gm.fddw.lecturefaq.models.user_dtos.UserResponseDTO
import de.thk.gm.fddw.lecturefaq.repositories.UsersRepository
import de.thk.gm.fddw.lecturefaq.util.UserDTOMapper
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsersServiceImpl(
    private val usersRepository: UsersRepository,
    private val userDTOMapper: UserDTOMapper
) : UsersService {
    override fun save(userDTO: CreateUserRequestDTO): UserResponseDTO {
        val newUser = userDTOMapper.mapToNewUser(userDTO)
        val savedUser = usersRepository.save(newUser)
        return userDTOMapper.mapToUserResponse(savedUser)
    }

    override fun findAll(): MutableIterable<UserResponseDTO> {
        val savedUsers = usersRepository.findAll()
        return savedUsers
            .map(userDTOMapper::mapToUserResponse)
            .toMutableList()
    }

    override fun findById(userId: UUID): UserResponseDTO {
        val foundUser = usersRepository
            .findById(userId)
            .orElseThrow {
                NoSuchElementException("User not found")
            }
        return userDTOMapper.mapToUserResponse(foundUser)
    }

    override fun removeById(userId: UUID) {
        usersRepository.deleteById(userId)
    }

    override fun updateById(userId: UUID, userDTO: UpdateUserRequestDTO): UserResponseDTO {
        val existingUser = usersRepository
            .findById(userId)
            .orElseThrow {
                NoSuchElementException("User not found")
            }
        val updatedUser = userDTOMapper.mapToUpdatedUser(userDTO, existingUser)
        val savedUser = usersRepository.save(updatedUser)
        return userDTOMapper.mapToUserResponse(savedUser)
    }
}