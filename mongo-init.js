db.createUser(
        {
            user: "userMongo",
            pwd: "passMongo",
            roles: [
                {
                    role: "readWrite",
                    db: "databaseMongo"
                }
            ]
        }
);
