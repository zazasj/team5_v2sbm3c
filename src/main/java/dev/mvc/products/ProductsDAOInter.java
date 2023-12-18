package dev.mvc.products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Spring Boot가 자동 구현
 * @author soldesk
 *
 */
public interface ProductsDAOInter {
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
   * @param categoryID
   * @return
   */
  public ArrayList<ProductsVO> list_by_categoryID(int categoryID);
  
  /**
   * 조회
   * @param productID
   * @return
   */
  public ProductsVO read(int productID);
  
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
   * @param productID
   * @return 삭제된 레코드 갯수
   */
  public int delete(int productID);
  
  /**
   * FK categoryID 값이 같은 레코드 갯수 산출
   * @param categoryID
   * @return
   */
  public int count_by_categoryID(int categoryID);
 
  /**
   * 특정 카테고리에 속한 모든 레코드 삭제
   * @param categoryID
   * @return 삭제된 레코드 갯수
   */
  public int delete_by_categoryID(int categoryID);

  /**
   * 리뷰 수 증가
   * @param 
   * @return
   */ 
  public int increaseReviewcnt(int productid);
 
  /**
   * 리뷰 수 감소
   * @param 
   * @return
   */   
  public int decreaseReviewcnt(int productid);
  
}




