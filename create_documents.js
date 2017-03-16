db = new Mongo().getDB("db");

db.createCollection('persons', {
    validator: {
        $and: [
            { name: {$type: 'string'} },
            { birthday: {$type: 'date'} },
            { country: {$type: 'string'} },
            {
                occupations: [
                    {$type: 'string'}
                ]
            }
        ]
    }
}
);
db.persons.createIndex({name: 1});

db.createCollection('films', {
    validator: {
        $and: [
            { name: {$type: 'string'} },
	    {
                producers: [
                    { person_id: {$type: 'string'} }
                ]
            },
            { year: {$type: 'int'} },
            { duration: {$type: 'int'} },
            { 
		countries: [
		    { name: {$type: 'string'} }
		]
	    },
            { rating: {$type: 'double'} },
            {
                genres: [
                    { name: {$type: 'string'} }
                ]
            },
            { description: {$type: 'string'} },
            {
                actors: [
                    { person_id: {$type: 'string'} }
                ]
            },
            {
                reviews: [
                    { review_id: {$type: 'string'} }
                ]
            }
        ]
    }
}
);
db.films.createIndex({name: 1});
db.films.createIndex({rating: 1});

db.createCollection('users', {
    validator: {
        $and: [
            { nickname: {$type: 'string'} },
            { email: {$type: 'string'} },
            { password: {$type: 'string'} },  //hash?! auth
            { birthday: {$type: 'date'} },
            { registration_date: {$type: 'date'} },
            { country: {$type: 'string'} },
            { city: {$type: 'string'} },
            {
                reviews: [
                    { review_id: {$type: 'string'} }
                ]
            },
            {
                friends: [
                    { user_id: {$type: 'string'} }
                ]
            }
        ]
    }
}
);
db.users.createIndex({nickname: 1});

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

