package com.exampleLogin.member.entity

import com.exampleLogin.common.status.Gender
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_member_login_id", columnNames = ["loginId"])]
) // 로그인아이디를 유니크
class Member (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false, length = 30, updatable = false)
    val loginId: String, // 멤버가 업데이트 될때 로그인아이디는 변경되지 않도록 설정

    @Column(nullable = false, length = 100)
    val password: String,

    @Column(nullable = false, length = 10)
    val name: String,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE) // 날짜만 입력
    val birthDate: LocalDate,

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING) // DB에 이넘 String이 사용된다.
    val gender: Gender,

    @Column(nullable = false, length = 30)
    val email: String,


    )
