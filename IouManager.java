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

    public List<Person> getPeople() {
        return people;
    }

    public List<Person> getPeopleSorted() {
        Collections.sort(people);
        return people;
    }

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
