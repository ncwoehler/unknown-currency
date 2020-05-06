package de.nwoehler.unknown.currency;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@RestController
public class Controller {

    private static final String[] CURRENCIES = { "EUR", "CHF", "PLN", "DKK", "GBP" };
    private static final Random random = new Random();

    // Use CountDownLatch to ensure a minimum number of requests to be made
    private static final CountDownLatch LATCH = new CountDownLatch(10);

    @PostConstruct
    public void scheduleShutdown() {
        new Thread(() -> {
            try {
                LATCH.await();
            } catch (InterruptedException e) { }
            System.exit(0);
        }).start();
    }

    @GetMapping("/")
    public ResponseEntity<Object> getData() {
        try {
            final InputStream resourceAsStream = Controller.class.getResourceAsStream("/body.json");
            String body = new BufferedReader(new InputStreamReader(resourceAsStream))
                    .lines().collect(Collectors.joining("\n"));
            body = body.replace("EUR", CURRENCIES[random.nextInt(CURRENCIES.length)]);

            return new ResponseEntity<>(body, HttpStatus.OK);
        } finally {
            LATCH.countDown();
        }
    }

}
