package de.thk.gm.fddw.lecturefaq.util

import de.thk.gm.fddw.lecturefaq.models.Lecture
import de.thk.gm.fddw.lecturefaq.models.Question
import de.thk.gm.fddw.lecturefaq.models.User
import de.thk.gm.fddw.lecturefaq.models.question_dtos.CreateQuestionRequestDTO
import de.thk.gm.fddw.lecturefaq.models.question_dtos.QuestionResponseDTO
import de.thk.gm.fddw.lecturefaq.models.question_dtos.UpdateQuestionRequestDTO
import org.springframework.stereotype.Component

@Component
class QuestionsDTOMapper {

    fun mapToQuestionResponse(question: Question): QuestionResponseDTO {
        return QuestionResponseDTO(
            id = question.id,
            lectureId = question.lecture.id,
            userId = question.user.id,
            text = question.text,
            createdAt = question.createdAt,
            chatUserName = question.chatUserName,
            likesCount = question.likedBy.size,
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
            text = createQuestionRequestDTO.text,
            createdAt = createQuestionRequestDTO.createdAt,
            chatUserName = createQuestionRequestDTO.chatUserName
        )
    }

    fun mapToUpdatedQuestion(
        questionForUpdate: UpdateQuestionRequestDTO,
        lecture: Lecture,
        user: User,
        text: String,
        savedQuestion: Question
    ): Question {
        savedQuestion.lecture = lecture
        savedQuestion.user = user
        savedQuestion.text = text
        return savedQuestion
    }
}