package de.thk.gm.fddw.lecturefaq.util

import de.thk.gm.fddw.lecturefaq.models.Lecture
import de.thk.gm.fddw.lecturefaq.models.User
import de.thk.gm.fddw.lecturefaq.models.lecture_dtos.CreateLectureRequestDTO
import de.thk.gm.fddw.lecturefaq.models.lecture_dtos.LectureResponseDTO
import de.thk.gm.fddw.lecturefaq.models.lecture_dtos.UpdateLectureRequestDTO
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class LecturesDTOMapper {

    fun mapToLecturesResponse(lecture: Lecture): LectureResponseDTO {
        return LectureResponseDTO(
            id = lecture.id,
            title = lecture.title,
            description = lecture.description,
            type = lecture.type,
            link = lecture.link,
            users = lecture.users,
            code = lecture.code
        )
    }

    fun mapToNewLecture(lecture: CreateLectureRequestDTO, user: User): Lecture {
        return Lecture(
            title = lecture.title,
            description = lecture.description,
            type = lecture.type,
            link = lecture.link,
            code = lecture.code,
            users = mutableListOf(user),
            creatorId = user.id
        )
    }

    //TODO: Consider encapsulating more of the logic to get needed infos for mapping inside this method
    fun mapToUpdatedLecture(
        updateLectureRequestDTO: UpdateLectureRequestDTO,
        lecture: Lecture,
        users: MutableList<User>
    ): Lecture {
        lecture.title = updateLectureRequestDTO.title ?: lecture.title
        lecture.description = updateLectureRequestDTO.description ?: lecture.description
        lecture.type = updateLectureRequestDTO.type ?: lecture.type
        lecture.link = updateLectureRequestDTO.link ?: lecture.link
        lecture.users = users
        lecture.code = updateLectureRequestDTO.code ?: lecture.code
        return lecture
    }
}