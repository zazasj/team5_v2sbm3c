package dev.mvc.category;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.category.CategoryProc")
public class CategoryProc implements CategoryProcInter {

  @Autowired
  private CategoryDAOInter categoryDAO;
  
  @Override
  public int create(CategoryVO categoryVO) {
    int cnt = this.categoryDAO.create(categoryVO);
    return cnt;
  }

  @Override
  public ArrayList<CategoryVO> list_all() {
    ArrayList<CategoryVO> list = this.categoryDAO.list_all();
    return list;
  }

  @Override
  public CategoryVO read(int categoryID) {
    CategoryVO categoryVO = this.categoryDAO.read(categoryID);
    return categoryVO;
  }

  @Override
  public int update(CategoryVO categoryVO) {
    int cnt = this.categoryDAO.update(categoryVO);
    return cnt;
  }

  @Override
  public int delete(int categoryID) {
    int cnt = this.categoryDAO.delete(categoryID);
    return cnt;
  }

  @Override
  public int update_seqno_forward(int categoryID) {
    int cnt = this.categoryDAO.update_seqno_forward(categoryID);
    return cnt;
  }

  @Override
  public int update_seqno_backward(int categoryID) {
    int cnt = this.categoryDAO.update_seqno_backward(categoryID);
    return cnt;
  }

  @Override
  public int update_visible_y(int categoryID) {
    int cnt = this.categoryDAO.update_visible_y(categoryID);
    return cnt;
  }

  @Override
  public int update_visible_n(int categoryID) {
    int cnt = this.categoryDAO.update_visible_n(categoryID);
    return cnt;
  }

  @Override
  public ArrayList<CategoryVO> list_all_y() {
    ArrayList<CategoryVO> list = this.categoryDAO.list_all_y();
    return list;
  }

  
}
