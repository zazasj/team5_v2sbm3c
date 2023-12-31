package dev.mvc.member;
 
import java.security.KeyStore.Entry.Attribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.admin.AdminVO;
import dev.mvc.adminlog.AdlogService;
import dev.mvc.login.LoginService;
import dev.mvc.maillog.MailService;
import dev.mvc.memberwithdraw.WithdrawService;
import dev.mvc.tool.MailTool;
import dev.mvc.tool.Tool;
 
@Controller
public class MemberCont {
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") // @Component("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc;

  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc = null;
  
  @Autowired
  private LoginService loginService;
  
  @Autowired
  private WithdrawService withservice;
  
  @Autowired
  private MailService mailService;
  
  public MemberCont(){
    System.out.println("-> MemberCont created.");
  }
  
  
  // http://localhost:9091/member/checkID.do?id=user1@gmail.com
  /**
  * ID 以묐났 泥댄겕, JSON 異쒕젰
  * @return {"cnt":0}, {"cnt":1}
  */
  @ResponseBody
  @RequestMapping(value="/member/checkID.do", 
                              method=RequestMethod.GET ,
                              produces = "text/plain;charset=UTF-8" )
  public String checkID(String id) {
    System.out.println("-> id: " + id);
    
    try {
      Thread.sleep(3000); // 3 珥� 吏��뿰
    }catch(Exception e) {
      
    }
      int cnt2 = this.memberProc.checkID(id);
      
      JSONObject json = new JSONObject();
      json.put("cnt2", cnt2); // �궎�� 媛� = HashMap
     
      return json.toString(); // {"cnt":1}     
    
    
  }

  // http://localhost:9091/member/create.do
  /**
  * �벑濡� �뤌
  * @return
  */
  @RequestMapping(value="/member/create.do", method=RequestMethod.GET )
  public ModelAndView create() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/member/create"); // /WEB-INF/views/member/create.jsp
   
