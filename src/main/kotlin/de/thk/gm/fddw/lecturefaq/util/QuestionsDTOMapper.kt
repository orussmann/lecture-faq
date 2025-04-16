package de.thk.gm.fddw.lecturefaq.util

import de.thk.gm.fddw.lecturefaq.models.Lecture
import de.thk.gm.fddw.lecturefaq.models.Question
import de.thk.gm.fddw.lecturefaq.models.User
import de.thk.gm.fddw.lecturefaq.models.questions_dto.CreateQuestionRequestDTO
import de.thk.gm.fddw.lecturefaq.models.questions_dto.QuestionResponseDTO
import de.thk.gm.fddw.lecturefaq.models.questions_dto.UpdateQuestionRequestDTO
import org.springframework.stereotype.Component

@Component
class QuestionsDTOMapper {

    fun mapToQuestionResponse(question: Question): QuestionResponseDTO {
        return QuestionResponseDTO(
            id = question.id,
            lectureId = question.lecture.id,
            userId = question.user.id,
            text = question.text
        )
    }

    fun mapToNewQuestion(
        createQuestionRequestDTO: CreateQuestionRequestDTO,
        lecture: Lecture,
        user: User
    ): Question {
        return Question(
            lecture = lecture,
            user = user,
            text = createQuestionRequestDTO.text
        )
    }

    fun updateQuestionFromTo(
        questionForUpdate: UpdateQuestionRequestDTO,
        lecture: Lecture,
        user: User,
        text: String,
        savedQuestion: Question
    ): Question {
        return savedQuestion.copy(
            lecture = lecture,
            user = user,
            text = text
        )
    }
}