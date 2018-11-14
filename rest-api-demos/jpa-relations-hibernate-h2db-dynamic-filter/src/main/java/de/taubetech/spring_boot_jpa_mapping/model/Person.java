package de.taubetech.spring_boot_jpa_mapping.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "person")
@JsonFilter("personFilter")
public class Person implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	private String name;

	private int age;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "person")
	private PersonProfile personProfile;

	@OneToMany(mappedBy = "person")
	private Set<Skill> skills = new HashSet<Skill>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "person_party",
			joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "party_id", referencedColumnName = "id"))
	private Set<Party> parties = new HashSet<Party>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public PersonProfile getPersonProfile() {
		return personProfile;
	}

	public void setPersonProfile(PersonProfile personProfile) {
		this.personProfile = personProfile;
	}

	public Set<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

	public Set<Party> getParties() {
		return parties;
	}

	public void setParties(Set<Party> parties) {
		this.parties = parties;
	}

}