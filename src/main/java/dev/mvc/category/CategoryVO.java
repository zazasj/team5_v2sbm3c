package dev.mvc.category;

public class CategoryVO {

  private int categoryID;
  private String name;
  private int cnt;
  private String rdate;
  private int seqno;
  private String visible;
  public int getCategoryID() {
    return categoryID;
  }
  public void setCategoryID(int categoryID) {
    this.categoryID = categoryID;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getCnt() {
    return cnt;
  }
  public void setCnt(int cnt) {
    this.cnt = cnt;
  }
  public String getRdate() {
    return rdate;
  }
  public void setRdate(String rdate) {
    this.rdate = rdate;
  }
  public int getSeqno() {
    return seqno;
  }
  public void setSeqno(int seqno) {
    this.seqno = seqno;
  }
  public String getVisible() {
    return visible;
  }
  public void setVisible(String visible) {
    this.visible = visible;
  }
  
  @Override
  public String toString() {
    return "CategoryVO [categoryID=" + categoryID + ", name=" + name + ", cnt=" + cnt + ", rdate=" + rdate
        + ", seqno=" + seqno + ", visible=" + visible + "]";
  }
}
