package de.nwoehler.unknown.currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.util.stream.IntStream;

@Component
public class FetchJob {

    private final Logger logger = LoggerFactory.getLogger(FetchJob.class);

    @Autowired
    private RestOperations restOperations;

    private int counter = 0;

    @Scheduled(fixedDelay = 1)
    public void fetchValue() {
        IntStream.range(0, 10).parallel().forEach(this::doFetch);
    }

    private void doFetch(int id) {
        try {
            final RequestEntity<Body> requestEntity = new RequestEntity<>(HttpMethod.GET, URI.create("http://localhost:8080"));
            final ResponseEntity<Body> exchange = restOperations.exchange(requestEntity, Body.class);
            logger.info("{} {} - {} - {}", "Task", id, exchange.getStatusCode(), exchange.getBody().getValue());
            if (exchange.getStatusCode() != HttpStatus.OK) {
                System.exit(1);
            }
        } catch (RuntimeException e) {
            logger.error("Error...", e);
            System.exit(1);
        }
    }

}
