package de.taubetech.spring_boot_jpa_mapping.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "party")
@JsonFilter("partyFilter")
public class Party implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	private String location;

	@Column(name = "party_date")
	@JsonFormat(pattern = "YYYY-MM-dd")
	private Date date;

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "parties")
	private Set<Person> persons = new HashSet<Person>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<Person> getPersons() {
		return persons;
	}

	public void setPersons(Set<Person> persons) {
		this.persons = persons;
	}

}