package dev.mvc.order_pay;

public class Order_payVO {
/*
 *  order_payno                       NUMBER(10)     NOT NULL    PRIMARY KEY,
    MEMBERNO                          NUMBER(10)     NULL ,
    rname                             VARCHAR2(30)     NOT NULL,
    rtel                              VARCHAR2(14)     NOT NULL,
    rzipcode                          VARCHAR2(5)    NULL ,
    raddress1                         VARCHAR2(80)     NOT NULL,
    raddress2                         VARCHAR2(50)     NOT NULL,
    paytype                           NUMBER(1)    DEFAULT 0     NOT NULL,
    amount                            NUMBER(10)     DEFAULT 0     NOT NULL,
    rdate                              DATE     NOT NULL,
 * */
  
  /** 주문 번호 */
  private int order_payno;
  /** 회원 번호 */
  private int memberno;
  /** 수취인 성명 */
  private String rname = "";
  /** 수취인 전화 번호 */
  private String rtel = "";
  /** 수취인 우편 번호 */
  private String rzipcode = "";
  /** 수취인 주소 1 */
  private String raddress1 = "";
  /** 수취인 주소 2 */
  private String raddress2 = "";
  /** 결재 타입 1: 신용 카드, 2. 모바일 결재, 3. 포인트 결재 4. 계좌 이체, 5: 직접 입금 */
  private int paytype = 1;
  /** 결재 금액 */
  private int amount = 0;
  /** 주문일 */
  private String rdate = "";
  
  public int getOrder_payno() {
    return order_payno;
  }
  public void setOrder_payno(int order_payno) {
    this.order_payno = order_payno;
  }
  public int getMemberno() {
    return memberno;
  }
  public void setMemberno(int memberno) {
    this.memberno = memberno;
  }
  public String getRname() {
    return rname;
  }
  public void setRname(String rname) {
    this.rname = rname;
  }
  public String getRtel() {
    return rtel;
  }
  public void setRtel(String rtel) {
    this.rtel = rtel;
  }
  public String getRzipcode() {
    return rzipcode;
  }
  public void setRzipcode(String rzipcode) {
    this.rzipcode = rzipcode;
  }
  public String getRaddress1() {
    return raddress1;
  }
  public void setRaddress1(String raddress1) {
    this.raddress1 = raddress1;
  }
  public String getRaddress2() {
    return raddress2;
  }
  public void setRaddress2(String raddress2) {
    this.raddress2 = raddress2;
  }
  public int getPaytype() {
    return paytype;
  }
  public void setPaytype(int paytype) {
    this.paytype = paytype;
  }
  public int getAmount() {
    return amount;
  }
  public void setAmount(int amount) {
    this.amount = amount;
  }
  public String getRdate() {
    return rdate;
  }
  public void setRdate(String rdate) {
    this.rdate = rdate;
  }
  
  @Override
  public String toString() {
    return "Order_payVO [order_payno=" + order_payno + ", memberno=" + memberno + ", rname=" + rname + ", rtel=" + rtel
        + ", rzipcode=" + rzipcode + ", raddress1=" + raddress1 + ", raddress2=" + raddress2 + ", paytype=" + paytype
        + ", amount=" + amount + ", rdate=" + rdate + "]";
  }
  
  
}