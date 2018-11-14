INSERT INTO person (id, name, age) VALUES
	(1, 'Peter', 25),
	(2, 'John', 30),
	(3, 'Katie', 18);

INSERT INTO person_profile (id, city, country, person_id) VALUES
    (1, 'Stuttgart', 'Germany', 1),
    (2, 'Berlin', 'Germany', 2),
    (3, 'Paris', 'France', 3);
	
INSERT INTO skill (id, person_id, name, level) VALUES
	(1, 1, 'Juggling', 'GOOD'),
	(2, 1, 'Dancing', 'AWESOME'),
	(3, 2, 'Juggling', 'AWESOME'),
	(4, 2, 'Story-telling', 'GODLIKE'),
	(5, 3, 'Singing', 'GOOD');

INSERT INTO party (id, location, party_date) VALUES
	(1, 'Old Folks Club', '2016-09-20'),
	(2, 'Luxury Yacht Party', '2016-12-05');
	
INSERT INTO person_party (person_id, party_id) VALUES
	(1, 1),
	(1, 2),
	(2, 1),
	(3, 2);
	