function insert_review(id, mark, text, date, author, film_id) {
    var film = db.films.findOne(
        { "_id": film_id } );

    if(typeof film == 'undefined' || film == null) {
        print("Invalid film id.");
        return;
    }

    db.reviews.update(
        {_id: id},
        {
            $set: {
                _id: id,
                mark: mark,
                text: text,
                date: date,
                author: author,
                film_id: film_id
            }
        }, {upsert: true});

    db.films.update(
        { _id: film_id },
        { $addToSet: { reviews: id } }
    )
}