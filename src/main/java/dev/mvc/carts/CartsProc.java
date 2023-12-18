package dev.mvc.carts;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.carts.CartsProc")
public class CartsProc implements CartsProcInter {
  @Autowired
  private CartsDAOInter cartsDAO;
  
  @Override
  public int create(CartsVO cartsVO) {
    int cnt = this.cartsDAO.create(cartsVO);
    return cnt;
  }

  @Override
  public ArrayList<CartsVO> list_by_memberno(int memberno) {
    ArrayList<CartsVO> list = this.cartsDAO.list_by_memberno(memberno);
    return list;
  }

  @Override
  public int delete(int cartID) {
    int cnt = this.cartsDAO.delete(cartID);
    return cnt;
  }

  @Override
  public int update_cnt(CartsVO cartsVO) {
    int cnt = this.cartsDAO.update_cnt(cartsVO);
    return 0;
  }

}
