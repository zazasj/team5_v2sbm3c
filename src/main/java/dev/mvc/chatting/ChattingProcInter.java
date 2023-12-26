package dev.mvc.chatting;

import java.util.ArrayList;
import java.util.HashMap;

public interface ChattingProcInter {
	/**
	 * 등록
	 * @param chattingVO
	 * @return
	 */
	public int create(ChattingVO chattingVO);
	
	/**
	 * 챗봇 목록
	 * @return
	 */
	public ArrayList<ChattingVO> list_all();
	
	/**
	 * 조회
	 * @param chattingno
	 * @return
	 */
	public ChattingVO read(int chattingno);
	
	/**
	 * 삭제
	 * @param chattingno
	 * @return
	 */
	public int delete(int chattingno);
	
	/**
	 * 카테고리별 검색 목록
	 * @param map
	 * @return
	 */
	public ArrayList<ChattingVO> list_by_chattingno_search(HashMap<String, Object> hashMap);
	  
	/**
	 * 카테고리별 검색된 레코드 갯수
	 * @param map
	 * @return
	 */
	public int search_count(HashMap<String, Object> hashMap);
	
	/**
	 *  특정 카테고리의 검색 + 페이징된 글목록
	 *  spring framework이 JDBC 관련 코드를 모두 생성해줌
	 * @return
	 */
	public ArrayList<ChattingVO> list_by_chattingno_search_paging(ChattingVO chattingVO);
	
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
