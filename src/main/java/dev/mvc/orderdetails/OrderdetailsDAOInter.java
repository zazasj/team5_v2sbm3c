package dev.mvc.orderdetails;

import java.util.HashMap;
import java.util.List;

public interface OrderdetailsDAOInter {
  /**
   * 등록
   * @param order_itemVO
   * @return
   */
  public int create(OrderdetailsVO orderdetailsVO);

  /**
   * 회원별 주문 결재 목록
   * @param order_payno
   * @return
   */
  public List<OrderdetailsVO> list_by_memberno(HashMap<String, Object> map);
}
