DROP TABLE Persons_films;
DROP TABLE Films_persons;
DROP INDEX Sessions_time;
DROP INDEX Cinema_sessions
DROP INDEX Cinemas_rating;
DROP TABLE Cinema_sessions;
DROP TABLE Cinemas;
DROP INDEX Films_rating; 
DROP TABLE Films;
DROP TABLE Films_genres;
DROP TABLE Genres_films;
DROP TABLE Friends;
DROP TABLE Persons;
DROP TABLE Reviews;
DROP TABLE Users_reviews;
DROP TABLE Films_reviews; 
DROP TABLE Users;

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

CREATE TABLE Cinema_sessions (
    film uuid,
    cinema uuid,
    price int,
    hall int,
    time timestamp,
	PRIMARY KEY(film, cinema, time)
);

CREATE INDEX Cinemas_sessions 
on Cinema_sessions 
(cinema)
;
CREATE INDEX Sessions_time 
on Cinema_sessions 
(time)
;

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

CREATE TABLE Films (
    id uuid,
    name text,
    year int,
    duration int,
    description text,
    rating float,
	countries set<text>,
	genres set<text>,
	PRIMARY KEY(name, id)
);

CREATE INDEX ON Films (genres);
CREATE INDEX ON Films (year);
CREATE INDEX Films_rating 
on Films 
(rating)
;

CREATE TABLE Friends (
    user text,
    friend text,
	PRIMARY KEY(user, friend)
) ;

CREATE TABLE Persons (
    id uuid,
    name text,
    birthday date,
    country text,
	occupations set<text>,
	PRIMARY KEY(name, id)
);

CREATE TABLE Reviews (
    id timeuuid PRIMARY KEY,
    mark int,
    review text
);

CREATE TABLE Users_reviews (
    review timeuuid,
    author text,
	PRIMARY KEY(author, review)
);

CREATE TABLE Films_reviews (
    review timeuuid,
    film uuid,
	PRIMARY KEY(film, review)
);

CREATE TABLE Users (
    nickname text PRIMARY KEY,
    email text,
    password text,
    birthday date,
    registration_date date,
    country text,
    city text
);

INSERT INTO Persons (
    "id",
    "name",
    "birthday",
    "country",
	"occupations"
) 
VALUES(
	85b3f57e-9d2c-4cb3-a952-ea9763c39931,
	'Brad Pitt', 
	'1963-12-18', 
	'USA', 
	{'actor', 'producer'}
);

INSERT INTO Persons (
    "id",
    "name",
    "birthday",
    "country",
	"occupations"
) 
VALUES(
	b93527aa-938d-471c-97a5-901e71095a4f,
	'Doug Liman', 
	'1965-07-24', 
	'USA', 
	{'producer'}
);

INSERT INTO Persons (
    "id",
    "name",
    "birthday",
    "country",
	"occupations"
) 
VALUES(
	5cfe396a-706c-4506-a8e2-fb933a127e32,
	'Angelina Jolie', 
	'1975-06-04', 
	'USA', 
	{'actor', 'producer'}
);

	
SELECT * FROM Persons WHERE name = 'Angelina Jolie';

INSERT INTO Kinopoisk.Films(
    "id",
    "name",
    "year",
    "duration",
    "description",
	"countries",
	"genres"
)
VALUES(
	a172443a-755a-49c0-ac94-462fe923cbd7,
	$$Mr. & Mrs. Smith$$, 
	2005, 
	120, 
	$$Happy endings are for stories that haven't finished yet$$,
	{'USA'}, 
	{'action_film', 'thriller', 'melodrama'}
);

SELECT * FROM Films WHERE genres CONTAINS 'thriller';

INSERT INTO Persons_films(
    "person",
    "kind",
    "film"
)
VALUES(
	5cfe396a-706c-4506-a8e2-fb933a127e32,
	'actor',
	a172443a-755a-49c0-ac94-462fe923cbd7
);

INSERT INTO Persons_films(
    "person",
    "kind",
    "film"
)
VALUES(
	85b3f57e-9d2c-4cb3-a952-ea9763c39931,
	'actor',
	a172443a-755a-49c0-ac94-462fe923cbd7
);

INSERT INTO Persons_films(
    "person",
    "kind",
    "film"
)
VALUES(
	b93527aa-938d-471c-97a5-901e71095a4f,
	'director',
	a172443a-755a-49c0-ac94-462fe923cbd7
);

INSERT INTO Films_persons(
    "person",
    "kind",
    "film"
)
VALUES(
	5cfe396a-706c-4506-a8e2-fb933a127e32,
	'actor',
	a172443a-755a-49c0-ac94-462fe923cbd7
);

INSERT INTO Films_persons(
    "person",
    "kind",
    "film"
)
VALUES(
	85b3f57e-9d2c-4cb3-a952-ea9763c39931,
	'actor',
	a172443a-755a-49c0-ac94-462fe923cbd7
);

INSERT INTO Films_persons(
    "person",
    "kind",
    "film"
)
VALUES(
	b93527aa-938d-471c-97a5-901e71095a4f,
	'director',
	a172443a-755a-49c0-ac94-462fe923cbd7
);

INSERT INTO Reviews(
	"id",
    "mark",
    "review"
)
VALUES(
    23020440-1593-11e7-a251-1716dbe5f4e1,
    3,
    'Very, very bad'
);

INSERT INTO Users_reviews(
	"review",
	"author") 
VALUES(
    23020440-1593-11e7-a251-1716dbe5f4e1,
    'Superuser'
);

INSERT INTO Films_reviews(
	"review",
	"film")
VALUES(
    23020440-1593-11e7-a251-1716dbe5f4e1,
    a172443a-755a-49c0-ac94-462fe923cbd7
);
				 	
INSERT INTO Kinopoisk.Users(
    "nickname",
    "email",
    "password",
    "birthday",
    "registration_date",
    "country",
    "city"
) 
VALUES(
    'Superuser',
    'mail@mail.ru',
    '1234',
    '1996-11-06',
    toDate(toTimestamp(now())),
    'Russia',
    'Spb'
);

INSERT INTO Kinopoisk.Users(
    "nickname",
    "email",
    "password",
    "birthday",
    "registration_date",
    "country",
    "city"
) 
VALUES(
    'Someuser',
    'm@gmail.ru',
    '1234',
    '1992-04-10',
    toDate(toTimestamp(now())),
    'Russia',
    'Moscow'
);

INSERT INTO Friends 
("user", "friend")
VALUES(
   'Someuser',
   'Superuser'
) ;
INSERT INTO Friends 
("user", "friend")
VALUES(
   'Superuser',
   'Someuser'
) ;
