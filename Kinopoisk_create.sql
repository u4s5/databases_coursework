CREATE SEQUENCE Activities_seq;
CREATE SEQUENCE Activities_person_seq;
CREATE SEQUENCE Activities_kind_seq;
CREATE SEQUENCE Cinema_sessions_seq;
CREATE SEQUENCE Cinemas_seq;
CREATE SEQUENCE Films_seq;
CREATE SEQUENCE Films_genres_seq;
CREATE SEQUENCE Genres_films_seq;
CREATE SEQUENCE Friends_seq;
CREATE SEQUENCE Genres_seq;
CREATE SEQUENCE Kinds_of_activities_seq; 
CREATE SEQUENCE Persons_seq;
CREATE SEQUENCE Reviews_seq;
CREATE SEQUENCE Users_seq;

CREATE TABLE Activities (
    id int  NOT NULL,
    person int  NOT NULL,
    kind int  NOT NULL,
    film int  NOT NULL,
    CONSTRAINT ActivitiCREATE SEQUENCE Activities_seq;
CREATE SEQUENCE Activities_person_seq;
CREATE SEQUENCE Activities_kind_seq;
CREATE SEQUENCE Cinema_sessions_seq;
CREATE SEQUENCE Cinemas_seq;
CREATE SEQUENCE Films_seq;
CREATE SEQUENCE Films_genres_seq;
CREATE SEQUENCE Genres_films_seq;
CREATE SEQUENCE Friends_seq;
CREATE SEQUENCE Genres_seq;
CREATE SEQUENCE Kinds_of_activities_seq; 
CREATE SEQUENCE Persons_seq;
CREATE SEQUENCE Reviews_seq;
CREATE SEQUENCE Users_seq;

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

CREATE TABLE Films (
    id int  NOT NULL,
    name varchar2(50)  NOT NULL,
    year int  NOT NULL,
    duration int  NOT NULL,
    description clob  NOT NULL,
    rating number(5,3)  NOT NULL,
    countries varchar2(100) NOT NULL,
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

CREATE TABLE Friends (
    id int  NOT NULL,
    user1 int  NOT NULL,
    user2 int  NOT NULL,
    CONSTRAINT Friends_pk PRIMARY KEY (id)
) ;

CREATE TABLE Genres (
    id int  NOT NULL,
    name varchar2(30)  NOT NULL,
    CONSTRAINT Genres_pk PRIMARY KEY (id)
) ;

CREATE TABLE Kinds_of_activities (
    id int  NOT NULL,
    name varchar2(30)  NOT NULL,
    CONSTRAINT Kinds_of_activities_pk PRIMARY KEY (id)
) ;

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

-- Reference: Friends_Users1 (table: Friends)
ALTER TABLE Friends ADD CONSTRAINT Friends_Users1
    FOREIGN KEY (user1)
    REFERENCES Users (id);

-- Reference: Friends_Users2 (table: Friends)
ALTER TABLE Friends ADD CONSTRAINT Friends_Users2
    FOREIGN KEY (user2)
    REFERENCES Users (id);

-- Reference: Users_Reviews (table: Reviews)
ALTER TABLE Reviews ADD CONSTRAINT Users_Reviews
    FOREIGN KEY (author)
    REFERENCES Users (id);
    
INSERT INTO Persons
VALUES(
	1,
	'Brad Pitt', 
	TO_DATE('1963-12-18', 'YYYY-MM-dd'), 
	'USA'
);

INSERT INTO Persons
VALUES(
	3,
	'Doug Liman', 
	TO_DATE('1965-07-24', 'YYYY-MM-dd'), 
	'USA'
);

INSERT INTO Persons
VALUES(
	2,
	'Angelina Jolie', 
	TO_DATE('1975-06-04', 'YYYY-MM-dd'), 
	'USA'
);
	
INSERT INTO Genres VALUES(1, 'action film');
INSERT INTO Genres VALUES(2, 'thriller');
INSERT INTO Genres VALUES(3, 'melodrama');

INSERT INTO Films
VALUES(
	1,
	'Mr.' || CHR(38)|| ' Mrs. Smith', 
	2005, 
	120, 
	'Happy endings are for stories that haven''t finished yet',
  7.5,
	'USA'
);

INSERT INTO Films_genres VALUES(
    1,
    1,
    1
) ;
INSERT INTO Films_genres VALUES(
    2,
    1,
    2
) ;
INSERT INTO Films_genres VALUES(
    3,
    1,
    3
) ;

INSERT INTO Kinds_of_activities VALUES(
    1,
    'actor'
) ;
INSERT INTO Kinds_of_activities VALUES(
    2,
    'director'
) ;

INSERT INTO  Activities VALUES(
    1,
    1,
    1,
    1
) ;
INSERT INTO  Activities VALUES(
    2,
    2,
    1,
    1
) ;
INSERT INTO  Activities VALUES(
    3,
    3,
    2,
    1
) ;
				 	
INSERT INTO Users
VALUES(
	1,
    'Superuser',
    'mail@mail.ru',
    '1234',
    TO_DATE('1996-11-06', 'YYYY-MM-dd'),
    sysdate,
    'Russia',
    'Spb'
);

INSERT INTO Users
VALUES(
	2,
    'Someuser',
    'm@gmail.ru',
    '1234',
    TO_DATE('1992-04-10', 'YYYY-MM-dd'),
     sysdate,
    'Russia',
    'Moscow'
);

INSERT INTO Friends
VALUES(
  1,
   1,
   2
) ;

INSERT INTO Reviews
VALUES(
    1,
    3,
    'Very, very bad',
	sysdate,
	1,
	1
);  

COMMIT;es_unique UNIQUE (person, kind, film),
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

CREATE TABLE Films (
    id int  NOT NULL,
    name varchar2(50)  NOT NULL,
    year int  NOT NULL,
    duration int  NOT NULL,
    description clob  NOT NULL,
    rating number(5,3)  NOT NULL,
	countries varchar2(100) NOT NULL,
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

CREATE TABLE Friends (
    id int  NOT NULL,
    user1 int  NOT NULL,
    user2 int  NOT NULL,
    CONSTRAINT Friends_pk PRIMARY KEY (id)
) ;

CREATE TABLE Genres (
    id int  NOT NULL,
    name varchar2(30)  NOT NULL,
    CONSTRAINT Genres_pk PRIMARY KEY (id)
) ;

CREATE TABLE Kinds_of_activities (
    id int  NOT NULL,
    name varchar2(30)  NOT NULL,
    CONSTRAINT Kinds_of_activities_pk PRIMARY KEY (id)
) ;

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

-- Reference: Friends_Users1 (table: Friends)
ALTER TABLE Friends ADD CONSTRAINT Friends_Users1
    FOREIGN KEY (user1)
    REFERENCES Users (id);

-- Reference: Friends_Users2 (table: Friends)
ALTER TABLE Friends ADD CONSTRAINT Friends_Users2
    FOREIGN KEY (user2)
    REFERENCES Users (id);

-- Reference: Users_Reviews (table: Reviews)
ALTER TABLE Reviews ADD CONSTRAINT Users_Reviews
    FOREIGN KEY (author)
    REFERENCES Users (id);

INSERT INTO Persons
VALUES(
	1,
	'Brad Pitt', 
	TO_DATE('1963-12-18', 'YYYY-MM-dd'), 
	'USA'
);

INSERT INTO Persons
VALUES(
	3,
	'Doug Liman', 
	TO_DATE('1965-07-24', 'YYYY-MM-dd'), 
	'USA'
);

INSERT INTO Persons
VALUES(
	2,
	'Angelina Jolie', 
	TO_DATE('1975-06-04', 'YYYY-MM-dd'), 
	'USA'
);
	
INSERT INTO Genres VALUES(1, 'action film');
INSERT INTO Genres VALUES(2, 'thriller');
INSERT INTO Genres VALUES(3, 'melodrama');

INSERT INTO Films
VALUES(
	1,
	'Mr. & Mrs. Smith', 
	2005, 
	120, 
	"Happy endings are for stories that haven't finished yet",
	'USA'
);

INSERT INTO Films_genres VALUES(
    1,
    1,
    1
) ;
INSERT INTO Films_genres VALUES(
    2,
    1,
    2
) ;
INSERT INTO Films_genres VALUES(
    3,
    1,
    3
) ;

INSERT INTO Kinds_of_activities VALUES(
    1,
    'actor'
) ;
INSERT INTO Kinds_of_activities VALUES(
    2,
    'director'
) ;

INSERT INTO  Activities VALUES(
    1,
    1,
    1,
    1
) ;
INSERT INTO  Activities VALUES(
    2,
    2,
    1,
    1
) ;
INSERT INTO  Activities VALUES(
    3,
    3,
    2,
    1
) ;
				 	
INSERT INTO Users
VALUES(
	1,
    'Superuser',
    'mail@mail.ru',
    '1234',
    TO_DATE('1996-11-06', 'YYYY-MM-dd'),
    sysdate(),
    'Russia',
    'Spb'
);

INSERT INTO Users
VALUES(
	2,
    'Someuser',
    'm@gmail.ru',
    '1234',
    TO_DATE('1992-04-10', 'YYYY-MM-dd'),
     sysdate(),
    'Russia',
    'Moscow'
);

INSERT INTO Friends
VALUES(
   1,
   2
) ;

INSERT INTO Reviews
VALUES(
    1,
    3,
    'Very, very bad',
	sysdate(),
	1,
	1
);

