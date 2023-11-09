/**********************************/
/* Table Name: 관리자 */
/**********************************/
DROP TABLE Admin CASCADE CONSTRAINTS;

CREATE TABLE Admin(
        Adminno    NUMBER(5) NOT NULL PRIMARY KEY,
		AdminID VARCHAR(20) NOT NULL UNIQUE,
        Passwd VARCHAR(100) NOT NULL,
		Adname VARCHAR(100) NOT NULL,
		Email VARCHAR(50),
		CreatedAt DATE NOT NULL
);

DROP SEQUENCE ADMIN_SEQ;

CREATE SEQUENCE ADMIN_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 99999            -- 최대값: 99999 --> NUMBER(5) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지

INSERT INTO admin(Adminno, AdminID, Passwd, Adname, CreatedAt, Email)
VALUES(admin_seq.nextval, 'admin1', '1234', '관리자1', sysdate, 'admin1@naver.com');

INSERT INTO admin(Adminno, AdminID, Passwd, Adname, CreatedAt, Email)
VALUES(admin_seq.nextval, 'admin2', '1234', '관리자2', sysdate, 'admin2@naver.com');

commit;

SELECT * FROM admin ORDER BY adminno ASC;

/**********************************/
/* Table Name: 사용자 테이블 */
/**********************************/
DROP TABLE Users CASCADE CONSTRAINTS;

CREATE TABLE Users(
		UserID NUMERIC(10) PRIMARY KEY,
		Username VARCHAR(100) NOT NULL,
		Password VARCHAR(100) NOT NULL,
		Email VARCHAR(100),
		Age INT NOT NULL,
		Address VARCHAR(255) NOT NULL,
		PhoneNumber VARCHAR(50) NOT NULL,
        CreatedAt  DATE NOT NULL
);

DROP SEQUENCE USERS_SEQ;

CREATE SEQUENCE USERS_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지

INSERT INTO Users(UserID, Username, Password, Email, Age, Address,PhoneNumber,CreatedAt)
VALUES(users_seq.nextval, 'user1', '1234', 'user1@naver.com',24,'경기도 안양시','01099887766', sysdate);

INSERT INTO Users(UserID, Username, Password, Email, Age, Address,PhoneNumber,CreatedAt)
VALUES(users_seq.nextval, 'user2', '1234', 'user2@naver.com',24,'경기도 수원시','01038384848' ,sysdate);

commit;

SELECT * FROM Users ORDER BY UserID ASC;


/**********************************/
/* Table Name: 주문 */
/**********************************/
CREATE TABLE Orders(
		OrderID  NUMERIC(10) PRIMARY KEY,
		UserID NUMERIC(10),
		OrderDate DATE,
		TotalAmount VARCHAR(100),
		Status VARCHAR(50),
  FOREIGN KEY (UserID) REFERENCES Users (UserID)
);

CREATE SEQUENCE ORDERS_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지

INSERT INTO Orders(OrderID, UserID, OrderDate, TotalAmount, Status)
VALUES(orders_seq.nextval, 1, sysdate, '100000','대기중');

/**********************************/
/* Table Name: 카테고리 */
/**********************************/
CREATE TABLE Category(
		CategoryID NUMERIC(10) PRIMARY KEY,
		CategoryName VARCHAR(100),
		CNT                           		NUMBER(7)		 DEFAULT 0 NOT NULL,
		RDATE                         		DATE		     NOT NULL,
        SEQNO                               NUMBER(5)        DEFAULT 1 NOT NULL,
        VISIBLE                             CHAR(1)          DEFAULT 'Y' NOT NULL 
);

CREATE SEQUENCE CATEGORY_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지

INSERT INTO Category(CategoryID, CategoryName, CNT, RDATE)
VALUES(category_seq.nextval, '위스키',  0 ,sysdate);

INSERT INTO Category(CategoryID, CategoryName, CNT, RDATE)
VALUES(category_seq.nextval, '브랜디&꼬냑',  0 ,sysdate);

INSERT INTO Category(CategoryID, CategoryName, CNT, RDATE)
VALUES(category_seq.nextval, '와인',  0 ,sysdate);

INSERT INTO Category(CategoryID, CategoryName, CNT, RDATE)
VALUES(category_seq.nextval, '리큐르',  0 ,sysdate);

INSERT INTO Category(CategoryID, CategoryName, CNT, RDATE)
VALUES(category_seq.nextval, '민속주',  0 ,sysdate);

