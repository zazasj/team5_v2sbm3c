package dev.mvc.cateGroup;

import java.util.ArrayList;

public interface CateGroupDAOInter {
  
  /**
   * 등록, 추상메소드 -> Spring Boot이 구현함. 
   * @param cateGroupVO 객체
   * @return
   */
  public int create(CateGroupVO cateGroupVO);
  
  /**
   * 전체 목록
   * @return
   */
  public ArrayList<CateGroupVO> list_all();
  
  /**
   * 조회
   * @param GrpID
   * @return
   */
  public CateGroupVO read(int GrpID);
  
  /**
   * 수정
   * @param GrpID
   * @return
   */
  public int update(CateGroupVO cateGroupVO);
  
  /**
   * 삭제
   * @param GrpID 삭제할 레코드 번호
   * @return 삭제된 레코드 수
   */
  public int delete(int GrpID);
  
  /**
   * 우선 순위 높임, 10 등 -> 1 등   
   * @param GrpID
   * @return 수정된 레코드 갯수
   */
  public int update_seqno_forward(int GrpID);

  /**
   * 우선 순위 낮춤, 1 등 -> 10 등   
   * @param GrpID
   * @return 수정된 레코드 갯수
   */
  public int update_seqno_backward(int GrpID);
  
  /**
   * 카테고리 공개 설정
   * @param GrpID
   * @return
   */
  public int update_visible_y(int GrpID);
  
  /**
   * 카테고리 비공개 설정
   * @param GrpID
   * @return
   */
  public int update_visible_n(int GrpID);
  
  /**
   * 비회원/회원 SELECT LIST
   * @return
   */
  public ArrayList<CateGroupVO> list_all_y();
}
