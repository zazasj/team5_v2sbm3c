/**********************************/
/* Table Name: 관리자 */
/**********************************/
CREATE TABLE Admin(
		Adminno NUMERIC(5) PRIMARY KEY,
		AdminID NUMERIC(10) NOT NULL,
		Adname VARCHAR(100),
		Password VARCHAR(100),
		Email VARCHAR(50),
		CreatedAt DATETIME
);

/**********************************/
/* Table Name: 사용자 테이블 */
/**********************************/
CREATE TABLE Users(
		UserID NUMERIC(10) PRIMARY KEY,
		Username VARCHAR(100),
		Password VARCHAR(100),
		Email VARCHAR(100),
		Age INT,
		Address VARCHAR(255),
		PhoneNumber VARCHAR(20)
);

/**********************************/
/* Table Name: 주문 */
/**********************************/
CREATE TABLE Orders(
		OrderID  NUMERIC(10) PRIMARY KEY,
		UserID NUMERIC(10),
		OrderDate DATETIME,
		TotalAmount VARCHAR(100),
		Status VARCHAR(50),
  FOREIGN KEY (UserID) REFERENCES Users (UserID)
);

/**********************************/
/* Table Name: 카테고리 */
/**********************************/
CREATE TABLE Category(
		CategoryID NUMERIC(10) PRIMARY KEY,
		CategoryName VARCHAR(100),
		CNT NUMERIC(7) NOT NULL,
		RDATE DATE,
		SEQNO NUMERIC(5),
		VISIBLE CHAR(1)
);

/**********************************/
/* Table Name: 공급업체 */
/**********************************/
CREATE TABLE Suppliers(
		SupplierID NUMERIC(10) PRIMARY KEY,
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
		CreatedAt DATETIME,
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
		ReviewDate DATETIME,
  FOREIGN KEY (ProductID) REFERENCES Products (ProductID),
  FOREIGN KEY (UserID) REFERENCES Users (UserID)
);

/**********************************/
/* Table Name: 재고 */
/**********************************/
CREATE TABLE Inventory(
		InventoryID VARCHAR(10) PRIMARY KEY,
		ProductID NUMERIC(10),
		Quantity NUMERIC(10),
		LastUpdated DATETIME,
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
		SessionStart DATETIME,
		SessionEnd DATETIME,
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
		CreatedAt DATETIME,
  FOREIGN KEY (UserID) REFERENCES Users (UserID),
  FOREIGN KEY (ProductID) REFERENCES Products (ProductID)
);

);

