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