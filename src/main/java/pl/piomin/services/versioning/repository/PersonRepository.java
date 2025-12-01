package pl.piomin.services.versioning.repository;

import org.springframework.stereotype.Repository;
import pl.piomin.services.versioning.mapper.PersonMapper;
import pl.piomin.services.versioning.model.Person;
import pl.piomin.services.versioning.model.PersonCurrent;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {

	private List<Person> persons = new ArrayList<>();
	private PersonMapper mapper;

	public PersonRepository(PersonMapper mapper) {
		this.mapper = mapper;
	}

	public Person add(Person person) {
		Person p = mapper.mapToOld(person);
		p.setId((long) (persons.size()+1));
		persons.add(p);
		if (person instanceof PersonCurrent)
			return mapper.mapToCurrent(p);
		return p;
	}
	
	public Person update(Person person) {
		persons.set(person.getId().intValue() - 1, person);
		return person;
	}
	
	public Person update(Long id, Person person) {
		Person p = mapper.mapToOld(person);
		persons.set(id.intValue() - 1, p);
		return mapper.mapToCurrent(p);
	}
	
	public Person findById(Long id) {
		return persons.stream()
				.filter(a -> a.getId().equals(id))
				.findFirst()
				.orElse(null);
	}
	
	public void delete(Long id) {
		persons.remove(id.intValue());
	}
	
}
