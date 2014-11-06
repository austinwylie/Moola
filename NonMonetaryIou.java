import org.joda.time.DateTime;
import java.math.BigDecimal;

public class NonMonetaryIou extends Iou {
    public NonMonetaryIou(String title, String description) {
        super(title, description);
    }

    public NonMonetaryIou(String title, String description, DateTime timestamp) {
        super(title, description, timestamp);
    }

    @Override
    public BigDecimal getMonetaryValue() {
        return new BigDecimal(0.0);
    }
}
