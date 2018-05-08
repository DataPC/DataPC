
CREATE TABLE Computer(
	id SERIAL PRIMARY KEY,
	location varchar
);

CREATE TABLE Component_type(
	id SERIAL PRIMARY KEY,
	name VARCHAR UNIQUE
);

CREATE TABLE Manufacturer(
	id SERIAL PRIMARY KEY,
	name VARCHAR UNIQUE
);

CREATE TABLE Model(
	id SERIAL PRIMARY KEY,
	manufacturer_id SERIAL references Manufacturer(id),
	component_type_id INT references Component_type(id),
	name VARCHAR
);

CREATE TABLE Component(
	id SERIAL PRIMARY KEY,
	computer_id INT references Computer(id),
	model_id INT references Model(id)
);

CREATE TABLE Service_type(
	id SERIAL PRIMARY KEY,
	name VARCHAR UNIQUE
);

CREATE TABLE Service(
	id SERIAL PRIMARY KEY,
	computer_id INT references Computer(id),
	service_type_id INT references Service_type(id),
	servie_date DATE
);