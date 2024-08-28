package com.exampleLogin.member.controller

import com.exampleLogin.member.dto.MemberDtoReq
import com.exampleLogin.member.svc.MemberService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/member")
@RestController
class MemberController (
    private val memberService: MemberService
    ){
    /**
     * 회원가입
     */
    @PostMapping("/signup")
    fun signUp(@RequestBody memberDtoReq: MemberDtoReq): String{
        return memberService.signUp(memberDtoReq)
    }
}