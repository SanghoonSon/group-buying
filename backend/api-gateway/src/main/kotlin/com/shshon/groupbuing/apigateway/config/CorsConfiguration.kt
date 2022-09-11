package com.shshon.groupbuing.apigateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import java.util.*

@Configuration
class CorsConfiguration {

    @Bean
    fun corsWebFilter(): CorsWebFilter {
        val corsConfig = org.springframework.web.cors.CorsConfiguration()
        corsConfig.allowedOriginPatterns = Collections.singletonList("*")
        corsConfig.maxAge = 3600L
        corsConfig.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        corsConfig.addAllowedHeader("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfig)

        return CorsWebFilter(source)
    }
}