package de.taubetech.spring_boot_jpa_mapping.controller;

import de.taubetech.spring_boot_jpa_mapping.exception.BadRequestException;
import de.taubetech.spring_boot_jpa_mapping.exception.ResourceNotFoundException;
import de.taubetech.spring_boot_jpa_mapping.model.Person;
import de.taubetech.spring_boot_jpa_mapping.model.Skill;
import de.taubetech.spring_boot_jpa_mapping.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/skills")
public class SkillController {

    @Autowired
    SkillRepository skillRepository;

    @GetMapping()
    public List<Skill> getSkills() {
        return skillRepository.findAll();
    }

    @GetMapping(path = "/{skillId}")
    public Skill getSkill(@PathVariable Long skillId) {
        Optional<Skill> skill = skillRepository.findById(skillId);
        if(!skill.isPresent()) {
            throw new ResourceNotFoundException("GET: Skill not found with id " + skillId);
        }

        return skill.get();
    }

    @PostMapping()
    public Skill createSkill(@Valid @RequestBody Skill skill) {
        Skill skillRet = null;
        try {
            skillRet = skillRepository.save(skill);
        } catch(Exception ex) {
            throw new BadRequestException("POST: Skill could not be created");
        }

        return skillRet;
    }

    @PutMapping(path = "/{skillId}")
    public Skill updateSkill(@PathVariable Long skillId, @Valid @RequestBody Skill skillRequest) {
        return skillRepository.findById(skillId)
                .map(skill -> {
                    skill.setName(skillRequest.getName());
                    skill.setLevel(skillRequest.getLevel());
                    return skillRepository.save(skill);
                }).orElseThrow(()-> new ResourceNotFoundException("PUT: Skill not found with id " + skillId));
    }

    @DeleteMapping(path = "/{skillId}")
    public ResponseEntity<?> deleteSkill(@PathVariable Long skillId) {
        return skillRepository.findById(skillId)
                .map(skill -> {
                    skillRepository.delete(skill);
                    return ResponseEntity.ok().build();
                }).orElseThrow(()-> new ResourceNotFoundException("DELETE: Skill not found with id " + skillId));
    }

    @GetMapping(path = "/{skillId}/person")
    public Person getPersonBySkill(@PathVariable Long skillId) {
        Optional<Skill> skill = skillRepository.findById(skillId);
        if(!skill.isPresent()) {
            throw new ResourceNotFoundException("GET (Person): Skill not found with id " + skillId);
        }

        return skill.get().getPerson();
    }
}
