DROP TABLE IF EXISTS "user_relations";
DROP TABLE IF EXISTS "post_user_likes";
DROP TABLE IF EXISTS "comment";
DROP TABLE IF EXISTS "post";
DROP TABLE IF EXISTS "role";
DROP TABLE IF EXISTS "users";
DROP TABLE IF EXISTS "location";


CREATE TABLE IF NOT EXISTS location (
    id SERIAL PRIMARY KEY,
    longitude DOUBLE PRECISION NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    country VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "users" (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    type VARCHAR(255) NOT NULL,
    location_id INT,
    is_activated BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (location_id) REFERENCES location(id)
);

CREATE TABLE IF NOT EXISTS role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE IF NOT EXISTS post (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    description TEXT NOT NULL,
    image VARCHAR(255) NOT NULL,
    location_id INT,
    time_of_publishing TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES "users"(id) ON DELETE CASCADE,
    FOREIGN KEY (location_id) REFERENCES location(id)
);

CREATE TABLE IF NOT EXISTS comment (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    text TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "users"(id),
    FOREIGN KEY (post_id) REFERENCES post(id)
);

CREATE TABLE IF NOT EXISTS user_relations (
    followed_user_id INT NOT NULL,
    follower_id INT NOT NULL,
    PRIMARY KEY (followed_user_id, follower_id),
    FOREIGN KEY (followed_user_id) REFERENCES "users"(id),
    FOREIGN KEY (follower_id) REFERENCES "users"(id)
);

CREATE TABLE IF NOT EXISTS post_user_likes (
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES "users"(id),
    FOREIGN KEY (post_id) REFERENCES "post"(id)
);

-- Sample inserts
insert into "location" (longitude, latitude, city, country)
values (19.8369, 45.2671, 'Novi Sad', 'Serbia');
insert into "location" (longitude, latitude, city, country)
values (20.4489, 44.8176, 'Belgrade', 'Serbia');
insert into "location" (longitude, latitude, city, country)
values (21.8974, 43.3217, 'Niš', 'Serbia');

insert into "users" (username, password, first_name, last_name, email, type, location_id, is_activated)
values ('marko123', 'password123', 'Marko', 'Marković', 'marko@email.com', 'REGISTERED', 1, true);
insert into "users" (username, password, first_name, last_name, email, type, location_id, is_activated)
values ('milan2014', 'password456', 'Milan', 'Milanović', 'milan@email.com', 'ADMIN', 2, true);
insert into "users" (username, password, first_name, last_name, email, type, location_id, is_activated)
values ('ivana2014', 'password789', 'Ivana', 'Ivanović', 'ivana@email.com', 'REGISTERED', 1, true);
insert into "users" (username, password, first_name, last_name, email, type, location_id, is_activated)
values ('bojan2014', 'password101', 'Bojan', 'Bojanović', 'bojan@email.com', 'REGISTERED', 3, false);

insert into "post" (user_id, description, image, location_id, time_of_publishing)
values (1, 'Post description for Marko', 'image1.jpg', 1, '2024-11-07 10:30:00');
insert into "post" (user_id, description, image, location_id, time_of_publishing)
values (2, 'Post description for Milan', 'image2.jpg', 2, '2024-11-07 11:00:00');
insert into "post" (user_id, description, image, location_id, time_of_publishing)
values (3, 'Post description for Ivana', 'image3.jpg', 1, '2024-11-07 11:30:00');

insert into "comment" (user_id, post_id, text)
values (1, 1, 'Great post, Marko!');
insert into "comment" (user_id, post_id, text)
values (2, 2, 'Nice work, Milan.');
insert into "comment" (user_id, post_id, text)
values (3, 3, 'Interesting read, Ivana.');

insert into "user_relations" (followed_user_id, follower_id)
values (1, 2);
insert into "user_relations" (followed_user_id, follower_id)
values (2, 3);

insert into "post_user_likes" (user_id, post_id)
values (1, 2);
insert into "post_user_likes" (user_id, post_id)
values (2, 3);