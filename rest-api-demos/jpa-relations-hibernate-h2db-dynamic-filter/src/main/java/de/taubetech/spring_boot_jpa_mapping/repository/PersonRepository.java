package de.taubetech.spring_boot_jpa_mapping.repository;

import de.taubetech.spring_boot_jpa_mapping.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}