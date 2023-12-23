package dev.mvc.order_pay;

import java.util.List;

public interface Order_payProcInter {
  /**
   * 
   * @param order_payVO
   * @return
   */
  public int create(Order_payVO order_payVO);
  
  /**
   * 회원별 주문 결재 목록
   * @param memberno
   * @return
   */
  public List<Order_payVO> list_by_memberno(int memberno);
  
  public Order_payVO read_by_memberno(int memberno);
}
