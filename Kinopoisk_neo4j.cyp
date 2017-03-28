MATCH (n)
DETACH DELETE n;

CREATE (p:Person {name: "Angelina Jolie", 
				  birthday: "1975-06-04", 
				  country: "USA"}) return p;
CREATE (p:Person {name: "Brad Pitt", 
				  birthday: "1963-12-18", 
				  country: "USA"}) return p;
CREATE (p:Person {name: "Doug Liman", 
				  birthday: "1965-07-24", 
				  country: "USA"}) return p;
				  
CREATE INDEX ON :Person(name);				  

CREATE (f:Film {name: "Mr. & Mrs. Smith", 
				year: 2005, 
				duration: 120, 
				description: "Happy endings are for stories that haven't finished yet",
				countries:["USA"], 
				rating: 7.5, 
				genres: ["action_film", "thriller", "melodrama"]});

MATCH (p1:Person {name: "Angelina Jolie"}), (f:Film {name: "Mr. & Mrs. Smith"})
MERGE (p1)-[:ACTED_IN]->(f);
MATCH (p2:Person {name: "Brad Pitt"}), (f:Film {name: "Mr. & Mrs. Smith"})
MERGE (p2)-[:ACTED_IN]->(f);
MATCH (p3:Person {name: "Doug Liman"}), (f:Film {name: "Mr. & Mrs. Smith"})
MERGE (p3)-[:DIRECTED]->(f);

CREATE INDEX ON :Film(name);
CREATE INDEX ON :Film(rating);

CREATE (u:User { nickname: "Superuser",
                 email: "mail@mail.ru",
                 password: "qwerty",
                 birthday: "1990-09-11",
                 registration_date: timestamp(),
                 country: "Russia",
                 city: "SPb"}) return u;
CREATE (u:User { nickname: "Someuser",
                 email: "m@gmail.ru",
                 password: "1234",
                 birthday: "1992-04-10",
                 registration_date: timestamp(),
                 country: "Russia",
                 city: "Moscow"}) return u;	
				 
MATCH (u1:User {nickname: "Superuser"}), (u2:User {nickname: "Someuser"})
MERGE (u1)-[:FRIEND]-(u2);	

CREATE INDEX ON :User(nickname);	

CREATE (r:Review{name:"Disappointment",
				 mark: 3,
                 review: "Very, very bad",
                 date: timestamp()})
WITH r
MATCH (u:User {nickname: "Superuser"})
MERGE (u)-[:WROTE]->(r);

CREATE (c:Cinema {name: "Velikan Park",
                 adress: "Alexandrovsky park 4/3",
                 rating: 4.6 });
				 
CREATE (s:Cinema_session {price: 200,
						  hall: 1,
                          date: "2014-09-11"})
WITH s						  
MATCH (c:Cinema {name: "Velikan Park", adress: "Alexandrovsky park 4/3"}),
	  (f:Film {name: "Mr. & Mrs. Smith"})
MERGE (s)-[:CINEMA]->(c)
MERGE (s)-[:FILM]->(f);

MATCH (n) RETURN n;