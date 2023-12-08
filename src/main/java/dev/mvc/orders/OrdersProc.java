package dev.mvc.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.orders.OrdersProc")
public class OrdersProc implements OrdersProcInter {
  @Autowired
  private OrdersDAOInter ordersDAO;
  
  @Override
  public OrdersVO getOrderInfo(int OrderID) {
    OrdersVO ordersVO = ordersDAO.getOrderInfo(OrderID);
    return ordersVO;
  }
  
  @Override
  public int create(OrdersVO ordersVO) {
    // 구현
    return 0;
  }

  @Override
  public int update(OrdersVO ordersVO) {
    // 구현
    return 0;
  }

  @Override
  public int delete(int OrderID) {
    // 구현
    return 0;
  }
}