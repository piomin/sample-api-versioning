package pl.piomin.services.versioning.model;

public abstract class Person {

	private Long id;
	private String name;
	private Gender gender;

	public Person() {

	}
	
	public Person(Long id, String name, Gender gender) {
		this.id = id;
		this.name = name;
		this.gender = gender;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

}
