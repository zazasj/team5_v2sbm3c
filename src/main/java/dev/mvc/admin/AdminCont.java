package dev.mvc.admin;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.tool.Tool;

@Controller
public class AdminCont {
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") // "dev.mvc.admin.AdminProc"라고 명명된 클래스
  private AdminProcInter adminProc; // AdminProcInter를 구현한 AdminProc 클래스의 객체를 자동으로 생성하여 할당
  
  public AdminCont() {
    System.out.println("-> AdminCont created.");
  }
  
  /**
   * POST 요청시 JSP 페이지에서 JSTL 호출 기능 지원, 새로고침 방지, EL에서 param으로 접근
   * POST → url → GET → 데이터 전송
   * @return
   */
  @RequestMapping(value="/admin/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();

    mav.setViewName(url); // forward
    
    return mav; // forward
  }
  
  /**
   * 로그아웃 처리
   * @param session
   * @return
   */
  @RequestMapping(value="/admin/logout.do", method=RequestMethod.GET)
  public ModelAndView logout(HttpSession session){
    ModelAndView mav = new ModelAndView();
    session.invalidate(); // 모든 session 변수 삭제
    
    mav.setViewName("redirect:/index.do"); 
    
    return mav;
  }
  
  /**
   * Cookie 로그인 폼
   * http://localhost:9093/admin/login.do
   * @return
   */
  @RequestMapping(value="/admin/login.do", method=RequestMethod.GET)
  public ModelAndView login() {
    ModelAndView mav = new ModelAndView();
    
    mav.setViewName("/admin/login_form_ck"); // /WEB-INF/views/admin/login_form_ck.jsp
    
    return mav;
  }
  
  /**
  * Cookie 기반 로그인 처리
  * @param response Cookie를 쓰기위해 필요
  * @param session 로그인 정보를 메모리에 기록
  * @param id  회원 아이디
  * @param passwd 회원 패스워드
  * @param id_save 회원 아이디 Cookie에 저장 여부
  * @param passwd_save 패스워드 Cookie에 저장 여부
  * @param id_save 폼에 입력된 id 저장 여부
  * @param passwd_save 폼에 입력된 passwd 저장 여부
  * @return
  */
  // http://localhost:9091/admin/login.do 
  @RequestMapping(value = "/admin/login.do", 
                            method = RequestMethod.POST)
  public ModelAndView login_proc(
                            HttpServletResponse response,
                            HttpSession session,
                            AdminVO adminVO, String id_save, String passwd_save) {
    ModelAndView mav = new ModelAndView();
   
    int cnt = adminProc.login(adminVO);
    if (cnt == 1) { // 로그인 성공시 회원 정보 조회
      AdminVO adminVO_read = adminProc.read_by_id(adminVO.getId()); // DBMS에서 id를 이용한 회원 조회
      session.setAttribute("adminno", adminVO_read.getAdminno()); // 서버의 메모리에 기록
      session.setAttribute("admin_id", adminVO_read.getId());
      session.setAttribute("admin_mname", adminVO_read.getMname());
      session.setAttribute("admin_grade", adminVO_read.getGrade());
   
      String id = adminVO.getId();                  // 폼에 입력된 id
      String passwd = adminVO.getPasswd();  // 폼에 입력된 passwd 
      
      // -------------------------------------------------------------------
      // id 관련 쿠기 저장
      // -------------------------------------------------------------------
      if (Tool.checkNull(id_save).equals("Y")) { // id를 저장할 경우, Checkbox를 체크한 경우, 체크하지 않으면 null
        Cookie ck_admin_id = new Cookie("ck_admin_id", id);
        ck_admin_id.setPath("/");  // root 폴더에 쿠키를 기록함으로 모든 경로에서 쿠기 접근 가능
        ck_admin_id.setMaxAge(60 * 60 * 24 * 30); // 30 day, 초단위
        response.addCookie(ck_admin_id); // client의 chrome 관련 폴더에 Cookie 저장
      } else { // N, id를 저장하지 않는 경우, Checkbox를 체크 해제한 경우
        Cookie ck_admin_id = new Cookie("ck_admin_id", ""); // 값을 삭제한 Cookie 객체 생성
        ck_admin_id.setPath("/");
        ck_admin_id.setMaxAge(0); // 수명을 0초로 지정
        response.addCookie(ck_admin_id);  // client의 chrome 관련 폴더에 기존 Cookie를 덮어씀
      }
      
      // id를 저장할지 선택하는 CheckBox 체크 여부
      Cookie ck_admin_id_save = new Cookie("ck_admin_id_save", id_save);
      ck_admin_id_save.setPath("/");
      ck_admin_id_save.setMaxAge(60 * 60 * 24 * 30); // 30 day
      response.addCookie(ck_admin_id_save);
      // -------------------------------------------------------------------
  
      // -------------------------------------------------------------------
      // Password 관련 쿠기 저장
      // -------------------------------------------------------------------
      if (Tool.checkNull(passwd_save).equals("Y")) { // 패스워드 저장할 경우
        Cookie ck_admin_passwd = new Cookie("ck_admin_passwd", passwd);
        ck_admin_passwd.setPath("/");
        ck_admin_passwd.setMaxAge(60 * 60 * 24 * 30); // 30 day
        response.addCookie(ck_admin_passwd);
      } else { // N, 패스워드를 저장하지 않을 경우
        Cookie ck_admin_passwd = new Cookie("ck_admin_passwd", "");
        ck_admin_passwd.setPath("/");
        ck_admin_passwd.setMaxAge(0);
        response.addCookie(ck_admin_passwd);
      }
      
      // passwd를 저장할지 선택하는  CheckBox 체크 여부
      Cookie ck_admin_passwd_save = new Cookie("ck_admin_passwd_save", passwd_save);
      ck_admin_passwd_save.setPath("/");
      ck_admin_passwd_save.setMaxAge(60 * 60 * 24 * 30); // 30 day
      response.addCookie(ck_admin_passwd_save);
      // -------------------------------------------------------------------
   
      mav.setViewName("redirect:/index.do");  
    } else {
      mav.addObject("url", "/admin/login_fail_msg");
      mav.setViewName("redirect:/admin/msg.do"); // Post -> Get
    }
       
    return mav;
  }
    
  
}

