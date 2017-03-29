CREATE TABLE Persons_films(
    person uuid,
    kind text,
    film uuid,
	PRIMARY KEY(person, film, kind)
);

CREATE TABLE Films_persons(
    person uuid,
    kind text,
    film uuid,
	PRIMARY KEY(film, person, kind)
);

-- Table: Cinema_sessions
CREATE TABLE Cinema_sessions (
    film uuid,
    cinema uuid,
    price int,
    hall int,
    time timestamp,
	PRIMARY KEY(film, cinema, time)
) ;

-- Table: Cinemas
CREATE TABLE Cinemas (
	id uuid,
    name text,
    adress text,
    rating float,
	PRIMARY KEY((name, adress), id)
) ;

CREATE INDEX Cinemas_rating 
on Cinemas 
(rating)
;

-- Table: Films
CREATE TABLE Films (
    id uuid,
    name text,
    year int,
    duration int,
    description text,
    rating float,
	countries set<text>,
	PRIMARY KEY(name, id)
);

CREATE INDEX Films_rating 
on Films 
(rating)
;

CREATE TABLE Films_genres (
    film uuid,
    genre text,
    PRIMARY KEY (film, genre)
);

CREATE TABLE Genres_films(
    film uuid,
    genre text,
    PRIMARY KEY (genre, film)
);

CREATE TABLE Friends (
    user text,
    friend text,
	PRIMARY KEY(user, friend)
) ;

-- Table: Persons
CREATE TABLE Persons (
    id uuid,
    name text,
    birthday date,
    country text,
	occupations set<text>,
	PRIMARY KEY(name, id)
);

-- Table: Reviews
CREATE TABLE Reviews (
    id timeuuid PRIMARY KEY,
    mark int,
    review text
);

CREATE TABLE Users_reviews (
    review timeuuid,
    author text,
	PRIMARY_KEY(author, review)
);

CREATE TABLE Films_reviews (
    review timeuuid,
    film uuid,
	PRIMARY_KEY(film, review)
);

-- Table: Users
CREATE TABLE Users (
    nickname text PRIMARY KEY,
    email text,
    password text,
    birthday date,
    registration_date date,
    country text,
    city text
);


