package dev.mvc.order_pay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface Order_payDAOInter {
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
  
  public ArrayList<Order_payVO> list_by_memberno_search(HashMap<String, Object> hashMap);
  
  public int search_count(HashMap<String, Object> hashMap);
  
  public ArrayList<Order_payVO> list_by_memberno_search_paging(Order_payVO order_payVO);
  
  public List<Order_payVO> list();
  
  public int delete(int order_payno);
  
}
