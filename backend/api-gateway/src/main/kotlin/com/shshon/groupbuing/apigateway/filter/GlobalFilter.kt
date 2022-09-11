package com.shshon.groupbuing.apigateway.filter

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class GlobalFilter: AbstractGatewayFilterFactory<GlobalFilter.Config>(Config::class.java) {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val request = exchange.request
            val response = exchange.response

            if(config.preLogger) {
                log.info("Filter Start - id : ${request.id}, method: ${request.methodValue}, url : ${request.uri}")
            }

            return@GatewayFilter chain.filter(exchange).then(Mono.fromRunnable {
                if(config.postLogger) {
                    log.info("Filter End - id : ${request.id}, method: ${request.methodValue}, status : ${response.statusCode}")
                }
            })
        }
    }

    data class Config(
        val preLogger: Boolean,
        val postLogger: Boolean
    )
}
