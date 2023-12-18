package dev.mvc.carts;

public class CartsVO {
  
  /*CREATE TABLE Carts(
      cartID NUMERIC(10) PRIMARY KEY,
          memberno NUMERIC(10),
      cdate DATE,
      cnt NUMERIC(10),
      ProductID NUMERIC(10),    
    FOREIGN KEY (ProductID) REFERENCES Products (ProductID) ON DELETE CASCADE,
    FOREIGN KEY (memberno) REFERENCES Member (memberno) ON DELETE CASCADE
  );*/
  
  /** 쇼핑 카트 번호 */
  private int cartID;
  /** 상품 번호 */
  private int productID;
  /** 상품명 */
  private String pName = "";
  /** 메인 이미지 preview */
  private String thumb = "";
  /** 가격 */
  private int price;
  /** 회원 번호 */
  private int memberno;
  /** 수량 */
  private int cnt;
  /** 금액 = 판매가 x 수량 */
  private int tot;
  /** 등록일 */
  private String cdate;
  public int getCartID() {
    return cartID;
  }
  public void setCartID(int cartID) {
    this.cartID = cartID;
  }
  public int getProductID() {
    return productID;
  }
  public void setProductID(int productID) {
    this.productID = productID;
  }
  public String getpName() {
    return pName;
  }
  public void setpName(String pName) {
    this.pName = pName;
  }
  public String getThumb() {
    return thumb;
  }
  public void setThumb(String thumb) {
    this.thumb = thumb;
  }
  public int getPrice() {
    return price;
  }
  public void setPrice(int price) {
    this.price = price;
  }
  public int getMemberno() {
    return memberno;
  }
  public void setMemberno(int memberno) {
    this.memberno = memberno;
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
  public String getCdate() {
    return cdate;
  }
  public void setCdate(String cdate) {
    this.cdate = cdate;
  }
  @Override
  public String toString() {
    return "CartsVO [cartID=" + cartID + ", productID=" + productID + ", pName=" + pName + ", thumb=" + thumb
        + ", price=" + price + ", memberno=" + memberno + ", cnt=" + cnt + ", tot=" + tot + ", cdate=" + cdate + "]";
  }
  
}
