package com.beomsu.kafkaotalkbe.global.domain

import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

interface ICustomLocalDateTime {
    fun plusHour(hours: Long): Instant
}

@Component
class CustomLocalDateTime: ICustomLocalDateTime {
    override fun plusHour(hours: Long): Instant {
        return LocalDateTime.now()
            .plusHours(hours)
            .atZone(ZoneId.of("Asia/Seoul"))
            .toInstant()
    }
}