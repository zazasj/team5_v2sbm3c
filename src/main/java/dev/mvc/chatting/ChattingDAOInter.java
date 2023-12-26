package dev.mvc.chatting;

import java.util.ArrayList;
import java.util.HashMap;

import dev.mvc.chatting.ChattingVO;
import dev.mvc.supplier.SupplierVO;

public interface ChattingDAOInter {
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
	
}