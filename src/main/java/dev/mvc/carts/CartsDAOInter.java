package dev.mvc.carts;

import java.util.ArrayList;

public interface CartsDAOInter {
  /**
   * 카트에 상품 등록
   * @param cartVO
   * @return
   */
  public int create(CartsVO cartsVO);
  
  /**
   * memberno 회원 번호별 쇼핑카트 목록 출력
   * @return
   */
  public ArrayList<CartsVO> list_by_memberno(int memberno);
  
  /**
   * 상품 삭제
   * @param cartID
   * @return
   */
  public int delete(int cartID);
  
  /**
   * 수량 변경
   * @param cartID
   * @return
   */
  public int update_cnt(CartsVO cartsVO);
}
