package com.shshon.groupbuing.apigateway.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "token")
data class TokenProperty(
    val secret: String
)