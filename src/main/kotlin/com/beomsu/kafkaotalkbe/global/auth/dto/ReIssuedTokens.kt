package com.beomsu.kafkaotalkbe.global.auth.dto

data class ReIssuedTokens(
    val accessToken: String,
    val refreshToken: String
)