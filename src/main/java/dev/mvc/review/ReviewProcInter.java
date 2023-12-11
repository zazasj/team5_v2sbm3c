package dev.mvc.review;

import java.util.ArrayList;


public interface ReviewProcInter {

  public int create(ReviewVO reviewVO);
  
  public ArrayList<ReviewVO> list_all(int productid);
  
  public int delete(int reviewno);
}
