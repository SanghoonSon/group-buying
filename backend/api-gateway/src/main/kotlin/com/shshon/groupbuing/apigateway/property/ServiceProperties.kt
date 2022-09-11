package com.shshon.groupbuing.apigateway.property

import com.shshon.groupbuing.apigateway.exception.ServiceUriNotFoundException
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "service")
data class ServiceProperties(val details: List<Details>) {

    data class Details(
        val id: String,
        val uri: String
    )

    fun getUri(id: String): String {
        return this.details.stream()
            .filter { it.id == id }
            .findFirst()
            .orElseThrow { ServiceUriNotFoundException("${id}로 등록 된 Service Uri를 찾을 수 없습니다.") }
            .uri
    }
}
