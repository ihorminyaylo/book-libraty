CREATE USER library_user WITH PASSWORD 'root';

GRANT ALL ON DATABASE library TO library_user;

CREATE TABLE author (
  id             SERIAL PRIMARY KEY,
  first_name     VARCHAR(256) NOT NULL,
  second_name    VARCHAR(256),
  create_date    TIMESTAMP    NOT NULL DEFAULT current_timestamp,
  average_rating DOUBLE PRECISION      DEFAULT 0
);

CREATE TABLE book (
  id             SERIAL PRIMARY KEY,
  isbn           VARCHAR(17) UNIQUE,
  name           VARCHAR(256) NOT NULL,
  publisher      VARCHAR(256),
  year_published INTEGER,
  create_date    TIMESTAMP    NOT NULL DEFAULT current_timestamp,
  average_rating DOUBLE PRECISION      DEFAULT 0
);

CREATE TABLE review (
  id             SERIAL PRIMARY KEY,
  comment        TEXT      NOT NULL,
  commenter_name VARCHAR   NOT NULL,
  rating         INTEGER   NOT NULL CHECK (rating >= 1 AND rating <= 5),
  create_date    TIMESTAMP NOT NULL DEFAULT current_timestamp,
  book_id        INTEGER   NOT NULL,
  FOREIGN KEY (book_id) REFERENCES book (id)
);

CREATE TABLE author_book (
  author_id INTEGER NOT NULL,
  book_id   INTEGER NOT NULL,
  FOREIGN KEY (author_id) REFERENCES author (id),
  FOREIGN KEY (book_id) REFERENCES book (id)
);

CREATE FUNCTION calculate_average_rating()
  RETURNS TRIGGER AS $calculates$
BEGIN
  UPDATE book
  SET average_rating = (SELECT AVG(rating)
                        FROM review
                        WHERE book.id = review.book_id)
  WHERE new.book_id = book.id;

  UPDATE author
  SET average_rating = (SELECT AVG(rating)
                        FROM review
                          JOIN author_book ON review.book_id = author_book.book_id
                        WHERE author_book.book_id IN (SELECT author_book.book_id
                                                      FROM author_book
                                                      WHERE author_id IN (SELECT author_id
                                                                          FROM author_book
                                                                          WHERE author_book.book_id = new.book_id)))
  WHERE author.id IN (SELECT author_id
                      FROM author_book
                      WHERE book_id = new.book_id);
  RETURN NEW;
END;
$calculates$
LANGUAGE plpgsql;

CREATE TRIGGER avgRating
AFTER INSERT ON review
FOR EACH ROW EXECUTE PROCEDURE calculate_average_rating();


CREATE OR REPLACE FUNCTION calculate_average_rating_author_delete_book()
  RETURNS TRIGGER AS $calculates$
BEGIN
  UPDATE author
  SET average_rating = (SELECT AVG(rating)
                        FROM review
                          JOIN author_book ON review.book_id = author_book.book_id
                        WHERE review.book_id IN (SELECT author_book.book_id
                                                 FROM author_book
                                                 WHERE author_book.author_id = old.author_id))
  WHERE author.id = old.author_id;
  RETURN OLD;
END;
$calculates$
LANGUAGE plpgsql;

CREATE TRIGGER authorAvgRating
AFTER DELETE ON author_book
FOR EACH ROW EXECUTE PROCEDURE calculate_average_rating_author_delete_book();

INSERT INTO author VALUES (DEFAULT, 'Ihor', 'Miniailo', '2017-10-14 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Mykola', 'Halchuk', '2016-08-12 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Sergiy', 'Shumeyko', '2015-09-04 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Karina', 'Miniailo', '2016-08-11 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Vasiliy', 'Nahirnyak', '2018-01-03 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Artem', 'Krais', '2017-11-05 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Vasiliy', 'Mirnuy', '2017-09-25 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Miroslava', 'Leshcenko', '2016-07-17 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Sergiy', 'Beluy', '2015-06-07 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Marko', 'Gichko', '2015-08-01 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Vatilay', 'Paladiy', '2017-02-14 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Irina', 'Romannuk', '2016-03-08 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Petro', 'Petrovych', '2017-05-24 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Nikola', 'Zayzev', '2016-06-09 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Garik', 'Martirosyan', '2018-01-06 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Leo', 'Kleyman', '2017-07-05 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Leonid', 'Milman', '2016-08-12 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Igor', 'Krais', '2015-09-23 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Mihaela', 'Durda', '2017-10-07 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Roman', 'Khrestek', '2016-11-16 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Anna', 'Suhetskaya', '2015-12-25 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Christina', 'Maksimchuk', '2017-09-30 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Stanislav', 'Obshankiy', '2015-07-18 12:31:27.374594');
INSERT INTO author VALUES (DEFAULT, 'Andriy', 'Vakarchuk', '2015-03-02 12:31:27.374594');

