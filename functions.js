db.system.js.save(
    {
        _id: "insert_films",
        value: function (name, producer_id, date, duration, countries, rating, genres, description, actor_ids) {
            var producer = db.persons.findOne(
                {"_id": producer_id});

            if (typeof producer == 'undefined' || producer == null) {
                print("Invalid producer id.");
                return;
            }

            var actors;
            for (var i = 0; i < actor_ids.length; i++) {
                actors[i] = db.persons.findOne(
                    {"_id": actor_ids[i]});

                if (typeof actors[i] == 'undefined' || actors[i] == null) {
                    print("Invalid actor id.");
                    return;
                }
            }

            var id;
            db.reviews.insert({
                name: name,
                producer: {
                    producer_id: producer_id,
                    producer_name: producer.name,
                    producer_surname: producer.surname
                },
                date: date,
                duration: duration,
                countries: countries,
                rating: rating,
                genres: genres,
                description: description,
                actors: [],
                reviews: [],
            }, function (err, docsInserted) {
                id = docsInserted._id;
            })

            for (var i = 0; i < actor_ids.length; i++) {
                db.reviews.update(
                    {_id: id},
                    {
                        $addToSet: {
                            actors: {
                                person_id: actors[i]._id,
                                person_name: actors[i].name,
                                person_surname: actors[i].surname
                            }
                        }
                    })
            }
        }
    }
)

db.system.js.save(
    {
        _id: "insert_tickets",
        value: function (film_id, price, cinema_name, cinema_adress, hall, row, seat, status, date) {
            var film = db.films.findOne(
                { "_id": film_id } );

            if(typeof film == 'undefined' || film == null) {
                print("Invalid film id.");
                return;
            }

            db.tickets.insert({
                film: {
                    film_id: film_id,
                    film_name: film.name
                },
              price: price,
              cinema: {
                    name: cinema_name,
                    adress: cinema_adress
              },
             hall: hall,
             row: row,
             seat: seat,
             status: status,
             date: date
            });
        }
    }
)

db.system.js.save(
    {
        _id: "insert_reviews",
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

            db.reviews.insert({
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
        }
    }
)