package dev.mvc.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import dev.mvc.inventory.InventoryVO;

public interface InventoryProcInter {
  /**
   * 등록, 추상 메소드 -> Spring Boot이 구현함.
   * @param InventoryVO 객체
   * @return 등록된 레코드 갯수
   */
  public int create(InventoryVO InventoryVO);
  
  /**
   * 전체 목록
   * @return
   */
  public ArrayList<InventoryVO> list_all();
  
  /**
   * 전체 목록
   * @return
   */
  public ArrayList<InventoryVO> list();
  
  /**
   * 조회
   * @param InventoryID
   * @return
   */
  public InventoryVO read(int InventoryID);
  
  /**
   * 조회
   * @param InventoryStatus
   * @return
   */
  public InventoryVO readString(String InventoryStatus);
  
  /**
   * 수정   
   * @param InventoryVO
   * @return 수정된 레코드 갯수
   */
  public int update(InventoryVO InventoryVO);
  
  /**
   * 수정   
   * @param InventoryVO
   * @return 수정된 레코드 갯수
   */
  public int updatequantity(InventoryVO InventoryVO);

  /**
   * 삭제
   * @param InventoryID 삭제할 레코드 PK 번호
   * @return 삭제된 레코드 갯수
   */
  public int delete(int InventoryID);

  
  /**
   * 카테고리별 검색 목록
   * @param map
   * @return
   */
  public ArrayList<InventoryVO> list_by_inventoryStatus_search(HashMap<String, Object> hashMap);
  
  /**
   * 카테고리별 검색된 레코드 갯수
   * @param map
   * @return
   */
  public int search_count(HashMap<String, Object> hashMap);
  
  /**
   * 카테고리별 검색 목록 + 페이징
   * @param inventoryVO
   * @return
   */
  public ArrayList<InventoryVO> list_by_inventoryStatus_search_paging(InventoryVO inventoryVO);
  
  /** 
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
   *
   * @param inventoryStatus          카테고리번호 
   * @param now_page      현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명
   * @param search_count 검색 레코드수   
   * @return 페이징 생성 문자열
   */ 
  public String pagingBox(String inventoryStatus, int now_page, String word, String list_file, int search_count);

}



