package pl.piomin.services.versioning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.piomin.services.versioning.model.PersonCurrent;
import pl.piomin.services.versioning.model.PersonOld;
import pl.piomin.services.versioning.repository.PersonRepository;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	@Qualifier("oldRepository")
	PersonRepository oldRepository;
	@Autowired
	@Qualifier("currentRepository")
	PersonRepository currentRepository;

	@PostMapping({"/v1.0", "/v1.1"})
	public PersonOld add(@RequestBody PersonOld person) {
		return (PersonOld) oldRepository.add(person);
	}

	@PostMapping("/v1.2")
	public PersonCurrent add(@RequestBody PersonCurrent person) {
		return (PersonCurrent) currentRepository.add(person);
	}
	
	@PutMapping("/v1.0")
	@Deprecated
	public PersonOld update(@RequestBody PersonOld person) {
		return (PersonOld) oldRepository.update(person);
	}
	
	@PutMapping("/v1.1/{id}")
	public PersonOld update(@PathVariable("id") Long id, @RequestBody PersonOld person) {
		return (PersonOld) oldRepository.update(person);
	}
	
	@PutMapping("/v1.2/{id}")
	public PersonCurrent update(@PathVariable("id") Long id, @RequestBody PersonCurrent person) {
		return (PersonCurrent) currentRepository.update(person);
	}
	
	@GetMapping({"/v1.0/{id}", "/v1.1/{id}"})
	public PersonOld findByIdOld(@PathVariable("id") Long id) {
		return (PersonOld) oldRepository.findById(id);
	}
	
	@GetMapping("/v1.2/{id}")
	public PersonCurrent findById(@PathVariable("id") Long id) {
		return (PersonCurrent) currentRepository.findById(id);
	}
	
	@DeleteMapping({"/v1.0/{id}", "/v1.1/{id}", "/v1.2/{id}"})
	public void delete(@PathVariable("id") Long id) {
		oldRepository.delete(id);
	}
	
}
