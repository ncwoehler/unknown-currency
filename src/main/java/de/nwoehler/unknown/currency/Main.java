package de.nwoehler.unknown.currency;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        final List<CurrencyUnit> currencyUnits = IntStream.range(0, 10)
                .parallel()
                .mapToObj(i -> Monetary.getCurrency("EUR"))
                .collect(Collectors.toList());
        System.out.println("Done - " + currencyUnits.size());
    }

}
