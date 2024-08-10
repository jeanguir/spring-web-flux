package com.example.webfluxdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;

@Component
public class EventHandler {

    @Autowired
    private EventService eventService;

    public Mono<ServerResponse> getAllEvents(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(eventService.getAllEvents(), Event.class);
    }

    public Mono<ServerResponse> getEventById(ServerRequest request) {
        String eventId = request.pathVariable("id");
        return eventService.getEventById(eventId)
                .flatMap(event -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(event))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> createEvent(ServerRequest request) {
        Mono<Event> eventMono = request.bodyToMono(Event.class);
        return eventMono.flatMap(eventService::createEvent)
                .flatMap(event -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(event));
    }

    public Mono<ServerResponse> updateEvent(ServerRequest request) {
        String eventId = request.pathVariable("id");
        Mono<Event> eventMono = request.bodyToMono(Event.class);
        return eventMono.flatMap(event -> eventService.updateEvent(eventId, event))
                .flatMap(updatedEvent -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(updatedEvent))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> deleteEvent(ServerRequest request) {
        String eventId = request.pathVariable("id");
        return eventService.deleteEvent(eventId)
                .flatMap(v -> ok().build())
                .switchIfEmpty(notFound().build());
    }
}

