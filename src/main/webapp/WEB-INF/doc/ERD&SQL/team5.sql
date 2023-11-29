/**********************************/
/* Table Name: 관리자 */
/**********************************/
DROP TABLE Admin CASCADE CONSTRAINTS;

CREATE TABLE Admin(
        ADMINNO    NUMBER(5)    NOT NULL,
        ID         VARCHAR(20)   NOT NULL UNIQUE, -- 아이디, 중복 안됨, 레코드를 구분 
        PASSWD     VARCHAR(15)   NOT NULL, -- 패스워드, 영숫자 조합
        MNAME      VARCHAR(20)   NOT NULL, -- 성명, 한글 10자 저장 가능
        MDATE      DATE          NOT NULL, -- 가입일    
        GRADE      NUMBER(2)     NOT NULL, -- 등급(1~10: 관리자, 정지 관리자: 20, 탈퇴 관리자: 99)    
        PRIMARY KEY (ADMINNO) 
);

DROP SEQUENCE ADMIN_SEQ;

CREATE SEQUENCE ADMIN_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 99999            -- 최대값: 99999 --> NUMBER(5) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지

INSERT INTO admin(adminno, id, passwd, mname, mdate, grade)
VALUES(admin_seq.nextval, 'admin1', '1234', '관리자1', sysdate, 1);

INSERT INTO admin(adminno, id, passwd, mname, mdate, grade)
VALUES(admin_seq.nextval, 'admin2', '2345', '관리자2', sysdate, 1);

INSERT INTO admin(adminno, id, passwd, mname, mdate, grade)
VALUES(admin_seq.nextval, 'admin3', '3456', '관리자3', sysdate, 1);
commit;

SELECT * FROM admin ORDER BY adminno ASC;
SELECT adminno, id, passwd, mname, mdate, grade FROM admin ORDER BY adminno ASC;


/**********************************/
/* Table Name: 회원 테이블 */
/**********************************/
DROP TABLE Member CASCADE CONSTRAINTS;

CREATE TABLE Member(
		memberno NUMERIC(10) NOT NULL PRIMARY KEY,
		id VARCHAR(50) NOT NULL,
		passwd VARCHAR(100) NOT NULL,
		mname VARCHAR(100) NOT NULL,
		tel VARCHAR(20),
		Age INT,
        zipcode VARCHAR(5) NULL,
		address1 VARCHAR(200),
		address2 VARCHAR(100),
		mdate DATE NOT NULL,
		grade NUMERIC(2) NOT NULL
);

DROP SEQUENCE MEMBER_SEQ;

CREATE SEQUENCE MEMBER_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지

INSERT INTO Member(memberno, id, passwd,mname,tel, Age, address1,mdate,grade)
VALUES(member_seq.nextval, 'user1@naver.com', '1234','회원1','01099887766',24,'경기도 안양시', sysdate,1);

INSERT INTO Member(memberno, id, passwd,mname,tel, Age, address1,mdate,grade)
VALUES(member_seq.nextval, 'user2@gmail.com', '1234','회원2','01038384848',26,'경기도 수원시', sysdate,1);

ALTER TABLE Member ADD zipcode VARCHAR(5);

commit;

SELECT * FROM Member ORDER BY memberno ASC;


/**********************************/
/* Table Name: 주문 */
/**********************************/
DROP TABLE Orders CASCADE CONSTRAINTS;
CREATE TABLE Orders(
		OrderID  NUMERIC(10) PRIMARY KEY,
		memberno NUMERIC(10),
		OrderDate DATE,
		TotalAmount NUMERIC(20),
		Status VARCHAR(50),
  FOREIGN KEY (memberno) REFERENCES Member (memberno)
);

UPDATE Orders SET TotalAmount = NULL;
ALTER TABLE Orders MODIFY TotalAmount NUMERIC(20);

DROP SEQUENCE ORDERS_SEQ;

