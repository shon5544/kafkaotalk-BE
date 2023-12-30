package com.beomsu.kafkaotalkbe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaotalkBeApplication

fun main(args: Array<String>) {
	runApplication<KafkaotalkBeApplication>(*args)
}
