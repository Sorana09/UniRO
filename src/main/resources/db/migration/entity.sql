DROP TABLE IF EXISTS university_entity CASCADE;
DROP TABLE IF EXISTS category_entity CASCADE;
DROP TABLE IF EXISTS university_category CASCADE;

DROP TABLE IF EXISTS user_entity CASCADE;
DROP TABLE IF EXISTS sessions CASCADE;

CREATE TABLE user_entity (
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
                                   rank INT,
                                   admission_requirements VARCHAR(1000)
);

CREATE TABLE category_entity (
                                 id SERIAL PRIMARY KEY,
                                 name VARCHAR(100) NOT NULL
);
CREATE TABLE university_category (
                                     university_id INT NOT NULL,
                                     category_id INT NOT NULL,
                                     PRIMARY KEY (university_id, category_id),
                                     FOREIGN KEY (university_id) REFERENCES university_entity(id) ON DELETE CASCADE,
                                     FOREIGN KEY (category_id) REFERENCES category_entity(id) ON DELETE CASCADE
);
CREATE TABLE sessions (
                          id SERIAL PRIMARY KEY ,
                          session_key VARCHAR(128) NOT NULL UNIQUE,
                          expires_at TIMESTAMP NOT NULL,
                          user_id INT NOT NULL,
                          CONSTRAINT fk_session_user_id FOREIGN KEY (user_id)
                              REFERENCES user_entity(id)
                              ON DELETE CASCADE
);


