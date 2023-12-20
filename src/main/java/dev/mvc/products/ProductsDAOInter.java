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
   * 모든 상품목록 + 검색 + 페이징
   * @return
   */
  public ArrayList<ProductsVO> list_all_search_paging(ProductsVO productsVO);
  
  /**
   * 모든 검색된 레코드 갯수
   * @param map
   * @return
   */
  public int search_count_all(HashMap<String, Object> hashMap);
  
  /**
   * 위스키에 등록된 상품목록
   * @return
   */
  public ArrayList<ProductsVO> list_all_1();
  
  /**
   * 위스키에 등록된 상품목록 + 검색 + 페이징
   * @return
   */
  public ArrayList<ProductsVO> list_all_1_search_paging(ProductsVO productsVO);
  
  /**
   * 위스키 검색된 레코드 갯수
   * @param map
   * @return
   */
  public int search_count_1(HashMap<String, Object> hashMap);
  
  /**
   * 브랜디/꼬냑에 등록된 상품목록
   * @return
   */
  public ArrayList<ProductsVO> list_all_2();
  
  /**
   * 브랜디/꼬냑에 등록된 상품목록 + 검색 + 페이징
   * @return
   */
  public ArrayList<ProductsVO> list_all_2_search_paging(ProductsVO productsVO);
  
  /**
   * 브랜디/꼬냑 검색된 레코드 갯수
   * @param map
   * @return
   */
  public int search_count_2(HashMap<String, Object> hashMap);
  
  /**
   * 와인에 등록된 상품목록
   * @return
   */
  public ArrayList<ProductsVO> list_all_3();
  
  /**
   * 와인에 등록된 상품목록 + 검색 + 페이징
   * @return
   */
  public ArrayList<ProductsVO> list_all_3_search_paging(ProductsVO productsVO);
  
  /**
   * 와인 검색된 레코드 갯수
   * @param map
   * @return
   */
  public int search_count_3(HashMap<String, Object> hashMap);
  
  /**
   * 리큐르에 등록된 상품목록
   * @return
   */
  public ArrayList<ProductsVO> list_all_4();
  
  /**
   * 리큐르에 등록된 상품목록 + 검색 + 페이징
   * @return
   */
  public ArrayList<ProductsVO> list_all_4_search_paging(ProductsVO productsVO);
  
  /**
   * 리큐르 검색된 레코드 갯수
   * @param map
   * @return
   */
  public int search_count_4(HashMap<String, Object> hashMap);
  
  /**
   * 전통주에 등록된 상품목록
   * @return
   */
  public ArrayList<ProductsVO> list_all_5();
  
  /**
   * 전통주에 등록된 상품목록 + 검색 + 페이징
   * @return
   */
  public ArrayList<ProductsVO> list_all_5_search_paging(ProductsVO productsVO);
  
  /**
   * 전통주 검색된 레코드 갯수
   * @param map
   * @return
   */
  public int search_count_5(HashMap<String, Object> hashMap);
  
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




