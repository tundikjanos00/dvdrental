USE dvdrental;

-- Teszt DVD-k
INSERT INTO dvd (title, genre, movie_length, daily_price, available) VALUES
('The Matrix', 'Sci-Fi', 136, 2.99, TRUE),
('Titanic', 'Romance', 195, 3.49, TRUE),
('Inception', 'Sci-Fi', 148, 3.99, TRUE),
('Shrek', 'Animation', 90, 1.99, TRUE),
('Gladiator', 'Action', 155, 2.49, FALSE);

-- Teszt ügyfelek
INSERT INTO customer (name, email, phone) VALUES
('János Kiss', 'janos.kiss@example.com', '06201234567'),
('Éva Szabó', 'eva.szabo@example.com', '06301234567');

-- Teszt kölcsönzés
INSERT INTO rental (customer_id, dvd_id, rental_date, return_date) VALUES
(1, 1, '2025-06-01', '2025-06-03'),
(2, 2, '2025-06-10', NULL);
