package de.nwoehler.unknown.currency;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.UnknownCurrencyException;
import javax.money.spi.ServiceProvider;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        final List<CurrencyUnit> currencyUnits = IntStream.range(0, 2)
                .parallel()
                .mapToObj(Main::getCurrency)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        currencyUnits.addAll(IntStream.range(0, 2)
                .mapToObj(Main::getCurrency)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));

        LOGGER.info(() -> "Done - Successful retrieved: " + currencyUnits.size());
    }

    private static CurrencyUnit getCurrency(int run) {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        LOGGER.info(() -> "getCurrency(" + run + ") - ContextClassLoader - " + contextClassLoader);
        for (ServiceProvider sp : ServiceLoader.load(ServiceProvider.class)) {
            LOGGER.info(() -> "getCurrency(" + run + ") - ServiceProvider - " + sp);
        }
        LOGGER.info(() -> "getCurrency(" + run + ") - CurrencyProviderNames - " + Monetary.getCurrencyProviderNames());
        LOGGER.info(() -> "getCurrency(" + run + ") - DefaultCurrencyProviderChain - " + Monetary.getDefaultCurrencyProviderChain());
        try {
            return Monetary.getCurrency("EUR");
        } catch (UnknownCurrencyException e) {
            LOGGER.log(Level.SEVERE, e, () -> "getCurrency(" + run + ") failed.");
            return null;
        }
    }

}
