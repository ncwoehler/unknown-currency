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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Component
public class FetchJob {

    private final Logger logger = LoggerFactory.getLogger(FetchJob.class);

    @Autowired
    private RestOperations restOperations;

    private int counter = 0;

    @Scheduled(fixedDelay = 1)
    public void fetchValue() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        try {
            List<Future<Void>> futures = executorService.invokeAll(Arrays.asList(createTask(), createTask(), createTask(), createTask()));
            for (Future<Void> f : futures) {
                f.get();
            }
        } catch (ExecutionException ex) {
            logger.error("Error...", ex);
            System.exit(1);
        } catch (InterruptedException e) {
            // ignore
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    private Task createTask() {
        return new Task(restOperations, counter++);
    }

    private static class Task implements Callable<Void> {

        private final Logger logger = LoggerFactory.getLogger(Task.class);

        private final RestOperations restOperations;
        private final int id;

        public Task(RestOperations restOperations, int id) {
            this.restOperations = restOperations;
            this.id = id;
        }

        @Override
        public Void call() {
            doFetch();
            return null;
        }

        private void doFetch() {
            final RequestEntity<Body> requestEntity = new RequestEntity<>(HttpMethod.GET, URI.create("http://localhost:8080"));
            final ResponseEntity<Body> exchange = restOperations.exchange(requestEntity, Body.class);
            logger.info("{} {} - {} - {}", "Task", id, exchange.getStatusCode(), exchange.getBody().getValue());
            if (exchange.getStatusCode() != HttpStatus.OK) {
                System.exit(1);
            }
        }
    }

}
