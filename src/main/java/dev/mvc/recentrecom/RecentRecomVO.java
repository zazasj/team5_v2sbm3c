package dev.mvc.recentrecom;

import java.sql.Timestamp;

public class RecentRecomVO {
  private int recentRecomID;
  private int productID;
  private Timestamp addedDate;
  
  private String imagefile;
  private String pname;

  // 생성자, getter, setter, toString 등 필요한 메서드들은 생략

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