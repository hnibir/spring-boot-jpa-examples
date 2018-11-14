package de.taubetech.spring_boot_jpa_mapping.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import de.taubetech.spring_boot_jpa_mapping.exception.BadRequestException;
import de.taubetech.spring_boot_jpa_mapping.exception.ResourceNotFoundException;
import de.taubetech.spring_boot_jpa_mapping.model.Party;
import de.taubetech.spring_boot_jpa_mapping.model.Person;
import de.taubetech.spring_boot_jpa_mapping.repository.PersonRepository;
import de.taubetech.spring_boot_jpa_mapping.repository.SkillRepository;
import de.taubetech.spring_boot_jpa_mapping.util.JsonFilterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/people")
public class PersonController {

	@Autowired
	private PersonRepository personRepo;

	@Autowired
	private SkillRepository skillRepository;

	/**
	 * Find all person entries with its profile, skills and parties
	 * @return list of person
	 */
	@RequestMapping(method = RequestMethod.GET)
	public MappingJacksonValue getPeople() {
		// Map retrieved data entries to Jackson
		MappingJacksonValue mapping = new MappingJacksonValue(personRepo.findAll());

		// Set filters to mapped data entries
		mapping.setFilters(JsonFilterUtil.FilterPerson.getMiniPersonFilter());

		return mapping;
	}

	@RequestMapping(value = "/{personId}", method = RequestMethod.GET)
	public MappingJacksonValue getPerson(@PathVariable long personId) {
		Optional<Person> person = personRepo.findById(personId);

		if (!person.isPresent()) {
			throw new ResourceNotFoundException("GET: Person not found with id " + personId);
		}

		// Map retrieved data entries to Jackson
		MappingJacksonValue mapping = new MappingJacksonValue( person.get());

		// Set filters to mapped data entries
		mapping.setFilters(JsonFilterUtil.FilterPerson.getDetailPersonFilter());

		return mapping;
	}

	@RequestMapping(method = RequestMethod.POST)
	public MappingJacksonValue addPerson(@Valid @RequestBody Person person) {
		Person personRet = null;
		try {
			personRet = personRepo.save(person);
		} catch (Exception ex) {
			throw new BadRequestException("POST: Person could not be created");
		}

		// Map retrieved data entries to Jackson
		MappingJacksonValue mapping = new MappingJacksonValue(personRet);

		// Set filters to mapped data entries
		mapping.setFilters(JsonFilterUtil.FilterPerson.getMiniPersonFilter());

		return mapping;
	}

	@PutMapping(path = "/{personId}")
	public MappingJacksonValue updatePerson(@PathVariable Long personId, @Valid @RequestBody Person personRequest) {
		Person personRet = personRepo.findById(personId)
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

		// Map retrieved data entries to Jackson
		MappingJacksonValue mapping = new MappingJacksonValue( personRet);

		// Set filters to mapped data entries
		mapping.setFilters(JsonFilterUtil.FilterPerson.getDetailPersonFilter());

		return mapping;
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
	public MappingJacksonValue getPersonParties(@PathVariable long personId) {
		Optional<Person> person = personRepo.findById(personId);
		if(!person.isPresent()) {
			throw new ResourceNotFoundException("GET (Parties): Person not found with id " + personId);
		}

		// Map retrieved data entries to Jackson
		MappingJacksonValue mapping = new MappingJacksonValue(person.get().getParties());

		// Set filters to mapped data entries
		mapping.setFilters(JsonFilterUtil.FilterParty.getMiniPartyFilter());

		return mapping;
	}
}
