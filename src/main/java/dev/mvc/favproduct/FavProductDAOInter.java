package dev.mvc.favproduct;

import java.util.ArrayList;
import java.util.HashMap;

public interface FavProductDAOInter {

	public int create(FavProductVO favproductVO);
	/**
	 * memberno 회원 번호별 쇼핑카트 목록 출력
	 * @return
	 */
	public ArrayList<FavProductVO> list_by_memberno(int memberno);
	
	/**
	 * 상품 삭제
	 * @param cartno
	 * @return
	 */
	public int delete(int favID);
	
	public int checkIfFavorite(HashMap<String, Object> map);
}
