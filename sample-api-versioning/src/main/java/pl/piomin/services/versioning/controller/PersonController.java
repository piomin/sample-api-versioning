package pl.piomin.services.versioning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.piomin.services.versioning.mapper.PersonMapper;
import pl.piomin.services.versioning.model.PersonCurrent;
import pl.piomin.services.versioning.model.PersonOld;
import pl.piomin.services.versioning.repository.PersonRepository;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	PersonMapper mapper;
	@Autowired
	PersonRepository repository;
	
	@PostMapping(produces = {"application/vnd.piomin.app-v1.0+json", "application/vnd.piomin.app-v1.1+json"})
	public PersonOld add(@RequestBody PersonOld person) {
		return (PersonOld) repository.add(person);
	}

	@PostMapping(produces = "application/vnd.piomin.app-v1.2+json")
	public PersonCurrent add(@RequestBody PersonCurrent person) {
		return mapper.map((PersonOld) repository.add(person));
	}
	
	@PutMapping(produces = "application/vnd.piomin.app-v1.0+json")
	@Deprecated
	public PersonOld update(@RequestBody PersonOld person) {
		return (PersonOld) repository.update(person);
	}
	
	@PutMapping(value = "/{id}", produces = "application/vnd.piomin.app-v1.1+json")
	public PersonOld update(@PathVariable("id") Long id, @RequestBody PersonOld person) {
		return (PersonOld) repository.update(person);
	}
	
	@PutMapping(value = "/{id}", produces = "application/vnd.piomin.app-v1.2+json")
	public PersonCurrent update(@PathVariable("id") Long id, @RequestBody PersonCurrent person) {
		return mapper.map((PersonOld) repository.update(person));
	}
	
	@GetMapping(name = "findByIdOld", value = "/{idOld}", produces = {"application/vnd.piomin.app-v1.0+json", "application/vnd.piomin.app-v1.1+json"})
	@Deprecated
	public PersonOld findByIdOld(@PathVariable("idOld") Long id) {
		return (PersonOld) repository.findById(id);
	}
	
	@GetMapping(name = "findById", value = "/{id}", produces = "application/vnd.piomin.app-v1.2+json")
	public PersonCurrent findById(@PathVariable("id") Long id) {
		return mapper.map((PersonOld) repository.findById(id));
	}
	
	@DeleteMapping(value = "/{id}", produces = {"application/vnd.piomin.app-v1.0+json", "application/vnd.piomin.app-v1.1+json", "application/vnd.piomin.app-v1.2+json"})
	public void delete(@PathVariable("id") Long id) {
		repository.delete(id);
	}
	
}
