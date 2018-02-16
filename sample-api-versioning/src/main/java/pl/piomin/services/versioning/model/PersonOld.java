package pl.piomin.services.versioning.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;

@ApiModel("Person")
public class PersonOld extends Person {

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;

	public PersonOld() {

	}	
	
	public PersonOld(Long id, String name, Gender gender, Date birthDate) {
		super(id, name, gender);
		this.birthDate = birthDate;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

}
