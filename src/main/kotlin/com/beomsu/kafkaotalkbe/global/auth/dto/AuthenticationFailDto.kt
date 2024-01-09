package com.beomsu.kafkaotalkbe.global.auth.dto

data class AuthenticationFailDto(
    val success: Boolean = false,
    val message: String = "",
)