    return mav; // forward
  }

  /**
   * �벑濡� 泥섎━
   * @param memberVO
   * @return
   */
  @RequestMapping(value="/member/create.do", method=RequestMethod.POST)
  public ModelAndView create(MemberVO memberVO){
    ModelAndView mav = new ModelAndView();
    
    // 以묐났 ID 寃��궗
    int checkID_cnt = this.memberProc.checkID(memberVO.getId());
    if (checkID_cnt == 0) {
      // System.out.println("id: " + memberVO.getId());
      
      memberVO.setGrade(15); // 湲곕낯 �쉶�썝 媛��엯 �벑濡� 15 吏��젙
      
      int cnt= memberProc.create(memberVO); // SQL insert
      
      if (cnt == 1) { // insert �젅肄붾뱶 媛쒖닔, �쉶�썝 媛��엯 �꽦怨�
        mav.addObject("code", "create_success");
        mav.addObject("mname", memberVO.getMname());  // �솉湲몃룞�떂(user4) �쉶�썝 媛��엯�쓣 異뺥븯�빀�땲�떎.
        mav.addObject("id", memberVO.getId());
      } else { // �쉶�썝 媛��엯 �떎�뙣
        mav.addObject("code", "create_fail");
        // mav.addObject("cnt", 0);  // 異붽��맂 �젅肄붾뱶 �뾾�쓬.  
      }
      
      mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
      
    } else {
      mav.addObject("code", "duplicate_fail"); // �씠誘� �궗�슜以묒씤 id�엫�쑝濡� 媛��엯 �떎�뙣
      mav.addObject("cnt", 0);                       // 異붽��맂 �젅肄붾뱶 �뾾�쓬.      
    }

    mav.addObject("url", "/member/msg");  // /member/msg -> /member/msg.jsp
    
    mav.setViewName("redirect:/member/msg.do");
    return mav;
  }
  
  /**
   * �깉濡쒓퀬移� 諛⑹�, EL�뿉�꽌 param�쑝濡� �젒洹�, POST -> GET -> /member/msg.jsp
   * @return
   */
  @RequestMapping(value="/member/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();

    mav.setViewName(url); // forward
    
    return mav; // forward
  }
  
  @RequestMapping(value="/member/msg2.do", method=RequestMethod.GET)
  public ModelAndView msg2(HttpSession session){
    ModelAndView mav = new ModelAndView();
    mav.addObject("mname", session.getAttribute("mname"));
    mav.addObject("fid", session.getAttribute("fid"));    
    mav.setViewName("/member/msg2"); // /WEB-INF/views/member/create.jsp
    return mav; // forward
  }

  
  /**
  * 紐⑸줉 異쒕젰 媛��뒫
  * @param session
  * @return
  */
  @RequestMapping(value="/member/list.do", method=RequestMethod.GET)
  public ModelAndView list(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      ArrayList<MemberVO> list = this.memberProc.list();
      mav.addObject("list", list);

      mav.setViewName("/member/list"); // /webapp/WEB-INF/views/member/list.jsp

    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
        
    return mav;
  } 
  
  /**
   * �쉶�썝 議고쉶
   * http://localhost:9091/member/read.do?memberno=1
   * 愿�由ъ옄, �쉶�썝 蹂몄씤留� 媛��뒫
   * @param memberno
   * @return
   */
  @RequestMapping(value="/member/read.do", method=RequestMethod.GET)
  public ModelAndView read(HttpSession session, HttpServletRequest request){
    ModelAndView mav = new ModelAndView();
    
    int memberno = 0;
    if (this.memberProc.isMember(session) || this.adminProc.isAdmin(session)) { 
      // 濡쒓렇�씤�븳 寃쎌슦

      if (this.memberProc.isMember(session)) { // �쉶�썝�쑝濡� 濡쒓렇�씤
        // session�쓣 �궗�슜�븯�뿬 �쁽�옱 濡쒓렇�씤�븳 �궗�슜�옄�쓽 memberno 媛믩쭔 �씫�쓬�쑝濡� �떎瑜� �궗�슜�옄�쓽 
        // �젙蹂대�� 議고쉶 �븷 �닔 �뾾�쓬.
        memberno = (int)session.getAttribute("memberno");
        
      } else if (this.adminProc.isAdmin(session)) { // 愿�由ъ옄濡� 濡쒓렇�씤
        // 愿�由ъ옄�뒗 紐⑤뱺 �쉶�썝�쓽 �젙蹂대�� 議고쉶 �븷 �닔 �엳�뼱�빞�븿�쑝濡� parameter濡� �쉶�썝踰덊샇瑜� �씠�슜�븯�뿬 議고쉶
        memberno = Integer.parseInt(request.getParameter("memberno"));
        
      }

      MemberVO memberVO = this.memberProc.read(memberno);
      mav.addObject("memberVO", memberVO);
      mav.setViewName("/member/read"); // /member/read.jsp
      
    } else {
      // 濡쒓렇�씤�쓣 �븯吏� �븡�� 寃쎌슦
      mav.setViewName("/member/login_need"); // /webapp/WEB-INF/views/member/login_need.jsp
    }
    
    return mav; // forward
  }
  
  /**
   * �쉶�썝 �젙蹂� �닔�젙 泥섎━
   * @param memberVO
   * @return
   */
  @RequestMapping(value="/member/update.do", method=RequestMethod.POST)
  public ModelAndView update(MemberVO memberVO){
    ModelAndView mav = new ModelAndView();
    
    // System.out.println("id: " + memberVO.getId());
    
    int cnt= this.memberProc.update(memberVO);
    
    if (cnt == 1) {
      mav.addObject("code", "update_success");
      mav.addObject("mname", memberVO.getMname());  // �솉湲몃룞�떂(user4) �쉶�썝 �젙蹂대�� 蹂�寃쏀뻽�뒿�땲�떎.
      mav.addObject("id", memberVO.getId());
    } else {
      mav.addObject("code", "update_fail");
    }

    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
    mav.addObject("url", "/member/msg");  // /member/msg -> /member/msg.jsp
    
    mav.setViewName("redirect:/member/msg.do");
    
    return mav;
  }
  
  /**
   * �쉶�썝 �궘�젣
   * @param memberno
   * @return
   */
  @RequestMapping(value="/member/delete.do", method=RequestMethod.GET)
  public ModelAndView delete(HttpSession session,int memberno){
    ModelAndView mav = new ModelAndView();    
    MemberVO memberVO = this.memberProc.read(memberno); // �궘�젣�븷 �젅肄붾뱶瑜� �궗�슜�옄�뿉寃� 異쒕젰�븯湲곗쐞�빐 �씫�쓬.
    mav.addObject("memberVO", memberVO);
    session.setAttribute("delmno", memberno);
    mav.setViewName("/member/delete"); // /member/delete.jsp
    
    return mav; // forward
  }
 
  /**
   * �쉶�썝 �궘�젣 泥섎━
   * @param memberVO
   * @return
   */
  @RequestMapping(value="/member/delete.do", method=RequestMethod.POST)
  public ModelAndView delete_proc(HttpSession session){
    ModelAndView mav = new ModelAndView();   
    MemberVO memberVO = this.memberProc.read((int)session.getAttribute("delmno")); // �궘�젣�븷 �젅肄붾뱶瑜� �궗�슜�옄�뿉寃� 異쒕젰�븯湲곗쐞�빐 �씫�쓬.
    mav.addObject("memberVO", memberVO);
    int memberno = memberVO.getMemberno();
    String id = memberVO.getId();
    int grade = memberVO.getGrade();
    
    //탈퇴로그 
    withservice.createWithdrawRecord(memberno, id, grade);
    
    int cnt= this.memberProc.delete(memberno);
    

    if (cnt == 1) {
      mav.addObject("code", "delete_success_admin");
    } else {
      mav.addObject("code", "delete_fail");
    }

    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
    mav.addObject("url", "/member/msg");  // /member/msg -> /member/msg.jsp
    
    mav.setViewName("redirect:/member/msg.do");
    
    return mav;
  }

