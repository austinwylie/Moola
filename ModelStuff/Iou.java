import org.joda.time.DateTime;
import java.math.BigDecimal;

public abstract class Iou implements Comparable<Iou> {
    private static final boolean REVERSE_SORT = false;
    private String title;
    private String notes;
    private DateTime timestamp;

    /**
     * Initializes a NonMonetaryIOU with the specified values and a current timestamp.
     */
    public Iou(String title, BigDecimal monetaryValue) {
        this.title = title;
        this.monetaryValue = monetaryValue;
        this.timestamp = new DateTime();
    }

    /**
     * Initializes a NonMonetaryIOU with the specified values.
     */
    public Iou(String title, BigDecimal monetaryValue, DateTime timestamp) {
        this.title = title;
        this.monetaryValue = monetaryValue
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(Iou iou) {
        int direction = REVERSE_SORT ? -1 : 1;
        return direction * this.timestamp.compareTo(iou.getTimestamp());
    }

    public String getTitle() {
        return title;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getMonetaryValue() {
        return monetaryValue;
    }

    public BigDecimal setMonetaryValue(BigDecimal monetaryValue) {
        this.monetaryValue = monetaryValue;
    }
}
