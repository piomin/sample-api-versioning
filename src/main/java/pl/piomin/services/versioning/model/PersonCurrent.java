package pl.piomin.services.versioning.model;

import io.swagger.annotations.ApiModel;

@ApiModel("Person")
public class PersonCurrent extends Person {

	private int age;

	public PersonCurrent() {

	}

	public PersonCurrent(Long id, String name, Gender gender, int age) {
		super(id, name, gender);
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
