import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class IouManager {
    private List<Person> people;

    public IouManager() {
        people = new ArrayList<Person>();
    }

    public void addPerson(Person person) {
        people.add(0, person);
    }

    /**
     * @return The people whose IOUs are tracked.
     * No assumptions can be made about the List's ordering.
     */
    public List<Person> getPeople() {
        return people;
    }

    /**
     * @return The people whose IOUs are tracked, ordered by their most recent IOU in reverse
     * chronological order.
     */
    public List<Person> getPeopleSorted() {
        Collections.sort(people);
        return people;
    }

    /**
     * Given a string, returns the people whose name starts with that substring. If this is called
     * with each new keystroke, it acts as live autocompletion. This is naive, but
     * if there's only a handful of users, performance shouldn't be too bad.
     * We can speed it up if it comes to that.
     */
    public List<Person> getAutocompletedPeople(String partialName) {
        List<Person> results = new ArrayList<Person>();

        for (Person p : this.people) {
            Collection<String> tokens = Arrays.asList(p.getName().split("\\s+"));

            boolean matchFound = false;

            for (String token : tokens) {
                if (token.toUpperCase().startsWith(partialName.toUpperCase())) {
                    matchFound = true;
                    break;
                }
            }

            if (matchFound) {
                results.add(p);
                continue;
            }
        }

        Collections.sort(results);
        return results;
    }
}
