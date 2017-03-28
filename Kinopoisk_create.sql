-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2017-03-28 22:58:04.392

-- tables
-- Table: Activities
CREATE TABLE Activities (
    id int  NOT NULL,
    person int  NOT NULL,
    kind int  NOT NULL,
    film int  NOT NULL,
    CONSTRAINT Activities_unique UNIQUE (person, kind, film),
    CONSTRAINT Activities_pk PRIMARY KEY (id)
) ;

CREATE INDEX Activities_person 
on Activities 
(person ASC)
;

CREATE INDEX Activities_film 
on Activities 
(film ASC)
;

CREATE INDEX Activities_kind 
on Activities 
(kind ASC)
;

-- Table: Cinema_sessions
CREATE TABLE Cinema_sessions (
    id int  NOT NULL,
    film int  NOT NULL,
    cinema int  NOT NULL,
    price int  NOT NULL,
    hall int  NOT NULL,
    time timestamp  NOT NULL,
    CONSTRAINT Cinema_sessions_pk PRIMARY KEY (id)
) ;

CREATE INDEX Cinema_sessions_film 
on Cinema_sessions 
(film ASC)
;

CREATE INDEX Cinema_sessions_cinema 
on Cinema_sessions 
(cinema ASC)
;

-- Table: Cinemas
CREATE TABLE Cinemas (
    id int  NOT NULL,
    name varchar2(50)  NOT NULL,
    adress varchar2(90)  NOT NULL,
    rating number(5,3)  NOT NULL,
    CONSTRAINT Cinemas_pk PRIMARY KEY (id)
) ;

CREATE INDEX Cinemas_name 
on Cinemas 
(name ASC)
;

-- Table: Films
CREATE TABLE Films (
    id int  NOT NULL,
    name varchar2(50)  NOT NULL,
    year int  NOT NULL,
    duration int  NOT NULL,
    description clob  NOT NULL,
    rating number(5,3)  NOT NULL,
    CONSTRAINT Films_pk PRIMARY KEY (id)
) ;

CREATE INDEX Films_name 
on Films 
(name ASC)
;

CREATE INDEX Films_rating 
on Films 
(rating ASC)
;

-- Table: Films_genres
CREATE TABLE Films_genres (
    id int  NOT NULL,
    film int  NOT NULL,
    genre int  NOT NULL,
    CONSTRAINT Films_genres_pk PRIMARY KEY (id)
) ;

CREATE INDEX Films_genres 
on Films_genres 
(film ASC)
;

CREATE INDEX Genres_films 
on Films_genres 
(genre ASC)
;

-- Table: Genres
CREATE TABLE Genres (
    id int  NOT NULL,
    name varchar2(30)  NOT NULL,
    CONSTRAINT Genres_pk PRIMARY KEY (id)
) ;

-- Table: Kinds_of_activities
CREATE TABLE Kinds_of_activities (
    id int  NOT NULL,
    name varchar2(30)  NOT NULL,
    CONSTRAINT Kinds_of_activities_pk PRIMARY KEY (id)
) ;

-- Table: Persons
CREATE TABLE Persons (
    id int  NOT NULL,
    name varchar2(50)  NOT NULL,
    birthday date  NOT NULL,
    country varchar2(50)  NOT NULL,
    CONSTRAINT Persons_pk PRIMARY KEY (id)
) ;

CREATE INDEX Persons_name 
on Persons 
(name ASC)
;

-- Table: Reviews
CREATE TABLE Reviews (
    id int  NOT NULL,
    mark int  NOT NULL,
    review clob  NOT NULL,
    "date" date  NOT NULL,
    author int  NOT NULL,
    film int  NOT NULL,
    CONSTRAINT Reviews_pk PRIMARY KEY (id)
) ;

CREATE INDEX Reviews_film 
on Reviews 
(film ASC)
;

CREATE INDEX Reviews_author 
on Reviews 
(author ASC)
;

CREATE INDEX Reviews_date 
on Reviews 
("date" ASC)
;

-- Table: Users
CREATE TABLE Users (
    id int  NOT NULL,
    nickname varchar2(50)  NOT NULL,
    email varchar2(50)  NOT NULL,
    password varchar2(70)  NOT NULL,
    birthday date  NOT NULL,
    registration_date date  NOT NULL,
    country varchar2(50)  NOT NULL,
    city varchar2(50)  NOT NULL,
    CONSTRAINT Users_pk PRIMARY KEY (id)
) ;

CREATE INDEX Users_name 
on Users 
(nickname ASC)
;

-- foreign keys
-- Reference: Activities_Films (table: Activities)
ALTER TABLE Activities ADD CONSTRAINT Activities_Films
    FOREIGN KEY (film)
    REFERENCES Films (id);

-- Reference: Activities_Kinds_of_activities (table: Activities)
ALTER TABLE Activities ADD CONSTRAINT Activities_Kinds_of_activities
    FOREIGN KEY (kind)
    REFERENCES Kinds_of_activities (id);

-- Reference: Activities_Persons (table: Activities)
ALTER TABLE Activities ADD CONSTRAINT Activities_Persons
    FOREIGN KEY (person)
    REFERENCES Persons (id);

-- Reference: Cinema_sessions_Cinemas (table: Cinema_sessions)
ALTER TABLE Cinema_sessions ADD CONSTRAINT Cinema_sessions_Cinemas
    FOREIGN KEY (cinema)
    REFERENCES Cinemas (id);

-- Reference: Films_Cinema_sessions (table: Cinema_sessions)
ALTER TABLE Cinema_sessions ADD CONSTRAINT Films_Cinema_sessions
    FOREIGN KEY (film)
    REFERENCES Films (id);

-- Reference: Films_Reviews (table: Reviews)
ALTER TABLE Reviews ADD CONSTRAINT Films_Reviews
    FOREIGN KEY (film)
    REFERENCES Films (id);

-- Reference: Films_genres_Films (table: Films_genres)
ALTER TABLE Films_genres ADD CONSTRAINT Films_genres_Films
    FOREIGN KEY (film)
    REFERENCES Films (id);

-- Reference: Films_genres_Genres (table: Films_genres)
ALTER TABLE Films_genres ADD CONSTRAINT Films_genres_Genres
    FOREIGN KEY (genre)
    REFERENCES Genres (id);

-- Reference: Users_Reviews (table: Reviews)
ALTER TABLE Reviews ADD CONSTRAINT Users_Reviews
    FOREIGN KEY (author)
    REFERENCES Users (id);

-- End of file.

