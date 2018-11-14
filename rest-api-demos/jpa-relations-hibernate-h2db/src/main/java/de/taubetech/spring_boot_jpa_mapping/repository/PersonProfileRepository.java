package de.taubetech.spring_boot_jpa_mapping.repository;

import de.taubetech.spring_boot_jpa_mapping.model.PersonProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonProfileRepository extends JpaRepository<PersonProfile, Long> {

}
