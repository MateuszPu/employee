db.createUser(
        {
            user: "testUser",
            pwd: "testPass",
            roles: [
                {
                    role: "readWrite",
                    db: "testDb"
                }
            ]
        }
);
