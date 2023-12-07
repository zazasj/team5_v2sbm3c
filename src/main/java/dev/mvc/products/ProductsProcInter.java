package dev.mvc.products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 개발자가 구현합니다.
 * @author soldesk
 *
 */
public interface ProductsProcInter {
  /**
   * 등록, 추상 메소드
   * @param productsVO
   * @return
   */
  public int create(ProductsVO productsVO);

  /**
   * 모든 카테고리의 등록된 글목록
   * @return
   */
  public ArrayList<ProductsVO> list_all();
 
  /**
   * 카테고리별 등록된 글 목록
   * @param CateogoryID
   * @return
   */
  public ArrayList<ProductsVO> list_by_categoryID(int CateogoryID);
  
  /**
   * 조회
   * @param ProductID
   * @return
   */
  public ProductsVO read(int ProductID);
  
  /**
   * 카테고리별 검색 목록
   * @param map
   * @return
   */
  public ArrayList<ProductsVO> list_by_categoryID_search(HashMap<String, Object> hashMap);
  
  /**
   * 카테고리별 검색된 레코드 갯수
   * @param map
   * @return
   */
  public int search_count(HashMap<String, Object> hashMap);
  
  /**
   * 카테고리별 검색 목록 + 페이징
   * @param productsVO
   * @return
   */
  public ArrayList<ProductsVO> list_by_categoryID_search_paging(ProductsVO productsVO);

  /** 
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
   *
   * @param cateno          카테고리번호 
   * @param now_page      현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명
   * @param search_count 검색 레코드수   
   * @return 페이징 생성 문자열
   */ 
  public String pagingBox(int categoryID, int now_page, String word, String list_file, int search_count);

  /**
   * 글 정보 수정
   * @param productsVO
   * @return 처리된 레코드 갯수
   */
  public int update_text(ProductsVO productsVO);
  
  /**
   * 파일 정보 수정
   * @param productsVO
   * @return 처리된 레코드 갯수
   */
  public int update_file(ProductsVO productsVO);
  
  /**
   * 삭제
   * @param ProductID
   * @return 삭제된 레코드 갯수
   */
  public int delete(int productID);
  
  /**
   * FK CategoryID 값이 같은 레코드 갯수 산출
   * @param CategoryID
   * @return
   */
  public int count_by_categoryID(int CategoryID);
  
  /**
   * 특정 카테고리에 속한 모든 레코드 삭제
   * @param CategoryID
   * @return 삭제된 레코드 갯수
   */
  public int delete_by_categoryID(int CategoryID);
  
}
