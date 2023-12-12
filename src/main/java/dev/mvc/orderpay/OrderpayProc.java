package dev.mvc.orderpay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.orderpay.OrderpayProc")
public class OrderpayProc implements OrderpayProcInter {
  @Autowired
  private OrderpayDAOInter orderpayDAO;
  
  public int create(OrderpayVO orderpayVO) {
    int cnt = this.orderpayDAO.create(orderpayVO);
    return cnt;
  }

}
