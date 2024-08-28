package com.example.demo.member.service

import com.example.demo.common.authority.JwtTokenProvider
import com.example.demo.common.authority.TokenInfo
import com.example.demo.common.ex.InvalidInputEx
import com.example.demo.common.status.Role
import com.example.demo.member.dto.LoginDto
import com.example.demo.member.dto.MemberDtoReq
import com.example.demo.member.dto.MemberDtoResponse
import com.example.demo.member.entity.Member
import com.example.demo.member.entity.MemberRole
import com.example.demo.member.repository.MemberRepository
import com.example.demo.member.repository.MemberRoleRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service

@Transactional
@Service
class MemberService (
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder, // 로그인후 토큰 발급을 위한 생성자
    private val jwtTokenProvider: JwtTokenProvider // 로그인후 토큰 발급을 위한 생성자
        ) {
    /***
     * 회원가입
     */
    fun signUp(memberDtoReq: MemberDtoReq): String {

        //ID 중복 검사
        var member: Member? = memberRepository.findByLoginId(memberDtoReq.loginId)
       
        //멤버가 이미 존재했을때
        if (member != null) {
            throw InvalidInputEx("loginId", "이미 등록된 ID 입니다.")
        }

        //멤버가 비어있을때 로직
        member = memberDtoReq.toEntity()
        memberRepository.save(member)

        val memberRole: MemberRole = MemberRole(null, Role.MEMBER, member)
        memberRoleRepository.save(memberRole) //회원가입시 권한 부여
        
        return "회원가입이 완료되었습니다."
    }

    /**
     * 로그인 -> 토큰 발행
     * 로그인시 호출되는 함수, 로그인시 아이디, 비밀번호를 받아 발행한 토큰 정보를 돌려준다 (TokenInfo를 통하여)
     */

    fun login(loginDto: LoginDto): TokenInfo {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.loginId, loginDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return jwtTokenProvider.createToken(authentication)
    }

    /**
     *  내 정보 조회
     */
    fun searchMyInfo(id: Long): MemberDtoResponse {
        val member: Member = memberRepository.findByIdOrNull(id) ?: throw InvalidInputEx("id", "회원번호(${{id}})가 존재하지 않는 유저 입니다")
        return member.toDto()
    }

    /**
     * 내 정보 수정
     */
    
    fun saveMyInfo(memberDtoReq: MemberDtoReq): String {
        val member: Member = memberDtoReq.toEntity()
        memberRepository.save(member)
        return "수정이 완료되었습니다."
    }
     }

