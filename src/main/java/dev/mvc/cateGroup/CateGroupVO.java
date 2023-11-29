package dev.mvc.cateGroup;

public class CateGroupVO {

  private int GrpID;
  private String gname;
  private String rdate;
  private int Seqno;
  private String Visible;
  
  
  public int getGrpID() {
    return GrpID;
  }
  public void setGrpID(int GrpID) {
    this.GrpID = GrpID;
  }
  public String getGname() {
    return gname;
  }
  public void setGname(String gname) {
    this.gname = gname;
  }
  public String getRdate() {
    return rdate;
  }
  public void setRdate(String rdate) {
    this.rdate = rdate;
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
    return "CateGroupVO [GrpID=" + GrpID + ", gname=" + gname + ", rdate=" + rdate + ", Seqno=" + Seqno + ", Visible="
        + Visible + "]";
  }
  
  
}
