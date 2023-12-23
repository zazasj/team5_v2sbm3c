package dev.mvc.order_item;

import java.util.HashMap;
import java.util.List;

public interface Order_itemProcInter {
  /**
   * 등록
   * @param order_itemVO
   * @return
   */
  public int create(Order_itemVO order_itemVO);

  public List<Order_itemVO> list_by_memberno(HashMap<String, Object> map);
}
