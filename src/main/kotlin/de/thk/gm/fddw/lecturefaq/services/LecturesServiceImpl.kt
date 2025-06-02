package de.thk.gm.fddw.lecturefaq.services

import de.thk.gm.fddw.lecturefaq.models.lecture_dtos.CreateLectureRequestDTO
import de.thk.gm.fddw.lecturefaq.models.lecture_dtos.LectureResponseDTO
import de.thk.gm.fddw.lecturefaq.models.lecture_dtos.UpdateLectureRequestDTO
import de.thk.gm.fddw.lecturefaq.repositories.LecturesRepository
import de.thk.gm.fddw.lecturefaq.repositories.UsersRepository
import de.thk.gm.fddw.lecturefaq.util.LecturesDTOMapper
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException
import org.slf4j.LoggerFactory
import org.slf4j.MarkerFactory
import org.springframework.transaction.annotation.Transactional


private val logger = LoggerFactory.getLogger(LecturesServiceImpl::class.java)

@Service
class LecturesServiceImpl(
    private val lecturesRepository: LecturesRepository,
    private val lecturesDTOMapper: LecturesDTOMapper,
    private val usersRepository: UsersRepository
) : LecturesService {
    @Transactional
    override fun save(lecture: CreateLectureRequestDTO): LectureResponseDTO {
        val user = usersRepository
            .findById(lecture.userId)
            .orElseThrow { NoSuchElementException("User for this lecture not found") }
        logger.debug(MarkerFactory.getMarker("LecturesServiceImpl"), "User: {}", user)
        val newLecture = lecturesDTOMapper.mapToNewLecture(lecture, user)
        user.addLecture(newLecture)
        val savedLecture = lecturesRepository.save(newLecture)
        usersRepository.save(user)
        return lecturesDTOMapper.mapToLecturesResponse(savedLecture)
    }

    override fun findAll(): MutableIterable<LectureResponseDTO> {
        val savedLectures = lecturesRepository.findAll()
        return savedLectures
            .map(lecturesDTOMapper::mapToLecturesResponse)
            .toMutableList()
    }

    override fun findById(lectureId: UUID): LectureResponseDTO {
        val foundLecture = lecturesRepository
            .findById(lectureId)
            .orElseThrow { NoSuchElementException("Lecture not found") }
        return lecturesDTOMapper.mapToLecturesResponse(foundLecture)
    }

    override fun findAllByUserId(userId: UUID): List<LectureResponseDTO> {
        val usersLectures = usersRepository.findById(userId).get().lectures
        return usersLectures.map(lecturesDTOMapper::mapToLecturesResponse)
    }

    override fun removeById(lectureId: UUID, userId: UUID) {
        val user = usersRepository
            .findById(userId)
            .orElseThrow {
            NoSuchElementException("User not found")
        }
        val lecture = lecturesRepository
            .findById(lectureId)
            .orElseThrow{
                NoSuchElementException("Lecture not found")
            }
        user.removeLecture(lecture)
        lecturesRepository.deleteById(lectureId)
        usersRepository.save(user)
    }

    @Transactional
    override fun updateById(lectureId: UUID, userId: UUID, lectureDTO: UpdateLectureRequestDTO): LectureResponseDTO {
        val existingLecture = lecturesRepository
            .findById(lectureId)
            .orElseThrow { NoSuchElementException("Lecture not found") }
        val existingUser = usersRepository
            .findById(userId)
            .orElseThrow { NoSuchElementException("User doesn't exist") }
        var updatedUsers = existingLecture
            .users
            .map { if (it.id == userId) existingUser else it }
            .toMutableList()
        if (lectureDTO.userId != null) {
            usersRepository
                .findById(lectureDTO.userId)
                .orElseThrow { NoSuchElementException("User for this update not found") }
        }
        val updatedLecture = lecturesDTOMapper.mapToUpdatedLecture(lectureDTO, existingLecture, updatedUsers)
        val savedLecture = lecturesRepository.save(updatedLecture)
        return lecturesDTOMapper.mapToLecturesResponse(savedLecture)
    }
}