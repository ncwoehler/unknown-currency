package de.nwoehler.unknown.currency;

import javax.money.MonetaryAmount;

public class Body {

    private MonetaryAmount value;

    public MonetaryAmount getValue() {
        return value;
    }

    public void setValue(MonetaryAmount value) {
        this.value = value;
    }
}
