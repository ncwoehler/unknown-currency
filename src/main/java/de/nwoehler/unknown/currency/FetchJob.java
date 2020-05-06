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

@Component
public class FetchJob {

    private Logger logger = LoggerFactory.getLogger(FetchJob.class);

    @Autowired
    private RestOperations restOperations;

    @Scheduled(fixedDelay = 1)
    public void fetchValue1() {
        doFetch("fetchValue1");
    }

    @Scheduled(fixedDelay = 1)
    public void fetchValue2() {
        doFetch("fetchValue2");
    }

    @Scheduled(fixedDelay = 1)
    public void fetchValue3() {
        doFetch("fetchValue3");
    }

    private void doFetch(String jobName) {
        final RequestEntity<Body> requestEntity = new RequestEntity<>(HttpMethod.GET, URI.create("http://localhost:8080"));
        final ResponseEntity<Body> exchange = restOperations.exchange(requestEntity, Body.class);
        logger.info("{} - {} - {}", jobName, exchange.getStatusCode(), exchange.getBody().getValue());
        if(exchange.getStatusCode() != HttpStatus.OK) {
            System.exit(1);
        }
    }

}
