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
import de.taubetech.spring_boot_jpa_mapping.repository.PartyRepository;
import de.taubetech.spring_boot_jpa_mapping.util.JsonFilterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/parties")
public class PartyController {

	@Autowired
	private PartyRepository partyRepo;

	@RequestMapping(method = RequestMethod.GET)
	public MappingJacksonValue getParties() {
		// Map retrieved data entries to Jackson
		MappingJacksonValue mapping = new MappingJacksonValue(partyRepo.findAll());

		// Set filters to mapped data entries
		mapping.setFilters(JsonFilterUtil.FilterParty.getMiniPartyFilter());

		return mapping;
	}

	@RequestMapping(value = "/{partyId}", method = RequestMethod.GET)
	public MappingJacksonValue getParty(@PathVariable long partyId) {
		Optional<Party> party = partyRepo.findById(partyId);
		if(!party.isPresent()) {
			throw new ResourceNotFoundException("GET: Party not found with id " + partyId);
		}

		// Map retrieved data entries to Jackson
		MappingJacksonValue mapping = new MappingJacksonValue(party.get());

		// Set filters to mapped data entries
		mapping.setFilters(JsonFilterUtil.FilterParty.getMiniPartyFilter());

		return mapping;
	}

	@RequestMapping(method = RequestMethod.POST)
	public MappingJacksonValue addParty(@Valid @RequestBody Party party) {
		Party partyRet = null;
		try {
			partyRet = partyRepo.save(party);
		} catch (Exception ex) {
			throw new BadRequestException("POST: Party could not be created");
		}

		// Map retrieved data entries to Jackson
		MappingJacksonValue mapping = new MappingJacksonValue(partyRet);

		// Set filters to mapped data entries
		mapping.setFilters(JsonFilterUtil.FilterParty.getMiniPartyFilter());

		return mapping;
	}

	@PutMapping(path = "/{partyId}")
	public MappingJacksonValue updateParty(@PathVariable Long partyId, @Valid @RequestBody Party partyRequest) {
		Party partyRet = partyRepo.findById(partyId)
				.map(party -> {
					party.setLocation(partyRequest.getLocation());
					return partyRepo.save(party);
				}).orElseThrow(() -> new ResourceNotFoundException("PUT: Party not found with id " + partyId));

		// Map retrieved data entries to Jackson
		MappingJacksonValue mapping = new MappingJacksonValue(partyRet);

		// Set filters to mapped data entries
		mapping.setFilters(JsonFilterUtil.FilterParty.getMiniPartyFilter());

		return mapping;
	}

	@RequestMapping(value = "/{partyId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteParty(@PathVariable long partyId) {
		return partyRepo.findById(partyId)
				.map(party -> {
					partyRepo.delete(party);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("DELETE: Party not found with id " + partyId));
	}

	@RequestMapping(value = "/{partyId}/people", method = RequestMethod.GET)
	public MappingJacksonValue getPersonParties(@PathVariable long partyId) {
		Optional<Party> party = partyRepo.findById(partyId);
		if(!party.isPresent()) {
			throw new ResourceNotFoundException("GET (Parties): Party not found with id " + partyId);
		}

		// Map retrieved data entries to Jackson
		MappingJacksonValue mapping = new MappingJacksonValue(party.get().getPersons());

		// Set filters to mapped data entries
		mapping.setFilters(JsonFilterUtil.FilterPerson.getMiniPersonFilter());

		return mapping;
	}
}
