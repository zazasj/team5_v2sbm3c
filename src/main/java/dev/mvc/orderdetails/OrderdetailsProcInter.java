package dev.mvc.orderdetails;

import java.util.HashMap;
import java.util.List;

public interface OrderdetailsProcInter {
  /**
   * 등록
   * @param order_itemVO
   * @return
   */
  public int create(OrderdetailsVO orderdetailsVO);

  public List<OrderdetailsVO> list_by_memberno(HashMap<String, Object> map);
}
