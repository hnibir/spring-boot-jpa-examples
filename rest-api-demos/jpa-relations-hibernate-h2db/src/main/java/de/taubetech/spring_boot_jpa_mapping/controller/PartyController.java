package de.taubetech.spring_boot_jpa_mapping.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.taubetech.spring_boot_jpa_mapping.exception.BadRequestException;
import de.taubetech.spring_boot_jpa_mapping.exception.ResourceNotFoundException;
import de.taubetech.spring_boot_jpa_mapping.model.Party;
import de.taubetech.spring_boot_jpa_mapping.model.Person;
import de.taubetech.spring_boot_jpa_mapping.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/parties")
public class PartyController {

	@Autowired
	private PartyRepository partyRepo;

	@RequestMapping(method = RequestMethod.GET)
	public List<Party> getParties() {
		return partyRepo.findAll();
	}

	@RequestMapping(value = "/{partyId}", method = RequestMethod.GET)
	public Party getParty(@PathVariable long partyId) {
		Optional<Party> party = partyRepo.findById(partyId);
		if(!party.isPresent()) {
			throw new ResourceNotFoundException("GET: Party not found with id " + partyId);
		}

		return party.get();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Party addParty(@Valid @RequestBody Party party) {
		Party partyRet = null;
		try {
			partyRet = partyRepo.save(party);
		} catch (Exception ex) {
			throw new BadRequestException("POST: Party could not be created");
		}
		return  partyRet;
	}

	@PutMapping(path = "/{partyId}")
	public Party updateParty(@PathVariable Long partyId, @Valid @RequestBody Party partyRequest) {
		return partyRepo.findById(partyId)
				.map(party -> {
					party.setLocation(partyRequest.getLocation());
					return partyRepo.save(party);
				}).orElseThrow(() -> new ResourceNotFoundException("PUT: Party not found with id " + partyId));
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
	public Set<Person> getPersonParties(@PathVariable long partyId) {
		Optional<Party> party = partyRepo.findById(partyId);
		if(!party.isPresent()) {
			throw new ResourceNotFoundException("GET (Parties): Party not found with id " + partyId);
		}

		return party.get().getPersons();
	}
}