CREATE SEQUENCE ORDERS_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지

INSERT INTO Orders(OrderID, memberno, OrderDate, TotalAmount, Status)
VALUES(orders_seq.nextval, 1, sysdate, '0','대기중');

UPDATE Orders SET TotalAmount='99000' WHERE OrderID=1;

SELECT * FROM Orders;

commit;
/**********************************/
/* Table Name: 카테고리그룹 */
/**********************************/
CREATE TABLE CateGroup(
		GrpID NUMERIC(10) PRIMARY KEY,
		gname VARCHAR(50) NOT NULL,
		Seqno NUMERIC(5) DEFAULT 99 NOT NULL,
		Visible CHAR(1) DEFAULT 'Y' NOT NULL,
		rdate DATE
);

CREATE SEQUENCE CATEGROUP_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지



INSERT INTO CateGroup(GrpID, gname, rdate)
VALUES(categroup_seq.nextval, '위스키',sysdate);

INSERT INTO CateGroup(GrpID, gname, rdate)
VALUES(categroup_seq.nextval, '브랜디/꼬냑',sysdate);

INSERT INTO CateGroup(GrpID, gname, rdate)
VALUES(categroup_seq.nextval, '와인',sysdate);

INSERT INTO CateGroup(GrpID, gname, rdate)
VALUES(categroup_seq.nextval, '리큐르',sysdate);

INSERT INTO CateGroup(GrpID, gname, rdate)
VALUES(categroup_seq.nextval, '전통주',sysdate);

INSERT INTO CateGroup(GrpID, gname, rdate)
VALUES(categroup_seq.nextval, '커뮤니티',sysdate);

UPDATE CateGroup SET Seqno=95 WHERE GrpID=6;

SELECT * FROM CateGroup;

/**********************************/
/* Table Name: 카테고리 */
/**********************************/
CREATE TABLE Category(
		CategoryID NUMERIC(10) PRIMARY KEY,
		GrpID NUMERIC(10),
		CategoryName VARCHAR(100),
		cnt NUMERIC(7) NOT NULL,
		Rdate DATE,
		Seqno NUMERIC(5) DEFAULT 99 NOT NULL,
		Visible CHAR(1)  DEFAULT 'Y' NOT NULL,
  FOREIGN KEY (GrpID) REFERENCES CateGroup (GrpID)
);

CREATE SEQUENCE CATEGORY_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지


INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 1 , '영국',  0 ,sysdate, 80);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 1 , '미국',  0 ,sysdate, 81);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 1 , '캐나다',  0 ,sysdate, 82);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 1 , '일본',  0 ,sysdate, 83);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 1 , '기타',  0 ,sysdate, 84);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 3 , '프랑스',  0 ,sysdate, 85);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 3 , '칠레',  0 ,sysdate, 86);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 3 , '이태리',  0 ,sysdate, 87);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 3 , '스페인',  0 ,sysdate, 87);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 3 , '미국',  0 ,sysdate, 88);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 3 , '독일  ',  0 ,sysdate, 89);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 3 , '남아공  ',  0 ,sysdate, 90);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 3 , '스위트와인',  0 ,sysdate, 91);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 3 , '스파쿨링와인',  0 ,sysdate, 92);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 3 , '기타와인',  0 ,sysdate, 93);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 4 , '진',  0 ,sysdate, 94);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 4 , '럼',  0 ,sysdate, 95);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 4 , '보드카',  0 ,sysdate, 96);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 4 , '데낄라',  0 ,sysdate, 97);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 4 , '리큐르',  0 ,sysdate, 98);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 4 , '기타',  0 ,sysdate, 99);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 4 , '기타',  0 ,sysdate, 99);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 5 , '중국주',  0 ,sysdate, 99);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 5 , '일본주',  0 ,sysdate, 100);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 5 , '민속주',  0 ,sysdate, 101);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 6 , '상품문의',  0 ,sysdate, 102);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 6 , '공지사항',  0 ,sysdate, 103);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 6 , '기업대량구매',  0 ,sysdate, 104);

