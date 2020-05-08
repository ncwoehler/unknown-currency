package org.javamoney.moneta.spi;

import javax.money.CurrencyUnit;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Singleton implementation of a currency cache for {@link OwnCurrencyProvider}.
 */
public enum CurrencyCache {

    INSTANCE;

    private final Logger logger = Logger.getLogger(CurrencyCache.class.getName());

    /**
     * Internal shared cache of {@link CurrencyUnit} instances.
     */
    private final Map<String, CurrencyUnit> cacheContent = loadCurrencies();

    private Map<String, CurrencyUnit> loadCurrencies() {
        logger.info(() -> "Loading currencies");
        Set<Currency> availableCurrencies = Currency.getAvailableCurrencies();
        Map<String, CurrencyUnit> result = new HashMap<>(availableCurrencies.size());
        for (Currency jdkCurrency : availableCurrencies) {
            CurrencyUnit cu = new JDKCurrencyAdapter(jdkCurrency);
            result.put(cu.getCurrencyCode(), cu);
        }
        logger.info(() -> "Finished loading");
        return Collections.unmodifiableMap(result);
    }

    public Map<String, CurrencyUnit> getCache() {
        logger.info(() -> "Getting cache");
        return cacheContent;
    }

}

