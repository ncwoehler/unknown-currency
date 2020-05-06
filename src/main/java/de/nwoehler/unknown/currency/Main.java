package de.nwoehler.unknown.currency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.util.stream.IntStream;

@EnableScheduling
@SpringBootApplication
public class Main {

    static {
        Monetary.getCurrency("EUR");
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 1 && args[0].equalsIgnoreCase("standalone")) {
            runStandalone();
        } else {
            SpringApplication.run(Main.class, args);
        }
    }

    public static void runStandalone() throws Exception {
        IntStream.range(0, 10).parallel().forEach(i -> Monetary.getCurrency("EUR"));
    }

}