INSERT INTO Category(CategoryID,GrpID, CategoryName, CNT, RDATE, Seqno)
VALUES(category_seq.nextval, 6 , '고객센터',  0 ,sysdate, 105);

SELECT * FROM Category;
/**********************************/
/* Table Name: 공급업체 */
/**********************************/
DROP TABLE Suppliers CASCADE CONSTRAINTS;
CREATE TABLE Suppliers(
		SupplierID NUMERIC(10) PRIMARY KEY,
        Adminno    NUMBER(5),
		SName VARCHAR(100) NOT NULL,
		ContactInfo VARCHAR(200) NOT NULL,
		SAddress VARCHAR(200) NOT NULL,
        Rdate DATE,
        FOREIGN KEY (Adminno) REFERENCES Admin (Adminno)
);
DROP SEQUENCE SUPPLY_SEQ;
CREATE SEQUENCE SUPPLY_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지


INSERT INTO Suppliers(SupplierID, Adminno, SName, ContactInfo, SAddress, Rdate)
VALUES(supply_seq.nextval,1, '[주]주류사업장' , '연락처 : 010-1234-5678 이메일: abcd123@naver.com', '경기도 수원시' , sysdate);

INSERT INTO Suppliers(SupplierID, Adminno, SName, ContactInfo, SAddress, Rdate)
VALUES(supply_seq.nextval,1, '공식 주류 특판장', '연락처 : 010-1111-2233 이메일: abcd123@naver.com', '서울시 강남구' , sysdate);

SELECT * FROM Suppliers;
/**********************************/
/* Table Name: 제품 */
/**********************************/
DROP TABLE Products;

CREATE TABLE Products(
		ProductID NUMERIC(10) PRIMARY KEY,
		CategoryID NUMERIC(10) NOT NULL,
		SupplierID NUMERIC(10) NOT NULL,
        Origin VARCHAR(20) NOT NULL,
		PName VARCHAR(200) NOT NULL,
		Description VARCHAR(1000),
		Volume NUMERIC(30) NOT NULL,
		AlcoholContent NUMERIC(4) NOT NULL,
		Price NUMERIC(30) NOT NULL,
		ImageFile VARCHAR(50),
		ImageFileSaved VARCHAR(50),
		Thumbs VARCHAR(50), --이미지preview
		sizes NUMERIC(10), --이미지preview크기
		Pcnt NUMERIC(9) DEFAULT 0 , --조회수
		recom NUMERIC(8) DEFAULT 0, --추천수
  FOREIGN KEY (CategoryID) REFERENCES Category (CategoryID),
  FOREIGN KEY (SupplierID) REFERENCES Suppliers (SupplierID)
);
DROP  SEQUENCE PRODUCT_SEQ;

