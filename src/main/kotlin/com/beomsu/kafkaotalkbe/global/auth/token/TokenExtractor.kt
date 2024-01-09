package com.beomsu.kafkaotalkbe.global.auth.token

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class TokenExtractor(
    @Value("\${jwt.secret}")
    private val _secretKey: String
) {

    init {
        secretKey = _secretKey
    }

    companion object {
        lateinit var secretKey: String

        fun extractToken(
            headerName: String,
            request: HttpServletRequest,
            response: HttpServletResponse
        ): String? {
            val token: String? = request.getHeader(headerName)

            return if (token != null) TokenChecker.removeBearer(token, response) else null
        }
    }
}