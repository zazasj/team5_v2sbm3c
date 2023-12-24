package dev.mvc.shipping;

import java.util.ArrayList;
import java.util.HashMap;


public interface ShippingDAOInter {
  /**
   * 등록, 추상 메소드 -> Spring Boot이 구현함.
   * @param shippingVO 객체
   * @return 등록된 레코드 갯수
   */
  public int create(ShippingVO shippingVO);
  
  /**
   * 전체 목록
   * @return
   */
  public ArrayList<ShippingVO> list_all();
  
  
  /**
   * 전체 목록
   * @return
   */
  public ArrayList<ShippingVO> list();
  
  /**
   * 조회
   * @param shippingID
   * @return
   */
  public ShippingVO read(int shippingID);
  
  /**
   * 수정   
   * @param shippingVO
   * @return 수정된 레코드 갯수
   */
  public int update(ShippingVO shippingVO);
  /**
   * 카테고리별 검색 목록
   * @param map
   * @return
   */
  public ArrayList<ShippingVO> list_by_shippingID_search(HashMap<String, Object> hashMap);
  
  
  /**
   * 카테고리별 검색된 레코드 갯수
   * @param map
   * @return
   */
  public int search_count(HashMap<String, Object> hashMap);
  
  /**
   * 카테고리별 검색 목록 + 페이징
   * @param shippingVO
   * @return
   */
  public ArrayList<ShippingVO> list_by_shippingID_search_paging(ShippingVO shippingVO);
  
  /**
   * 패스워드 검사
   * @param hashMap
   * @return
   */
  public int password_check(HashMap<String, Object> hashMap);
  
  /**
   * 글 정보 수정
   * @param shippingVO
   * @return 처리된 레코드 갯수
   */
  public int update_text(ShippingVO shippingVO);

  /**
   * 파일 정보 수정
   * @param shippingVO
   * @return 처리된 레코드 갯수
   */
  public int update_file(ShippingVO shippingVO);
 
  /**
   * 삭제
   * @param shippingID
   * @return 삭제된 레코드 갯수
   */
  public int delete(int shippingID);
  
  
}



