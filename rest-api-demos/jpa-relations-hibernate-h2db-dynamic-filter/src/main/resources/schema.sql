CREATE TABLE person (
    id BIGINT PRIMARY KEY auto_increment,
    name VARCHAR(32),
    age INT,
);

CREATE TABLE person_profile (
    id BIGINT PRIMARY KEY auto_increment,
    city VARCHAR(100),
    country VARCHAR(100),
    person_id BIGINT REFERENCES person (id)
);

CREATE TABLE skill (
    id BIGINT PRIMARY KEY auto_increment,
    person_id BIGINT REFERENCES person (id),
    name VARCHAR(16),
    level VARCHAR(16)
);

CREATE TABLE party (
    id BIGINT PRIMARY KEY auto_increment,
    location VARCHAR(64),
    party_date TIMESTAMP,
);

CREATE TABLE person_party (
  person_id BIGINT NOT NULL REFERENCES person (id),
  party_id BIGINT NOT NULL REFERENCES party (id),
  PRIMARY KEY (person_id, party_id),
);
