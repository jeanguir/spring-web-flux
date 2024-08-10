package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(EventHandler handler) {
        return RouterFunctions
                .nest(path("/events"),
                        route(GET("/"), handler::getAllEvents)
                                .andRoute(GET("/{id}"), handler::getEventById)
                                .andRoute(POST("/"), handler::createEvent)
                                .andRoute(PUT("/{id}"), handler::updateEvent)
                                .andRoute(DELETE("/{id}"), handler::deleteEvent)
                );
    }
}

