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
  public void setGrpID(int grpID) {
    GrpID = grpID;
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
  public void setSeqno(int seqno) {
    Seqno = seqno;
  }
  public String getVisible() {
    return Visible;
  }
  public void setVisible(String visible) {
    Visible = visible;
  }
  @Override
  public String toString() {
    return "CateGroupVO [GrpID=" + GrpID + ", gname=" + gname + ", rdate=" + rdate + ", Seqno=" + Seqno + ", Visible="
        + Visible + "]";
  }
  
  
}
