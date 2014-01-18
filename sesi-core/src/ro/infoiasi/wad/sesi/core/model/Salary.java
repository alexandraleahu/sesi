package ro.infoiasi.wad.sesi.core.model;

import com.google.common.base.Objects;

public class Salary  {

    private double numericalValue;

    private Currency currency;

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("numericalValue", numericalValue)
                .add("currency", currency)
                .toString();
    }

    public double getNumericalValue() {
        return numericalValue;
    }

    public void setNumericalValue(double numericalValue) {
        this.numericalValue = numericalValue;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
