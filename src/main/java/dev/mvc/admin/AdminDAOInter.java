package dev.mvc.admin;

public interface AdminDAOInter {
  
  public int checkID(String id);
  /**
   * 로그인
   * @param AdminVO
   * @return
   */
  public int login(AdminVO adminVO);
  
  /**
   * 회원 정보
   * @param String
   * @return
   */
  public AdminVO read_by_id(String id);
  
  /**
   * 회원 정보 조회
   * @param admino
   * @return
   */
  public AdminVO read(int admino);
  
}
