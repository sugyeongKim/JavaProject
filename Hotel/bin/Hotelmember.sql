CREATE TABLE MEMBER_TEST(	
NUM			INT(11) NOT NULL AUTO_INCREMENT, 
ID			VARCHAR(20) NOT NULL, 
PASSWORD	VARCHAR(200) NOT NULL, 
NAME		VARCHAR(10) NOT NULL,
GENDER		VARCHAR(4) NOT NULL,
AGE			INT(3) NOT NULL,
TEL			VARCHAR(13) NOT NULL,
ADDR		VARCHAR(50) NOT NULL,
MILEAGE 	INT(10) NOT NULL,
PRIMARY KEY (ID),
KEY NUM (NUM)
)

CREATE TABLE MEMBER(	
NUM			INT(11) NOT NULL AUTO_INCREMENT, 
NAME		VARCHAR(10) NOT NULL,
GENDER		VARCHAR(4) NOT NULL,
AGE			INT(3) NOT NULL,
TEL			VARCHAR(13) NOT NULL,
ADDR		VARCHAR(50) NOT NULL,
MILEAGE 	INT(10) NOT NULL,
PRIMARY KEY (NUM)
)

CREATE TABLE STAFF(
NUMBER		INT(11) NOT NULL AUTO_INCREMENT,
NAME		VARCHAR(10) NOT NULL,
GENDER		VARCHAR(4) NOT NULL,
AGE			INT(3) NOT NULL,
TEL			VARCHAR(13) NOT NULL,
ADDR		VARCHAR(50) NOT NULL,
POSITION	VARCHAR(30) NOT NULL,
department	VARCHAR(30) NOT NULL,
PRIMARY KEY (NUMBER)
)

CREATE TABLE ROOM(
NUM				INT(3) NOT NULL,
CHK				TINYINT(1) NOT NULL,
USER_NAME		VARCHAR(10) NOT NULL,
IN_DATE			DATETIME NOT NULL,
USE_TERM		INT(3) NOT NULL,
SERVICE_PRICE	INT(10) NOT NULL,
PRIMARY KEY(NUM)
)

ALTER TABLE MEMBER CHANGE MILEAGE TOTAL_AMOUNT INT;

ALTER TABLE MEMBER ADD ROOM_TYPE VARCHAR(10) NOT NULL NULL FIRST;

ALTER TABLE MEMBER ADD INSERTDATE TIMESTAMP NOT NULL NULL AFTER ROOM_TYPE;