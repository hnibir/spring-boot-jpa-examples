package de.taubetech.spring_boot_jpa_mapping.controller;

import de.taubetech.spring_boot_jpa_mapping.exception.ResourceNotFoundException;
import de.taubetech.spring_boot_jpa_mapping.model.Person;
import de.taubetech.spring_boot_jpa_mapping.model.PersonProfile;
import de.taubetech.spring_boot_jpa_mapping.repository.PersonProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/profiles")
public class PersonProfileController {

    @Autowired
    PersonProfileRepository personProfileRepository;

    @GetMapping()
    public List<PersonProfile> getProfiles() {
        return personProfileRepository.findAll();
    }

    @GetMapping(path = "/{profileId}")
    public PersonProfile getProfile(@PathVariable Long profileId) {
        Optional<PersonProfile> profile = personProfileRepository.findById(profileId);
        if(!profile.isPresent()) {
            throw new ResourceNotFoundException("GET: Profile not found with id " + profileId);
        }

        return profile.get();
    }

    @GetMapping(path = "/{profileId}/person")
    public Person getPersonByProfile(@PathVariable Long profileId) {
        Optional<PersonProfile> profile = personProfileRepository.findById(profileId);
        if(!profile.isPresent()) {
            throw new ResourceNotFoundException("GET (Person): Profile not found with id " + profileId);
        }
        System.out.println("Person id " + profile.get().getPerson().getId());
        return profile.get().getPerson();
    }
}
