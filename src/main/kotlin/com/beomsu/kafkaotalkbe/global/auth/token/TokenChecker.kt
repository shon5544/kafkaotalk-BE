package com.beomsu.kafkaotalkbe.global.auth.token

import com.beomsu.kafkaotalkbe.app.friends.application.port.out.UserPersistencePort
import com.beomsu.kafkaotalkbe.global.dto.ResponseSender
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class TokenChecker(
    @Value("\${jwt.secretKey}")
    private val _secretKey: String,

    _userPersistManager: UserPersistencePort
) {
    init {
        secretKey = _secretKey
        userPersistManager = _userPersistManager
    }
    
    companion object {
        private lateinit var secretKey: String
        private lateinit var userPersistManager: UserPersistencePort

        private const val BEARER = "Bearer "

        fun removeBearer(
            token: String,
            response: HttpServletResponse
        ): String? {
            // if (token == null) {
            //     ResponseSender.setBadRequestResponse(
            //         response,
            //         "인증 실패: 토큰이 비어있습니다."
            //     )
            //
            //     return null
            // }

            if (!token.startsWith(BEARER)) {
                ResponseSender.setBadRequestResponse(
                    response,
                    "인증 실패: 토큰의 유형이 잘못 됐습니다."
                )

                return null
            }

            val result = token.replace(BEARER, "")

            if (!isTokenValid(result)) {
                ResponseSender.setBadRequestResponse(
                    response,
                    "인증 실패: 유효한 토큰이 아닙니다."
                )

                return null
            }

            return result
        }

        fun isTokenValid(token: String): Boolean {
            val key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))

            return try {
                Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)

                true
            } catch (e: Exception) {
                false
            }
        }
    }
}