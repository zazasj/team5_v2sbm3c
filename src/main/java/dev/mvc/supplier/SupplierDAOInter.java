package dev.mvc.supplier;

import java.util.ArrayList;

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
}
