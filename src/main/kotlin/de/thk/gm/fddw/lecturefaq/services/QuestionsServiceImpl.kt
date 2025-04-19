package de.thk.gm.fddw.lecturefaq.services

import de.thk.gm.fddw.lecturefaq.models.question_dto.CreateQuestionRequestDTO
import de.thk.gm.fddw.lecturefaq.models.question_dto.QuestionResponseDTO
import de.thk.gm.fddw.lecturefaq.models.question_dto.UpdateQuestionRequestDTO
import de.thk.gm.fddw.lecturefaq.repositories.LecturesRepository
import de.thk.gm.fddw.lecturefaq.repositories.QuestionsRepository
import de.thk.gm.fddw.lecturefaq.repositories.UsersRepository
import de.thk.gm.fddw.lecturefaq.util.LecturesDTOMapper
import de.thk.gm.fddw.lecturefaq.util.QuestionsDTOMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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
    @Transactional
    override fun save(question: CreateQuestionRequestDTO): QuestionResponseDTO {
        val lecture = lecturesRepository
            .findById(question.lectureId)
            .orElseThrow { NoSuchElementException("Lecture not found") }
        //TODO: Is this correct? -> Is there a relationship user-question?
        val user = usersRepository
            .findById(question.userId)
            .orElseThrow {
                IllegalArgumentException("User not found")
            }
        val questionToBeSaved = questionsDTOMapper.mapToNewQuestion(question, lecture, user)
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

    @Transactional
    override fun updateById(questionId: UUID, questionDTO: UpdateQuestionRequestDTO): QuestionResponseDTO {
        val existingQuestion = questionsRepository
            .findById(questionId)
            .orElseThrow { NoSuchElementException("Question not found") }
        var lectureForUpdate = existingQuestion.lecture
        var userForUpdate = existingQuestion.user           //TODO: Fix needed? -> What if user does not exist?
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
        val updatedQuestion = questionsDTOMapper.mapToUpdatedQuestion(
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