package com.github.ayastrebov.unknown.currency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.money.CurrencyUnit;
import javax.money.Monetary;

public class Main {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        try {
            List<Task> tasks = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                tasks.add(new Task());
            }
            List<Future<CurrencyUnit>> futures = executorService.invokeAll(tasks);
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
