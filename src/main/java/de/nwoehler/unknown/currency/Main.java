package de.nwoehler.unknown.currency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.money.Monetary;
import java.util.stream.IntStream;

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
        IntStream.range(0, 10).parallel().forEach(i -> Monetary.getCurrency("EUR"));
    }

}
