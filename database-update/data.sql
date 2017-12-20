CREATE USER library_user WITH password 'root';

GRANT ALL ON DATABASE library TO library_user;

CREATE TABLE author (id SERIAL PRIMARY KEY,
                     first_name VARCHAR(256) NOT NULL,
                     second_name VARCHAR(256),
                     create_date TIMESTAMP NOT NULL DEFAULT current_timestamp,
                     average_rating DOUBLE PRECISION);

CREATE TABLE book (id SERIAL PRIMARY KEY,
                   name VARCHAR(256) NOT NULL ,
                   publisher VARCHAR(256),
                   year_published INTEGER,
                   create_date TIMESTAMP NOT NULL DEFAULT current_timestamp,
                   average_rating DOUBLE PRECISION);

CREATE TABLE review (id SERIAL PRIMARY KEY,
                     comment TEXT NOT NULL ,
                     commenter_name VARCHAR NOT NULL ,
                     rating INTEGER NOT NULL CHECK(rating >= 1 AND rating <= 5),
                     create_date TIMESTAMP NOT NULL DEFAULT current_timestamp,
                     book_id INTEGER NOT NULL ,
FOREIGN KEY (book_id) REFERENCES book(id));

CREATE TABLE author_book (author_id INTEGER NOT NULL,
                          book_id INTEGER NOT NULL,
  FOREIGN KEY (author_id) REFERENCES author(id),
  FOREIGN KEY (book_id) REFERENCES book(id));


CREATE FUNCTION calculate_average_rating_book() RETURNS TRIGGER AS $calculates$ -- todo: why are you recalculate average_rating for all books?
BEGIN
UPDATE book
SET average_rating = (SELECT AVG(rating) FROM review
WHERE book.id = review.book_id);
RETURN NEW;
END;
$calculates$
LANGUAGE plpgsql;

CREATE TRIGGER bookAvgRating
AFTER INSERT ON review
FOR EACH ROW EXECUTE PROCEDURE calculate_average_rating_book();


