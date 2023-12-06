package dev.mvc.orders;

public interface OrdersProcInter {

  /**
   * 등록, 추상메소드 -> Spring Boot이 구현함. 
   * @param ordersVO 객체
   * @return
   */
  public int create(OrdersVO ordersVO);
  
}
