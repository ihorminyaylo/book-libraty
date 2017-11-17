CREATE DATABASE library;
CREATE USER library_user WITH password 'root';
GRANT ALL ON DATABASE library TO library_user;
CREATE TABLE author (id SERIAL PRIMARY KEY,
                     first_name VARCHAR(256) NOT NULL,
                     second_name VARCHAR(256) NOT NULL,
                     create_date TIMESTAMP NOT NULL DEFAULT current_timestamp,
                     average_rating DOUBLE PRECISION NOT NULL DEFAULT VALUE 0);
CREATE TABLE book (id SERIAL PRIMARY KEY,
                   name VARCHAR(256) NOT NULL ,
                   publisher VARCHAR(256) NOT NULL ,
                   year_published INTEGER NOT NULL,
                   create_date TIMESTAMP NOT NULL DEFAULT current_timestamp,
                   average_rating DOUBLE PRECISION NOT NULL DEFAULT VALUE 0);
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

  -- todo: For calculate average rating for every book.
CREATE FUNCTION avg_book_rating() RETURNS TRIGGER AS
  $$BEGIN UPDATE "book"
    SET "average_rating" = AVG("review", "rating")
    FROM "review"
    WHERE "book"."id" = "review"."book_id";
    RETURN NEW;
    END;$$
LANGUAGE plpgsql;
CREATE TRIGGER average_rating_for_book AFTER INSERT ON review
FOR EACH ROW EXECUTE PROCEDURE avg_book_rating();


  -- todo: INSERT values into our tables
INSERT INTO author VALUES (DEFAULT , 'Ihor', 'Miniailo');
INSERT INTO author VALUES (DEFAULT , 'Mykola', 'Halchuk');
INSERT INTO author VALUES (DEFAULT , 'Sergiy', 'Shumeyko');
INSERT INTO author VALUES (DEFAULT , 'Karina', 'Miniailo');
INSERT INTO author VALUES (DEFAULT , 'Vasiliy', 'Nahirnyak');
INSERT INTO author VALUES (DEFAULT , 'Artem', 'Krais');

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
