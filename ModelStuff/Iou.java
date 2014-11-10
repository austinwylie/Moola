import org.joda.time.DateTime;
import java.math.BigDecimal;

public abstract class Iou implements Comparable<Iou> {
    protected static final boolean REVERSE_SORT = false;
    protected String title;
    protected String notes;
    protected DateTime timestamp;

    protected Iou(String title, String notes) {
        this.title = title;
        this.notes = notes;
        this.timestamp = new DateTime();
    }

    protected Iou(String title, String notes, DateTime timestamp) {
        this.title = title;
        this.notes = notes;
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(Iou iou) {
        int direction = REVERSE_SORT ? -1 : 1;
        return direction * this.timestamp.compareTo(iou.getTimestamp());
    }

    public boolean isMonetary() {
        if (this.getClass().equals(MonetaryIou.class)) {
            return true;
        } else {
            return false;
        }
    }

    public String getTitle() {
        return title;
    }

    public String getNotes() {
        return notes;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public abstract BigDecimal getMonetaryValue();
}
