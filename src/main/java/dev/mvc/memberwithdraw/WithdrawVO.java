package dev.mvc.memberwithdraw;

public class WithdrawVO {
  //#{memberno}, #{id}, #{grade},
  private int memberno; 
  private String id;
  private int grade;
  
  public int getMemberno() {
    return memberno;
  }
  public void setMemberno(int memberno) {
    this.memberno = memberno;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public int getGrade() {
    return grade;
  }
  public void setGrade(int grade) {
    this.grade = grade;
  }
  

}
