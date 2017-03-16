db.persons.drop();
db.createCollection('persons', {
        validator: {
            $and: [
                { name: {$type: 'string'} },
                { birthday: {$type: 'date'} },
                { country: {$type: 'string'} }
            ]
        }
    }
);
db.persons.createIndex({name: 1});

db.films.drop();
db.createCollection('films', {
        validator: {
            $and: [
                { name: {$type: 'string'} },
                { year: {$type: 'int'} },
                { duration: {$type: 'int'} },
                { description: {$type: 'string'} }
            ]
        }
    }
);
db.films.createIndex({name: 1});
db.films.createIndex({rating: 1});

db.users.drop();
db.createCollection('users', {
        validator: {
            $and: [
                { nickname: {$type: 'string'} },
                { email: {$type: 'string'} },
                { password: {$type: 'string'} },
                { birthday: {$type: 'date'} },
                { registration_date: {$type: 'date'} },
                { country: {$type: 'string'} },
                { city: {$type: 'string'} }
            ]
        }
    }
);
db.users.createIndex({nickname: 1});

db.reviews.drop();
db.createCollection('reviews', {
        validator: {
            $and: [
                { mark: {$type: 'int'} },
                { review: {$type: 'string'} },
                { date: {$type: 'date'} },
                { author_id: {$type: 'string'} },
                { film_id: {$type: 'string'} }
            ]
        }
    }
);
db.reviews.createIndex({film_id: 1});

db.createCollection("cinemas", {
        validator: {
            $and: [
                { name: {$type: 'string'} },
                { adress: {$type: 'string'} },
                { rating: {$type: 'double'} },
            ]
        }
    }
)

db.cinema_sessions.drop();
db.createCollection("cinema_sessions", {
        validator: {
            $and: [
                { film_id: {$type: 'string'} } ,
                { price: {$type: 'int'} },
                { cinema_id: {$type: 'string'} },
                { hall: {$type: 'int'} },
                { date: {$type: 'date'} }
            ]
        }
    }
)

db.tickets.createIndex({cinema: 1});
db.tickets.createIndex({film_id: 1});
db.tickets.createIndex({price: 1});



