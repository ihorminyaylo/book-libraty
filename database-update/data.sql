CREATE DATABASE library;
CREATE USER library_user WITH password 'root';
GRANT ALL ON DATABASE library TO library_user;
CREATE TABLE author (id SERIAL PRIMARY KEY,
                     first_name VARCHAR(256) NOT NULL,
                     second_name VARCHAR(256) NOT NULL,
                     create_date TIMESTAMP NOT NULL,
                     average_rating DOUBLE PRECISION);
CREATE TABLE book (id SERIAL PRIMARY KEY,
                   name VARCHAR(256) NOT NULL ,
                   publisher VARCHAR(256) NOT NULL ,
                   year_published INTEGER NOT NULL,
                   create_date TIMESTAMP NOT NULL,
                   average_rating DOUBLE PRECISION);
CREATE TABLE review (id SERIAL PRIMARY KEY,
                     comment VARCHAR NOT NULL ,
                     commenter_name VARCHAR NOT NULL ,
                     rating INTEGER NOT NULL CHECK(rating >= 1 AND rating <= 5),
                     create_date TIMESTAMP NOT NULL ,
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