package com.example.demo.common.ex

class InvalidInputEx(
    val fieldName: String = "",
    message: String = "Invalid Input"
) : RuntimeException(message)