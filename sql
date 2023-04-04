CREATE TABLE members (
  id INT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  birthdate DATE NOT NULL,
  class_id INT,
  FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE SET NULL
);

CREATE TABLE classes (
  id INT PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE class_members (
  class_id INT NOT NULL,
  member_id INT NOT NULL,
  PRIMARY KEY (class_id, member_id),
  FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE,
  FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE
);

CREATE TABLE code_reviews (
  id INT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(200) NOT NULL,
  datetime DATETIME NOT NULL,
  class_id INT NOT NULL,
  FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE
);


DELIMITER //

CREATE TRIGGER insert_class_member AFTER INSERT ON members
FOR EACH ROW
BEGIN
  INSERT INTO class_members (class_id, member_id) VALUES (NEW.class_id, NEW.id);
END //

DELIMITER ;

DELIMITER $$
CREATE TRIGGER trg_members_delete
AFTER DELETE ON members
FOR EACH ROW
BEGIN
    DELETE FROM class_members WHERE member_id = OLD.id;
END $$
DELIMITER ;



