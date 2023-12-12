package dev.mvc.chatting;

import java.util.ArrayList;

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
}
