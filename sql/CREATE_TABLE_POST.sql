CREATE TABLE POSTS(
    ID NUMBER,
    CONSTRAINT POST_PK PRIMARY KEY (ID),
    MESSAGE CLOB,
    DATE_POSTED DATE,
    USER_POSTED_ID NUMBER,
    CONSTRAINT USER_POSTED_FK FOREIGN KEY (USER_POSTED_ID) REFERENCES USERS(ID)
);