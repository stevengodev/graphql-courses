CREATE TABLE IF NOT EXISTS course_user (
    id_student INTEGER NOT NULL REFERENCES "user"(id),
    id_course INTEGER NOT NULL REFERENCES course(id),
    PRIMARY KEY (id_student, id_course)
);