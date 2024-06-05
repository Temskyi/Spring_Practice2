package ua.kpi.its.lab.rest.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router
import ua.kpi.its.lab.rest.svc.TrainService
import ua.kpi.its.lab.rest.dto.TrainRequest

@Configuration
class WebConfig {

    @Bean
    fun functionalRoutes(trainService: TrainService): RouterFunction<*> = router {
        fun <T : Any> wrapNotFoundError(call: () -> T?): ServerResponse {
            return try {
                val result = call()
                if (result != null) {
                    ServerResponse.ok().body(result)
                } else {
                    ServerResponse.notFound().build()
                }
            } catch (e: IllegalArgumentException) {
                ServerResponse.notFound().build()
            }
        }

        "/fn".nest {
            "/trains".nest {
                GET("") {
                    ServerResponse.ok().body(trainService.read())
                }
                GET("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    wrapNotFoundError { trainService.readById(id) }
                }
                POST("") { req ->
                    val train = req.body(TrainRequest::class.java)
                    ServerResponse.ok().body(trainService.create(train))
                }
                PUT("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    val train = req.body(TrainRequest::class.java)
                    wrapNotFoundError { trainService.updateById(id, train) }
                }
                DELETE("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    wrapNotFoundError { trainService.deleteById(id) }
                }
            }
        }
    }
}
