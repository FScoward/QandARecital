# Project スキーマ
 
# --- !Ups
 
CREATE TABLE Project (
    project_id bigint(20) NOT NULL AUTO_INCREMENT,
    project_name text NOT NULL,
    owner bigint(20) NOT NULL,
    PRIMARY KEY (project_id)
);

CREATE TABLE Member (
    member_id bigint(20) NOT NULL AUTO_INCREMENT,
    username text NOT NULL,
    project_id bigint(20),
    authority varchar(10),
    PRIMARY KEY (member_id),
    FOREIGN KEY (project_id)
      REFERENCES Project(project_id)
);

CREATE TABLE Question (
    question_id bigint(20) NOT NULL AUTO_INCREMENT,
    question text NOT NULL,
    questioner bigint(20),
    project_id bigint(20),
    PRIMARY KEY (question_id),
    FOREIGN KEY (project_id)
      REFERENCES Project(project_id),
    FOREIGN KEY (questioner)
      REFERENCES Member(member_id)
);


 
# --- !Downs
 
DROP TABLE Project;
DROP TABLE Member;
DROP TABLE Question;
