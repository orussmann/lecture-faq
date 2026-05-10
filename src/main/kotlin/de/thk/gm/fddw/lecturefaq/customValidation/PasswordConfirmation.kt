package de.thk.gm.fddw.lecturefaq.customValidation

import de.thk.gm.fddw.lecturefaq.models.user_dtos.CreateUserRequestDTO
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PasswordConfirmationValidator::class])
annotation class PasswordConfirmation(
    val message: String = "Password confirmation does not match",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class PasswordConfirmationValidator : ConstraintValidator<PasswordConfirmation, CreateUserRequestDTO> {
    override fun isValid(value: CreateUserRequestDTO, context: ConstraintValidatorContext?): Boolean {
        val passwordConfirmationValid = value.password == value.passwordConfirmation

        if (passwordConfirmationValid) {
            return true
        }

        context?.disableDefaultConstraintViolation()
        context?.buildConstraintViolationWithTemplate(context.defaultConstraintMessageTemplate)
            ?.addPropertyNode("passwordConfirmation")
            ?.addConstraintViolation()

        return false
    }
}
