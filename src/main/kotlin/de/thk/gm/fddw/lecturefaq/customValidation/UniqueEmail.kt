package de.thk.gm.fddw.lecturefaq.customValidation

import de.thk.gm.fddw.lecturefaq.repositories.UsersRepository
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass


@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueEmailValidator::class])
annotation class UniqueEmail(
    val message: String = "A user with this email already exists",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class UniqueEmailValidator(
    private val usersRepository: UsersRepository
) : ConstraintValidator<UniqueEmail, String> {
    override fun isValid(value: String, context: ConstraintValidatorContext?): Boolean {
        val noSuchUser = usersRepository.findByEmail(value) == null

        if (noSuchUser) {
            return true
        }

        context?.disableDefaultConstraintViolation()
        context?.buildConstraintViolationWithTemplate(context.defaultConstraintMessageTemplate)
            ?.addConstraintViolation()

        return false
    }
}
