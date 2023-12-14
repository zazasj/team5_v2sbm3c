package dev.mvc.recommend;

import java.util.List;

public interface RecommendDAOInter {
  List<ProductVO> getRecommendProducts(String grpid);

}
