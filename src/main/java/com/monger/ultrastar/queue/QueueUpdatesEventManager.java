package com.monger.ultrastar.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;

@Service
public class QueueUpdatesEventManager {

    private final Logger logger = LoggerFactory.getLogger( QueueUpdatesEventManager.class );
    private final Map<String, SseEmitter> clients;
    public QueueUpdatesEventManager() {
        clients = new HashMap<>();
    }

    public void subscribe( SseEmitter client, String id ) {
        logger.debug("New client subscription: {}", id );
        clients.put( id, client );
    }

    public void unsubscribe( String id ) {
        logger.debug("Client unsubscribed: {}", id );
        clients.remove( id );
    }

    public void submitQueueUpdateEvent() {
        logger.debug("New queue update event" );
        logger.info("Clients connected {} ", clients.size() );
        List<String> deadClients = new ArrayList<>();
        for ( String id : clients.keySet() ) {
            try {
                SseEmitter client = clients.get( id );
                client.send("QueueUpdatedEvent");
            } catch (IOException e) {
                logger.error("Error sending event to client {} ", id  );
                deadClients.add( id );
            }
        }
        logger.info("Dead clients {} ", deadClients.size() );
        deadClients.forEach(( id )->clients.remove( id ));
    }
}
