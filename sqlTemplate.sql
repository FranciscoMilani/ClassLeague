CREATE TABLE class (
    id serial NOT NULL,
    name VARCHAR(255) NOT NULL,
    shift VARCHAR(255) NOT NULL,
    cycle VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE student (
    id serial NOT NULL,
    name VARCHAR(255) NOT NULL,
    class_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (class_id) REFERENCES class(id)
);


