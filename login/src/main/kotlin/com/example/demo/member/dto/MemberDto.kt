package com.example.demo.member.dto

import com.example.demo.common.annotation.ValidEnum
import com.example.demo.common.status.Gender
import com.example.demo.member.entity.Member
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class MemberDtoReq (
    //dto에 어떤값이던 담기도록하기위해 nullable
    var id: Long?,

    @field:NotBlank // 빈값을 받지 않을 것이기때문에 NotBlank
    @JsonProperty("loginId")
    private val _loginId: String?,

    @field:NotBlank
    @field:Pattern(
        regexp="^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,20}\$",
        message = "영문, 숫자, 특수문자를 포함한 8~20자리로 입력해주세요"
    )
    @JsonProperty("password")
    private val _password: String?,

    @field:NotBlank
    @JsonProperty("name")
    private val _name: String?,

    @field:NotBlank
    @JsonProperty("birthDate")
    @field:Pattern(
        regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
        message = "날짜형식(YYYY-MM-DD)을 확인해주세요"
    )
    private val _birthDate: String?,

    @field:NotBlank
    @JsonProperty("gender")
    @field:ValidEnum(enumClass = Gender::class, message = "MAN 이나 WOMAN 중 하나를 선택해주세요")
    private val _gender: String?,

    @field:NotBlank
    @field:Email
    @JsonProperty("email")
    private val _email: String?,
    ){
    //custom getter -> 서로 이어준다
    val loginId: String
        get() = _loginId!!
    val password: String
        get() = _password!!
    val name: String
        get() = _name!!
    val birthDate: LocalDate
        get() = _birthDate!!.toLocalDate()
    val gender: Gender
        get() = Gender.valueOf(_gender!!) // 클래스에 있기때문에 처리 방식이 다르다
    val email: String
        get() = _email!!
    
    private fun String.toLocalDate(): LocalDate = // String.LocalDate()를 LocalDate 반환하는 확장함수
    LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd")) // 이 패턴에 맞게 변환

    //멤버 생성자를 사용해 엔티티를 반환하는 함수
    fun toEntity(): Member =
        Member(id, loginId, password, name, birthDate, gender, email)
}

//로그인 후 토큰발행을 위한 Dto
data class LoginDto(
    @field:NotBlank // 빈값을 받지 않을 것이기때문에 NotBlank
    @JsonProperty("loginId")
    private val _loginId: String?,

    @field:NotBlank
    @JsonProperty("password")
    private val _password: String?,
){

    val loginId: String
        get() = _loginId!!
    val password: String
        get() = _password!!
}

//유저 정보 조회를 위한 dto
data class MemberDtoResponse(
    val id: Long,
    val longId: String,
    val name: String,
    val birthData: String,
    val gender: String,
    val email: String,
)