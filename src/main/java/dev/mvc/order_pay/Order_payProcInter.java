package dev.mvc.order_pay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dev.mvc.products.ProductsVO;

public interface Order_payProcInter {
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

  /** 
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
   *
   * @param now_page      현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명
   * @param search_count 검색 레코드수   
   * @return 페이징 생성 문자열
   */ 
  public String pagingBox(int memberno, int now_page, String word, String list_file, int search_count);
  
  public List<Order_payVO> list();
  
  public int delete(int order_payno);
}
