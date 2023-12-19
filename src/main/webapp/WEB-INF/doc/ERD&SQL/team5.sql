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
VALUES(admin_seq.nextval, 'admin2', '1234', '관리자2', sysdate, 1);

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
VALUES(member_seq.nextval, 'user1@naver.com', '1234','회원1','01099887766',24,'경기도 안양시', sysdate,15);

INSERT INTO Member(memberno, id, passwd,mname,tel, Age, address1,mdate,grade)
VALUES(member_seq.nextval, 'user2@gmail.com', '1234','회원2','01038384848',26,'경기도 수원시', sysdate,15);

ALTER TABLE Member ADD zipcode VARCHAR(5);

commit;

DELETE FROM Member
WHERE memberno = 3;

UPDATE Member
SET grade = 15
WHERE memberno = 1;

SELECT * FROM Member ORDER BY memberno ASC;

SELECT memberno FROM Member WHERE grade = 99;

/**********************************/
/* Table Name: 주문 */
/**********************************/
DROP TABLE Orders CASCADE CONSTRAINTS;
CREATE TABLE Orders (
  OrderID NUMBER(10) PRIMARY KEY,
  OrderDate TIMESTAMP,
  memberno NUMBER(10),
  ProductID NUMBER(10),
  Price NUMBER(10),
  Mname VARCHAR(100), -- Member 테이블의 Mname 정보를 담을 컬럼 추가
  CONSTRAINT fk_orders_member FOREIGN KEY (memberno) REFERENCES Member(memberno),
  CONSTRAINT fk_orders_products FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);

ALTER TABLE Orders
MODIFY OrderDate DATE;

UPDATE Orders
SET OrderDate = TO_CHAR(OrderDate, 'YYYY-MM-DD HH24:MI');

INSERT INTO Orders (OrderID, OrderDate, memberno, ProductID, Price, Mname)
VALUES (ORDERS_SEQ.nextval, sysdate, 1, 20, (SELECT Price FROM Products WHERE ProductID = 20),(SELECT Mname FROM Member WHERE memberno = 1));

INSERT INTO Orders (OrderID, OrderDate, memberno, ProductID, Price, Mname)
VALUES (ORDERS_SEQ.nextval, sysdate, 2, 21, (SELECT Price FROM Products WHERE ProductID = 21),(SELECT Mname FROM Member WHERE memberno = 2));

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

UPDATE CateGroup SET Seqno=90 WHERE GrpID=6;

SELECT * FROM CateGroup;

