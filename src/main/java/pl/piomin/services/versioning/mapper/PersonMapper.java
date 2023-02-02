package pl.piomin.services.versioning.mapper;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Component;

import pl.piomin.services.versioning.model.PersonCurrent;
import pl.piomin.services.versioning.model.PersonOld;

@Component
public class PersonMapper {

	public PersonCurrent map(PersonOld person) {
		int age = Period.between(person.getBirthDate(), LocalDate.now()).getYears();
		return new PersonCurrent(person.getId(), person.getName(), person.getGender(), age);
	}
	
}
