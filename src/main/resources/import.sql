insert into location (longitude, latitude, city, country) values (19.8369, 45.2671, 'Novi Sad', 'Serbia');
insert into location (longitude, latitude, city, country) values (20.4489, 44.8176, 'Belgrade', 'Serbia');
insert into location (longitude, latitude, city, country) values (21.8974, 43.3217, 'Niš', 'Serbia');

insert into users (username, password, first_name, last_name, email, type, location_id, is_activated) values ('marko123', 'password123', 'Marko', 'Marković', 'marko@email.com', 'REGISTERED', 1, true);
insert into users (username, password, first_name, last_name, email, type, location_id, is_activated) values ('milan2014', 'password456', 'Milan', 'Milanović', 'milan@email.com', 'ADMIN', 2, true);
insert into users (username, password, first_name, last_name, email, type, location_id, is_activated) values ('ivana2014', 'password789', 'Ivana', 'Ivanović', 'ivana@email.com', 'REGISTERED', 1, true);
insert into users (username, password, first_name, last_name, email, type, location_id, is_activated) values ('bojan2014', 'password101', 'Bojan', 'Bojanović', 'bojan@email.com', 'REGISTERED', 3, false);

insert into post (user_id, description, image, location_id, time_of_publishing) values (1, 'Post description for Marko', 'image1.jpg', 1, '2024-11-07 10:30:00');
insert into post (user_id, description, image, location_id, time_of_publishing) values (2, 'Post description for Milan', 'image2.jpg', 2, '2024-11-07 11:00:00');
insert into post (user_id, description, image, location_id, time_of_publishing) values (3, 'Post description for Ivana', 'image3.jpg', 1, '2024-11-07 11:30:00');

insert into comment (user_id, post_id, text) values (1, 1, 'Great post, Marko!');
insert into comment (user_id, post_id, text) values (2, 2, 'Nice work, Milan.');
insert into comment (user_id, post_id, text) values (3, 3, 'Interesting read, Ivana.');

insert into user_relations (followed_user_id, follower_id) values (1, 2);
insert into user_relations (followed_user_id, follower_id) values (2, 3);

insert into post_user_likes (user_id, post_id) values (1, 2);
insert into post_user_likes (user_id, post_id) values (2, 3);