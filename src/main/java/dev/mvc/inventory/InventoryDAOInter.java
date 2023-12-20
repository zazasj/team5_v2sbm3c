package dev.mvc.inventory;

import java.util.ArrayList;
import java.util.HashMap;

public interface InventoryDAOInter {
  /**
   * 등록, 추상 메소드 -> Spring Boot이 구현함.
   * @param inventoryVO 객체
   * @return 등록된 레코드 갯수
   */
  public int create(InventoryVO inventoryVO);
  
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
   * @param inventoryID
   * @return
   */
  public InventoryVO read(int inventoryID);
  
  /**
   * 조회
   * @param inventoryStatus
   * @return
   */
  public InventoryVO readString(String inventoryStatus);
  
  /**
   * 수정   
   * @param inventoryVO
   * @return 수정된 레코드 갯수
   */
  public int update(InventoryVO inventoryVO);
  
  /**
   * 수정   
   * @param inventoryVO
   * @return 수정된 레코드 갯수
   */
  public int updatequantity(InventoryVO inventoryVO);

  /**
   * 삭제
   * @param inventoryID 삭제할 레코드 PK 번호
   * @return 삭제된 레코드 갯수
   */
  public int delete(int inventoryID);
  
  /**
   * 비회원/회원 SELECT LIST
   * @return
   */
  public ArrayList<InventoryVO> list_all_y();
  
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
}



