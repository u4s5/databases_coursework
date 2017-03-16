db.persons.deleteMany({})
db.films.deleteMany({})
db.users.deleteMany({})
db.reviews.deleteMany({})
db.tickets.deleteMany({})

db.loadServerScripts();

db.persons.insert(
    {
        name: "Angelina Jolie",
        birthday: new Date("1975-06-04") ,
        country: "USA" ,
        occupations: [
            "actor", "producer"
        ]
    });

db.persons.insert(
    {
        name: "Bred Pitt",
        birthday: new Date("1963-12-18") ,
        country: "USA" ,
        occupations: [
            "actor", "producer"
        ]
    });

db.persons.insert(
    {
        name: "Doug Liman",
        birthday: new Date("1965-07-24") ,
        country: "USA" ,
        occupations: [
            "producer"
        ]
    });

insert_film("Mr. & Mrs. Smith", [db.persons.findOne({name: "Doug Liman"})._id],
    2005, 120, ["USA"], parseFloat(7.5), ["action_film", "thriller", "melodrama"],
    "Happy endings are for stories that haven't finished yet",
    [ db.persons.findOne({name: "Angelina Jolie"})._id, db.persons.findOne({name: "Bred Pitt"})._id ]);

db.cinemas.insert({ name: "Velikan Park", adress: "Alexandrovsky park 4/3", rating: parseFloat(4.6) });

insert_cinema_session(200, db.cinemas.findOne({ name: "Velikan Park" })._id,
    db.films.findOne({ name: "Mr. & Mrs. Smith" })._id, 1, "09-11-2014");

