CREATE TABLE Activities (
    id int  PRIMARY KEY,
    person int,
    kind int,
    film int
);

CREATE INDEX Activities_person 
on Activities 
(person)
;

CREATE INDEX Activities_film 
on Activities 
(film)
;

CREATE INDEX Activities_kind 
on Activities 
(kind)
;

-- Table: Cinema_sessions
CREATE TABLE Cinema_sessions (
    id int  PRIMARY KEY,
    film int,
    cinema int,
    price int,
    hall int,
    time timestamp
) ;

CREATE INDEX Cinema_sessions_film 
on Cinema_sessions 
(film)
;

CREATE INDEX Cinema_sessions_cinema 
on Cinema_sessions 
(cinema)
;

-- Table: Cinemas
CREATE TABLE Cinemas (
    id int  PRIMARY KEY,
    name varchar,
    adress varchar,
    rating float
) ;

CREATE INDEX Cinemas_name 
on Cinemas 
(name)
;

-- Table: Films
CREATE TABLE Films (
    id int PRIMARY KEY,
    name varchar,
    year int,
    duration int,
    description text,
    rating float
) ;

CREATE INDEX Films_name 
on Films 
(name)
;

CREATE INDEX Films_rating 
on Films 
(rating)
;

-- Table: Films_genres
CREATE TABLE Films_genres (
    id int  PRIMARY KEY,
    film int,
    genre int
) ;

CREATE INDEX Films_genres 
on Films_genres 
(film)
;

CREATE INDEX Genres_films 
on Films_genres 
(genre)
;

CREATE TABLE Friends (
    id int  PRIMARY KEY,
    user1 int,
    user2 int
) ;

-- Table: Genres
CREATE TABLE Genres (
    id int  PRIMARY KEY,
    name varchar
) ;

-- Table: Kinds_of_activities
CREATE TABLE Kinds_of_activities (
    id int PRIMARY KEY,
    name varchar
) ;

-- Table: Persons
CREATE TABLE Persons (
    id int PRIMARY KEY,
    name varchar,
    birthday date,
    country varchar
);

CREATE INDEX Persons_name 
on Persons 
(name)
;

-- Table: Reviews
CREATE TABLE Reviews (
    id int  PRIMARY KEY,
    mark int,
    review text,
    "date" date,
    author int,
    film int
) ;

CREATE INDEX Reviews_film 
on Reviews 
(film)
;

CREATE INDEX Reviews_author 
on Reviews 
(author)
;

CREATE INDEX Reviews_date 
on Reviews 
("date")
;

-- Table: Users
CREATE TABLE Users (
    id int PRIMARY KEY,
    nickname varchar,
    email varchar,
    password varchar,
    birthday date,
    registration_date date,
    country varchar,
    city varchar
);

CREATE INDEX Users_name 
on Users 
(nickname)
;


