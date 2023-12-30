package com.beomsu.kafkaotalkbe.app.friends.adapter.out

import com.beomsu.kafkaotalkbe.app.friends.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface UserRepository {
}

@Repository
class UserRepositoryImpl(private val userRepository: UserJpaRepository): UserRepository {

}


@Repository
interface UserJpaRepository: JpaRepository<User, Long> {
}