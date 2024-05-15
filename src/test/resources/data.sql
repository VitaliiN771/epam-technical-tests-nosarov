DROP TABLE couriers;
CREATE TABLE couriers
(
    ID      INTEGER PRIMARY KEY,
    FST_NME VARCHAR(64) NOT NULL,
    LST_NME VARCHAR(64) NOT NULL,
    ACTV    BOOLEAN     NOT NULL
);

INSERT INTO couriers (ID, FST_NME, LST_NME, ACTV)
VALUES (1, 'John', 'Random', 1);

INSERT INTO couriers (ID, FST_NME, LST_NME, ACTV)
VALUES (2, 'Sam', 'Kin', 1);

INSERT INTO couriers (ID, FST_NME, LST_NME, ACTV)
VALUES (3, 'TestName', 'TestLastName', 0);

INSERT INTO couriers (ID, FST_NME, LST_NME, ACTV)
VALUES (4, 'Ihor', 'Jonson', 0);