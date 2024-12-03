DROP TABLE IF EXISTS university_entity CASCADE;
DROP TABLE IF EXISTS category_entity CASCADE;
DROP TABLE IF EXISTS user_entity CASCADE;

CREATE TABLE user_entity(
                             id SERIAL PRIMARY KEY,
                             email VARCHAR(255) NOT NULL UNIQUE,
                             first_name VARCHAR(255) NOT NULL,
                             last_name VARCHAR(255) NOT NULL,
                             hashed_password VARCHAR(255) NOT NULL
);

CREATE TABLE university_entity (
                                   id SERIAL PRIMARY KEY,
                                   name VARCHAR(255) NOT NULL,
                                   location VARCHAR(255) NOT NULL,
                                   website VARCHAR(255) NOT NULL,
                                   rank INT NOT NULL,
                                   admission_requirements VARCHAR(1000) NOT NULL
);

CREATE TABLE category_entity (
                                 id SERIAL PRIMARY KEY,
                                 name VARCHAR(100) NOT NULL,
                                 user_id BIGINT NOT NULL,
                                 CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
