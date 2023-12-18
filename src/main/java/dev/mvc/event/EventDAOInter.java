package dev.mvc.event;

import java.util.ArrayList;
import java.util.HashMap;


public interface EventDAOInter {
	
	  public int create(EventVO eventVO);

	  
	  public ArrayList<EventVO> list_all();
	  
	  
	  public EventVO read(int eventno);
	  
	  /**
	   * 카테고리별 검색 목록
	   * @param map
	   * @return
	   */
	  public ArrayList<EventVO> list_by_eventno_search(HashMap<String, Object> hashMap);
	  
	  /**
	   * 카테고리별 검색된 레코드 갯수
	   * @param map
	   * @return
	   */
	  public int search_count(HashMap<String, Object> hashMap);
	 
	  public ArrayList<EventVO> list_by_eventno_search_paging(EventVO eventVO);
	  
	  public int update_text(EventVO eventVO);

	  public int update_file(EventVO eventVO);
	 
	
	  public int delete(int eventno);

}
