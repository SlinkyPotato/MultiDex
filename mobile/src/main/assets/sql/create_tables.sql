CREATE TABLE poke_moves (
	id int(6) unsigned primary key,
    name varchar(60) not null,
    description text,
    effect text,
    notes text
);
