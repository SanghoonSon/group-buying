package com.shshon.groupbuing.apigateway

import com.shshon.groupbuing.apigateway.property.ServiceProperties
import com.shshon.groupbuing.apigateway.property.TokenProperty
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties(TokenProperty::class, ServiceProperties::class)
class ApiGatewayApplication

fun main(args: Array<String>) {
    runApplication<ApiGatewayApplication>(*args)
}
