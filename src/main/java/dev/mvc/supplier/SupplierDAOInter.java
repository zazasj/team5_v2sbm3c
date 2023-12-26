package dev.mvc.supplier;

import java.util.ArrayList;
import java.util.HashMap;


public interface SupplierDAOInter {

	/**
	 * 등록
	 * @param SupplierVO
	 * @return
	 */
	public int create(SupplierVO supplierVO);
	
	/**
	 * 관리자별 전체 목록
	 * @param managerno
	 * @return
	 */
	public ArrayList<SupplierVO> list_all_adminno(int adminno);
	
	/** 한창명 넣음
     */
    public ArrayList<SupplierVO> list_all();
	
	/**
	 * 조회
	 * @param supplierno
	 * @return
	 */
	public SupplierVO read(int supplierid);
	
	/**
	 * 카테고리별 검색 목록
	 * @param map
	 * @return
	 */
	public ArrayList<SupplierVO> list_by_supplierid_search(HashMap<String, Object> hashMap);
	  
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
	public ArrayList<SupplierVO> list_by_supplierid_search_paging(SupplierVO supplierVO);
	 
	  
	/**
	 * 삭제
	 * @param 삭제할 레코드 PK 번호
	 * @return 삭제한 레코드 갯수
	 */
	public int delete(int supplierid);
	  
	/**
	 * 수정
	 * @param SupplierVO
	 * @return
	 */
	public int update(SupplierVO supplierVO);	
	
	/**
	 * 글 정보 수정
	 * @param supplierVO
	 * @return 처리된 레코드 갯수
	 */
	public int update_text(SupplierVO supplierVO);
	
	/**
	 * 파일 정보 수정
	 * @param supplierVO
	 * @return 처리된 레코드 갯수
	 */
	public int update_file(SupplierVO supplierVO);
}