INSERT INTO Category(CategoryID, CategoryName, CNT, RDATE)
VALUES(category_seq.nextval, '중국주',  0 ,sysdate);

INSERT INTO Category(CategoryID, CategoryName, CNT, RDATE)
VALUES(category_seq.nextval, '일본주',  0 ,sysdate);
/**********************************/
/* Table Name: 공급업체 */
/**********************************/
CREATE TABLE Suppliers(
		SupplierID NUMERIC(10) PRIMARY KEY,
		ActivityID NUMERIC(50),
		SName VARCHAR(100),
		ContactInfo VARCHAR(200),
		SAddress VARCHAR(200)
);

/**********************************/
/* Table Name: 제품 */
/**********************************/
CREATE TABLE Products(
		ProductID NUMERIC(10) PRIMARY KEY,
		CategoryID NUMERIC(10),
		SupplierID NUMERIC(10),
		PName VARCHAR(200),
		Description VARCHAR(300),
		Volume NUMERIC(30),
		AlcoholContent NUMERIC(4),
		Price NUMERIC(30),
		ImageFile VARCHAR(50),
		ImageFileSaved VARCHAR(50),
		THUMB1 VARCHAR(50),
		SIZE1 NUMERIC(10),
  FOREIGN KEY (CategoryID) REFERENCES Category (CategoryID),
  FOREIGN KEY (SupplierID) REFERENCES Suppliers (SupplierID)
);

/**********************************/
/* Table Name: 주문상세 */
/**********************************/
CREATE TABLE OrderDetails(
		OrderDetailID NUMERIC(10) PRIMARY KEY,
		Quantity NUMERIC(10),
		Price NUMERIC(10),
		OrderID  NUMERIC(10),
		ProductID NUMERIC(10),
  FOREIGN KEY (OrderID ) REFERENCES Orders (OrderID ),
  FOREIGN KEY (ProductID) REFERENCES Products (ProductID)
);

/**********************************/
/* Table Name: 장바구니 */
/**********************************/
CREATE TABLE Carts(
		CartID NUMERIC(10) PRIMARY KEY,
		CreatedAt DATE,
		UserID NUMERIC(10),
  FOREIGN KEY (UserID) REFERENCES Users (UserID)
);

/**********************************/
/* Table Name: 리뷰 */
/**********************************/
CREATE TABLE Reviews(
		ReviewID NUMERIC(10) PRIMARY KEY,
		ProductID NUMERIC(10),
		UserID NUMERIC(10),
		Rating NUMERIC(3),
		Comment VARCHAR(300),
		ReviewDate DATE,
  FOREIGN KEY (ProductID) REFERENCES Products (ProductID),
  FOREIGN KEY (UserID) REFERENCES Users (UserID)
);

/**********************************/
/* Table Name: 재고 */
/**********************************/
CREATE TABLE Inventory(
		InventoryID VARCHAR(10) PRIMARY KEY,
		ProductID NUMERIC(10),
		ActivityID NUMERIC(50),
		Quantity NUMERIC(10),
		LastUpdated DATE,
  FOREIGN KEY (ProductID) REFERENCES Products (ProductID)
);

/**********************************/
/* Table Name: 배송 */
/**********************************/
CREATE TABLE Shipping(
		ShippingID NUMERIC(10) PRIMARY KEY,
		OrderID  NUMERIC(10),
		ShippingType VARCHAR(50),
		Cost INT,
		EstimatedDeliveryDate DATE,
		TrackingNumber VARCHAR(200),
  FOREIGN KEY (OrderID ) REFERENCES Orders (OrderID )
);

/**********************************/
/* Table Name: 챗봇 */
/**********************************/
CREATE TABLE ChatbotSessions(
		SessionID INT PRIMARY KEY,
		UserID NUMERIC(10),
		SessionStart DATE,
		SessionEnd DATE,
		UserQuery VARCHAR(500),
		BotResponse VARCHAR(500),
  FOREIGN KEY (UserID) REFERENCES Users (UserID)
);

/**********************************/
/* Table Name: 추천시스템 */
/**********************************/
CREATE TABLE Recommendations(
		RID NUMERIC(10) PRIMARY KEY,
		UserID NUMERIC(10),
		ProductID NUMERIC(10),
		CreatedAt DATE,
  FOREIGN KEY (UserID) REFERENCES Users (UserID),
  FOREIGN KEY (ProductID) REFERENCES Products (ProductID)
);

