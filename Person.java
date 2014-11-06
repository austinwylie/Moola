import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Person implements Comparable<Person> {
    protected static final boolean REVERSE_SORT = false;
    private String name;
    private String phoneNumber;
    private String email;
    private List<Iou> ious;

    public Person(String name) {
        this.name = name;
        this.ious = new LinkedList<Iou>();
    }

    public List<Iou> getIous() {
        return ious;
    }

    public List<Iou> getIousSorted() {
        Collections.sort(ious);
        return ious;
    }

    public BigDecimal getTotalOwedAmount() {
        BigDecimal sum = new BigDecimal(0.0);

        for (Iou iou : ious) {
            sum = sum.add(iou.getMonetaryValue());
        }

        return sum;
    }

    public void addIou(Iou iou) {
        ious.add(0, iou);
    }

    public int getIouCount() {
        return ious.size();
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int compareTo(Person person) {
        int direction = REVERSE_SORT ? -1 : 1;
        int result = 0;

        if (ious.isEmpty() && person.getIous().isEmpty()) {
            result = name.compareTo(person.name);
        } else if (ious.isEmpty()) {
            result = 1;
        } else if (person.getIous().isEmpty()) {
            result = -1;
        } else {
            result = this.getIous().get(0).compareTo(person.getIous().get(0));
            result *= direction;
        }

        return result;
    }
}
