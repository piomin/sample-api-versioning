package pl.piomin.services.versioning.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;

@ApiModel("Person")
public class PersonOld extends Person {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	public PersonOld() {

	}	
	
	public PersonOld(Long id, String name, Gender gender, LocalDate birthDate) {
		super(id, name, gender);
		this.birthDate = birthDate;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

}
