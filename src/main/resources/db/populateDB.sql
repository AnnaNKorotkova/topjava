DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id) VALUES
('2020-04-04 07:00:00', 'Завтрак', '500', 100000),
('2020-04-04 13:00:00', 'Обед', '1000', 100000),
('2020-04-04 18:00:00', 'Ужин', '500', 100000),
('2020-04-05 00:00:00', 'Ещё хочу', '100', 100000),
('2020-04-05 07:05:00', 'Завтрак', '400', 100000),
('2020-04-05 13:05:00', 'Обед', '1000', 100000),
('2020-04-05 18:00:00', 'Ужин', '500', 100000),
('2020-04-06 00:00:00', 'Ещё хочу', '100', 100000),
('2020-04-04 00:00:00', 'Админы тоже едят', '1000', 100001);

