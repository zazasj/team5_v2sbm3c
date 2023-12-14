package dev.mvc.recommend;

import java.util.List;

public interface RecommendProcInter {
  List<ProductVO> getRecommendProducts(String grpid);
}
