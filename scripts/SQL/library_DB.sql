DROP TABLE IF EXISTS author;

CREATE TABLE
    author (
        id SERIAL PRIMARY KEY,
        author_name VARCHAR NOT NULL,
        work_count int NOT NULL,
        page_key VARCHAR NOT NULL,
        birth_date VARCHAR,
        death_date VARCHAR,
        created_at TIMESTAMP,
        created_by VARCHAR,
        updated_at TIMESTAMP,
        updated_by VARCHAR
    );

INSERT INTO
    author (
        author_name,
        work_count,
        page_key,
        birth_date,
        death_date
    )
VALUES
    (
        'Adam Mickiewicz',
        15,
        'keyyyy',
        'Here we will try to parse it',
        'samehere'
    );

DROP TABLE IF EXISTS book;

CREATE TABLE
    book (
        id SERIAL PRIMARY KEY,
        title VARCHAR NOT NULL,
        author_id INTEGER,
        created_at TIMESTAMP,
        created_by VARCHAR,
        updated_at TIMESTAMP,
        updated_by VARCHAR,
        FOREIGN KEY (author_id) REFERENCES author (id)
    );

INSERT INTO
    book (title, author_id)
VALUES
    ('Pan Tadeusz', 1),
    ('Pan nie tadeusz', 1);

DROP TABLE IF EXISTS book_edition;

CREATE TABLE
    book_edition (
        id SERIAL PRIMARY KEY,
        publisher VARCHAR,
        number_of_pages INTEGER,
        subjects VARCHAR,
        book_id INTEGER,
        created_at TIMESTAMP,
        created_by VARCHAR,
        updated_at TIMESTAMP,
        updated_by VARCHAR,
        FOREIGN KEY (book_id) REFERENCES book (id)
    )
INSERT INTO
    book_edition (publisher, number_of_pages, subjects, book_id)
VALUES
    ('Pan Tadeusz', 645, 'Tadeusz, pan', 1),
    ('Pan nie tadeusz', 66474, 'nie pan taduesz', 1);