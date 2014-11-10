import org.joda.time.DateTime;
import java.math.BigDecimal;

public class MonetaryIou extends Iou {
    private BigDecimal monetaryValue;

    /**
     * Initializes a MonetaryIOU with the specified values and a current timestamp.
     */
    public MonetaryIou(String title, String notes, BigDecimal monetaryValue) {
        super(title, notes);
        this.monetaryValue = monetaryValue;
    }

    /**
     * Initializes a MonetaryIOU with the specified values.
     */
    public MonetaryIou(String title, String notes, BigDecimal monetaryValue, DateTime timestamp) {
        super(title, notes, timestamp);
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