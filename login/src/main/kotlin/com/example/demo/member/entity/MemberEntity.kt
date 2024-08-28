package com.example.demo.member.entity

import com.example.demo.common.status.Gender
import com.example.demo.common.status.Role
import com.example.demo.member.dto.MemberDtoResponse
import jakarta.persistence.*
import org.apache.tomcat.util.http.FastHttpDateFormat.formatDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_member_login_id", columnNames = ["loginId"])]
) // 로그인아이디를 유니크
class Member(
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
) {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    val memberRole : List<MemberRole>? = null

    //birthDate 형변환을 위한 함수
    private fun LocalDate.formatDate(): String =
        this.format(DateTimeFormatter.ofPattern("yyyyMMdd"))

    fun toDto(): MemberDtoResponse = MemberDtoResponse(id!!, loginId, name, birthDate.formatDate(), gender.desc, email)
}

// 회원가입시 권한 엔티티
@Entity
class MemberRole(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    val role: Role,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_member_role_member_id"))
    val member: Member, // 멤버와 조인


)