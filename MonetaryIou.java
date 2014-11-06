package austin.moola;

import org.joda.time.DateTime;

import java.math.BigDecimal;

public class MonetaryIou extends Iou {
    private BigDecimal monetaryValue;

    public MonetaryIou(String title, String description, BigDecimal monetaryValue) {
        super(title, description);
        this.monetaryValue = monetaryValue;
    }

    public MonetaryIou(String title, String description, BigDecimal monetaryValue, DateTime timestamp) {
        super(title, description, timestamp);
        this.monetaryValue = monetaryValue;
    }

    public void setMonetaryValue(BigDecimal monetaryValue) {
        this.monetaryValue = monetaryValue;
    }

    @Override
    public BigDecimal getMonetaryValue() {
        return monetaryValue;
    }
}
