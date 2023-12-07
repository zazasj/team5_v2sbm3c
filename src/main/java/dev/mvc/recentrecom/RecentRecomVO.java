package dev.mvc.recentrecom;

public class RecentRecomVO {
  private int recentRecomID;
  private int productID;
  private java.sql.Timestamp addedDate;
  
  public int getRecentRecomID() {
    return recentRecomID;
  }
  public void setRecentRecomID(int recentRecomID) {
    this.recentRecomID = recentRecomID;
  }
  public int getProductID() {
    return productID;
  }
  public void setProductID(int productID) {
    this.productID = productID;
  }
  public java.sql.Timestamp getAddedDate() {
    return addedDate;
  }
  public void setAddedDate(java.sql.Timestamp addedDate) {
    this.addedDate = addedDate;
  }
  
  private String imagefile;  // ProductsVO 클래스의 필드와 일치하도록 추가
  private String pname;      // ProductsVO 클래스의 필드와 일치하도록 추가

  // getter, setter 메서드 등을 작성

  public String getImagefile() {
    return imagefile;
  }

  public void setImagefile(String imagefile) {
    this.imagefile = imagefile;
  }

  public String getPname() {
    return pname;
  }

  public void setPname(String pname) {
    this.pname = pname;
  }
  @Override
  public String toString() {
    return "RecentRecomVO [recentRecomID=" + recentRecomID + ", productID=" + productID + ", addedDate=" + addedDate
        + ", imagefile=" + imagefile + ", pname=" + pname + "]";
  }
  
  
}
