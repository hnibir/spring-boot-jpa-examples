package de.taubetech.spring_boot_jpa_mapping.repository;

import de.taubetech.spring_boot_jpa_mapping.model.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {

}
