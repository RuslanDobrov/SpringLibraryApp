package ruslan.dobrov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruslan.dobrov.repositories.PersonBookRepository;

@Service
@Transactional(readOnly = true)
public class PersonBookService {

    private final PersonBookRepository personBookRepository;

    @Autowired
    public PersonBookService(PersonBookRepository personBookRepository) {
        this.personBookRepository = personBookRepository;
    }
}
