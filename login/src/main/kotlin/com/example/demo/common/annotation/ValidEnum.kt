package com.example.demo.common.annotation

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [ValidEnumValidator::class])
annotation class ValidEnum(
    val message: String = "Invalid enum value", // 통합을 못하면 이 메세지를 출력한다.
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val enumClass: KClass<out Enum<*>> //젠더클래스가 이 프로퍼티에 들어감
)

class ValidEnumValidator: ConstraintValidator<ValidEnum, Any>{

    private lateinit var enumValues: Array<out Enum<*>>

    override fun initialize(annotation: ValidEnum) {
        enumValues = annotation.enumClass.java.enumConstants
    }

    //사용자로부터 받은 값이 value 파람에 들어감 enumvalues안에 젠더가 전달되면 man or woman이 전달받음
    override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
       if(value == null){
           return true
       }
        // 젠더가 하나라도 전달받으면 true
        return enumValues.any{ it.name == value.toString()}
    }
}