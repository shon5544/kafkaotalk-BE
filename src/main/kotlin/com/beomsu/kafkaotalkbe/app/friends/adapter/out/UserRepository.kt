package com.beomsu.kafkaotalkbe.app.friends.adapter.out

import com.beomsu.kafkaotalkbe.app.friends.application.port.out.UserPersistencePort
import com.beomsu.kafkaotalkbe.app.friends.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepository(private val userJpaRepository: UserJpaRepository):
    UserPersistencePort {

}


@Repository
interface UserJpaRepository: JpaRepository<User, Long> {
}