commit;
/**********************************/
/* Table Name: 카테고리 */
/**********************************/
CREATE TABLE Category(
		categoryID NUMERIC(10) PRIMARY KEY,
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

commit;
/**********************************/
/* Table Name: 공급업체 */
/**********************************/
DROP TABLE suppliers CASCADE CONSTRAINTS;
CREATE TABLE suppliers(
      supplierID NUMERIC(10) PRIMARY KEY,
      adminno    NUMBER(5),
      sName VARCHAR(100) NOT NULL,
      contactInfo VARCHAR(200) NOT NULL,
      saddress VARCHAR(200) NOT NULL,
      rdate DATE,
      FOREIGN KEY (adminno) REFERENCES Admin (adminno)
);



DROP SEQUENCE supply_seq;
CREATE SEQUENCE supply_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지


INSERT INTO suppliers(supplierID, adminno, sName, contactInfo, sAddress, rdate)
VALUES(supply_seq.nextval,1, '[주]주류사업장' , '연락처 : 010-1234-5678 이메일: abcd123@naver.com', '경기도 수원시' , sysdate);

INSERT INTO suppliers(supplierID, adminno, sName, contactInfo, sAddress, rdate)
VALUES(supply_seq.nextval,1, '공식 주류 특판장', '연락처 : 010-1111-2233 이메일: abcd123@naver.com', '서울시 강남구' , sysdate);

SELECT * FROM suppliers;
/**********************************/
/* Table Name: 제품 */
/**********************************/
DROP TABLE Products CASCADE CONSTRAINTS;

CREATE TABLE Products(
		productID NUMERIC(10) PRIMARY KEY,
		categoryID NUMERIC(10) NOT NULL,
		supplierID NUMERIC(10) NOT NULL,
		pName VARCHAR(200) NOT NULL,
        origin VARCHAR(30) NOT NULL, 
		description VARCHAR(1000),
		volume NUMERIC(30) NOT NULL,
		alcoholContent NUMERIC(4) NOT NULL,
		price NUMERIC(30) NOT NULL,
		imageFile VARCHAR(50),
		imageFileSaved VARCHAR(50),
		thumb VARCHAR(50), --이미지preview
		sizes NUMERIC(10), --이미지preview크기
		Pcnt NUMERIC(9) DEFAULT 0 , --조회수
		recom NUMERIC(8) DEFAULT 0, --추천수
        word VARCHAR(100),
  FOREIGN KEY (CategoryID) REFERENCES Category (CategoryID),
  FOREIGN KEY (supplierID) REFERENCES Suppliers (supplierID)
);
commit;

CREATE OR REPLACE TRIGGER trg_insert_recentrecom
AFTER INSERT ON Products
FOR EACH ROW
BEGIN
  INSERT INTO RecentRecom (RecentRecomID, ProductID, AddedDate, PName, ImageFile)
  VALUES (recentrecom_seq.nextval, :new.ProductID, SYSTIMESTAMP, :new.PName, :new.ImageFile);
END;

DROP TRIGGER trg_insert_recentrecom;

DROP  SEQUENCE PRODUCT_SEQ;

CREATE SEQUENCE PRODUCT_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지
  
  
INSERT INTO Products(productID, categoryID, supplierID, pName, origin, description, volume, alcoholContent, price)
VALUES(product_seq.nextval, 1 , 1 , '발렌타인 17년' ,'영국', '발렌타인은 19세기 George Ballantine씨가 블랜딩 해 팔기 시작한 블랜디드 스카치 위스키 입니다. 발렌다인은 향이 강하지 않게 블랜딩하고 블랜딩후 추가로 숙성시켜 목넘김이 부드럽습니다. 그렇기 때문에 한국인들에게 가장 선호하는 술로 알려져 있고 한국에서 가장 많이 팔리는 외국 브랜디 위스키 입니다11
발렌타인 17년은 황금빛 색깔과 바닐라 느낌의 우아한 향이 나고 오크와 피트의 스모크한 향의 달콤한 맛이 느껴집니다.',700,40,'125000');

INSERT INTO Products(productID, categoryID, supplierID, pName, origin, description, volume, alcoholContent, price)
VALUES(product_seq.nextval, 2 , 2 , '잭다니엘 허니' , '미국', '잭 허니는 잭 다니엘 특유의 클래식한 풍미에 천연 벌꿀에서 느껴지는 부드러운 달콤함, 풍부한 견과류의 향을 더한 것이 특징인 제품이다. 기존 잭 다니엘스(40도)에 비해 낮아진 35도의 저도수 알코올로 기존 잭 다니엘스 마니아 뿐만 아니라 여성들에게까지 큰 사랑을 받고 있다.',700,35,'43000');

INSERT INTO Products(productID, categoryID, supplierID, pName, origin, description, volume, alcoholContent, price)
VALUES(product_seq.nextval, 3 , 1 , '그린자켓 17년' , '캐나다', '그린 자켓 17년은 먼저 캐러멜과 같은 풍부하고 달콤한 향이 진한 과일과 오크 향과 균형을 이룹니다. 깊은 바닐라의 달콤함이 산딸기의 맛과 구운 아몬드의 고소한 맛, 참나무의 은은한 오크향이 함께 완벽한 조화를 이룹니다.',700, 36.5 ,'56000');

INSERT INTO Products(productID, categoryID, supplierID, pName, origin, description, volume, alcoholContent, price)
VALUES(product_seq.nextval, 4 , 2 , '산토리 가쿠빈' , '일본', '가쿠빈은 산토리의 다채로운 원주 가운데서 엄선된 몰트와 그레인 원주를 브렌딩하여 한국 소비자의 입맛에 맞게 만든 제품 입니다.
1937년 가쿠빈(角瓶)탄생 이후 70년이 넘도록 지속된 네모진 병모양과 거북이 등조각 모양이 변치 않은 확실한 품질을 말해주고 있습니다.
SUNTORY 가쿠빈은 키몰트의 야마자키 버본준원주 및 미디엄타입 그레인에서 유래하는 달짝지근한 향기나, 도톰하고 둥글둥글한 깊은 맛이 특징으로 가정의 어떤 요리와도 잘 어울리므로 반주로 더없이 훌륭한 제품입니다.',700, 40 ,'45000');

INSERT INTO Products(productID, categoryID, supplierID, pName, origin, description, volume, alcoholContent, price)
VALUES(product_seq.nextval, 5 , 1 , '카발란 클래식' , '대만', '카발란 클래식은 세심하게 고른 여러 캐스크에서 숙성된 결과물로, 풍부하면서도 부드러운 맛과 향이 특징입니다. 먼저 향을 맡아보면 난초와 열대과일 향이 부드럽게 나면서 꿀, 망고, 배, 바닐라, 코코넛의 향이 잔잔하게 남아있어 깨끗하고 우아한 느낌을 느낄 수 있습니다.
그리고 첫 모금을 마셔보면 따뜻하고 부드러운 텍스쳐가 느껴지고, 망고 주스의 달콤함과 스파이시함이 절묘하게 어우러지면서 마지막에 시트러스함이 입안을 감도는 피니쉬를 선사합니다.',700, 40 ,'208000');

INSERT INTO Products(productID, categoryID, supplierID, pName, origin, description, volume, alcoholContent, price)
VALUES(product_seq.nextval, 6 , 1 , '샤또 보류어 보르도 슈페리어' ,'프랑스', '색 향: 루비레드컬러. 과일향과 바닐라향
잘익은 과일향과 바닐라 향이 오크부케와 멋진 조화를 이루고 타닌맛이 중량감을 주는 풀바디 와인 입니다. 오크통에서 24개월 숙성시킨 제품으로 세련된 고급와인 입니다.',750, 14 ,'37000');

INSERT INTO Products(productID, categoryID, supplierID, pName, origin, description, volume, alcoholContent, price)
VALUES(product_seq.nextval, 7 , 2 , '브리샤 까베르네쇼비뇽' , '칠레',  '수상경력:2010,2018 실버메달등 다수수상. 달콤하고 촉촉하며 생동감이 넘쳐 언제 어디서나 즐길 수 있는 미디움 바디 와인 입니다',750, 13 ,'16000');

INSERT INTO Products(productID, categoryID, supplierID, pName, origin, description, volume, alcoholContent, price)
VALUES(product_seq.nextval, 8 , 1 , '아리온 모스카토' , '이태리',  '향기로운 과일 향(복숭아·아카시아)이 조화롭고 신선한 향 산뜻하고 부드러우며 약간의 달콤한 맛 그리고 감미가 함께 곁들여져 있다. 특히 여성들에게 인기가 많고 선물하기 좋다. ',750, 5 ,'25000');

commit;

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
        		ShippingID NUMERIC(10),
  FOREIGN KEY (OrderID ) REFERENCES Orders (OrderID ),
  FOREIGN KEY (ProductID) REFERENCES Products (ProductID),
  FOREIGN KEY (ShippingID) REFERENCES Shipping (ShippingID)
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
		cdate DATE,
		cnt NUMERIC(10),
		productid NUMERIC(10),		
  FOREIGN KEY (productid) REFERENCES Products (productid) ON DELETE CASCADE,
  FOREIGN KEY (memberno) REFERENCES Member (memberno) ON DELETE CASCADE
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

DROP TABLE Reviews;
CREATE TABLE Reviews(
		reviewno NUMERIC(10) PRIMARY KEY,
        memberno NUMERIC(10),
		productid NUMERIC(10),
        reuser  VARCHAR(100),
        retitle VARCHAR(100),
		rating NUMERIC(3),
		recontent VARCHAR(1000),
		reviewdate DATE,		
  FOREIGN KEY (productid) REFERENCES Products (productid)ON DELETE CASCADE,
  FOREIGN KEY (memberno) REFERENCES Member (memberno) ON DELETE CASCADE
);
DROP SEQUENCE REVIEWS_SEQ;
CREATE SEQUENCE REVIEWS_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지
  
  INSERT INTO Reviews(reviewno, memberno, reuser, productid, retitle, rating, recontent , reviewdate)
  VALUES(reviews_seq.nextval, 1,'회원1', 1,'위스키 최고',9.5,'위스키가 싸고 맛있네요', sysdate);
  
  INSERT INTO Reviews(reviewno, memberno, reuser, productid, retitle, rating, recontent , reviewdate)
  VALUES(reviews_seq.nextval, 2,'회원2', 1,'JMT',10.0,'너무 좋아요.' , sysdate);
  
  SELECT reviewno, memberno, productid, reuser, retitle, rating, recontent, reviewdate FROM Reviews
  WHERE productid = 1;
  commit;
/**********************************/
/* Table Name: 재고 */
/**********************************/
DROP TABLE Inventory;
CREATE TABLE Inventory(
      inventoryID NUMERIC(10) PRIMARY KEY, --재고상태
        inventoryStatus VARCHAR(30),
      productID NUMERIC(10),
        pName VARCHAR(20),
        word VARCHAR(100),
        supplierID NUMERIC(10),
      quantity NUMERIC(10),
        addQuantity NUMERIC(10),
      lastUpdated DATE
        
  --FOREIGN KEY (productID) REFERENCES Products (productID),
  --FOREIGN KEY (pName) REFERENCES Products (pNAme),
  --FOREIGN KEY (supplierID) REFERENCES Suppliers (supplierID)
);

DROP SEQUENCE INVEN_SEQ;
CREATE SEQUENCE INVEN_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지
/**********************************/
/* Table Name: 배송 */
/**********************************/
DROP TABLE Shipping;
CREATE TABLE Shipping(
      shippingID NUMERIC(20) PRIMARY KEY,
      order_payno  NUMERIC(10),
      shippingType VARCHAR(50),
      deliveryPrice NUMERIC(10),
      estimatedDeliveryDate DATE,
      trackingNumber VARCHAR(200),
      deliveryStatus VARCHAR(20)
  --FOREIGN KEY (OrderID ) REFERENCES Orders (OrderID )
);


DROP SEQUENCE SHIPPING_SEQ;
CREATE SEQUENCE SHIPPING_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지
/**********************************/
/* Table Name: 챗봇 */
/**********************************/
DROP TABLE chatting;

CREATE TABLE chatting(
    chattingno  NUMBER(8)       NOT NULL PRIMARY KEY,
    memberno    NUMBER(10)      NOT NULL,
    msg         VARCHAR(1000)    NOT NULL,
    rdate      DATE            NOT NULL,
    FOREIGN KEY (memberno) REFERENCES member(memberno)
);

COMMENT ON TABLE CHATTING is '채팅';
COMMENT ON COLUMN CHATTING.CHATTINGNO is '채팅 번호';
COMMENT ON COLUMN CHATTING.MEMBERNO is '회원 번호';
COMMENT ON COLUMN CHATTING.MSG is '채팅 메시지';
COMMENT ON COLUMN CHATTING.RDATE is '등록일';

DROP SEQUENCE chatting_seq;

CREATE SEQUENCE chatting_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 99999999         -- 최대값: 99999999 --> NUMBER(8) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지
  
INSERT INTO chatting(chattingno, memberno, msg, rdate)
VALUES(chatting_seq.nextval, 2, '안녕하세요', sysdate);

INSERT INTO chatting(chattingno, memberno, msg, rdate)
VALUES(chatting_seq.nextval, 1, '네 안녕하세요, 저는 챗봇입니다.', sysdate);

COMMIT;

SELECT chattingno, memberno, msg, rdate FROM chatting ORDER BY chattingno DESC;
                                                                                                                                                                                                                                                                                               2023-11-23 04:03:52

-- 조회
SELECT chattingno, memberno, msg, rdate
  FROM chatting
  WHERE chattingno=1;

- 2023-11-23일자 채팅만 출력
SELECT chattingno, memberno, msg, rdate
FROM chatting
WHERE memberno=1 and SUBSTR(rdate, 1, 10) = '2023-11-23';

-- 시분초 일치하지 않음, 조회안됨
SELECT chattingno, memberno, msg, rdate
FROM chatting
WHERE memberno=1 and rdate = TO_DATE('2023-11-23', 'YYYY-MM-DD');

-- 문열로 변경하는 가능함
SELECT chattingno, memberno, msg, rdate
FROM chatting
WHERE memberno=1 and TO_CHAR(rdate, 'YYYY-MM-DD') = '2023-11-23';

UPDATE chatting
SET msg='반가워요~'
WHERE chattingno = 2;

commit;

DELETE FROM chatting;

/**********************************/
/* Table Name: 이벤트 */
/**********************************/
DROP TABLE Event;
CREATE TABLE Event(
		eventno NUMERIC(10) PRIMARY KEY,
		title VARCHAR(200),
		contents VARCHAR(2000),
		file1  VARCHAR2(100),
		file1saved  VARCHAR2(100),
		thumb1  VARCHAR2(100),
		size1 VARCHAR(1000),
		rdate DATE,
		Adminno NUMERIC(5),
  FOREIGN KEY (Adminno) REFERENCES Admin (Adminno) ON DELETE CASCADE
);
DROP SEQUENCE EVENT_SEQ;
CREATE SEQUENCE EVENT_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지
  
  
INSERT INTO Event(eventno, title, contents, rdate, Adminno)
VALUES(event_seq.nextval, '연말 위스키 10% 할인 이벤트' , '10만원 이상 위스키 전 품목 10% 할인! 10만원 이하 위스키 전품목 5% 할인! ', sysdate ,1);

-- 유형 1 전체 목록
SELECT * FROM Event
ORDER BY eventno ASC;

DELETE FROM Event WHERE eventno >= 2;


commit;
SELECT eventno, thumb1, file1
FROM Event
ORDER BY eventno ASC;

-- 유형 2 카테고리별 목록

        
INSERT INTO Event(eventno, title, contents , file1, file1saved, thumb1, size1, rdate, Adminno)
VALUES(event_seq.nextval, '연말 위스키 10% 할인 이벤트' , '10만원 이상 위스키 전 품목 10% 할인! 10만원 이하 위스키 전품목 5% 할인! '
,'space.jpg', 'space_1.jpg', 'space_t.jpg', 1000, sysdate ,1);
  
COMMIT;

UPDATE Event SET thumb1='' WHERE eventno=5;

-- 추가한 부분 세진 1213-- 
ALTER TABLE Event ADD word VARCHAR(200);

/**********************************/
/* Table Name: 관심상품 */
/**********************************/
CREATE TABLE FavProduct(
      FavID NUMERIC(10),
      ProductID NUMERIC(10),
      memberno NUMERIC(10),
      CreatedAt DATE,
  FOREIGN KEY (ProductID) REFERENCES Products (ProductID) ON DELETE CASCADE,
  FOREIGN KEY (memberno) REFERENCES Member (memberno) ON DELETE CASCADE
);

COMMENT ON TABLE FavProduct is '관심상품';
COMMENT ON COLUMN FavProduct.FavID is '관심상품ID';
COMMENT ON COLUMN FavProduct.ProductID is '제품ID';
COMMENT ON COLUMN FavProduct.memberno is '회원 번호';
COMMENT ON COLUMN FavProduct.CreateAt is '등록날짜';

CREATE SEQUENCE FAV_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지
  
-- INSERT
SELECT ProductID, PName, price FROM products;  -- 9번 사용 확인
PRODUCTID PNAME                                                                                                                                                                                                         PRICE
---------- -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ----------
         1 발렌타인 17년                                                                                                                                                                                                125000
         2 잭다니엘 허니                                                                                                                                                                                                 43000
         3 그린자켓 17년                                                                                                                                                                                                 56000
         4 산토리 가쿠빈                                                                                                                                                                                                 45000
         5 카발란 클래식                                                                                                                                                                                                208000
         7 샤또 보류어 보르도 슈페리어                                                                                                                                                                                   37000
         8 브리샤 까베르네쇼비뇽                                                                                                                                                                                         16000
         9 아리온 모스카토                      
      
-- INSERT   
SELECT memberno, mname FROM member;
MEMBERNO MNAME                                                                                               
---------- ----------------------------------------------------------------------------------------------------
         1 회원1                                                                                               
         2 회원2      
         
         
INSERT INTO FavProduct(FavID, ProductID, memberno, CreatedAt)
VALUES(Fav_seq.nextval, 1, 1, sysdate);

INSERT INTO FavProduct(FavID, ProductID, memberno, CreatedAt)
VALUES(Fav_seq.nextval, 2, 1, sysdate);
commit;

SELECT FavID, ProductID, memberno, CreatedAt FROM FavProduct ORDER BY FavID ASC;
FAVID  PRODUCTID   MEMBERNO CREATEDAT          
---------- ---------- ---------- -------------------
         1          1          1 2023-12-15 03:28:14
         2          2          1 2023-12-15 03:28:14
         
-- LIST contents join
SELECT f.FavID, p.ProductID, p.PName, p.Thumb, p.Price, f.memberno, f.CreatedAt
FROM Products p, FavProduct f
WHERE p.ProductID = f.ProductID
ORDER BY FavID ASC;

-- READ
SELECT f.FavID, p.ProductID, p.PName, p.Price, f.memberno, f.CreatedAt
FROM Products p, FavProduct f
WHERE (p.ProductID = f.ProductID) AND f.FavID=1;


-- UPDATE


-- DELETE
DELETE FROM FavProduct WHERE FavID=2;
commit;
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
DROP TABLE AdminLog;
CREATE TABLE AdminLog(
		adminlogno NUMERIC(10) PRIMARY KEY,
		tablename VARCHAR(50),
		recordid NUMERIC(10) DEFAULT 0,
		acttype VARCHAR(10),
		actdate DATE,
		Adminno NUMERIC(5),
  FOREIGN KEY (Adminno) REFERENCES Admin (ADMINNO) ON DELETE CASCADE
);
DROP SEQUENCE ADLOG_SEQ;

ALTER TABLE ADMINLOG MODIFY (ACTTYPE VARCHAR(30));
 

CREATE SEQUENCE ADLOG_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지

SELECT * FROM AdminLog;

/**********************************/
/* Table Name: 로그인 내역 */
/**********************************/
DROP TABLE Login;

CREATE TABLE Login(
		loginno NUMERIC(10) NOT NULL PRIMARY KEY,
		memberno NUMERIC(10),
		ip VARCHAR(30) NOT NULL,
		logindate DATE NOT NULL,
  FOREIGN KEY (memberno) REFERENCES Member (memberno) ON DELETE CASCADE
);

DROP SEQUENCE LOGIN_SEQ;
CREATE SEQUENCE LOGIN_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지

SELECT * FROM Login;
commit;

/**********************************/
/* Table Name: 회원 탈퇴 내역 */
/**********************************/
DROP TABLE MemberWithdraw;

CREATE TABLE MemberWithdraw(
		withno NUMERIC(10) NOT NULL PRIMARY KEY,
		memberno NUMERIC(10) NOT NULL,
		id VARCHAR(50) NOT NULL,
        grade NUMERIC(2) NOT NULL,
		withdate DATE NOT NULL
);
DROP SEQUENCE MEMBERWITHDRAW_SEQ;
CREATE SEQUENCE MEMBERWITHDRAW_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지

SELECT * FROM MemberWithdraw;
commit;


/**********************************/
/* Table Name: 메일 로그 */
/**********************************/
DROP TABLE MailLog;

CREATE TABLE MailLog(
		mailno NUMERIC(10) NOT NULL PRIMARY KEY,
		memberno NUMERIC(10) NOT NULL,
        id VARCHAR(50) NOT NULL,
		actname VARCHAR(30) NOT NULL,
		maildate DATE NOT NULL,
    FOREIGN KEY (memberno) REFERENCES Member (memberno)ON DELETE CASCADE
);

ALTER TABLE MailLog
RENAME COLUMN logindate TO maildate;
DROP SEQUENCE MAILLOG_SEQ;
CREATE SEQUENCE MAILLOG_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지

SELECT * FROM MailLog;
commit;


/**********************************/
/* Table Name: 좋아요 */
/**********************************/
DROP TABLE Like;

CREATE TABLE Plike(
		plikeno NUMERIC(10) NOT NULL PRIMARY KEY,
		memberno NUMERIC(10),
		productid NUMERIC(10) NOT NULL,
		ldate DATE NOT NULL,
        FOREIGN KEY (memberno) REFERENCES Member (memberno) ON DELETE CASCADE,
        FOREIGN KEY (productid) REFERENCES Products (productid) ON DELETE CASCADE
);

DROP SEQUENCE PLIKE_SEQ;
CREATE SEQUENCE PLIKE_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지

SELECT * FROM Plike;

DELETE FROM Plike
WHERE plikeno >= 0;

/**********************************/
/* Table Name: 추천테이블 */
/**********************************/


DROP TABLE RecentRecom;
CREATE TABLE RecentRecom (
  RecentRecomID NUMBER(10) PRIMARY KEY,
  ProductID NUMBER(10),
  AddedDate TIMESTAMP,
  FOREIGN KEY (ProductID) REFERENCES Products (ProductID)
);

ALTER TABLE RecentRecom
ADD (PName VARCHAR(200), ImageFile VARCHAR(50));

CREATE SEQUENCE RecentRecom_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999            -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지

SELECT RecentRecomID, ProductID, AddedDate, PName, ImageFile
FROM (
  SELECT RecentRecomID, ProductID, AddedDate, PName, ImageFile, ROWNUM AS rnum
  FROM (
    SELECT RecentRecomID, ProductID, AddedDate, PName, ImageFile
    FROM RecentRecom
    ORDER BY AddedDate DESC
  )
)
WHERE rnum <= 5;
  
DROP TABLE order_details CASCADE CONSTRAINTS;

CREATE TABLE order_details(
        order_detailsno                     NUMBER(10)         NOT NULL         PRIMARY KEY,
        memberno                        NUMBER(10)         NULL ,
        order_payno                     NUMBER(10)         NOT NULL,
        productid                        NUMBER(10)         NULL ,
        cnt                                   NUMBER(5)         DEFAULT 1         NOT NULL,
        tot                                   NUMBER(10)         DEFAULT 0         NOT NULL,
        stateno                               NUMBER(1)         DEFAULT 0         NOT NULL,
        rdate                                 DATE         NOT NULL,
  FOREIGN KEY (order_payno) REFERENCES order_pay (order_payno),
  FOREIGN KEY (memberno) REFERENCES MEMBER (memberno),
  FOREIGN KEY (productid) REFERENCES products (productid)
);

COMMENT ON TABLE order_details is '주문상세';
COMMENT ON COLUMN order_details.order_detailsno is '주문상세번호';
COMMENT ON COLUMN order_details.MEMBERNO is '회원 번호';
COMMENT ON COLUMN order_details.order_payno is '주문 번호';
COMMENT ON COLUMN order_details.productid is '제품 번호';
COMMENT ON COLUMN order_details.cnt is '수량';
COMMENT ON COLUMN order_details.tot is '합계';
COMMENT ON COLUMN order_details.stateno is '주문상태';
COMMENT ON COLUMN order_details.rdate is '주문날짜';

DROP SEQUENCE order_details_seq;
CREATE SEQUENCE order_details_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999999
  CACHE 2                     -- 2번은 메모리에서만 계산
  NOCYCLE;                   -- 다시 1부터 생성되는 것을 방지

-- 등록  
-- 배송 상태(stateno):  1: 결재 완료, 2: 상품 준비중, 3: 배송 시작, 4: 배달중, 5: 오늘 도착, 6: 배달 완료
-- FK(사전에 레코드가 등록되어 있어야함): memberno, order_payno, contentsno
-- 예) 3번 회원이 4번 결재를 했으며 구입 상품은 1번인 경우: 3, 4, 1
INSERT INTO order_details(order_detailsno, memberno, order_payno, productid, cnt, tot, stateno, rdate)
VALUES (order_details_seq.nextval, 1, 4, 1, 1, 10000, 1, sysdate);

commit; 


-- 전체 목록
SELECT order_itemno, memberno, order_payno, contentsno, cnt, tot, stateno, rdate
FROM order_item
ORDER BY order_itemno DESC;

--회원별 목록
SELECT order_itemno, memberno, order_payno, contentsno, cnt, tot, stateno, rdate
FROM order_item
WHERE memberno=1
ORDER BY order_itemno DESC;


-- 수정: 개발 안함.


-- 삭제
DELETE FROM order_item
WHERE memberno=1;

commit;

SELECT i.order_detailsno, i.memberno, i.order_payno, i.productid, i.cnt, i.tot, i.stateno, i.rdate,
           c.title, c.saleprice
FROM order_details i, products c 
WHERE (i.productid = c.productid) AND order_payno=2 AND memberno=1
ORDER BY order_detailsno DESC;

/**********************************/
/* Table Name: 주문_결재 */
/**********************************/
DROP TABLE order_pay CASCADE CONSTRAINTS;

CREATE TABLE order_pay(
    order_payno                     NUMBER(10)     NOT NULL    PRIMARY KEY,
    memberno                       NUMBER(10)     NULL ,
    rname                             VARCHAR2(30)     NOT NULL,
    rtel                                 VARCHAR2(14)     NOT NULL,
    rzipcode                          VARCHAR2(5)    NULL ,
    raddress1                         VARCHAR2(80)     NOT NULL,
    raddress2                         VARCHAR2(50)     NOT NULL,
    paytype                           NUMBER(1)    DEFAULT 0     NOT NULL,
    amount                            NUMBER(10)     DEFAULT 0     NOT NULL,
    rdate                              DATE     NOT NULL,
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO)
);

COMMENT ON TABLE order_pay is '주문_결재';
COMMENT ON COLUMN order_pay.order_payno is '주문 번호';
COMMENT ON COLUMN order_pay.MEMBERNO is '회원 번호';
COMMENT ON COLUMN order_pay.rname is '수취인성명';
COMMENT ON COLUMN order_pay.rtel is '수취인 전화번호';
COMMENT ON COLUMN order_pay.rzipcode is '수취인 우편번호';
COMMENT ON COLUMN order_pay.raddress1 is '수취인 주소1';
COMMENT ON COLUMN order_pay.raddress2 is '수취인 주소2';
COMMENT ON COLUMN order_pay.paytype is '결재 종류';
COMMENT ON COLUMN order_pay.amount is '결재금액';
COMMENT ON COLUMN order_pay.rdate is '주문날짜';


DROP SEQUENCE order_pay_seq;
CREATE SEQUENCE order_pay_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999999
  CACHE 2                     -- 2번은 메모리에서만 계산
  NOCYCLE;                   -- 다시 1부터 생성되는 것을 방지

-- 등록  
-- 결재 종류(paytype):  1: 신용 카드, 2: 모바일, 3: 포인트, 4: 계좌 이체, 5: 직접 입금  
INSERT INTO order_pay(order_payno, memberno, rname, rtel, rzipcode,
                                 raddress1, raddress2, paytype, amount, rdate)
VALUES (order_pay_seq.nextval, 1, '홍길순', '111-2222-3333', '12345',
             '서울시 종로구', '관철동', 1, 32000, sysdate);
INSERT INTO order_pay(order_payno, memberno, rname, rtel, rzipcode,
                                 raddress1, raddress2, paytype, amount, rdate)
VALUES (order_pay_seq.nextval, 1, '아로미', '111-2222-3333', '12345',
             '서울시 종로구', '관철동', 1, 15000, sysdate);
INSERT INTO order_pay(order_payno, memberno, rname, rtel, rzipcode,
                                 raddress1, raddress2, paytype, amount, rdate)
VALUES (order_pay_seq.nextval, 1, '왕눈이', '111-2222-3333', '12345',
             '서울시 종로구', '관철동', 1, 63000, sysdate);
commit; 


-- 전체 목록
SELECT order_payno, memberno, rname, rtel, rzipcode, raddress1, raddress2, paytype, amount, rdate
FROM order_pay
ORDER BY order_payno DESC;

--회원별 목록
SELECT order_payno, memberno, rname, rtel, rzipcode, raddress1, raddress2, paytype, amount, rdate
FROM order_pay
WHERE memberno=1
ORDER BY order_payno DESC;


-- 수정: 개발 안함.


-- 삭제
DELETE FROM order_pay
WHERE order_payno=1;

commit;

