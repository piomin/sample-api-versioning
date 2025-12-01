package pl.piomin.services.versioning.mapper;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Component;

import pl.piomin.services.versioning.model.Person;
import pl.piomin.services.versioning.model.PersonCurrent;
import pl.piomin.services.versioning.model.PersonOld;

@Component
public class PersonMapper {

	public Person mapToCurrent(Person person) {
		if (person instanceof PersonCurrent)
			return person;
		return new PersonCurrent(person.getId(), person.getName(), person.getGender(),
				Period.between(((PersonOld) person).getBirthDate(), LocalDate.now()).getYears());
	}

	public Person mapToOld(Person person) {
		if (person instanceof PersonOld)
			return person;
		return new PersonOld(person.getId(), person.getName(), person.getGender(),
				LocalDate.now().minusYears(((PersonCurrent) person).getAge()));
	}
	
}
