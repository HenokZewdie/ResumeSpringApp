package ResumeSpringPackage;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.NamedQuery;
import java.util.List;

/**
 * Created by student on 6/29/17.
 */
public interface PersonRepository extends CrudRepository<Person, Integer> {

    List<Person> findByEmail(String email);
    List<Person> findByFirstName(String fName);
}
