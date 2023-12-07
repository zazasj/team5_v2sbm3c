package dev.mvc.recentrecom;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.recentrecom.RecentRecomProc")
public class RecentRecomProc implements RecentRecomProcInter {
  @Autowired
  private RecentRecomDAOInter recentRecomDAO;

  @Override
  public int create(RecentRecomVO recentRecomVO) {
    return recentRecomDAO.create(recentRecomVO);
  }

  @Override
  public List<RecentRecomVO> list_recent_products() {
    return recentRecomDAO.list_recent_products();
  }
}