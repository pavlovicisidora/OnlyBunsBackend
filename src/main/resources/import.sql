INSERT INTO role (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, name) VALUES (2, 'ROLE_USER');

insert into location (longitude, latitude, city, country) values (19.8369, 45.2671, 'Novi Sad', 'Serbia');
insert into location (longitude, latitude, city, country) values (20.4489, 44.8176, 'Belgrade', 'Serbia');
insert into location (longitude, latitude, city, country) values (21.8974, 43.3217, 'Niš', 'Serbia');

insert into users (username, password, first_name, last_name, email, role_id, location_id, is_activated, last_password_reset_date, is_deleted, followers_num) values ('marko123', '$2a$10$llkMryD7KBQc1EkOeBdRO.5lcyVR3TEGqXh4G.JadCBu3mdfS44Y.', 'Marko', 'Marković', 'marko@email.com', 2, 1, true, '2024-11-07 11:30:00', false, 1);
insert into users (username, password, first_name, last_name, email, role_id, location_id, is_activated, last_password_reset_date, is_deleted, followers_num) values ('milan2014', '$2a$10$llkMryD7KBQc1EkOeBdRO.5lcyVR3TEGqXh4G.JadCBu3mdfS44Y.', 'Milan', 'Milanović', 'milan@email.com', 1, 2, true, '2024-11-07 11:30:00', false, 0);
insert into users (username, password, first_name, last_name, email, role_id, location_id, is_activated, last_password_reset_date, is_deleted, followers_num) values ('ivana2014', '$2a$10$llkMryD7KBQc1EkOeBdRO.5lcyVR3TEGqXh4G.JadCBu3mdfS44Y.', 'Ivana', 'Ivanović', 'ivana@email.com', 2, 1, true, '2024-11-07 11:30:00', false, 1);
insert into users (username, password, first_name, last_name, email, role_id, location_id, is_activated, last_password_reset_date, is_deleted, followers_num) values ('bojan2014', '$2a$10$llkMryD7KBQc1EkOeBdRO.5lcyVR3TEGqXh4G.JadCBu3mdfS44Y.', 'Bojan', 'Bojanović', 'bojan@email.com', 2, 3, false, '2024-10-09 11:30:00', false, 0);

insert into post (user_id, description, image, location_id, time_of_publishing, is_deleted) values (1, 'Post description for Marko', 'https://static.boredpanda.com/blog/wp-content/uploads/2015/09/cute-bunnies-116__605.jpg', 1, '2024-09-07 10:30:00', false);
insert into post (user_id, description, image, location_id, time_of_publishing, is_deleted) values (2, 'Post description for Milan', 'https://media.istockphoto.com/id/679868086/photo/best-friends-bunny-rabbit-and-chick-are-kissing.jpg?s=612x612&w=0&k=20&c=ZwOT041dXR8YWngStdLpK5HtZaiDfBRU05sme5hcyoE=', 2, '2024-12-15 11:00:00', false);
insert into post (user_id, description, image, location_id, time_of_publishing, is_deleted) values (3, 'Post description for Ivana', 'https://w0.peakpx.com/wallpaper/50/847/HD-wallpaper-bunnies-cute-animals-green-grass-white-fluffy-bunny.jpg', 1, '2024-12-12 11:30:00', false);

insert into comment (user_id, post_id, text) values (1, 1, 'Great post, Marko!');
insert into comment (user_id, post_id, text) values (2, 2, 'Nice work, Milan.');
insert into comment (user_id, post_id, text) values (3, 3, 'Interesting read, Ivana.');

insert into user_relations (followed_user_id, follower_id) values (1, 3);
insert into user_relations (followed_user_id, follower_id) values (3, 1);

insert into post_user_likes (user_id, post_id) values (1, 3);
insert into post_user_likes (user_id, post_id) values (3, 1);
insert into post_user_likes (user_id, post_id) values (1, 2);
insert into post_user_likes (user_id, post_id) values (3, 2);