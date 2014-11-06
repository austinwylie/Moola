import org.joda.time.DateTime;
import java.math.BigDecimal;

public class NonMonetaryIou extends Iou {
    public NonMonetaryIou(String title, String notes) {
        super(title, notes);
    }

    public NonMonetaryIou(String title, String notes, DateTime timestamp) {
        super(title, notes, timestamp);
    }

    @Override
    public BigDecimal getMonetaryValue() {
        return new BigDecimal(0.0);
    }
}
