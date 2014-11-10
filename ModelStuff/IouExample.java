import org.joda.time.DateTime;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        IouManager manager = new IouManager();

        Person austin = new Person("Austin Wylie");
        Person august = new Person("August Smith");
        Person robert = new Person("Robert Wylie");
        Person esha = new Person("Esha Joshi");

        austin.addIou(new MonetaryIou(
                "Lunch",
                "Austin forgot his wallet, so I paid for his sandwich at VG's",
                new BigDecimal(7.75)));

        austin.addIou(new MonetaryIou(
                "Movie tickets",
                null,
                new BigDecimal(-10.50)));

        austin.addIou(new NonMonetaryIou(
                "Basketball",
                null,
                new DateTime(2014, 10, 15, 13, 45, 30)));

        robert.addIou(new NonMonetaryIou(
                "Pokemon cards",
                null));

        august.addIou(new MonetaryIou(
                "New headlights",
                null,
                new BigDecimal(50.00)));

        manager.addPerson(austin);
        manager.addPerson(august);
        manager.addPerson(robert);

        printPeople(manager.getPeople());

        printPeople(manager.getAutocompletedPeople("au"));
        // returns Austin and August
        
        printPeople(manager.getAutocompletedPeople("wy"));
        // returns Austin and Robert
    }

    public static void printPeople(List<Person> people) {
        System.out.println();
        for (Person p : people) {
            System.out.println(p.getName() + ", " + p.getTotalOwedAmount().toString());
            printIous(p.getIous());
        }
    }

    public static void printIous(List<Iou> ious) {
        for (Iou iou : ious) {
            System.out.print(iou.getTitle() + ", " + iou.getTimestamp().toString() + ", " + iou.isMonetary() + "\n");
        }
    }
}
