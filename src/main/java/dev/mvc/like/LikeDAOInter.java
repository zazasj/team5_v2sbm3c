package dev.mvc.like;

import java.util.HashMap;

public interface LikeDAOInter {
  
  public int create(HashMap<String, Object> map);
  
  public int in_recom(int productid);
  
  public int de_recom(int productid);
  
  public int read(int productid);
  
  public int like_check(HashMap<String, Object> map);
  
  public int delete(HashMap<String, Object> map);

}