//  /**
//   * 濡쒓렇�씤 �뤌
//   * @return
//   */
//  // http://localhost:9091/member/login.do 
//  @RequestMapping(value = "/member/login.do", 
//                             method = RequestMethod.GET)
//  public ModelAndView login() {
//    ModelAndView mav = new ModelAndView();
//  
//    mav.setViewName("/member/login_form");
//    return mav;
//  }
//
//  /**
//   * 濡쒓렇�씤 泥섎━
//   * @return
//   */
//  // http://localhost:9091/member/login.do 
//  @RequestMapping(value = "/member/login.do", 
//                             method = RequestMethod.POST)
//  public ModelAndView login_proc(HttpSession session,
//                                                   String id, 
//                                                   String passwd) {
//    ModelAndView mav = new ModelAndView();
//    HashMap<String, Object> map = new HashMap<String, Object>();
//    map.put("id", id);
//    map.put("passwd", passwd);
//    
//    int count = this.memberProc.login(map); // id, passwd �씪移� �뿬遺� �솗�씤
//    if (count == 1) { // 濡쒓렇�씤 �꽦怨�
//      // System.out.println(id + " 濡쒓렇�씤 �꽦怨�");
//      MemberVO memberVO = memberProc.readById(id); // 濡쒓렇�씤�븳 �쉶�썝�쓽 �젙蹂� 議고쉶
//      session.setAttribute("memberno", memberVO.getMemberno());
//      session.setAttribute("id", id);
//      session.setAttribute("mname", memberVO.getMname());
//      session.setAttribute("grade", memberVO.getGrade());
//      
//      mav.setViewName("redirect:/index.do"); // �떆�옉 �럹�씠吏�濡� �씠�룞  
//    } else {
//      mav.addObject("url", "/member/login_fail_msg"); // login_fail_msg.jsp, redirect parameter �쟻�슜
//     
//      mav.setViewName("redirect:/member/msg.do"); // �깉濡쒓퀬移� 諛⑹�
//    }
//        
//    return mav;
//  }

  /**
   * 濡쒓렇�븘�썐 泥섎━
   * @param session
   * @return
   */
  @RequestMapping(value="/member/logout.do", 
                             method=RequestMethod.GET)
  public ModelAndView logout(HttpSession session){
    ModelAndView mav = new ModelAndView();
    session.invalidate(); // 紐⑤뱺 session 蹂��닔 �궘�젣
    
    mav.setViewName("redirect:/index.do"); 
    
    return mav;
  }

//  /**
//   * 濡쒓렇�씤 �뤌
//   * @return
//   */
//  // http://localhost:9091/member/login.do 
//  @RequestMapping(value = "/member/login.do", 
//                             method = RequestMethod.GET)
//  public ModelAndView login_cookie(HttpServletRequest request) {
//    ModelAndView mav = new ModelAndView();
//    
//    Cookie[] cookies = request.getCookies();
//    Cookie cookie = null;
//  
//    String ck_id = ""; // id ���옣
//    String ck_id_save = ""; // id ���옣 �뿬遺�瑜� 泥댄겕
//    String ck_passwd = ""; // passwd ���옣
//    String ck_passwd_save = ""; // passwd ���옣 �뿬遺�瑜� 泥댄겕
//  
//    if (cookies != null) { // 荑좏궎媛� 議댁옱�븳�떎硫�
//      for (int i=0; i < cookies.length; i++){
//        cookie = cookies[i]; // 荑좏궎 媛앹껜 異붿텧
//      
//        if (cookie.getName().equals("ck_id")){
//          ck_id = cookie.getValue(); 
//        }else if(cookie.getName().equals("ck_id_save")){
//          ck_id_save = cookie.getValue();  // Y, N
//        }else if (cookie.getName().equals("ck_passwd")){
//          ck_passwd = cookie.getValue();         // 1234
//        }else if(cookie.getName().equals("ck_passwd_save")){
//          ck_passwd_save = cookie.getValue();  // Y, N
//        }
//      }
//    }
//  
//    //    <input type='text' class="form-control" name='id' id='id' 
//    //            value='${ck_id }' required="required" 
//    //            style='width: 30%;' placeholder="�븘�씠�뵒" autofocus="autofocus">
//    mav.addObject("ck_id", ck_id);
//  
//    //    <input type='checkbox' name='id_save' value='Y' 
//    //            ${ck_id_save == 'Y' ? "checked='checked'" : "" }> ���옣
//    mav.addObject("ck_id_save", ck_id_save);
//  
//    mav.addObject("ck_passwd", ck_passwd);
//    mav.addObject("ck_passwd_save", ck_passwd_save);
//  
//    mav.setViewName("/member/login_form_ck"); // /member/login_form_ck.jsp
//    return mav;
//  }
  
  /**
   * Cookie 기반 로그인 폼
   * @param return_url 로그인 성공후 자동으로 이동할 주소
   * @return
   */
  // http://localhost:9093/member/login.do 
  @RequestMapping(value = "/member/login.do", 
                             method = RequestMethod.GET)
  public ModelAndView login_cookie(HttpServletRequest request,
                                                @RequestParam(value="return_url", defaultValue="") String return_url ) {
    ModelAndView mav = new ModelAndView();
    
    Cookie[] cookies = request.getCookies();
    Cookie cookie = null;

    String ck_id = ""; // id 저장
    String ck_id_save = ""; // id 저장 여부를 체크
    String ck_passwd = ""; // passwd 저장
    String ck_passwd_save = ""; // passwd 저장 여부를 체크

    if (cookies != null) {  // Cookie 변수가 있다면
      for (int i=0; i < cookies.length; i++){
        cookie = cookies[i]; // 쿠키 객체 추출
        
        if (cookie.getName().equals("ck_id")){
          ck_id = cookie.getValue();                                 // Cookie에 저장된 id
        }else if(cookie.getName().equals("ck_id_save")){
          ck_id_save = cookie.getValue();                          // Cookie에 id를 저장 할 것인지의 여부, Y, N
        }else if (cookie.getName().equals("ck_passwd")){
          ck_passwd = cookie.getValue();                          // Cookie에 저장된 password
        }else if(cookie.getName().equals("ck_passwd_save")){
          ck_passwd_save = cookie.getValue();                  // Cookie에 password를 저장 할 것인지의 여부, Y, N
        }
      }
    }
    
    mav.addObject("ck_id", ck_id); 
    mav.addObject("ck_id_save", ck_id_save);
    mav.addObject("ck_passwd", ck_passwd);
    mav.addObject("ck_passwd_save", ck_passwd_save);
    mav.addObject("return_url", return_url); // 로그인 성공후 자동으로 이동할 주소
    
    mav.setViewName("/member/login_form_ck");
    return mav;
  }
   
