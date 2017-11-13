CREATE DATABASE library;
CREATE TABLE authors (id VARCHAR(36) NOT NULL PRIMARY KEY,
                      first_name VARCHAR(36) NOT NULL,
                      second_name VARCHAR(36) NOT NULL);
CREATE TABLE books (id VARCHAR(36) NOT NULL PRIMARY KEY,
                    name VARCHAR(36) NOT NULL ,
                    publisher VARCHAR(36) NOT NULL ,
                    yearPublished INTEGER NOT NULL,
                    create_date DATE NOT NULL );
CREATE TABLE reviews (id VARCHAR(36) NOT NULL PRIMARY KEY,
                      comment VARCHAR(36) NOT NULL ,
                      commenterName VARCHAR(36) NOT NULL ,
                      rating DOUBLE PRECISION NOT NULL ,
                      create_date DATE NOT NULL ,
                      book_id VARCHAR(36) NOT NULL ,
  FOREIGN KEY (book_id) REFERENCES books(id));
CREATE TABLE author_book_keys(author_id VARCHAR(36) NOT NULL,
                              book_id VARCHAR(36) NOT NULL,
  FOREIGN KEY (author_id) REFERENCES authors(id),
  FOREIGN KEY (book_id) REFERENCES books(id));