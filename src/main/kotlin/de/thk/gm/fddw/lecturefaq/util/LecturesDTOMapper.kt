package de.thk.gm.fddw.lecturefaq.util

import de.thk.gm.fddw.lecturefaq.models.Lecture
import de.thk.gm.fddw.lecturefaq.models.User
import de.thk.gm.fddw.lecturefaq.models.lectures_dtos.CreateLectureRequestDTO
import de.thk.gm.fddw.lecturefaq.models.lectures_dtos.LectureResponseDTO
import de.thk.gm.fddw.lecturefaq.models.lectures_dtos.UpdateLectureRequestDTO
import org.springframework.stereotype.Component

@Component
class LecturesDTOMapper {

    fun mapToLecturesResponse(lecture: Lecture): LectureResponseDTO {
        return LectureResponseDTO(
            id = lecture.id,
            title = lecture.title,
            description = lecture.description,
            type = lecture.type,
            link = lecture.link,
            userId = lecture.user.id,
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
            user = user
        )
    }

    //TODO: Consider encapsulating more of the logic to get needed infos for mapping inside this method
    fun updateLectureFromTo(updateLectureRequestDTO: UpdateLectureRequestDTO, lecture: Lecture, user: User): Lecture {
        return lecture.copy(
            title = updateLectureRequestDTO.title ?: lecture.title,
            description = updateLectureRequestDTO.description ?: lecture.description,
            type = updateLectureRequestDTO.type ?: lecture.type,
            link = updateLectureRequestDTO.link ?: lecture.link,
            user = user,
            code = updateLectureRequestDTO.code ?: lecture.code
        )
    }
}