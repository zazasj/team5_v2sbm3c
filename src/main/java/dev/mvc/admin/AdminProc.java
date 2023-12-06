package dev.mvc.admin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.member.MemberVO;

@Component("dev.mvc.admin.AdminProc")
public class AdminProc implements AdminProcInter {
  @Autowired // AdminDAOInter interface를 구현한 클래스의 객체를 만들어 자동으로 할당해라.
  private AdminDAOInter adminDAO;
  
  
  @Override
  public int checkID(String id) {
    int cnt = this.adminDAO.checkID(id);
    return cnt;
  }
  @Override
  public int login(AdminVO adminVO) {
    int cnt = this.adminDAO.login(adminVO);
    return cnt;
  }

  @Override
  public AdminVO read_by_id(String id) {
    AdminVO adminVO = this.adminDAO.read_by_id(id);
    return adminVO;
  }

  @Override
  public boolean isAdmin(HttpSession session) {
    boolean admin = false;
    
    if (session != null) {
      String admin_id = (String)session.getAttribute("admin_id");
      
      if (admin_id != null) {
        admin = true;
      }
    }
    
    return admin;
    
  }

  @Override
  public AdminVO read(int admino) {
    AdminVO adminVO = this.adminDAO.read(admino);
    return adminVO;
  }
  
  
}


