package dev.mvc.cateGroup;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.cateGroup.CateGroupProc")
public class CateGroupProc implements CateGroupProcInter {

  @Autowired
  private CateGroupDAOInter cateGroupDAO;
  
  @Override
  public int create(CateGroupVO cateGroupVO) {
    int cnt = this.cateGroupDAO.create(cateGroupVO);
    return cnt;
  }

  @Override
  public ArrayList<CateGroupVO> list_all() {
    ArrayList<CateGroupVO> list = this.cateGroupDAO.list_all();
    return list;
  }

  @Override
  public CateGroupVO read(int GrpID) {
    CateGroupVO cateGroupVO = this.cateGroupDAO.read(GrpID);
    return cateGroupVO;
  }

  @Override
  public int update(CateGroupVO cateGroupVO) {
    int cnt = this.cateGroupDAO.update(cateGroupVO);
    return cnt;
  }

  @Override
  public int delete(int GrpID) {
    int cnt = this.cateGroupDAO.delete(GrpID);
    return cnt;
  }

  @Override
  public int update_seqno_forward(int GrpID) {
    int cnt = this.cateGroupDAO.update_seqno_forward(GrpID);
    return cnt;
  }

  @Override
  public int update_seqno_backward(int GrpID) {
    int cnt = this.cateGroupDAO.update_seqno_backward(GrpID);
    return cnt;
  }

  @Override
  public int update_visible_y(int GrpID) {
    int cnt = this.cateGroupDAO.update_visible_y(GrpID);
    return cnt;
  }

  @Override
  public int update_visible_n(int GrpID) {
    int cnt = this.cateGroupDAO.update_visible_n(GrpID);
    return cnt;
  }

  @Override
  public ArrayList<CateGroupVO> list_all_y() {
    ArrayList<CateGroupVO> list = this.cateGroupDAO.list_all_y();
    return list;
  }

  
}
