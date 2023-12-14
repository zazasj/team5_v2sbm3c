package dev.mvc.recommend;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.recommend.RecommendProc")
public class RecommendProc implements RecommendProcInter {
  
  @Autowired
  private RecommendDAOInter recommendDAO;

  @Override
  public List<ProductVO> getRecommendProducts(String grpid) {
      return recommendDAO.getRecommendProducts(grpid);
  }
}
