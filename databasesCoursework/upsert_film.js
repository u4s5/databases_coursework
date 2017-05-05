function insert_film(id, name, producer_ids, year, duration, countries, rating, genres, description, actor_ids) {
    var producers = [];
    for (var i = 0; i < producer_ids.length; i++) {
        producers[i] = db.people.findOne(
            {"_id": producer_ids[i]});

        if (typeof producers[i] == 'undefined' || producers[i] == null) {
            print("Invalid producer id.");
            return;
        }
    }

    var actors = [];
    for (var i = 0; i < actor_ids.length; i++) {
        actors[i] = db.people.findOne(
            {"_id": actor_ids[i]});

        if (typeof actors[i] == 'undefined' || actors[i] == null) {
            print("Invalid actor id.");
            return;
        }
    }

    db.films.update(
        {_id: id},
        {
            $set: {
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
            }
        }, {upsert: true});

    for (var i = 0; i < actor_ids.length; i++) {
        db.films.update(
            {_id: id},
            { $addToSet: { actors: actor_ids[i] } })
        db.people.update(
            {_id: actor_ids[i] },
            { $addToSet: { films: { id: id, position: "actor" } } })
    }
    for (var i = 0; i < producer_ids.length; i++) {
        db.films.update(
            {_id: id},
            { $addToSet: { producers: producer_ids[i] } }
        )
        db.people.update(
            {_id: producer_ids[i] },
            { $addToSet: { films: { id: id, position: "producer" } } })
    }
}