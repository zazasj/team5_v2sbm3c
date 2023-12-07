package dev.mvc.recentrecom;

import java.util.List;

public interface RecentRecomDAOInter {
  public int create(RecentRecomVO recentRecomVO);
  public List<RecentRecomVO> list_recent_products();

}
