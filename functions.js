db.system.js.save(
    {
        _id: "insert_film",
        value: function (name, producer_ids, year, duration, countries, rating, genres, description, actor_ids) {
            var producers = [];
            for (var i = 0; i < producer_ids.length; i++) {
                producers[i] = db.persons.findOne(
                    {"_id": producer_ids[i]});

                if (typeof producers[i] == 'undefined' || producers[i] == null) {
                    print("Invalid producer id.");
                    return;
                }
            }

            var actors = [];
            for (var i = 0; i < actor_ids.length; i++) {
                actors[i] = db.persons.findOne(
                    {"_id": actor_ids[i]});

                if (typeof actors[i] == 'undefined' || actors[i] == null) {
                    print("Invalid actor id.");
                    return;
                }
            }

            var id = new ObjectId();
            db.films.insert({
                _id: id,
                name: name,
                year: year,
                duration: duration,
                countries: countries,
                rating: rating,
                genres: genres,
                description: description,
                actors: [],
                reviews: [],
                producers: []
              },
                function (err, docsInserted) {
                print(docsInserted)
                id = docsInserted._id;
            });

            for (var i = 0; i < actor_ids.length; i++) {
                db.films.update(
                    {_id: id},
                    { $addToSet: { actors: actor_ids[i] } })
                db.persons.update(
                    {_id: actor_ids[i] },
                    { $addToSet: { films: { id: id, position: "actor" } } })
            }
            for (var j = 0; i < producer_ids.length; i++) {
                db.films.update(
                    {_id: id},
                    { $addToSet: { producers: producer_ids[i] } }
                )
                db.persons.update(
                    {_id: producer_ids[i] },
                    { $addToSet: { films: { id: id, position: "producer" } } })
            }
        }
    }
)

db.system.js.save(
    {
        _id: "insert_review",
        value: function (mark, text, date, author_id, film_id) {
            var film = db.films.findOne(
                { "_id": film_id } );

            if(typeof film == 'undefined' || film == null) {
                print("Invalid film id.");
                return;
            }
            var author = db.users.findOne(
                { "_id": author_id } );

            if(typeof author == 'undefined' || author == null) {
                print("Invalid user id.");
                return;
            }

            var id = new ObjectId();
            db.reviews.insert({
                _id: id,
             mark: mark,
             text: text,
             date: date,
             author: {
                    user_id: author_id,
                    user_nick: author.nick
                }
            },
            {
                film: {
                    film_id: film_id,
                    film_name: film.name
                }
            });

            db.users.update(
                { _id: author_id },
                { $addToSet: { reviews: id  } }
            )
            db.films.update(
                { _id: film_id },
                { $addToSet: { reviews: id } }
            )
        }
    }
)

db.system.js.save(
    {
        _id: "insert_cinema_session",
        value: function (price, cinema_id, film_id, hall, date) {
            var film = db.films.findOne(
                { "_id": film_id } );

            if(typeof film == 'undefined' || film == null) {
                print("Invalid film id.");
                return;
            }
            var cinema = db.cinemas.findOne(
                { "_id": cinema_id } );

            if(typeof cinema == 'undefined' || cinema == null) {
                print("Invalid cinema id.");
                return;
            }

            db.cinema_sessions.insert({
                film_id: film_id,
                price: price,
                cinema_id: cinema_id,
                hall: hall,
                date: date
            });
        }
    }
)

db.system.js.save(
    {
        _id: "add_film_to_person",
        value: function (person_id, film_id, position) {
            var film = db.films.findOne(
                { "_id": film_id } );

            if(typeof film == 'undefined' || film == null) {
                print("Invalid film id.");
                return;
            }

            var person = db.users.findOne(
                { "_id": person_id } );

            if(typeof person == 'undefined' || person == null) {
                print("Invalid person id.");
                return;
            }

            db.persons.update(
                {_id: person_id },
                { $addToSet: { films: { id: id, position: position } } })
            db.persons.update(
                {_id: person_id },
                { $addToSet: { occupations: position } })
        }
    }
)
db.system.js.save(
    {
        _id: "add_occupation_to_person",
        value: function (person_id, occupation) {
            var person = db.users.findOne(
                { "_id": person_id } );

            if(typeof person == 'undefined' || person == null) {
                print("Invalid person id.");
                return;
            }

            db.person.update(
                { _id: person_id },
                { $addToSet: { occupations: occupation } }
            )
        }
    }
)

db.system.js.save(
    {
        _id: "add_user_friend",
        value: function (user1_id, user2_id) {
            var user1 = db.users.findOne(
                { "_id": user1_id } );

            if(typeof user1 == 'undefined' || user1 == null) {
                print("Invalid user id.");
                return;
            }
            var user2 = db.users.findOne(
                { "_id": user2_id } );

            if(typeof user2 == 'undefined' || user2 == null) {
                print("Invalid user id.");
                return;
            }

            db.users.update(
                { _id: user1_id },
                { $addToSet: { friends: user2_id } }
            )
            db.users.update(
                { _id: user2_id },
                { $addToSet: { friends: user1_id } }
            )
        }
    }
)

db.loadServerScripts();