package com.beomsu.kafkaotalkbe.global.dto

import com.beomsu.kafkaotalkbe.global.auth.dto.AuthenticationFailDto
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component

@Component
class ResponseSender(
    _objectMapper: ObjectMapper,
) {

    init {
        objectMapper = _objectMapper
    }

    companion object {
        private lateinit var objectMapper: ObjectMapper

        fun setBadRequestResponse(response: HttpServletResponse, msg: String) {
            val responseDto = AuthenticationFailDto(false, msg)
            val result: String = objectMapper.writeValueAsString(responseDto)

            response.status = HttpServletResponse.SC_BAD_REQUEST
            response.characterEncoding = "UTF-8"
            response.contentType = "application/json"
            response.writer.write(result)
        }
    }
}