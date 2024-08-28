package com.exampleLogin.member.svc

import com.exampleLogin.member.dto.MemberDtoReq
import com.exampleLogin.member.entity.Member
import com.exampleLogin.member.repo.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Transactional
@Service
class MemberService (
    private val memberRepository: MemberRepository
        ) {
    /***
     * 회원가입
     */
    fun signUp(memberDtoReq: MemberDtoReq): String {

        //ID 중복 검사
        var member: Member? = memberRepository.findByLoginId(memberDtoReq.loginId)
       
        //멤버가 이미 존재했을때
        if (member != null) {
            return "이미 등록된 아이디 입니다."
        }

        //멤버가 비어있을때 로직
        member = Member(
            null,
            memberDtoReq.loginId,
            memberDtoReq.password,
            memberDtoReq.name,
            memberDtoReq.birthDate,
            memberDtoReq.gender,
            memberDtoReq.email
        )

        memberRepository.save(member)

        return "회원가입이 완료되었습니다."
    }
}

