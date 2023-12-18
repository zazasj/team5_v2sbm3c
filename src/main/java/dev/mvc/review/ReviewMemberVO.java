package dev.mvc.review;

public class ReviewMemberVO {

  /** 아이디 */
  private String id = "";
  
  /** 댓글 번호 */
  private int reviewno;
  /** 관련 글 번호 */
  private int productid;
  /** 회원 번호 */
  private int memberno;
  /** 제목*/
  private String retitle;

  private float rating;
  /** 내용 */
  private String recontent;
  /** 패스워드 */
  private String passwd;
  /** 등록일 */
  private String reviewdate;
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public int getReviewno() {
    return reviewno;
  }
  public void setReviewno(int reviewno) {
    this.reviewno = reviewno;
  }
  public int getProductid() {
    return productid;
  }
  public void setProductid(int productid) {
    this.productid = productid;
  }
  public int getMemberno() {
    return memberno;
  }
  public void setMemberno(int memberno) {
    this.memberno = memberno;
  }
  public String getRetitle() {
    return retitle;
  }
  public void setRetitle(String retitle) {
    this.retitle = retitle;
  }
  public float getRating() {
    return rating;
  }
  public void setRating(float rating) {
    this.rating = rating;
  }
  public String getRecontent() {
    return recontent;
  }
  public void setRecontent(String recontent) {
    this.recontent = recontent;
  }
  public String getPasswd() {
    return passwd;
  }
  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }
  public String getReviewdate() {
    return reviewdate;
  }
  public void setReviewdate(String reviewdate) {
    this.reviewdate = reviewdate;
  }
  
  
}
