# ✔️ 인증과 인가
인증 - 비행기 탑승을 위해 여권과 탑승권을 확인 받는 것을 예시로 들수있다.

인가 - 하지만 비행기 조종사가 아니기에 비행기 조종실에 들어갈수 없는것을 예시로 들수있다.

---
# 🔒 스프링 시큐리티

어플리케이션의 보안을 담당, 인증 및 인가를 필터의 흐름에 따라 처리

---
# 🟡 JWT (Json Web Token)
서로 간의 클레임을 안전하게 표헌하기 위한 개방형 업계 표준

header :  토큰의 유형과 서명 알고리즘 명시

payload : claim이라 불리는 사용자 인증, 인가 정보

signature : 헤더와 페이로드가 비밀키로 서명

---
# 🚨 에러노트: 
## 1. POST 요청하에 제대로 입력해서 보냈는데 incorrect string value ' xed x85 x8c xec x8a xa4...' for column~ 오류 발생

대처법: 인코딩 방법이 달라서, 즉 UTF-8이 아니여서 생기는 문제였음 -> MySql Client에서 database drop 후 다음과 같은 쿼리 입력
create database 데이터베이스명 character set utf8mb4 collate utf8mb4_unicode_ci;

## 2.application.yml 구문을 띄어쓰기 확실하게 정상적으로 입력했음에도 테이블 생성이 되지않음

대처법: 
1.데이터베이스 세팅이 안되어있었음 -> create database 데이터베이스명; 으로 데이터베이스 생성

## 2.패키지 설정이 잘못되었음 (example>common,member,demo(mainApplication.kt))
-> 패키지 설정을 다시함 (example>demo>common,member,mainApplication.kt)혹은 EntityScan으로 패키지 위치를 직접 설정함

## 3.토큰과 아이디 값으로 내 정보를 조회하는 과정에서 403 에러가 발생

대처법: CustomUserDetailsService의 member.memberRole!!.map { SimpleGrantedAuthority("Role_${it.role}")} 구문에서 Role이 소문자가 아닌 ROLE로 되어야 했음.
