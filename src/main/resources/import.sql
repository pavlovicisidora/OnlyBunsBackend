INSERT INTO role (id, name) VALUES (1, 'ADMIN');
INSERT INTO role (id, name) VALUES (2, 'USER');

insert into location (longitude, latitude, city, country) values (19.8369, 45.2671, 'Novi Sad', 'Serbia');
insert into location (longitude, latitude, city, country) values (20.4489, 44.8176, 'Belgrade', 'Serbia');
insert into location (longitude, latitude, city, country) values (21.8974, 43.3217, 'Niš', 'Serbia');

insert into users (username, password, first_name, last_name, email, role_id, location_id, is_activated, last_password_reset_date) values ('marko123', 'password123', 'Marko', 'Marković', 'marko@email.com', 2, 1, true, '2024-11-07 11:30:00');
insert into users (username, password, first_name, last_name, email, role_id, location_id, is_activated, last_password_reset_date) values ('milan2014', 'password456', 'Milan', 'Milanović', 'milan@email.com', 1, 2, true, '2024-11-07 11:30:00');
insert into users (username, password, first_name, last_name, email, role_id, location_id, is_activated, last_password_reset_date) values ('ivana2014', 'password789', 'Ivana', 'Ivanović', 'ivana@email.com', 2, 1, true, '2024-11-07 11:30:00');
insert into users (username, password, first_name, last_name, email, role_id, location_id, is_activated, last_password_reset_date) values ('bojan2014', 'password101', 'Bojan', 'Bojanović', 'bojan@email.com', 2, 3, false, '2024-11-07 11:30:00');

insert into post (user_id, description, image, location_id, time_of_publishing, is_deleted) values (1, 'Post description for Marko', 'image1.jpg', 1, '2024-09-07 10:30:00', false);
insert into post (user_id, description, image, location_id, time_of_publishing, is_deleted) values (2, 'Post description for Milan', 'image2.jpg', 2, '2024-11-07 11:00:00', true);
insert into post (user_id, description, image, location_id, time_of_publishing, is_deleted) values (3, 'Post description for Ivana', 'image3.jpg', 1, '2024-11-07 11:30:00', false);

insert into comment (user_id, post_id, text) values (1, 1, 'Great post, Marko!');
insert into comment (user_id, post_id, text) values (2, 2, 'Nice work, Milan.');
insert into comment (user_id, post_id, text) values (3, 3, 'Interesting read, Ivana.');

insert into user_relations (followed_user_id, follower_id) values (1, 2);
insert into user_relations (followed_user_id, follower_id) values (2, 3);
insert into post_user_likes (user_id, post_id) values (1, 2);
insert into post_user_likes (user_id, post_id) values (2, 3);
