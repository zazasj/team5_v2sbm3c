package dev.mvc.order_pay;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.order_pay.Order_payProc")
public class Order_payProc implements Order_payProcInter {
  @Autowired
  private Order_payDAOInter order_payDAO;
  
  public Order_payProc() {
    System.out.println("--> Order_payProc created.");
  }
  
  public int create(Order_payVO order_payVO) {
    int cnt = this.order_payDAO.create(order_payVO);
    return cnt;
  }

  @Override
  public List<Order_payVO> list_by_memberno(int memberno) {
    List<Order_payVO> list = this.order_payDAO.list_by_memberno(memberno);
    return list;
  }
  
  @Override
  public Order_payVO read_by_memberno(int memberno) {
    Order_payVO order_payVO = order_payDAO.read_by_memberno(memberno);

    return order_payVO;
  }
}
