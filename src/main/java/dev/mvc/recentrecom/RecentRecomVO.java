package dev.mvc.recentrecom;

import java.sql.Timestamp;

public class RecentRecomVO {
  private int recentRecomID;
  private int productID;
  private Timestamp addedDate;
  
  private String imagefile;
  private String pname;

  // 생성자, getter, setter, toString 등 필요한 메서드들은 생략

  
  @Override
  public String toString() {
    return "RecentRecomVO [recentRecomID=" + recentRecomID + ", productID=" + productID + ", addedDate=" + addedDate
        + ", imagefile=" + imagefile + ", pname=" + pname + "]";
  }

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

  public Timestamp getAddedDate() {
    return addedDate;
  }

  public void setAddedDate(Timestamp addedDate) {
    this.addedDate = addedDate;
  }

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
}