CREATE FUNCTION calculate_average_rating_author() RETURNS TRIGGER AS $calculates$ -- todo: why are you recalculate average_rating for all books?
BEGIN
UPDATE author
SET average_rating = (SELECT AVG(average_rating) FROM book JOIN author_book ON book.id = author_book.book_id
WHERE author.id = author_book.author_id;
  RETURN NEW;
END;
$calculates$
LANGUAGE plpgsql;

CREATE TRIGGER authorAvgRating
AFTER INSERT ON review
FOR EACH ROW EXECUTE PROCEDURE calculate_average_rating_author();


INSERT INTO author VALUES (DEFAULT , 'Ihor', 'Miniailo');
INSERT INTO author VALUES (DEFAULT , 'Mykola', 'Halchuk');
INSERT INTO author VALUES (DEFAULT , 'Sergiy', 'Shumeyko');
INSERT INTO author VALUES (DEFAULT , 'Karina', 'Miniailo');
INSERT INTO author VALUES (DEFAULT , 'Vasiliy', 'Nahirnyak');
INSERT INTO author VALUES (DEFAULT , 'Artem', 'Krais');
INSERT INTO author VALUES (DEFAULT , 'Vasiliy', 'Mirnuy');
INSERT INTO author VALUES (DEFAULT , 'Miroslava', 'Leshcenko');
INSERT INTO author VALUES (DEFAULT , 'Sergiy', 'Beluy');
INSERT INTO author VALUES (DEFAULT , 'Marko', 'Gichko');
INSERT INTO author VALUES (DEFAULT , 'Vatilay', 'Paladiy');
INSERT INTO author VALUES (DEFAULT , 'Irina', 'Romannuk');
INSERT INTO author VALUES (DEFAULT , 'Petro', 'Petrovych');
INSERT INTO author VALUES (DEFAULT , 'Nikola', 'Zayzev');
INSERT INTO author VALUES (DEFAULT , 'Garik', 'Martirosyan');
INSERT INTO author VALUES (DEFAULT , 'Leo', 'Kleyman');
INSERT INTO author VALUES (DEFAULT , 'Leonid', 'Milman');
INSERT INTO author VALUES (DEFAULT , 'Igor', 'Krais');
INSERT INTO author VALUES (DEFAULT , 'Mihaela', 'Durda');
INSERT INTO author VALUES (DEFAULT , 'Roman', 'Khrestek');
INSERT INTO author VALUES (DEFAULT , 'Anna', 'Suhetskaya');
INSERT INTO author VALUES (DEFAULT , 'Christina', 'Maksimchuk');
INSERT INTO author VALUES (DEFAULT , 'Stanislav', 'Obshankiy');
INSERT INTO author VALUES (DEFAULT , 'Andriy', 'Vakarchuk');

INSERT INTO book VALUES (DEFAULT , 'Java', 'SoftServe', 2017);
INSERT INTO book VALUES (DEFAULT , 'AngularJS', 'SoftServe', 2015);
INSERT INTO book VALUES (DEFAULT , 'Angular 2', 'SoftServe Academy', 2009);
INSERT INTO book VALUES (DEFAULT , 'SQL', 'Chernivtsi Print Office', 2000);
INSERT INTO book VALUES (DEFAULT , 'Hibernate', 'SoftServe', 2016);
INSERT INTO book VALUES (DEFAULT , 'Spring MVC', 'Chernivtsi Print Office', 2017);
INSERT INTO book VALUES (DEFAULT , 'JDBC', 'SoftServe', 2014);

INSERT INTO author_book VALUES (2, 1);
INSERT INTO author_book VALUES (2, 2);
INSERT INTO author_book VALUES (2, 3);
INSERT INTO author_book VALUES (3, 4);
INSERT INTO author_book VALUES (4, 5);
INSERT INTO author_book VALUES (5, 6);
INSERT INTO author_book VALUES (6, 7);
INSERT INTO author_book VALUES (7, 7);
INSERT INTO author_book VALUES (7, 6);
INSERT INTO author_book VALUES (5, 2);

INSERT INTO review VALUES (DEFAULT , 'Best book', 'WoW. I love this book', 5, DEFAULT , 1);
INSERT INTO review VALUES (DEFAULT , 'Good', 'Not bad', 4, DEFAULT , 1);
INSERT INTO review VALUES (DEFAULT , 'Java book', 'good', 1, DEFAULT , 1);
INSERT INTO review VALUES (DEFAULT , 'Best book', 'WoW. I love this book', 5, DEFAULT , 1);
INSERT INTO review VALUES (DEFAULT , 'Good', 'Not bad', 4, DEFAULT , 1);
INSERT INTO review VALUES (DEFAULT , 'Java book', 'good', 5, DEFAULT , 2);
INSERT INTO review VALUES (DEFAULT , 'Best book', 'WoW. I love this book', 5, DEFAULT , 2);
INSERT INTO review VALUES (DEFAULT , 'Good', 'Not bad', 4, DEFAULT , 2);
INSERT INTO review VALUES (DEFAULT , 'Java book', 'good', 5, DEFAULT , 2);
INSERT INTO review VALUES (DEFAULT , 'Best book', 'WoW. I love this book', 5, DEFAULT , 2);
INSERT INTO review VALUES (DEFAULT , 'Good', 'Not bad', 5, DEFAULT , 2);
INSERT INTO review VALUES (DEFAULT , 'Java book', 'good', 5, DEFAULT , 2);
INSERT INTO review VALUES (DEFAULT , 'Good', 'Not bad', 3, DEFAULT , 3);
INSERT INTO review VALUES (DEFAULT , 'Java book', 'good', 3, DEFAULT , 3);
INSERT INTO review VALUES (DEFAULT , 'Good', 'Not bad', 2, DEFAULT , 4);
INSERT INTO review VALUES (DEFAULT , 'Java book', 'good', 2, DEFAULT , 4);
INSERT INTO review VALUES (DEFAULT , 'Good', 'Not bad', 1, DEFAULT , 5);
INSERT INTO review VALUES (DEFAULT , 'Java book', 'good', 1, DEFAULT , 5);
INSERT INTO review VALUES (DEFAULT , 'Java book', 'good', 1, DEFAULT , 6);
INSERT INTO review VALUES (DEFAULT , 'Java book', 'good', 2, DEFAULT , 7);
