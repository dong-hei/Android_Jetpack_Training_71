package com.exampleLogin.member.dto

import com.exampleLogin.common.status.Gender
import java.time.LocalDate

data class MemberDtoReq (
    val id: Long?,
    val loginId: String,
    val password: String,
    val name: String,
    val birthDate: LocalDate,
    val gender: Gender,
    val email: String,
    )