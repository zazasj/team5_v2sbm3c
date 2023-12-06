package dev.mvc.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.orders.OrdersProc")
public class OrdersProc {
  
  @Autowired
  private OrdersDAOInter ordersDAO;
  
  public int create(OrdersVO ordersVO) {
    int cnt = this.ordersDAO.create(ordersVO);
    return cnt;
  }
  
}
