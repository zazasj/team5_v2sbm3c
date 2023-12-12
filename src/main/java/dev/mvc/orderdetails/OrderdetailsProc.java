package dev.mvc.orderdetails;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.orderdetails.OrderdetailsProc")
public class OrderdetailsProc implements OrderdetailsProcInter {
  @Autowired
  private OrderdetailsDAOInter orderdetailsDAO;
  
  @Override
  public int create(OrderdetailsVO orderdetailsVO) {
    int cnt = this.orderdetailsDAO.create(orderdetailsVO);
    return cnt;
  }
  
  @Override
  public List<OrderdetailsVO> list_by_memberno(HashMap<String, Object> map) {
    List<OrderdetailsVO> list = null;
    list = this.orderdetailsDAO.list_by_memberno(map);
    return list;
  } 
  
}