CREATE SEQUENCE PRODUCT_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지
  
  
INSERT INTO Products(ProductID, CategoryID, SupplierID, PName, Origin, Description, Volume, AlcoholContent, Price)
VALUES(product_seq.nextval, 1 , 1 , '발렌타인 17년' , '영국', '발렌타인은 19세기 George Ballantine씨가 블랜딩 해 팔기 시작한 블랜디드 스카치 위스키 입니다. 발렌다인은 향이 강하지 않게 블랜딩하고 블랜딩후 추가로 숙성시켜 목넘김이 부드럽습니다. 그렇기 때문에 한국인들에게 가장 선호하는 술로 알려져 있고 한국에서 가장 많이 팔리는 외국 브랜디 위스키 입니다11
발렌타인 17년은 황금빛 색깔과 바닐라 느낌의 우아한 향이 나고 오크와 피트의 스모크한 향의 달콤한 맛이 느껴집니다.',700,40,'125000');

INSERT INTO Products(ProductID, CategoryID, SupplierID, PName, Origin, Description, Volume, AlcoholContent, Price)
VALUES(product_seq.nextval, 2 , 2 , '잭다니엘 허니' , '미국', '잭 허니는 잭 다니엘 특유의 클래식한 풍미에 천연 벌꿀에서 느껴지는 부드러운 달콤함, 풍부한 견과류의 향을 더한 것이 특징인 제품이다. 기존 잭 다니엘스(40도)에 비해 낮아진 35도의 저도수 알코올로 기존 잭 다니엘스 마니아 뿐만 아니라 여성들에게까지 큰 사랑을 받고 있다.',700,35,'43000');

INSERT INTO Products(ProductID, CategoryID, SupplierID, PName, Origin, Description, Volume, AlcoholContent, Price)
VALUES(product_seq.nextval, 3 , 1 , '그린자켓 17년' , '캐나다', '그린 자켓 17년은 먼저 캐러멜과 같은 풍부하고 달콤한 향이 진한 과일과 오크 향과 균형을 이룹니다. 깊은 바닐라의 달콤함이 산딸기의 맛과 구운 아몬드의 고소한 맛, 참나무의 은은한 오크향이 함께 완벽한 조화를 이룹니다.',700, 36.5 ,'56000');

INSERT INTO Products(ProductID, CategoryID, SupplierID, PName, Origin, Description, Volume, AlcoholContent, Price)
VALUES(product_seq.nextval, 4 , 2 , '산토리 가쿠빈' , '일본', '가쿠빈은 산토리의 다채로운 원주 가운데서 엄선된 몰트와 그레인 원주를 브렌딩하여 한국 소비자의 입맛에 맞게 만든 제품 입니다.
1937년 가쿠빈(角瓶)탄생 이후 70년이 넘도록 지속된 네모진 병모양과 거북이 등조각 모양이 변치 않은 확실한 품질을 말해주고 있습니다.
SUNTORY 가쿠빈은 키몰트의 야마자키 버본준원주 및 미디엄타입 그레인에서 유래하는 달짝지근한 향기나, 도톰하고 둥글둥글한 깊은 맛이 특징으로 가정의 어떤 요리와도 잘 어울리므로 반주로 더없이 훌륭한 제품입니다.',700, 40 ,'45000');

INSERT INTO Products(ProductID, CategoryID, SupplierID, PName, Origin, Description, Volume, AlcoholContent, Price)
VALUES(product_seq.nextval, 5 , 1 , '카발란 클래식' , '대만', '카발란 클래식은 세심하게 고른 여러 캐스크에서 숙성된 결과물로, 풍부하면서도 부드러운 맛과 향이 특징입니다. 먼저 향을 맡아보면 난초와 열대과일 향이 부드럽게 나면서 꿀, 망고, 배, 바닐라, 코코넛의 향이 잔잔하게 남아있어 깨끗하고 우아한 느낌을 느낄 수 있습니다.
그리고 첫 모금을 마셔보면 따뜻하고 부드러운 텍스쳐가 느껴지고, 망고 주스의 달콤함과 스파이시함이 절묘하게 어우러지면서 마지막에 시트러스함이 입안을 감도는 피니쉬를 선사합니다.',700, 40 ,'208000');

INSERT INTO Products(ProductID, CategoryID, SupplierID, PName, Origin, Description, Volume, AlcoholContent, Price)
VALUES(product_seq.nextval, 5 , 1 , '카발란 클래식' , '대만', '',700, 40 ,'208000');

INSERT INTO Products(ProductID, CategoryID, SupplierID, PName, Origin, Description, Volume, AlcoholContent, Price)
VALUES(product_seq.nextval, 6 , 1 , '샤또 보류어 보르도 슈페리어' , '프랑스', '색 향: 루비레드컬러. 과일향과 바닐라향
잘익은 과일향과 바닐라 향이 오크부케와 멋진 조화를 이루고 타닌맛이 중량감을 주는 풀바디 와인 입니다. 오크통에서 24개월 숙성시킨 제품으로 세련된 고급와인 입니다.',750, 14 ,'37000');

INSERT INTO Products(ProductID, CategoryID, SupplierID, PName, Origin, Description, Volume, AlcoholContent, Price)
VALUES(product_seq.nextval, 7 , 2 , '브리샤 까베르네쇼비뇽' , '칠레', '수상경력:2010,2018 실버메달등 다수수상. 달콤하고 촉촉하며 생동감이 넘쳐 언제 어디서나 즐길 수 있는 미디움 바디 와인 입니다',750, 13 ,'16000');

INSERT INTO Products(ProductID, CategoryID, SupplierID, PName, Origin, Description, Volume, AlcoholContent, Price)
VALUES(product_seq.nextval, 8 , 1 , '아리온 모스카토' , '이태리', '향기로운 과일 향(복숭아·아카시아)이 조화롭고 신선한 향 산뜻하고 부드러우며 약간의 달콤한 맛 그리고 감미가 함께 곁들여져 있다. 특히 여성들에게 인기가 많고 선물하기 좋다. ',750, 5 ,'25000');

SELECT * FROM Products;

/**********************************/
/* Table Name: 주문상세 */
/**********************************/
DROP TABLE OrderDetails;

CREATE TABLE OrderDetails(
		OrderDetail NUMERIC(10) PRIMARY KEY,
		Quantity NUMERIC(10),
		Price NUMERIC(10),
		OrderID  NUMERIC(10),
		ProductID NUMERIC(10),
  FOREIGN KEY (OrderID ) REFERENCES Orders (OrderID ),
  FOREIGN KEY (ProductID) REFERENCES Products (ProductID)
);
DROP SEQUENCE ORDERDE_SEQ;
CREATE SEQUENCE ORDERDE_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지
  
INSERT INTO OrderDetails (OrderDetail, Quantity, Price, OrderID, ProductID)
SELECT orderde_seq.nextval, 2, p.Price, 1, 2
FROM Products p
WHERE p.ProductID = 2;

INSERT INTO OrderDetails (OrderDetail, Quantity, Price, OrderID, ProductID)
SELECT orderde_seq.nextval, 1, p.Price, 1, 3
FROM Products p
WHERE p.ProductID = 3;

UPDATE OrderDetails SET Quantity=1 WHERE OrderDetail=1;

--밑에 문장은 OrderDetails 테이블에서 각 주문 (OrderID)에 대해 Quantity와 Price를 곱한 값의 합계를 계산합니다.  =  각 주문의 총 금액
-- 계산된 총 금액을 Orders 테이블의 TotalAmount 열에 업데이트. WHERE EXISTS 절은 OrderDetails 테이블에 해당 OrderID가 있는 경우에만 TotalAmount를 업데이트하도록 함.
UPDATE Orders
SET TotalAmount = (
    SELECT SUM(Quantity * Price)  
    FROM OrderDetails 
    WHERE OrderDetails.OrderID = Orders.OrderID
)
WHERE EXISTS (
    SELECT 1 
    FROM OrderDetails 
    WHERE OrderDetails.OrderID = Orders.OrderID
);

--여기까지

SELECT * FROM OrderDetails;

/**********************************/
/* Table Name: 장바구니 */
/**********************************/
CREATE TABLE Carts(
		CartID NUMERIC(10) PRIMARY KEY,
        memberno NUMERIC(10),
		CreatedAt DATETIME,
		cnt NUMERIC(10),
		ProductID NUMERIC(10),		
  FOREIGN KEY (ProductID) REFERENCES Products (ProductID),
  FOREIGN KEY (memberno) REFERENCES Member (memberno)
);

CREATE SEQUENCE CARTS_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지

/**********************************/
/* Table Name: 리뷰 */
/**********************************/
CREATE TABLE Reviews(
		ReviewID NUMERIC(10) PRIMARY KEY,
        memberno NUMERIC(10),
		ProductID NUMERIC(10),
		Rating NUMERIC(3),
		Comment VARCHAR(300),
		ReviewDate DATETIME,		
  FOREIGN KEY (ProductID) REFERENCES Products (ProductID),
  FOREIGN KEY (memberno) REFERENCES Member (memberno)
);

CREATE SEQUENCE REVIEWS_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지
/**********************************/
/* Table Name: 재고 */
/**********************************/
CREATE TABLE Inventory(
		InventoryID VARCHAR(10) PRIMARY KEY,
		ProductID NUMERIC(10),
        SupplierID NUMERIC(10),
		Quantity NUMERIC(10),
		LastUpdated DATETIME,
  FOREIGN KEY (ProductID) REFERENCES Products (ProductID),
  FOREIGN KEY (SupplierID) REFERENCES Suppliers (SupplierID)
);
CREATE SEQUENCE INVEN_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지
  
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
CREATE SEQUENCE SHIPPING_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지
/**********************************/
/* Table Name: 챗봇 */
/**********************************/
CREATE TABLE ChatbotSessions(
		SessionID INT PRIMARY KEY,
		Userno NUMERIC(10),
		SessionStart DATETIME,
		SessionEnd DATETIME,
		UserQuery VARCHAR(500),
		BotResponse VARCHAR(500),
  FOREIGN KEY (Userno) REFERENCES Users (Userno)
);
CREATE SEQUENCE CHATBOT_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지
/**********************************/
/* Table Name: 추천시스템 */
/**********************************/
CREATE TABLE Recommendations(
		RID NUMERIC(10) PRIMARY KEY,
		UserID NUMERIC(10),
		ProductID NUMERIC(10),
		CreatedAt DATETIME,
		Userno NUMERIC(10),
  FOREIGN KEY (ProductID) REFERENCES Products (ProductID),
  FOREIGN KEY (Userno) REFERENCES Users (Userno)
);
CREATE SEQUENCE RECOM_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지
/**********************************/
/* Table Name: 이벤트 */
/**********************************/
CREATE TABLE Event(
		eventno NUMERIC(10),
		title VARCHAR(50),
		contents VARCHAR(2000),
		thumbs VARCHAR(1000),
		files VARCHAR(1000),
		sizes VARCHAR(1000),
		rdate DATE,
		Adminno NUMERIC(5),
  FOREIGN KEY (Adminno) REFERENCES Admin (Adminno)
);

CREATE SEQUENCE EVENT_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지
/**********************************/
/* Table Name: 관심상품 */
/**********************************/
CREATE TABLE FavProduct(
		FavID NUMERIC(10),
		ProductID NUMERIC(10),
		memberno NUMERIC(10),
		CreatedAt DATE,
  FOREIGN KEY (ProductID) REFERENCES Products (ProductID),
  FOREIGN KEY (memberno) REFERENCES Member (memberno)
);

CREATE SEQUENCE FAV_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지
/**********************************/
/* Table Name: 결제 */
/**********************************/
CREATE TABLE Payments(
		PaymentID NUMERIC(10) PRIMARY KEY,
		OrderID  NUMERIC(10),
		memberno NUMERIC(10) NOT NULL,
		PaymentMethod INT,
		PaymentAmount INT,
		PaymentStatus VARCHAR(50),
		COLUMN_5 DATE,
		AuthorizationCode VARCHAR(100),
		PaymentDetails VARCHAR(500),
  FOREIGN KEY (OrderID ) REFERENCES Orders (OrderID ),
  FOREIGN KEY (memberno) REFERENCES Member (memberno)
);
CREATE SEQUENCE PAYMENTS_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지
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
CREATE SEQUENCE ADLOG_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지
