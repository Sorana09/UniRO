DROP TABLE IF EXISTS university_entity CASCADE;
DROP TABLE IF EXISTS category_entity CASCADE;
DROP TABLE IF EXISTS university_category CASCADE;
DROP TABLE IF EXISTS language_entity CASCADE;
DROP TABLE IF EXISTS study_program_entity CASCADE;
DROP TABLE IF EXISTS category_language CASCADE;
DROP TABLE IF EXISTS category_study_program CASCADE;

DROP TABLE IF EXISTS user_entity CASCADE;
DROP TABLE IF EXISTS sessions CASCADE;
DROP TABLE IF EXISTS review_entity CASCADE;

CREATE TABLE user_entity (
                             id SERIAL PRIMARY KEY,
                             email VARCHAR(255) NOT NULL UNIQUE,
                             first_name VARCHAR(255) NOT NULL,
                             last_name VARCHAR(255) NOT NULL,
                             hashed_password VARCHAR(255) NOT NULL,
                             is_admin BOOLEAN DEFAULT FALSE NOT NULL
);

ALTER TABLE user_entity ADD COLUMN interests_and_hobbies VARCHAR(1000);
ALTER TABLE user_entity ADD COLUMN suitable_cities VARCHAR(255);
ALTER TABLE user_entity DROP COLUMN recommandation;
ALTER TABLE user_entity ADD COLUMN recommandation VARCHAR(10000);

CREATE TABLE university_entity (
                                   id SERIAL PRIMARY KEY,
                                   name VARCHAR(255) NOT NULL,
                                   location VARCHAR(255) NOT NULL,
                                   website VARCHAR(255) NOT NULL,
                                   rank INT,
                                   admission_requirements VARCHAR(1000)
);
ALTER TABLE university_entity
ADD COLUMN latitude DECIMAL (9,6),
ADD COLUMN longitude DECIMAL(9,6);

ALTER TABLE university_entity ADD COLUMN description VARCHAR(10000);

CREATE TABLE category_entity (
                                 id SERIAL PRIMARY KEY,
                                 name VARCHAR(1000) NOT NULL
);

ALTER TABLE category_entity ADD COLUMN description VARCHAR(10000), ADD COLUMN latitude DECIMAL(9,6), ADD COLUMN longitude DECIMAL(9,6);
ALTER TABLE category_entity ADD COLUMN entrance_method VARCHAR(10000);
ALTER TABLE category_entity DROP COLUMN entrance_method ;

CREATE TABLE language_entity(
                                id SERIAL PRIMARY KEY,
                                name VARCHAR(1000) NOT NULL
);
CREATE TABLE study_program_entity(
                            id SERIAL PRIMARY KEY ,
                            name VARCHAR(1000) NOT NULL
);
CREATE TABLE category_language(
                    category_id INT REFERENCES category_entity(id) ON DELETE CASCADE,
                    language_id INT REFERENCES language_entity(id) ON DELETE CASCADE,
                    PRIMARY KEY (category_id,language_id)
);
CREATE TABLE category_study_program(
                    category_id INT REFERENCES category_entity(id) ON DELETE CASCADE,
                    study_program_id INT REFERENCES study_program_entity(id) ON DELETE CASCADE,
                    PRIMARY KEY (category_id, study_program_id)
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

CREATE TABLE review_entity(
                        id SERIAL PRIMARY KEY,
                        message VARCHAR(255) NOT NULL,
                        wrote_at TIMESTAMP NOT NULL,
                        user_id INT NOT NULL,
                        CONSTRAINT  fk_review_entity_user_id FOREIGN KEY (user_id)
                                              REFERENCES  user_entity(id)
                                              ON DELETE CASCADE,
                        university_id INT NOT NULL,
                        CONSTRAINT fk_review_entity_university_id FOREIGN KEY (university_id)
                                              REFERENCES  university_entity(id)
                                              ON DELETE CASCADE

)


