package de.taubetech.spring_boot_jpa_mapping.repository;

import de.taubetech.spring_boot_jpa_mapping.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {

}
