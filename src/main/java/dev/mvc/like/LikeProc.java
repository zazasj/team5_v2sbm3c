package dev.mvc.like;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.like.LikeProc")
public class LikeProc implements LikeProcInter{

  @Autowired
  private LikeDAOInter likeDAO;

  @Override
  public int create(HashMap<String, Object> map) {
    int cnt = this. likeDAO.create(map);
    return cnt;
  }

  @Override
  public int in_recom(int productid) {
    int cnt = this. likeDAO.in_recom(productid);
    return cnt;

  }

  @Override
  public int de_recom(int productid) {
    int cnt = this. likeDAO.de_recom(productid);
    return cnt;
  }

  @Override
  public int like_check(HashMap<String, Object> map) {
    int cnt = this. likeDAO.like_check(map);
    return cnt;
  }

  @Override
  public int delete(HashMap<String, Object> map) {
    int cnt = this. likeDAO.delete(map);
    return cnt;
  }
  
  @Override
  public int read(int productid) {
    int cnt = this. likeDAO.read(productid);
    return cnt;
  }

}
