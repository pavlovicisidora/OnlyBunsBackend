INSERT INTO role (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, name) VALUES (2, 'ROLE_USER');

insert into location (longitude, latitude, city, country) values (19.8369, 45.2671, 'Novi Sad', 'Serbia');
insert into location (longitude, latitude, city, country) values (20.4489, 44.8176, 'Belgrade', 'Serbia');
insert into location (longitude, latitude, city, country) values (21.8974, 43.3217, 'Niš', 'Serbia');
insert into location (longitude, latitude, city, country) values (19.8225, 45.0042, 'Ruma', 'Serbia');

insert into users (username, password, first_name, last_name, email, role_id, location_id, is_activated, last_password_reset_date, is_deleted, followers_num) values ('marko123', '$2a$10$llkMryD7KBQc1EkOeBdRO.5lcyVR3TEGqXh4G.JadCBu3mdfS44Y.', 'Marko', 'Marković', 'marko@email.com', 2, 1, true, '2024-11-07 11:30:00', false, 1);
insert into users (username, password, first_name, last_name, email, role_id, location_id, is_activated, last_password_reset_date, is_deleted, followers_num) values ('milan2014', '$2a$10$llkMryD7KBQc1EkOeBdRO.5lcyVR3TEGqXh4G.JadCBu3mdfS44Y.', 'Milan', 'Milanović', 'milan@email.com', 1, 2, true, '2024-11-07 11:30:00', false, 0);
insert into users (username, password, first_name, last_name, email, role_id, location_id, is_activated, last_password_reset_date, is_deleted, followers_num) values ('ivana2014', '$2a$10$llkMryD7KBQc1EkOeBdRO.5lcyVR3TEGqXh4G.JadCBu3mdfS44Y.', 'Ivana', 'Ivanović', 'ivana@email.com', 2, 1, true, '2024-11-07 11:30:00', false, 1);
insert into users (username, password, first_name, last_name, email, role_id, location_id, is_activated, last_password_reset_date, is_deleted, followers_num) values ('bojan2014', '$2a$10$llkMryD7KBQc1EkOeBdRO.5lcyVR3TEGqXh4G.JadCBu3mdfS44Y.', 'Bojan', 'Bojanović', 'bojan@email.com', 2, 3, false, '2024-10-09 11:30:00', false, 0);
insert into users (username, password, first_name, last_name, email, role_id, location_id, is_activated, last_password_reset_date, is_deleted, followers_num) values ('milica', '$2a$10$llkMryD7KBQc1EkOeBdRO.5lcyVR3TEGqXh4G.JadCBu3mdfS44Y.', 'Milica', 'Micić', 'milica@email.com', 2, 3, false, '2022-10-09 11:30:00', false, 0);

insert into post (user_id, description, image, location_id, time_of_publishing, is_deleted) values (2, 'Post description 1', 'image1.jpg', 3, '2023-09-07 10:30:00', false);
insert into post (user_id, description, image, location_id, time_of_publishing, is_deleted) values (2, 'Post description 2', 'image2.jpg', 2, '2024-12-15 11:00:00', false);
insert into post (user_id, description, image, location_id, time_of_publishing, is_deleted) values (3, 'Post description 3', 'image3.jpg', 1, '2024-12-12 11:30:00', false);
insert into post (user_id, description, image, location_id, time_of_publishing, is_deleted) values (1, 'Post description 4', 'image1.jpg', 2, '2023-09-07 10:30:00', true);
insert into post (user_id, description, image, location_id, time_of_publishing, is_deleted) values (4, 'Post description 5', 'image1.jpg', 4, '2024-09-07 10:30:00', false);

insert into comment (user_id, post_id, text, created_at) values (1, 1, 'Great post!', '2023-09-07 13:30:00');
insert into comment (user_id, post_id, text, created_at) values (2, 2, 'Nice work.', '2024-12-16 11:00:00');
insert into comment (user_id, post_id, text, created_at) values (3, 3, 'Interesting read.', '2024-12-13 11:30:00');

insert into user_relations (followed_user_id, follower_id) values (1, 3);
insert into user_relations (followed_user_id, follower_id) values (3, 1);

insert into post_user_likes (user_id, post_id) values (1, 3);
insert into post_user_likes (user_id, post_id) values (3, 1);
insert into post_user_likes (user_id, post_id) values (1, 2);
insert into post_user_likes (user_id, post_id) values (3, 2);

-- Privatni chat između Marka i Ivane
INSERT INTO chat (name, type, admin_id) VALUES (NULL, 'PRIVATE', NULL);
-- Grupni chat sa adminom Markom
INSERT INTO chat ( name, type, admin_id) VALUES ('Grupa: Druženje', 'GROUP', 1);

-- Poruke za privatni chat (Marko i Ivana)
INSERT INTO messages (content, timestamp, sender_id, chat_id) VALUES ('Zdravo Ivana!', '2025-01-15 12:00:00', 1, 1);
INSERT INTO messages (content, timestamp, sender_id, chat_id) VALUES ('Zdravo Marko! Kako si?', '2025-01-15 12:05:00', 3, 1);
-- Poruke za grupni chat
INSERT INTO messages (content, timestamp, sender_id, chat_id) VALUES ('Dobrodošli svi!', '2025-01-16 10:00:00', 1, 2);
INSERT INTO messages (content, timestamp, sender_id, chat_id)VALUES ('Hvala na pozivu!', '2025-01-16 10:15:00', 4, 2);
INSERT INTO messages (content, timestamp, sender_id, chat_id) VALUES ('Kada ćemo sledeći sastanak?', '2025-01-16 10:30:00', 3, 2);

INSERT INTO group_members (chat_id, user_id) VALUES (1, 1); -- Marko
INSERT INTO group_members (chat_id, user_id) VALUES (1, 3); -- Ivana
-- Dodavanje članova u grupni chat (Grupa: Druženje)
INSERT INTO group_members (chat_id, user_id) VALUES (2, 1); -- Marko (admin)
INSERT INTO group_members (chat_id, user_id) VALUES (2, 3); -- Ivana
INSERT INTO group_members (chat_id, user_id) VALUES (2, 4); -- Bojan
INSERT INTO group_members (chat_id, user_id) VALUES (2, 5); -- Milica



-- Lokacije za brigu o zecevima
INSERT INTO rabbit_care (name, city, country, latitude, longitude) VALUES ('Zeka Spa Beograd', 'Beograd', 'Srbija', 44.7866, 20.4489);
INSERT INTO rabbit_care (name, city, country, latitude, longitude) VALUES ('Bunny Paradise Novi Sad', 'Novi Sad', 'Srbija', 45.2671, 19.8335);