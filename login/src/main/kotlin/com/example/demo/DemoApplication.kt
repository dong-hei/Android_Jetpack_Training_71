package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@SpringBootApplication
//@EntityScan("package com.example.member.entity")
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}
