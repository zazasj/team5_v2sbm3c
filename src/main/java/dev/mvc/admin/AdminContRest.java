package dev.mvc.admin;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.tool.Tool;

@RestController // REST 컨트롤러 선언
public class AdminContRest {
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") // "dev.mvc.admin.AdminProc"라고 명명된 클래스
  private AdminProcInter adminProc; // AdminProcInter를 구현한 AdminProc 클래스의 객체를 자동으로 생성하여 할당

  public AdminContRest() {
    System.out.println("-> AdminContRest created.");
  }

  /**
   * Cookie 기반 로그인 처리
   * http://localhost:9091/admin/login_rest.do
    {
    "id": "admin1",
    "passwd": "69017000"
    }
   * @return
   */
  @PostMapping(path = "/admin/login_rest.do")
  public String login_proc(HttpServletResponse response, HttpSession session, @RequestBody AdminVO adminVO) {

    JSONObject json = new JSONObject();

    int cnt = adminProc.login(adminVO);
    if (cnt == 1) { // 로그인 성공시 회원 정보 조회
      AdminVO adminVO_read = adminProc.read_by_id(adminVO.getId()); // DBMS에서 id를 이용한 회원 조회
      session.setAttribute("adminno", adminVO_read.getAdminno()); // 서버의 메모리에 기록
      session.setAttribute("admin_id", adminVO_read.getId());
      session.setAttribute("admin_mname", adminVO_read.getMname());
      session.setAttribute("admin_grade", adminVO_read.getGrade());

      json.put("sw", true);
      json.put("adminno", adminVO_read.getAdminno());
    } else {
      json.put("sw", false);
    }

    return json.toString();
  }
  
  /**
   * 로그아웃 처리
   * http://localhost:9091/admin/logout_rest.do 
   * @param session
   * @return
   */
  @GetMapping(path="/admin/logout_rest.do")
  public String logout(HttpSession session){
    
    session.invalidate(); // 모든 session 변수 삭제
    
    JSONObject json = new JSONObject();

    json.put("logout", true);
         
    return json.toString();
  }
  
}



