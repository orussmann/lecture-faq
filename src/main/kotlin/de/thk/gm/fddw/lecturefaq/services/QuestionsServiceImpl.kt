package de.thk.gm.fddw.lecturefaq.services

import de.thk.gm.fddw.lecturefaq.models.questions_dto.CreateQuestionRequestDTO
import de.thk.gm.fddw.lecturefaq.models.questions_dto.QuestionResponseDTO
import de.thk.gm.fddw.lecturefaq.models.questions_dto.UpdateQuestionRequestDTO
import de.thk.gm.fddw.lecturefaq.repositories.LecturesRepository
import de.thk.gm.fddw.lecturefaq.repositories.QuestionsRepository
import de.thk.gm.fddw.lecturefaq.repositories.UsersRepository
import de.thk.gm.fddw.lecturefaq.util.LecturesDTOMapper
import de.thk.gm.fddw.lecturefaq.util.QuestionsDTOMapper
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

@Service
class QuestionsServiceImpl(
    private val questionsRepository: QuestionsRepository,
    private val lecturesRepository: LecturesRepository,
    private val questionsDTOMapper: QuestionsDTOMapper,
    private val lecturesDTOMapper: LecturesDTOMapper,
    private val usersRepository: UsersRepository
) : QuestionsService {
    override fun save(question: CreateQuestionRequestDTO): QuestionResponseDTO {
        val lecture = lecturesRepository
            .findById(question.lectureId)
            .orElseThrow { NoSuchElementException("Lecture not found") }
        val questionToBeSaved = questionsDTOMapper.mapToNewQuestion(question, lecture, lecture.user)
        lecture.questions.add(questionToBeSaved)
        lecturesRepository.save(lecture)
        return questionsDTOMapper.mapToQuestionResponse(questionToBeSaved)
    }

    override fun findAll(): MutableIterable<QuestionResponseDTO> {
        val savedQuestions = questionsRepository.findAll()
        return savedQuestions
            .map(questionsDTOMapper::mapToQuestionResponse)
            .toMutableList()
    }

    override fun findById(questionId: UUID): QuestionResponseDTO {
        val foundQuestion = questionsRepository
            .findById(questionId)
            .orElseThrow { NoSuchElementException("Question not found") }
        return questionsDTOMapper.mapToQuestionResponse(foundQuestion)
    }

    override fun findAllByUserId(userId: UUID): List<QuestionResponseDTO> {
        val usersQuestions = questionsRepository.findAllByUserId(userId)
        return usersQuestions.map(questionsDTOMapper::mapToQuestionResponse)
    }

    override fun findAllByLectureId(lectureId: UUID): List<QuestionResponseDTO> {
        val lectureQuestions = questionsRepository.findAllByLectureId(lectureId)
        return lectureQuestions.map(questionsDTOMapper::mapToQuestionResponse)
    }

    override fun removeById(questionId: UUID) {
        questionsRepository.deleteById(questionId)
    }

    override fun updateById(questionId: UUID, questionDTO: UpdateQuestionRequestDTO): QuestionResponseDTO {
        val existingQuestion = questionsRepository
            .findById(questionId)
            .orElseThrow { NoSuchElementException("Question not found") }
        var lectureForUpdate = existingQuestion.lecture
        var userForUpdate = existingQuestion.user
        val textForUpdate = questionDTO.text ?: existingQuestion.text
        if (questionDTO.lectureId != null) {
            lectureForUpdate = lecturesRepository
                .findById(questionDTO.lectureId)
                .orElseThrow { NoSuchElementException("Lecture not found") }

        }
        if (questionDTO.userId != null) {
            userForUpdate = usersRepository
                .findById(questionDTO.userId)
                .orElseThrow { NoSuchElementException("User not found") }

        }
        val updatedQuestion = questionsDTOMapper.updateQuestionFromTo(
            questionDTO,
            lectureForUpdate,
            userForUpdate,
            textForUpdate,
            existingQuestion
        )
        val savedQuestion = questionsRepository.save(updatedQuestion)
        return questionsDTOMapper.mapToQuestionResponse(savedQuestion)
    }
}