package dev.mvc.category;

/*
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
 */

public class CategoryVO {

  private int CategoryID;
  private int GrpID;
  private String CategoryName;
  private int cnt;
  private String Rdate;
  private int Seqno;
  private String Visible;
  
  public int getCategoryID() {
    return CategoryID;
  }
  public void setCategoryID(int CategoryID) {
    this.CategoryID = CategoryID;
  }
  public int getGrpID() {
    return GrpID;
  }
  public void setGrpID(int GrpID) {
    this.GrpID = GrpID;
  }
  public String getCategoryName() {
    return CategoryName;
  }
  public void setCategoryName(String CategoryName) {
    this.CategoryName = CategoryName;
  }
  public int getCnt() {
    return cnt;
  }
  public void setCnt(int cnt) {
    this.cnt = cnt;
  }
  public String getRdate() {
    return Rdate;
  }
  public void setRdate(String Rdate) {
    this.Rdate = Rdate;
  }
  public int getSeqno() {
    return Seqno;
  }
  public void setSeqno(int Seqno) {
    this.Seqno = Seqno;
  }
  public String getVisible() {
    return Visible;
  }
  public void setVisible(String Visible) {
    this.Visible = Visible;
  }
  
  
  @Override
  public String toString() {
    return "CategoryVO [CategoryID=" + CategoryID + ", GrpID=" + GrpID + ", CategoryName=" + CategoryName + ", cnt="
        + cnt + ", Rdate=" + Rdate + ", Seqno=" + Seqno + ", Visible=" + Visible + "]";
  }
  
  
}
