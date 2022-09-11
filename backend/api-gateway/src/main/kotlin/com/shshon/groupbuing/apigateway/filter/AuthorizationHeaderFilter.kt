package com.shshon.groupbuing.apigateway.filter

import com.shshon.groupbuing.apigateway.security.jwt.JwtTokenDecoder
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthorizationHeaderFilter(
    private val jwtTokenDecoder: JwtTokenDecoder
): AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>(Config::class.java) {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange: ServerWebExchange, chain: GatewayFilterChain ->
            val request = exchange.request
            val headers = request.headers
            if (hasAuthorizationToken(headers)) {
                return@GatewayFilter onError(
                    exchange,
                    "no authorization header",
                    HttpStatus.UNAUTHORIZED
                )
            }
            val authorizationHeader = headers[HttpHeaders.AUTHORIZATION]!![0]
            val jwt = authorizationHeader.replace("Bearer", "")
            if (!jwtTokenDecoder.isValidToken(jwt)) {
                return@GatewayFilter onError(
                    exchange,
                    "JWT token is not valid",
                    HttpStatus.UNAUTHORIZED
                )
            }
            chain.filter(exchange)
        }
    }

    private fun hasAuthorizationToken(headers: HttpHeaders) =
        !headers.containsKey(HttpHeaders.AUTHORIZATION) || headers[HttpHeaders.AUTHORIZATION]!!.isEmpty()


    private fun onError(exchange: ServerWebExchange, message: String, httpStatus: HttpStatus): Mono<Void> {
        val response = exchange.response
        response.statusCode = httpStatus
        log.error("uri : {}, message : {}", exchange.request.uri, message)
        return response.setComplete()
    }

    class Config {

    }
}