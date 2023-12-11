package dev.mvc.review;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.review.ReviewProc")
public class ReviewProc implements ReviewProcInter{

  @Autowired
  private ReviewDAOInter reviewDAO;
  
  
  @Override
  public int create(ReviewVO reviewVO) {
    int cnt = this.reviewDAO.create(reviewVO);
    return cnt;
  }

  @Override
  public ArrayList<ReviewVO> list_all(int productid) {
    ArrayList<ReviewVO> reviewVO= this.reviewDAO.list_all(productid);
    return reviewVO;
  }

  @Override
  public int delete(int reviewid) {
    int cnt = this.reviewDAO.delete(reviewid);
    return cnt;
  }

}
