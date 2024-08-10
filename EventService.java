package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Mono<Event> getEventById(String id) {
        return eventRepository.findById(id);
    }

    public Flux<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Mono<Event> createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Mono<Event> updateEvent(String id, Event eventDetails) {
        return eventRepository.findById(id)
                .flatMap(existingEvent -> {
                    existingEvent.setName(eventDetails.getName());
                    existingEvent.setLocation(eventDetails.getLocation());
                    return eventRepository.save(existingEvent);
                });
    }

    public Mono<Void> deleteEvent(String id) {
        return eventRepository.deleteById(id);
    }
}