INSERT INTO book VALUES (DEFAULT, '123-1-12-123456-5', 'Hibernate', 'SoftServe', 2016, '2016-09-07 12:31:27.405848', 1);
INSERT INTO book VALUES (DEFAULT, '123-1-12-123456-2', 'AngularJS', 'SoftServe', 2015, '2015-11-04 12:31:27.374594', 4.857142857142857);
INSERT INTO book VALUES (DEFAULT, '123-1-12-123456-7', 'JDBC', 'SoftServe', 2014, '2017-06-24 12:31:27.437101', 2);
INSERT INTO book VALUES (DEFAULT, '123-1-12-123456-1', 'Java', 'SoftServe', 2017, '2018-01-03 12:31:27.358964', 3.8);
INSERT INTO book VALUES (DEFAULT, '123-1-12-123456-6', 'Spring MVC', 'Chernivtsi Print Office', 2017, '2011-12-28 12:31:27.405848', 1);
INSERT INTO book VALUES (DEFAULT, '123-1-12-123456-4', 'SQL', 'Chernivtsi Print Office', 2000, '2014-05-14 12:31:27.405848', 2);
INSERT INTO book VALUES (DEFAULT, '123-1-12-123456-3', 'Angular 2', 'SoftServe Academy', 2009, '2012-10-14 12:31:27.374594', 3);

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

INSERT INTO review VALUES (DEFAULT, 'Best book', 'WoW. I love this book', 5, DEFAULT, 1);
INSERT INTO review VALUES (DEFAULT, 'Good', 'Not bad', 4, DEFAULT, 1);
INSERT INTO review VALUES (DEFAULT, 'Java book', 'good', 1, DEFAULT, 1);
INSERT INTO review VALUES (DEFAULT, 'Best book', 'WoW. I love this book', 5, DEFAULT, 1);
INSERT INTO review VALUES (DEFAULT, 'Good', 'Not bad', 4, DEFAULT, 1);
INSERT INTO review VALUES (DEFAULT, 'Java book', 'good', 5, DEFAULT, 2);
INSERT INTO review VALUES (DEFAULT, 'Best book', 'WoW. I love this book', 5, DEFAULT, 2);
INSERT INTO review VALUES (DEFAULT, 'Good', 'Not bad', 4, DEFAULT, 2);
INSERT INTO review VALUES (DEFAULT, 'Java book', 'good', 5, DEFAULT, 2);
INSERT INTO review VALUES (DEFAULT, 'Best book', 'WoW. I love this book', 5, DEFAULT, 2);
INSERT INTO review VALUES (DEFAULT, 'Good', 'Not bad', 5, DEFAULT, 2);
INSERT INTO review VALUES (DEFAULT, 'Java book', 'good', 5, DEFAULT, 2);
INSERT INTO review VALUES (DEFAULT, 'Good', 'Not bad', 3, DEFAULT, 3);
INSERT INTO review VALUES (DEFAULT, 'Java book', 'good', 3, DEFAULT, 3);
INSERT INTO review VALUES (DEFAULT, 'Good', 'Not bad', 2, DEFAULT, 4);
INSERT INTO review VALUES (DEFAULT, 'Java book', 'good', 2, DEFAULT, 4);
INSERT INTO review VALUES (DEFAULT, 'Good', 'Not bad', 1, DEFAULT, 5);
INSERT INTO review VALUES (DEFAULT, 'Java book', 'good', 1, DEFAULT, 5);
INSERT INTO review VALUES (DEFAULT, 'Java book', 'good', 1, DEFAULT, 6);
INSERT INTO review VALUES (DEFAULT, 'Java book', 'good', 2, DEFAULT, 7);