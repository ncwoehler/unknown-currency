package de.nwoehler.unknown.currency;

import javax.money.Monetary;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        IntStream.range(0, 10).parallel().forEach(i -> Monetary.getCurrency("EUR"));
        System.out.println("Done");
    }

}
