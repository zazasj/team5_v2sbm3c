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
/* Table Name: 회원 테이블 */
/**********************************/
CREATE TABLE Member(
		memberno NUMERIC(10) NOT NULL PRIMARY KEY,
		id NUMERIC(10) NOT NULL,
		passwd VARCHAR(100) NOT NULL,
		mname VARCHAR(100) NOT NULL,
		tel VARCHAR(20),
		Email VARCHAR(100),
		Age INT,
		address1 VARCHAR(255),
		address2 INT,
		mdate DATE NOT NULL,
		grade NUMERIC(2) NOT NULL
);

/**********************************/
/* Table Name: 주문 */
/**********************************/
CREATE TABLE Orders(
		OrderID  NUMERIC(10) PRIMARY KEY,
		OrderDate DATETIME,
		TotalAmount VARCHAR(100),
		Status VARCHAR(50),
		memberno NUMERIC(10),
  FOREIGN KEY (memberno) REFERENCES Member (memberno)
);

/**********************************/
/* Table Name: 카테고리그룹 */
/**********************************/
CREATE TABLE CateGroup(
		GrpID NUMERIC(10) PRIMARY KEY,
		gname VARCHAR(50),
		Seqno NUMERIC(5),
		Visible CHAR(1),
		rdate DATE
);

/**********************************/
/* Table Name: 카테고리 */
/**********************************/
CREATE TABLE Category(
		CategoryID NUMERIC(10) PRIMARY KEY,
		GrpID NUMERIC(10),
		CategoryName VARCHAR(100),
		cnt NUMERIC(7) NOT NULL,
		Rdate DATE,
		Seqno NUMERIC(5),
		Visible CHAR(1),
  FOREIGN KEY (GrpID) REFERENCES CateGroup (GrpID)
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
		Thumbs VARCHAR(50),
		sizes NUMERIC(10),
		Pcnt NUMERIC(9),
		recom NUMERIC(8),
  FOREIGN KEY (CategoryID) REFERENCES Category (CategoryID),
  FOREIGN KEY (SupplierID) REFERENCES Suppliers (SupplierID)
);

/**********************************/
/* Table Name: 주문상세 */
/**********************************/
CREATE TABLE OrderDetails(
		OrderDetail NUMERIC(10) PRIMARY KEY,
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
		cnt NUMERIC(10),
		ProductID NUMERIC(10),
		memberno NUMERIC(10),
  FOREIGN KEY (ProductID) REFERENCES Products (ProductID),
  FOREIGN KEY (memberno) REFERENCES Member (memberno)
);

/**********************************/
/* Table Name: 리뷰 */
/**********************************/
CREATE TABLE Reviews(
		ReviewID NUMERIC(10) PRIMARY KEY,
		ProductID NUMERIC(10),
		Rating NUMERIC(3),
		Comment VARCHAR(300),
		ReviewDate DATETIME,
		memberno NUMERIC(10),
  FOREIGN KEY (ProductID) REFERENCES Products (ProductID),
  FOREIGN KEY (memberno) REFERENCES Member (memberno)
);

/**********************************/
/* Table Name: 재고 */
/**********************************/
CREATE TABLE Inventory(
		InventoryID NUMERIC(10) PRIMARY KEY,
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
		SessionID NUMERIC(10) PRIMARY KEY,
		SessionStart DATETIME,
		SessionEnd DATETIME,
		UserQuery VARCHAR(500),
		BotResponse VARCHAR(500),
		memberno NUMERIC(10),
  FOREIGN KEY (memberno) REFERENCES Member (memberno)
);

/**********************************/
/* Table Name: 추천시스템 */
/**********************************/
CREATE TABLE Recommendations(
		RID NUMERIC(10) PRIMARY KEY,
		ProductID NUMERIC(10),
		CreatedAt DATETIME,
		Userno NUMERIC(10),
		memberno NUMERIC(10),
  FOREIGN KEY (ProductID) REFERENCES Products (ProductID),
  FOREIGN KEY (memberno) REFERENCES Member (memberno)
);

/**********************************/
/* Table Name: 황경태 */
/**********************************/
CREATE TABLE 황경태(

);

/**********************************/
/* Table Name: 한창명 */
/**********************************/
CREATE TABLE 한창명(

);

/**********************************/
/* Table Name: 전세진 */
/**********************************/
CREATE TABLE 전세진(

);

/**********************************/
/* Table Name: 이정탁 */
/**********************************/
CREATE TABLE 이정탁(

);

/**********************************/
/* Table Name: 홍재민 */
/**********************************/
CREATE TABLE 홍재민(

);

/**********************************/
/* Table Name: 이벤트 */
/**********************************/
CREATE TABLE Event(
		eventno NUMERIC(10) NOT NULL PRIMARY KEY,
		title VARCHAR(50),
		contents VARCHAR(2000),
		thumbs VARCHAR(1000),
		files VARCHAR(1000),
		sizes VARCHAR(1000),
		rdate DATE,
		Adminno NUMERIC(5),
  FOREIGN KEY (Adminno) REFERENCES Admin (Adminno)
);

/**********************************/
/* Table Name: 관심상품 */
/**********************************/
CREATE TABLE FavProduct(
		FavID NUMERIC(10),
		ProductID NUMERIC(10),
		CreatedAt DATE,
		memberno NUMERIC(10),
  FOREIGN KEY (ProductID) REFERENCES Products (ProductID),
  FOREIGN KEY (memberno) REFERENCES Member (memberno)
);

/**********************************/
/* Table Name: 결제 */
/**********************************/
CREATE TABLE Payments(
		PaymentID NUMERIC(10) NOT NULL PRIMARY KEY,
		OrderID  NUMERIC(10) NOT NULL,
		memberno NUMERIC(10) NOT NULL,
		PaymentMethod VARCHAR(100) NOT NULL,
		PaymentAmount VARCHAR(100) NOT NULL,
		PaymentStatus VARCHAR(50),
		PaymentDate DATE,
		AuthorizationCode VARCHAR(100),
		PaymentDetails VARCHAR(500),
  FOREIGN KEY (OrderID ) REFERENCES Orders (OrderID ),
  FOREIGN KEY (memberno) REFERENCES Member (memberno)
);

/**********************************/
/* Table Name: 관리자로그 */
/**********************************/
CREATE TABLE AdminLog(
		ActivityID NUMERIC(10) PRIMARY KEY,
		TableName VARCHAR(100),
		RecordID NUMERIC(10),
		ActType VARCHAR(10),
		ActDate DATE,
		Adminno NUMERIC(5),
  FOREIGN KEY (Adminno) REFERENCES Admin (Adminno)
);

/**********************************/
/* Table Name: 로그인 내역 */
/**********************************/
CREATE TABLE Login(
		loginno NUMERIC(10) NOT NULL PRIMARY KEY,
		memberno NUMERIC(10),
		ip NUMERIC(10) NOT NULL,
		logindate DATE NOT NULL,
  FOREIGN KEY (memberno) REFERENCES Member (memberno)
);

