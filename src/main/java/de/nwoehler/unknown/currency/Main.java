package de.nwoehler.unknown.currency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@EnableScheduling
@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length == 1 && args[0].equalsIgnoreCase("standalone")) {
            runStandalone();
        } else {
            SpringApplication.run(Main.class, args);
        }
    }

    public static void runStandalone() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        try {
            List<Future<CurrencyUnit>> futures = executorService.invokeAll(Arrays.asList(new Task(), new Task(), new Task(), new Task(), new Task()));
            for (Future<CurrencyUnit> f : futures) {
                f.get();
            }
        } catch (ExecutionException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);
    }

    private static class Task implements Callable<CurrencyUnit> {

        @Override
        public CurrencyUnit call() throws Exception {
            return Monetary.getCurrency("EUR");
        }
    }
}
