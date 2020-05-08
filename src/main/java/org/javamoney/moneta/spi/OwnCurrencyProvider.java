package org.javamoney.moneta.spi;

import javax.money.CurrencyQuery;
import javax.money.CurrencyUnit;
import javax.money.spi.CurrencyProviderSpi;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Own implementation of a {@link CurrencyUnit} based on the {@link JDKCurrencyProvider}
 */
public class OwnCurrencyProvider implements CurrencyProviderSpi {

    private static final Logger LOGGER = Logger.getLogger(OwnCurrencyProvider.class.getName());

    @Override
    public String getProviderName(){
        LOGGER.info(() -> "getProviderName()");
        return "own-provider";
    }

    /**
     * Return a {@link CurrencyUnit} instances matching the given
     * {@link javax.money.CurrencyContext}.
     *
     * @param currencyQuery the {@link javax.money.CurrencyContext} containing the parameters determining the query. not null.
     * @return the corresponding {@link CurrencyUnit}, or null, if no such unit
     * is provided by this provider.
     */
    public Set<CurrencyUnit> getCurrencies(CurrencyQuery currencyQuery){
        LOGGER.info(() -> "Getting currencies");
        Set<CurrencyUnit> result = new HashSet<>();
        if(!currencyQuery.getCurrencyCodes().isEmpty()) {
            for (String code : currencyQuery.getCurrencyCodes()) {
                CurrencyUnit cu = getCache().get(code);
                if (cu != null) {
                    result.add(cu);
                }
                else{
                    Pattern pattern = Pattern.compile(code);
                    getCache().keySet().stream()
                            .filter(k -> pattern.matcher(k).matches())
                            .forEach(r -> result.add(getCache().get(r)));
                }
            }
            return result;
        }
        if(!currencyQuery.getCountries().isEmpty()) {
            for (Locale country : currencyQuery.getCountries()) {
                CurrencyUnit cu = getCurrencyUnit(country);
                if (cu != null) {
                    result.add(cu);
                }
            }
            return result;
        }
        if(!currencyQuery.getNumericCodes().isEmpty()) {
            for (Integer numCode : currencyQuery.getNumericCodes()) {
                List<CurrencyUnit> cus = getCurrencyUnits(numCode);
                result.addAll(cus);
            }
            return result;
        }
        // No constraints defined, return all.
        result.addAll(getCache().values());
        return result;
    }

    private List<CurrencyUnit> getCurrencyUnits(int numCode) {
        LOGGER.info(() -> "getCurrencyUnits(int numCode)");
        List<CurrencyUnit> result = new ArrayList<>();
        for(Currency currency: Currency.getAvailableCurrencies()){
            if(currency.getNumericCode()==numCode){
                result.add(getCache().get(currency.getCurrencyCode()));
            }
        }
        return result;
    }


    private CurrencyUnit getCurrencyUnit(Locale locale) {
        LOGGER.info(() -> "getCurrencyUnit(Locale locale)");

        Currency cur;
        try {
            cur = Currency.getInstance(locale);
            if (Objects.nonNull(cur)) {
                return getCache().get(cur.getCurrencyCode());
            }
        } catch (Exception e) {
            if (Logger.getLogger(getClass().getName()).isLoggable(Level.FINEST)) {
                Logger.getLogger(getClass().getName()).finest(
                        "No currency for locale found: " + locale);
            }
        }
        return null;
    }

    private Map<String, CurrencyUnit> getCache() {
        return CurrencyCache.INSTANCE.getCache();
    }

}

