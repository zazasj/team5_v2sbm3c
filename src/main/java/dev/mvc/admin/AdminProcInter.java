package dev.mvc.admin;

import javax.servlet.http.HttpSession;

public interface AdminProcInter {
  
  public int checkID(String id);
  /**
   * 로그인
   * @param AdminVO
   * @return
   */
  public int login(AdminVO adminVO);
  
  /**
   * 관리자 정보
   * @param String
   * @return
   */
  public AdminVO read_by_id(String id);
  
  /**
   * 관리자 로그인 체크
   * @param session
   * @return
   */
  public boolean isAdmin(HttpSession session);
  
  /**
   * 회원 정보 조회
   * @param admino
   * @return
   */
  public AdminVO read(int admino);
  
}