//  /**
//  * Cookie 湲곕컲 濡쒓렇�씤 泥섎━
//  * @param request Cookie瑜� �씫湲곗쐞�빐 �븘�슂
//  * @param response Cookie瑜� �벐湲곗쐞�빐 �븘�슂
//  * @param session 濡쒓렇�씤 �젙蹂대�� 硫붾え由ъ뿉 湲곕줉
//  * @param id  �쉶�썝 �븘�씠�뵒
//  * @param passwd �쉶�썝 �뙣�뒪�썙�뱶
//  * @param id_save �쉶�썝 �븘�씠�뵒 Cookie�뿉 ���옣 �뿬遺�
//  * @param passwd_save �뙣�뒪�썙�뱶 Cookie�뿉 ���옣 �뿬遺�
//  * @return
//  */
//  // http://localhost:9091/member/login.do 
//  @RequestMapping(value = "/member/login.do", 
//                            method = RequestMethod.POST)
//  public ModelAndView login_cookie_proc(
//                            HttpServletRequest request,
//                            HttpServletResponse response,
//                            HttpSession session,
//                            String id,
//                            String passwd,                            
//                            @RequestParam(value="id_save", defaultValue="") String id_save,
//                            @RequestParam(value="passwd_save", defaultValue="") String passwd_save) {
//    ModelAndView mav = new ModelAndView();
//    HashMap<String, Object> map = new HashMap<String, Object>();
//  
//    // client ip
//    String memberip=request.getRemoteAddr();
//    //System.out.println("-> ip: " + ip);
//    map.put("id", id);
//    map.put("passwd", passwd);
//    int cnt = memberProc.login(map);
//    MemberVO memberVO = memberProc.readById(id);
//    
//    if (cnt == 1 && memberVO.getGrade() < 20) { // 濡쒓렇�씤 �꽦怨�
//      // System.out.println(id + " 濡쒓렇�씤 �꽦怨�");
//      
//      session.setAttribute("memberno", memberVO.getMemberno()); // �꽌踰꾩쓽 硫붾え由ъ뿉 湲곕줉
//      session.setAttribute("id", id);
//      session.setAttribute("mname", memberVO.getMname());
//      session.setAttribute("grade", memberVO.getGrade());
//      int mno =memberVO.getMemberno();
//      
//   
//      // -------------------------------------------------------------------
//      // id 愿��젴 荑좉린 ���옣
//      // -------------------------------------------------------------------
//      if (id_save.equals("Y")) { // id瑜� ���옣�븷 寃쎌슦, Checkbox瑜� 泥댄겕�븳 寃쎌슦
//        Cookie ck_id = new Cookie("ck_id", id);
//        ck_id.setPath("/");  // root �뤃�뜑�뿉 荑좏궎瑜� 湲곕줉�븿�쑝濡� 紐⑤뱺 寃쎈줈�뿉�꽌 荑좉린 �젒洹� 媛��뒫
//        ck_id.setMaxAge(60 * 60 * 24 * 30); // 30 day, 珥덈떒�쐞
//        response.addCookie(ck_id); // id ���옣
//      } else { // N, id瑜� ���옣�븯吏� �븡�뒗 寃쎌슦, Checkbox瑜� 泥댄겕 �빐�젣�븳 寃쎌슦
//        Cookie ck_id = new Cookie("ck_id", "");
//        ck_id.setPath("/");
//        ck_id.setMaxAge(0);
//        response.addCookie(ck_id); // id ���옣
//      }
//      
//      // id瑜� ���옣�븷吏� �꽑�깮�븯�뒗  CheckBox 泥댄겕 �뿬遺�
//      Cookie ck_id_save = new Cookie("ck_id_save", id_save);
//      ck_id_save.setPath("/");
//      ck_id_save.setMaxAge(60 * 60 * 24 * 30); // 30 day
//      response.addCookie(ck_id_save);
//      // -------------------------------------------------------------------
//  
//      // -------------------------------------------------------------------
//      // Password 愿��젴 荑좉린 ���옣
//      // -------------------------------------------------------------------
//      if (passwd_save.equals("Y")) { // �뙣�뒪�썙�뱶 ���옣�븷 寃쎌슦
//        Cookie ck_passwd = new Cookie("ck_passwd", passwd);
//        ck_passwd.setPath("/");
//        ck_passwd.setMaxAge(60 * 60 * 24 * 30); // 30 day
//        response.addCookie(ck_passwd);
//      } else { // N, �뙣�뒪�썙�뱶瑜� ���옣�븯吏� �븡�쓣 寃쎌슦
//        Cookie ck_passwd = new Cookie("ck_passwd", "");
//        ck_passwd.setPath("/");
//        ck_passwd.setMaxAge(0);
//        response.addCookie(ck_passwd);
//      }
//      // passwd瑜� ���옣�븷吏� �꽑�깮�븯�뒗  CheckBox 泥댄겕 �뿬遺�
//      Cookie ck_passwd_save = new Cookie("ck_passwd_save", passwd_save);
//      ck_passwd_save.setPath("/");
//      ck_passwd_save.setMaxAge(60 * 60 * 24 * 30); // 30 day
//      response.addCookie(ck_passwd_save);
//      // -------------------------------------------------------------------
//      
//      //로그인 관련
//      loginService.createLoginRecord(mno, memberip);
//      
//      mav.setViewName("redirect:/index.do");  
//    } else {
//      /*
//       * if(memberVO.getGrade() == 99) { mav.addObject("code", "deletemember_msg");
//       * mav.setViewName("redirect:/member/msg.do"); }else { }
//       */     
//        mav.addObject("url", "/member/login_fail_msg");
//        mav.setViewName("redirect:/member/msg.do"); 
//         
//    }
//       
//    return mav;
//  }
  /**
   * Cookie 기반 로그인 처리
   * @param request Cookie를 읽기위해 필요
   * @param response Cookie를 쓰기위해 필요
   * @param session 로그인 정보를 메모리에 기록
   * @param id  회원 아이디
   * @param passwd 회원 패스워드
   * @param id_save 회원 아이디 Cookie에 저장 여부
   * @param passwd_save 패스워드 Cookie에 저장 여부
   * @param return_url 로그인 성공후 자동으로 이동할 주소 
   * @return
   */
  // http://localhost:9091/member/login.do 
  @RequestMapping(value = "/member/login.do", 
                             method = RequestMethod.POST)
  public ModelAndView login_cookie_proc(
                             HttpServletRequest request,
                             HttpServletResponse response,
                             HttpSession session,
                             String id, String passwd,
                             @RequestParam(value="id_save", defaultValue="") String id_save,
                             @RequestParam(value="passwd_save", defaultValue="") String passwd_save,
                             @RequestParam(value="return_url", defaultValue="") String return_url) {
    ModelAndView mav = new ModelAndView();
    HashMap<String, Object> map = new HashMap<String, Object>();
    // client ip
    String memberip=request.getRemoteAddr();
    map.put("id", id);
    map.put("passwd", passwd);
    
    int count = memberProc.login(map);
    if (count == 1) { // 로그인 성공
      // System.out.println(id + " 로그인 성공");
      MemberVO memberVO = memberProc.readById(id);
      session.setAttribute("memberno", memberVO.getMemberno()); // 서버의 메모리에 기록
      session.setAttribute("id", id);
      session.setAttribute("mname", memberVO.getMname());
      session.setAttribute("grade", memberVO.getGrade());
      int mno =memberVO.getMemberno();
      // -------------------------------------------------------------------
      // id 관련 쿠기 저장
      // -------------------------------------------------------------------
      if (id_save.equals("Y")) { // id를 저장할 경우, Checkbox를 체크한 경우
        Cookie ck_id = new Cookie("ck_id", id);
        ck_id.setPath("/");  // root 폴더에 쿠키를 기록함으로 모든 경로에서 쿠기 접근 가능
        ck_id.setMaxAge(60 * 60 * 72 * 10); // 30 day, 초단위
        response.addCookie(ck_id); // id 저장
      } else { // N, id를 저장하지 않는 경우, Checkbox를 체크 해제한 경우
        Cookie ck_id = new Cookie("ck_id", "");
        ck_id.setPath("/");
        ck_id.setMaxAge(0);
        response.addCookie(ck_id); // id 저장
      }
      // id를 저장할지 선택하는  CheckBox 체크 여부
      Cookie ck_id_save = new Cookie("ck_id_save", id_save);
      ck_id_save.setPath("/");
      ck_id_save.setMaxAge(60 * 60 * 72 * 10); // 30 day
      response.addCookie(ck_id_save);
      // -------------------------------------------------------------------

      // -------------------------------------------------------------------
      // Password 관련 쿠기 저장
      // -------------------------------------------------------------------
      if (passwd_save.equals("Y")) { // 패스워드 저장할 경우
        Cookie ck_passwd = new Cookie("ck_passwd", passwd);
        ck_passwd.setPath("/");
        ck_passwd.setMaxAge(60 * 60 * 72 * 10); // 30 day
        response.addCookie(ck_passwd);
      } else { // N, 패스워드를 저장하지 않을 경우
        Cookie ck_passwd = new Cookie("ck_passwd", "");
        ck_passwd.setPath("/");
        ck_passwd.setMaxAge(0);
        response.addCookie(ck_passwd);
      }
      // passwd를 저장할지 선택하는  CheckBox 체크 여부
      Cookie ck_passwd_save = new Cookie("ck_passwd_save", passwd_save);
      ck_passwd_save.setPath("/");
      ck_passwd_save.setMaxAge(60 * 60 * 72 * 10); // 30 day
      response.addCookie(ck_passwd_save);
      // -------------------------------------------------------------------
      loginService.createLoginRecord(mno, memberip);
      
      System.out.println("-> return_url: " + return_url);
      
      if (return_url.length() > 0) { // ★ 로그인 성공후 자동으로 이동할 주소
        mav.setViewName("redirect:" + return_url);  
      } else {
        mav.setViewName("redirect:/index.do");
      }
        
    } else {
      mav.addObject("url", "login_fail_msg");
      mav.setViewName("redirect:/member/msg.do"); 
    }
        
    return mav;
  }

      
  /**
   * �뙣�뒪�썙�뱶瑜� 蹂�寃쏀빀�땲�떎.
   * http://localhost:9091/member/passwd_update.do
   * @param memberno
   * @return
   */
  @RequestMapping(value="/member/passwd_update.do", method=RequestMethod.GET)
  public ModelAndView passwd_update(){
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/member/passwd_update"); // passwd_update.jsp
    
    return mav;
  }
  
  /**
   * �뙣�뒪�썙�뱶 寃��궗 
   * 濡쒓렇�씤 �떎�뻾 -> http://localhost:9091/member/passwd_check.do?current_passwd=1234
   * @param session
   * @param current_passwd �쁽�옱 �뙣�뒪�썙�뱶
   * @return 1: �씪移�, 0: 遺덉씪移�
   */
  @ResponseBody
  @RequestMapping(value="/member/passwd_check.do", method=RequestMethod.GET)
  public String passwd_check(HttpSession session, String current_passwd) {
    try {
      Thread.sleep(3000); // 3珥�
    } catch(Exception e) {

    }
    
    int memberno = (int)session.getAttribute("memberno");
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("memberno", memberno);
    map.put("passwd", current_passwd);
    int cnt = this.memberProc.passwd_check(map);
    
    JSONObject json = new JSONObject();
    json.put("cnt", cnt);
    
    return json.toString();
  }
  
  /**
   * �뙣�뒪�썙�뱶 蹂�寃� 泥섎━
   * @param memberno �쉶�썝 踰덊샇
   * @param current_passwd �쁽�옱 �뙣�뒪�썙�뱶
   * @param new_passwd �깉濡쒖슫 �뙣�뒪�썙�뱶
   * @return
   */
  @RequestMapping(value="/member/passwd_update.do", method=RequestMethod.POST)
  public ModelAndView passwd_update(HttpSession session, String current_passwd, String new_passwd){
    ModelAndView mav = new ModelAndView();

    // int memberno = 3; // �뀒�뒪�듃
    int memberno = (int)session.getAttribute("memberno"); // �쁽�옱 濡쒓렇�씤�븳 �쉶�썝�쓽 �젙蹂대쭔 �뙣�뒪�썙�뱶 蹂�寃� 媛��뒫
    
    MemberVO memberVO = this.memberProc.read(memberno); // �뙣�뒪�썙�뱶瑜� 蹂�寃쏀븯�젮�뒗 �쉶�썝 �젙蹂대�� �씫�쓬
    mav.addObject("mname", memberVO.getMname());  
    mav.addObject("id", memberVO.getId());
    
    // �쁽�옱 �뙣�뒪�썙�뱶 寃��궗�슜 �뜲�씠�꽣
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("memberno", memberno);
    map.put("passwd", current_passwd);
    
    int cnt = memberProc.passwd_check(map); // �쁽�옱 �뙣�뒪�썙�뱶 寃��궗
    int update_cnt = 0; // 蹂�寃쎈맂 �뙣�뒪�썙�뱶 �닔
    
    if (cnt == 1) { // �쁽�옱 �뙣�뒪�썙�뱶媛� �씪移섑븯�뒗 寃쎌슦
      map.put("passwd", new_passwd); // �깉濡쒖슫 �뙣�뒪�썙�뱶瑜� ���옣
      update_cnt = this.memberProc.passwd_update(map); // �뙣�뒪�썙�뱶 蹂�寃� 泥섎━
      
      if (update_cnt == 1) {
        mav.addObject("code", "passwd_update_success"); // �뙣�뒪�썙�뱶 蹂�寃� �꽦怨�
      } else {
        cnt = 0;  // �뙣�뒪�썙�뱶�뒗 �씪移섑뻽�쑝�굹 蹂�寃쏀븯吏��뒗 紐삵븿.
        mav.addObject("code", "passwd_update_fail");       // �뙣�뒪�썙�뱶 蹂�寃� �떎�뙣
      }
      
      mav.addObject("update_cnt", update_cnt);  // 蹂�寃쎈맂 �뙣�뒪�썙�뱶�쓽 媛��닔    
    } else {
      mav.addObject("code", "passwd_fail"); // �뙣�뒪�썙�뱶媛� �씪移섑븯吏� �븡�뒗 寃쎌슦
    }

    mav.addObject("cnt", cnt); // �뙣�뒪�썙�뱶 �씪移� �뿬遺�
    mav.addObject("url", "/member/msg");  // /member/msg -> /member/msg.jsp
    
    mav.setViewName("redirect:/member/msg.do");
    
    return mav;
  }
  
  @RequestMapping(value="/member/findid.do", method=RequestMethod.GET)
  public ModelAndView findid(){
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/member/findid"); 
    
    return mav;
  }
  
  @RequestMapping(value="/member/findid.do", method=RequestMethod.POST)
  public ModelAndView findid(HttpSession session, String name, String tel){
    ModelAndView mav = new ModelAndView();

  
    //int memberno = (int)session.getAttribute("memberno");
    
    //MemberVO memberVO = this.memberProc.read(memberno);
    //mav.addObject("mname", memberVO.getMname());  
    //mav.addObject("id", memberVO.getId());
    
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("name", name);
    map.put("tel", tel);    
    int cnt = memberProc.findid(map);
    MemberVO memberVO = memberProc.readBytel(tel);
    if (cnt == 1) {             
      session.setAttribute("memberno", memberVO.getMemberno()); 
      session.setAttribute("mname", name);
      //id라하면 로그인됨
      session.setAttribute("fid", memberVO.getId());
      String userid = memberVO.getId();
      
      mav.addObject("code", "findid_success"); 
      mav.addObject("userid", userid); 
      
      
           
    } else {
      mav.addObject("code", "findid_fail"); 
    }

    mav.addObject("cnt", cnt); 
    mav.addObject("url", "/member/msg");  
    
    Random random = new Random();
    StringBuilder verify_code = new StringBuilder();
    for (int i = 0; i < 6; i++) {
      verify_code.append(random.nextInt(10));
    }   
    session.setAttribute("verify_code", verify_code);
    
    //2줄 메일 로그 관련 
    String actname = "Find ID /" + verify_code ;   
   mailService.createMailRecord(memberVO.getMemberno(), memberVO.getId(), actname);
    
    MailTool mailTool = new MailTool();
    String receiver = "zazang0503@naver.com";
    String from = "zazang5@gmail.com";
    String title = "[술기운] 아이디 찾기 확인 메일 : "+verify_code ;
    String content = " <div style=\"text-align: left;\">\r\n"
        + "    <b><u>[술기운] 아이디 찾기 안내</u><b><br>\r\n"
        + "    <ul>\r\n"
        + "        <li>인증번호 : "+verify_code+"</li>\r\n"      
        + "    </ul>\r\n"   
        + " </div>";
    
    mailTool.send(receiver, from, title, content); // 메일 전송  
    
    
    
    mav.setViewName("redirect:/member/msg.do");
    
    return mav;
  }
  
  @RequestMapping(value="/member/findpwd.do", method=RequestMethod.GET)
  public ModelAndView findpwd(){
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/member/findpwd"); 
    
    return mav;
  }
  
  @RequestMapping(value="/member/findpwd.do", method=RequestMethod.POST)
  public ModelAndView findpwd(HttpSession session, String pid, String name){
    ModelAndView mav = new ModelAndView();

  
    //int memberno = (int)session.getAttribute("memberno");
    
    //MemberVO memberVO = this.memberProc.read(memberno);
    //mav.addObject("mname", memberVO.getMname());  
    //mav.addObject("id", memberVO.getId());
    
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("id", pid);
    map.put("name", name);    
    int cnt = memberProc.findpwd(map);
    MemberVO memberVO = memberProc.readById(pid);
    if (cnt == 1) {          
      session.setAttribute("memberno", memberVO.getMemberno()); 
      session.setAttribute("mname", name);
      //id라하면 로그인됨
      session.setAttribute("fid", memberVO.getId());
      String userid = memberVO.getId();
      
      mav.addObject("code", "findpwd_success"); 
      mav.addObject("userid", userid); 
      
           
    } else {
      mav.addObject("code", "findid_fail"); 
    }

    mav.addObject("cnt", cnt); 
    mav.addObject("url", "/member/msg");  
   
    Random random = new Random();
    StringBuilder verifyCode = new StringBuilder();
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // 영어 대문자와 숫자를 포함한 문자열

    for (int i = 0; i < 8; i++) {
        int index = random.nextInt(characters.length());
        verifyCode.append(characters.charAt(index));
    }

    String generatedCode = verifyCode.toString();
    session.setAttribute("verify_code", generatedCode);
    
    //2줄 메일 로그 관련
    String actname = "Find PWD /" + generatedCode ;
    mailService.createMailRecord(memberVO.getMemberno(), memberVO.getId(), actname);
    
    MailTool mailTool = new MailTool();
    String receiver = "zazang0503@naver.com";
    String from = "zazang5@gmail.com";
    String title = "[술기운] 비밀번호 재 설정 메일"; 
    String content = " <div style=\"text-align: left;\">\r\n"
        + "    <b><u>[술기운] 비밀번호 재 설정 안내</u><b><br>\r\n"
        + "    <ul>\r\n"
        + "        <li>새로운 임시 비밀번호 : "+generatedCode+"</li>\r\n"      
        + "    </ul>\r\n"   
        + " </div>";
    
    mailTool.send(receiver, from, title, content); // 메일 전송
    
    HashMap<String, Object> map2 = new HashMap<String, Object>();
    map2.put("memberno", memberVO.getMemberno());
    map2.put("passwd", generatedCode);
    this.memberProc.passwd_update(map2); 
    
    
    mav.setViewName("redirect:/member/msg.do");
    
    return mav;
  }
  
  /**
   * Ajax 기반 회원 조회
   * http://localhost:9091/member/read_ajax.do
   * @param memberno
   * @return
   */
  @RequestMapping(value="/member/read_ajax.do", method=RequestMethod.GET)
  @ResponseBody
  public String read_ajax(HttpSession session){
    int memberno = (int)session.getAttribute("memberno");
    
    MemberVO memberVO = this.memberProc.read(memberno);
    
    JSONObject json = new JSONObject();
    json.put("rname", memberVO.getMname());
    json.put("rtel", memberVO.getTel());
    json.put("rzipcode", memberVO.getZipcode());
    json.put("raddress1", memberVO.getAddress1());
    json.put("raddress2", memberVO.getAddress2());
    
    return json.toString();
  }
  
  /**
   * Cookie + Ajax 기반 로그인 처리
   * @param request Cookie를 읽기위해 필요
   * @param response Cookie를 쓰기위해 필요
   * @param session 로그인 정보를 메모리에 기록
   * @param id  회원 아이디
   * @param passwd 회원 패스워드
   * @param id_save 회원 아이디 Cookie에 저장 여부
   * @param passwd_save 패스워드 Cookie에 저장 여부
   * @return
   */
  // http://localhost:9091/member/login_ajax.do 
  @RequestMapping(value = "/member/login_ajax.do", 
                             method = RequestMethod.POST)
  @ResponseBody
  public String login_cookie_proc_ajax (
                             HttpServletRequest request,
                             HttpServletResponse response,
                             HttpSession session,
                             String id, String passwd,
                             @RequestParam(value="id_save", defaultValue="") String id_save,
                             @RequestParam(value="passwd_save", defaultValue="") String passwd_save) {

    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("id", id);
    map.put("passwd", passwd);
    
    int count = memberProc.login(map);
    if (count == 1) { // 로그인 성공
      // System.out.println(id + " 로그인 성공");
      MemberVO memberVO = memberProc.readById(id);
      session.setAttribute("memberno", memberVO.getMemberno()); // 서버의 메모리에 기록
      session.setAttribute("id", id);
      session.setAttribute("mname", memberVO.getMname());
      session.setAttribute("grade", memberVO.getGrade());
      
      // -------------------------------------------------------------------
      // id 관련 쿠기 저장
      // -------------------------------------------------------------------
      if (id_save.equals("Y")) { // id를 저장할 경우, Checkbox를 체크한 경우
        Cookie ck_id = new Cookie("ck_id", id);
        ck_id.setPath("/");  // root 폴더에 쿠키를 기록함으로 모든 경로에서 쿠기 접근 가능
        ck_id.setMaxAge(60 * 60 * 24 * 30); // 30 day, 초단위
        response.addCookie(ck_id); // id 저장
      } else { // N, id를 저장하지 않는 경우, Checkbox를 체크 해제한 경우
        Cookie ck_id = new Cookie("ck_id", "");
        ck_id.setPath("/");  // root 폴더에 쿠키를 기록함으로 모든 경로에서 쿠기 접근 가능
        ck_id.setMaxAge(0);
        response.addCookie(ck_id); // id 저장
      }
      // id를 저장할지 선택하는  CheckBox 체크 여부
      Cookie ck_id_save = new Cookie("ck_id_save", id_save);
      ck_id_save.setPath("/");  // root 폴더에 쿠키를 기록함으로 모든 경로에서 쿠기 접근 가능
      ck_id_save.setMaxAge(60 * 60 * 24 * 30); // 30 day
      response.addCookie(ck_id_save);
      // -------------------------------------------------------------------

      // -------------------------------------------------------------------
      // Password 관련 쿠기 저장
      // -------------------------------------------------------------------
      if (passwd_save.equals("Y")) { // 패스워드 저장할 경우
        Cookie ck_passwd = new Cookie("ck_passwd", passwd);
        ck_passwd.setPath("/");  // root 폴더에 쿠키를 기록함으로 모든 경로에서 쿠기 접근 가능
        ck_passwd.setMaxAge(60 * 60 * 24 * 30); // 30 day
        response.addCookie(ck_passwd);
      } else { // N, 패스워드를 저장하지 않을 경우
        Cookie ck_passwd = new Cookie("ck_passwd", "");
        ck_passwd.setPath("/");  // root 폴더에 쿠키를 기록함으로 모든 경로에서 쿠기 접근 가능
        ck_passwd.setMaxAge(0);
        response.addCookie(ck_passwd);
      }
      // passwd를 저장할지 선택하는  CheckBox 체크 여부
      Cookie ck_passwd_save = new Cookie("ck_passwd_save", passwd_save);
      ck_passwd_save.setPath("/");  // root 폴더에 쿠키를 기록함으로 모든 경로에서 쿠기 접근 가능
      ck_passwd_save.setMaxAge(60 * 60 * 24 * 30); // 30 day
      response.addCookie(ck_passwd_save);
      // -------------------------------------------------------------------
      
    }
    
    int cnt = count;
    
    JSONObject json = new JSONObject();
    json.put("cnt", cnt);
   
    return json.toString(); 
  }
  
  
}

