package de.thk.gm.fddw.lecturefaq.customValidation

import de.thk.gm.fddw.lecturefaq.models.answer_dtos.CreateAnswerRequestDTO
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass


@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [TwoAnswersMinimumValidator::class])
annotation class TwoAnswersMinimum(
    val message: String = "At least 2 answers must be provided",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class TwoAnswersMinimumValidator : ConstraintValidator<TwoAnswersMinimum, List<CreateAnswerRequestDTO>> {
    override fun isValid(value: List<CreateAnswerRequestDTO>, context: ConstraintValidatorContext?): Boolean {
        val validAnswersCount = value.count { it.text.isNotBlank() } >= 2

        if (validAnswersCount) {
            return true
        }

        context?.disableDefaultConstraintViolation()
        context?.buildConstraintViolationWithTemplate(context.defaultConstraintMessageTemplate)
            ?.addConstraintViolation()

        return false
    }
}
