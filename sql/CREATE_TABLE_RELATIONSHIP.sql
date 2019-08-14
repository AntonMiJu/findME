CREATE TABLE RELATIONSHIPS(
    USER_FROM_ID INT NOT NULL,
    USER_TO_ID INT NOT NULL,
    CONSTRAINT RELATIONSHIP_PK PRIMARY KEY (USER_FROM_ID, USER_TO_ID)
    STATUS VARCHAR(30),
    START_OF_RLT TIMESTAMP,
    CONSTRAINT USER_FROM_FK FOREIGN KEY USER_FROM_ID REFERENCES USERS (ID),
    CONSTRAINT USER_TO_FK FOREIGN KEY USER_TO_ID REFERENCES USERS (ID)
);