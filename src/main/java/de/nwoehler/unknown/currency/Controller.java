package de.nwoehler.unknown.currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@RestController
public class Controller {

    private static final String[] CURRENCIES = {"EUR", "CHF", "PLN", "DKK", "GBP"};

    // Use CountDownLatch to ensure a minimum number of requests to be made
    private final CountDownLatch latch = new CountDownLatch(10);
    private final Random random = new Random();
    private final Logger logger = LoggerFactory.getLogger(Controller.class);

    @PostConstruct
    public void scheduleShutdown() {
        new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
            }
            System.exit(0);
        }).start();
    }

    @GetMapping("/")
    public ResponseEntity<Object> getData() throws IOException {
        try(InputStream resourceAsStream = Controller.class.getResourceAsStream("/body.json")) {
            String body = new BufferedReader(new InputStreamReader(resourceAsStream))
                    .lines().collect(Collectors.joining("\n"));
            body = body.replace("EUR", CURRENCIES[random.nextInt(CURRENCIES.length)]);

            return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (RuntimeException | IOException e) {
            logger.error("Error...", e);
            System.exit(1);
            throw e;
        } finally {
            latch.countDown();
        }
    }

}
