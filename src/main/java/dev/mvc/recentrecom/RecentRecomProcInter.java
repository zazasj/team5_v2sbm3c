package dev.mvc.recentrecom;

import java.util.List;

public interface RecentRecomProcInter {
  public int create(RecentRecomVO recentRecomVO);
  public List<RecentRecomVO> list_recent_products();
}
