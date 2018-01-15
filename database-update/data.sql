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
                        WHERE author_book.author_id IN (SELECT author_id
                                                        FROM author_book
                                                        WHERE author_book.book_id = old.book_id))
  WHERE author.id = old.author_id;
  RETURN OLD;
END;
$calculates$
LANGUAGE plpgsql;

CREATE TRIGGER authorAvgRating
AFTER DELETE ON author_book
FOR EACH ROW EXECUTE PROCEDURE calculate_average_rating_author_delete_book();

INSERT INTO author VALUES (DEFAULT, 'Ihor', 'Miniailo');
INSERT INTO author VALUES (DEFAULT, 'Mykola', 'Halchuk');
INSERT INTO author VALUES (DEFAULT, 'Sergiy', 'Shumeyko');
INSERT INTO author VALUES (DEFAULT, 'Karina', 'Miniailo');
INSERT INTO author VALUES (DEFAULT, 'Vasiliy', 'Nahirnyak');
INSERT INTO author VALUES (DEFAULT, 'Artem', 'Krais');
INSERT INTO author VALUES (DEFAULT, 'Vasiliy', 'Mirnuy');
INSERT INTO author VALUES (DEFAULT, 'Miroslava', 'Leshcenko');
INSERT INTO author VALUES (DEFAULT, 'Sergiy', 'Beluy');
INSERT INTO author VALUES (DEFAULT, 'Marko', 'Gichko');
INSERT INTO author VALUES (DEFAULT, 'Vatilay', 'Paladiy');
INSERT INTO author VALUES (DEFAULT, 'Irina', 'Romannuk');
INSERT INTO author VALUES (DEFAULT, 'Petro', 'Petrovych');
INSERT INTO author VALUES (DEFAULT, 'Nikola', 'Zayzev');
INSERT INTO author VALUES (DEFAULT, 'Garik', 'Martirosyan');
INSERT INTO author VALUES (DEFAULT, 'Leo', 'Kleyman');
INSERT INTO author VALUES (DEFAULT, 'Leonid', 'Milman');
INSERT INTO author VALUES (DEFAULT, 'Igor', 'Krais');
INSERT INTO author VALUES (DEFAULT, 'Mihaela', 'Durda');
INSERT INTO author VALUES (DEFAULT, 'Roman', 'Khrestek');
INSERT INTO author VALUES (DEFAULT, 'Anna', 'Suhetskaya');
INSERT INTO author VALUES (DEFAULT, 'Christina', 'Maksimchuk');
INSERT INTO author VALUES (DEFAULT, 'Stanislav', 'Obshankiy');
INSERT INTO author VALUES (DEFAULT, 'Andriy', 'Vakarchuk');

INSERT INTO public.book (id, isbn, name, publisher, year_published, create_date, average_rating)
VALUES (5, '123-1-12-123456-5', 'Hibernate', 'SoftServe', 2016, '2016-09-07 12:31:27.405848', 1);
INSERT INTO public.book (id, isbn, name, publisher, year_published, create_date, average_rating)
VALUES (2, '123-1-12-123456-2', 'AngularJS', 'SoftServe', 2015, '2015-11-04 12:31:27.374594', 4.857142857142857);
INSERT INTO public.book (id, isbn, name, publisher, year_published, create_date, average_rating)
VALUES (7, '123-1-12-123456-7', 'JDBC', 'SoftServe', 2014, '2017-06-24 12:31:27.437101', 2);
INSERT INTO public.book (id, isbn, name, publisher, year_published, create_date, average_rating)
VALUES (1, '123-1-12-123456-1', 'Java', 'SoftServe', 2017, '2018-01-03 12:31:27.358964', 3.8);
INSERT INTO public.book (id, isbn, name, publisher, year_published, create_date, average_rating)
VALUES (6, '123-1-12-123456-6', 'Spring MVC', 'Chernivtsi Print Office', 2017, '2011-12-28 12:31:27.405848', 1);
INSERT INTO public.book (id, isbn, name, publisher, year_published, create_date, average_rating)
VALUES (4, '123-1-12-123456-4', 'SQL', 'Chernivtsi Print Office', 2000, '2014-05-14 12:31:27.405848', 2);
INSERT INTO public.book (id, isbn, name, publisher, year_published, create_date, average_rating)
VALUES (3, '123-1-12-123456-3', 'Angular 2', 'SoftServe Academy', 2009, '2012-10-14 12:31:27.374594', 3);

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