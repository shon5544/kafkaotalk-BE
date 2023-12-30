package com.beomsu.kafkaotalkbe.app.friends.application.port.out

import com.beomsu.kafkaotalkbe.app.friends.domain.User

interface UserPersistencePort {
    fun findByRefreshToken(refreshToken: String): User?
}