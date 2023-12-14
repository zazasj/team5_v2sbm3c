package dev.mvc.recommend;

public class ProductVO {
  private String pname;
  private String imagefile;
  public String getPname() {
    return pname;
  }
  public void setPname(String pname) {
    this.pname = pname;
  }
  public String getImagefile() {
    return imagefile;
  }
  public void setImagefile(String imagefile) {
    this.imagefile = imagefile;
  }
  @Override
  public String toString() {
    return "productVO [pname=" + pname + ", imagefile=" + imagefile + "]";
  }
  
}
