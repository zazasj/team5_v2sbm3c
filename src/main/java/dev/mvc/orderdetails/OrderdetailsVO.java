package dev.mvc.orderdetails;

public class OrderdetailsVO {

  /*
  COMMENT ON TABLE order_details is '주문상세';
  COMMENT ON COLUMN order_details.order_detailsno is '주문상세번호';
  COMMENT ON COLUMN order_details.MEMBERNO is '회원 번호';
  COMMENT ON COLUMN order_details.order_payno is '주문 번호';
  COMMENT ON COLUMN order_details.productid is '제품 번호';
  COMMENT ON COLUMN order_details.cnt is '수량';
  COMMENT ON COLUMN order_details.tot is '합계';
  COMMENT ON COLUMN order_details.stateno is '주문상태';
  COMMENT ON COLUMN order_details.rdate is '주문날짜';
   * 
   * */
    /** 주문상세번호 */
    private int order_detailsno=0;  // 초기값: 0
    
    /** 회원 번호 */
    private int memberno = 0;
    
    /** 주문 번호 */
    private int order_payno = 0;
    
    /** 컨텐츠 번호 */
    private int productid = 0;
    
    /** 제목, contents join */
    private String title = "";
    
    /** 판매가, contents join */
    private int saleprice;
    
    /** 수량 */
    private int cnt = 0;
    
    /** 합계
     *  배송비는 주문 결재시에 통합적으로 결재되고 개별 상품 목록에는 배송비가 제외됨
     *  여기서는 교보문고의 유형을 개발함.
     *  
     *  - 교보문고는 배송비가 전체 주문에 통합됨
     *    . 여러권의 책을 구입해도 배송비는 3,000
     *  - 옥션은 모든 상품에 배공비가 독립적으로 적용됨
     *    . 물건 3개이면 배송비 3,000 x 3 = 9,000
     *   */
    private int tot;
    
    /** 배송 상태(stateno):  1: 결재 완료, 2: 상품 준비중, 3: 배송 시작, 4: 배달중, 5: 오늘 도착, 6: 배달 완료   */
    private int stateno;
    
    /** 주문날짜 */
    private String rdate = ""; // 초기값: null

    public int getOrder_detailsno() {
      return order_detailsno;
    }

    public void setOrder_detailsno(int order_detailsno) {
      this.order_detailsno = order_detailsno;
    }

    public int getMemberno() {
      return memberno;
    }

    public void setMemberno(int memberno) {
      this.memberno = memberno;
    }

    public int getOrder_payno() {
      return order_payno;
    }

    public void setOrder_payno(int order_payno) {
      this.order_payno = order_payno;
    }

    public int getProductid() {
      return productid;
    }

    public void setProductid(int productid) {
      this.productid = productid;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public int getSaleprice() {
      return saleprice;
    }

    public void setSaleprice(int saleprice) {
      this.saleprice = saleprice;
    }

    public int getCnt() {
      return cnt;
    }

    public void setCnt(int cnt) {
      this.cnt = cnt;
    }

    public int getTot() {
      return tot;
    }

    public void setTot(int tot) {
      this.tot = tot;
    }

    public int getStateno() {
      return stateno;
    }

    public void setStateno(int stateno) {
      this.stateno = stateno;
    }

    public String getRdate() {
      return rdate;
    }

    public void setRdate(String rdate) {
      this.rdate = rdate;
    }

}
