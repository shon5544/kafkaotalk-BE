package com.beomsu.kafkaotalkbe.global.auth.token

import com.beomsu.kafkaotalkbe.app.friends.application.port.out.UserPersistencePort
import com.beomsu.kafkaotalkbe.app.friends.domain.User
import com.beomsu.kafkaotalkbe.global.auth.dto.ReIssuedTokens
import com.beomsu.kafkaotalkbe.global.domain.ICustomLocalDateTime
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.nio.charset.StandardCharsets
import java.util.Date

@Component
class TokenProvider(
    @Value("\${jwt.secret}")
    private val secretKey: String,

    @Value("\${jwt.access.expiration}")
    private val accessTokenExpiration: Long,

    @Value("\${jwt.refresh.expiration}")
    private val refreshTokenExpiration: Long,

    private val localDateTime: ICustomLocalDateTime,
    private val userRepository: UserPersistencePort
) {
    companion object {
        const val ACCESS_TOKEN_SUBJECT = "AccessToken"
        const val REFRESH_TOKEN_SUBJECT = "RefreshToken"
        const val EMAIL = "email"
    }

    fun createAccessToken(user: User): String {
        val key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))

        return Jwts.builder()
            .setSubject(ACCESS_TOKEN_SUBJECT)
            .claim(EMAIL, user.email)
            .setExpiration(
                Date.from(
                    localDateTime.plusHour(accessTokenExpiration)
                )
            )
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()!!
    }

    fun createRefreshToken(): String {
        val key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))

        return Jwts.builder()
            .setSubject(REFRESH_TOKEN_SUBJECT)
            .setExpiration(
                Date.from(
                    localDateTime.plusHour(refreshTokenExpiration)
                )
            )
            .compact()!!
    }

    @Transactional
    fun reIssueTokens(refreshToken: String): ReIssuedTokens {
        val user = userRepository.findByRefreshToken(refreshToken)
            ?: throw EntityNotFoundException("토큰 재발급 실패: 해당 refresh token을 가진 User가 존재하지 않습니다.")

        val newAccessToken: String = createAccessToken(user)
        val newRefreshToken: String = createRefreshToken()

        user.refreshToken = refreshToken

        return ReIssuedTokens(newAccessToken, newRefreshToken)
    }
}