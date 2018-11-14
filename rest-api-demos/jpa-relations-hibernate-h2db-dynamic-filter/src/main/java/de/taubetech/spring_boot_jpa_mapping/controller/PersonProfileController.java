package de.taubetech.spring_boot_jpa_mapping.controller;

import de.taubetech.spring_boot_jpa_mapping.exception.BadRequestException;
import de.taubetech.spring_boot_jpa_mapping.exception.ResourceNotFoundException;
import de.taubetech.spring_boot_jpa_mapping.model.Party;
import de.taubetech.spring_boot_jpa_mapping.model.Person;
import de.taubetech.spring_boot_jpa_mapping.model.PersonProfile;
import de.taubetech.spring_boot_jpa_mapping.repository.PersonProfileRepository;
import de.taubetech.spring_boot_jpa_mapping.util.JsonFilterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/profiles")
public class PersonProfileController {

    @Autowired
    PersonProfileRepository personProfileRepository;

    @GetMapping()
    public MappingJacksonValue getProfiles() {
        // Map retrieved data entries to Jackson
        MappingJacksonValue mapping = new MappingJacksonValue(personProfileRepository.findAll());

        // Set filters to mapped data entries
        mapping.setFilters(JsonFilterUtil.FilterPersonProfile.getMiniProfileFilter());

        return mapping;
    }

    @GetMapping(path = "/{profileId}")
    public MappingJacksonValue getProfile(@PathVariable Long profileId) {
        Optional<PersonProfile> profile = personProfileRepository.findById(profileId);
        if(!profile.isPresent()) {
            throw new ResourceNotFoundException("GET: Profile not found with id " + profileId);
        }

        // Map retrieved data entries to Jackson
        MappingJacksonValue mapping = new MappingJacksonValue(profile.get());

        // Set filters to mapped data entries
        mapping.setFilters(JsonFilterUtil.FilterPersonProfile.getDetailProfileFilter());

        return mapping;
    }

    @PostMapping()
    public MappingJacksonValue addProfile(@Valid @RequestBody PersonProfile profile) {
        PersonProfile profileRet = null;
        try {
            profileRet = personProfileRepository.save(profile);
        } catch (Exception ex) {
            throw new BadRequestException("POST: Profile could not be created");
        }

        // Map retrieved data entries to Jackson
        MappingJacksonValue mapping = new MappingJacksonValue(profileRet);

        // Set filters to mapped data entries
        mapping.setFilters(JsonFilterUtil.FilterParty.getMiniPartyFilter());

        return mapping;
    }

    @PutMapping(path = "/{profileId}")
    public MappingJacksonValue updateParty(@PathVariable Long profileId, @Valid @RequestBody PersonProfile profileRequest) {
        PersonProfile profileRet = personProfileRepository.findById(profileId)
                .map(personProfile -> {
                    personProfile.setCity(profileRequest.getCity());
                    personProfile.setCountry(profileRequest.getCountry());
                    return personProfileRepository.save(personProfile);
                }).orElseThrow(() -> new ResourceNotFoundException("PUT: Profile not found with id " + profileId));

        // Map retrieved data entries to Jackson
        MappingJacksonValue mapping = new MappingJacksonValue(profileRet);

        // Set filters to mapped data entries
        mapping.setFilters(JsonFilterUtil.FilterPersonProfile.getMiniProfileFilter());

        return mapping;
    }

    @RequestMapping(value = "/{profileId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteParty(@PathVariable long profileId) {
        return personProfileRepository.findById(profileId)
                .map(personProfile -> {
                    personProfileRepository.delete(personProfile);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("DELETE: Profile not found with id " + profileId));
    }


    @GetMapping(path = "/{profileId}/person")
    public MappingJacksonValue getPersonByProfile(@PathVariable Long profileId) {
        Optional<PersonProfile> profile = personProfileRepository.findById(profileId);
        if(!profile.isPresent()) {
            throw new ResourceNotFoundException("GET (Person): Profile not found with id " + profileId);
        }

        // Map retrieved data entries to Jackson
        MappingJacksonValue mapping = new MappingJacksonValue(profile.get().getPerson());

        // Set filters to mapped data entries
        mapping.setFilters(JsonFilterUtil.FilterPerson.getMiniPersonFilter());

        return mapping;
    }
}
