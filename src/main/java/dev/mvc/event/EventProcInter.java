package dev.mvc.event;

import java.util.ArrayList;
import java.util.HashMap;

public interface EventProcInter {
	
	public int create(EventVO eventVO);

	  
	  public ArrayList<EventVO> list_all();
	  
	  
	  public EventVO read(int eventno);
	  
	  /**
     * 카테고리별 검색 목록
     * @param map
     * @return
     */
    public ArrayList<EventVO> list_by_eventno_search(HashMap<String, Object> hashMap);
    
    public ArrayList<EventVO> list_by_eventno_search_paging(EventVO eventVO);
    /**
     * 카테고리별 검색된 레코드 갯수
     * @param map
     * @return
     */
    public int search_count(HashMap<String, Object> hashMap);
	 
	  public int update_text(EventVO eventVO);

	  public int update_file(EventVO eventVO);
	 
	
	  public int delete(int eventno);
	  
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
	  public String pagingBox(int now_page, String word, String list_file, int search_count);

}
