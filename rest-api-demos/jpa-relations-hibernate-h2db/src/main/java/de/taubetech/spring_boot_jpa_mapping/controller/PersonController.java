package de.taubetech.spring_boot_jpa_mapping.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.taubetech.spring_boot_jpa_mapping.exception.BadRequestException;
import de.taubetech.spring_boot_jpa_mapping.exception.ResourceNotFoundException;
import de.taubetech.spring_boot_jpa_mapping.model.Party;
import de.taubetech.spring_boot_jpa_mapping.model.Person;
import de.taubetech.spring_boot_jpa_mapping.repository.PersonRepository;
import de.taubetech.spring_boot_jpa_mapping.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/people")
public class PersonController {

	@Autowired
	private PersonRepository personRepo;

	@Autowired
	private SkillRepository skillRepository;


	@RequestMapping(method = RequestMethod.GET)
	public List<Person> getPeople() {
		return personRepo.findAll();
	}

/**
	@RequestMapping(method = RequestMethod.GET)
	public MappingJacksonValue getPeople() {
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "age", "skills", "parties");

		FilterProvider filters = new SimpleFilterProvider().addFilter("personFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(personRepo.findAll());

		mapping.setFilters(filters);

		return mapping;
	}
 */

	@RequestMapping(value = "/{personId}", method = RequestMethod.GET)
	public Person getPerson(@PathVariable long personId) {
		Optional<Person> person = personRepo.findById(personId);

		if (!person.isPresent()) {
			throw new ResourceNotFoundException("GET: Person not found with id " + personId);
		}

		return person.get();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Person addPerson(@Valid @RequestBody Person person) {
		Person personRet = null;
		try {
			personRet = personRepo.save(person);
		} catch (Exception ex) {
			throw new BadRequestException("POST: Person could not be created");
		}
		return  personRet;
	}

	@PutMapping(path = "/{personId}")
	public Person updatePerson(@PathVariable Long personId, @Valid @RequestBody Person personRequest) {
		return personRepo.findById(personId)
				.map(person -> {
					person.setName(personRequest.getName());
					person.setAge(personRequest.getAge());
					if(personRequest.getSkills().size() > 0) {
						person.setSkills(personRequest.getSkills());
						System.out.println("Skill length " + personRequest.getSkills().size());
						skillRepository.saveAll(personRequest.getSkills());
					}
					return personRepo.save(person);
				}).orElseThrow(() -> new ResourceNotFoundException("PUT: Person not found with id " + personId));
	}

	@RequestMapping(value = "/{personId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletePerson(@PathVariable long personId) {
		return personRepo.findById(personId)
				.map(person -> {
					personRepo.delete(person);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("DELETE: Person not found with id " + personId));
	}
	
	@RequestMapping(value = "/{personId}/parties", method = RequestMethod.GET)
	public Set<Party> getPersonParties(@PathVariable long personId) {
		Optional<Person> person = personRepo.findById(personId);
		if(!person.isPresent()) {
			throw new ResourceNotFoundException("GET (Parties): Person not found with id " + personId);
		}

		return person.get().getParties();
	}
}
