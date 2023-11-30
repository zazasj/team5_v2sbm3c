package dev.mvc.category;

import java.util.ArrayList;

public interface CategoryProcInter {
  
  /**
   * 등록, 추상메소드 -> Spring Boot이 구현함. 
   * @param categoryVO 객체
   * @return
   */
  public int create(CategoryVO categoryVO);
  
  /**
   * 전체 목록
   * @return
   */
  public ArrayList<CategoryVO> list_all();
  
  /**
   * 조회
   * @param categoryID
   * @return
   */
  public CategoryVO read(int CategoryID);
  
  /**
   * 수정
   * @param categoryID
   * @return
   */
  public int update(CategoryVO categoryVO);
  
  /**
   * 삭제
   * @param categoryID 삭제할 레코드 번호
   * @return 삭제된 레코드 수
   */
  public int delete(int CategoryID);
  
  /**
   * 우선 순위 높임, 10 등 -> 1 등   
   * @param categoryID
   * @return 수정된 레코드 갯수
   */
  public int update_seqno_forward(int CategoryID);

  /**
   * 우선 순위 낮춤, 1 등 -> 10 등   
   * @param categoryID
   * @return 수정된 레코드 갯수
   */
  public int update_seqno_backward(int CategoryID);
  
  /**
   * 카테고리 공개 설정
   * @param categoryID
   * @return
   */
  public int update_visible_y(int CategoryID);
  
  /**
   * 카테고리 비공개 설정
   * @param categoryID
   * @return
   */
  public int update_visible_n(int CategoryID);
  
  /**
   * 비회원/회원 SELECT LIST
   * @return
   */
  public ArrayList<CategoryVO> list_all_y();
}
