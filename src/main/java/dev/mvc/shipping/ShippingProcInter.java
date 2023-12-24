package dev.mvc.shipping;

import java.util.ArrayList;
import java.util.HashMap;

import dev.mvc.shipping.ShippingVO;

public interface ShippingProcInter {
  /**
   * 등록, 추상 메소드 -> Spring Boot이 구현함.
   * @param ShippingVO 객체
   * @return 등록된 레코드 갯수
   */
  public int create(ShippingVO ShippingVO);
  
  /**
   * 전체 목록
   * @return
   */
  public ArrayList<ShippingVO> list_all();
  
  /**
   * 조회
   * @param ShippingID
   * @return
   */
  public ShippingVO read(int ShippingID);
  
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
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
   *
   * @param shippingID          카테고리번호 
   * @param now_page      현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명
   * @param search_count 검색 레코드수   
   * @return 페이징 생성 문자열
   */ 
  public String pagingBox(int now_page, String word, String list_file, int search_count);
  /** 
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
   *
   * @param tripno          카테고리번호 
   * @param now_page      현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명
   * @param search_count 검색 레코드수   
   * @return 페이징 생성 문자열
   */ 
  public String pagingBox2(int shippingID, int now_page, String word, String list_file, int search_count);
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
   * 수정   
   * @param ShippingVO
   * @return 수정된 레코드 갯수
   */
  public int update(ShippingVO ShippingVO);

  /**
   * 삭제
   * @param ShippingID 삭제할 레코드 PK 번호
   * @return 삭제된 레코드 갯수
   */
  public int delete(int shippingID);
  
}



