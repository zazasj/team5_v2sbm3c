package dev.mvc.review;

import java.util.List;
import java.util.Map;


public interface ReviewProcInter {

  public int create(ReviewVO reviewVO);
  
  public List<ReviewVO> list();
  
  public List<ReviewVO> list_by_productid(int productid);
  
  public List<ReviewVO> list_memberno(int memberno);  
  
  public List<ReviewMemberVO> list_member_join(int memberno);
  
  public List<ReviewMemberVO> list_by_productid_join(int productid);
  
  public List<ReviewMemberVO> list_by_productid_join_add(int productid);
  
  public int checkPasswd(Map<String, Object> map);
  
  public int delete(int reviewno);
}
