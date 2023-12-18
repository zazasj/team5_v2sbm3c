package dev.mvc.review;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.member.MemberProcInter;

@Controller
public class ReviewCont {
  
  @Autowired
  @Qualifier("dev.mvc.review.ReviewProc")
  private ReviewProcInter reviewProc;
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc;
  
  
  public ReviewCont(){
    System.out.println("--> ReplyCont created.");
  }
  @ResponseBody
  @RequestMapping(value = "/review/create.do",
                            method = RequestMethod.POST,
                            produces = "text/plain;charset=UTF-8")
  public String create(ReviewVO reviewVO) {
    int cnt = reviewProc.create(reviewVO);
    
    JSONObject obj = new JSONObject();
    obj.put("cnt",cnt);
 
    return obj.toString(); // {"cnt":1}
  }
  
  @RequestMapping(value="/review/list.do", method=RequestMethod.GET)
  public ModelAndView list(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) {
      List<ReviewVO> list = reviewProc.list();
      
      mav.addObject("list", list);
      mav.setViewName("/review/list"); // /webapp/review/list.jsp

    } else {
      mav.addObject("return_url", "/review/list.do"); // 로그인 후 이동할 주소 ★
      
      mav.setViewName("redirect:/admin/login.do"); // /WEB-INF/views/member/login_ck_form.jsp
    }
    
    return mav;
  }
  

  /**
   <xmp>   
   글이 없는 경우: {"list":[]}
   글이 있는 경우
   {"list":[
            {"memberno":1,"rdate":"2019-12-18 16:46:43","passwd":"123","replyno":3,"content":"댓글 3","contentsno":1}
            ,
            {"memberno":1,"rdate":"2019-12-18 16:46:39","passwd":"123","replyno":2,"content":"댓글 2","contentsno":1}
            ,
            {"memberno":1,"rdate":"2019-12-18 16:46:35","passwd":"123","replyno":1,"content":"댓글 1","contentsno":1}
            ] 
   }
   </xmp>  
   * @param contentsno
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/review/list_by_productid.do",
                            method = RequestMethod.GET,
                            produces = "text/plain;charset=UTF-8")
  public String list_by_productid(int productid) {
    List<ReviewVO> list = reviewProc.list_by_productid(productid);
    
    JSONObject obj = new JSONObject();
    obj.put("list", list);
 
    return obj.toString(); 

  }
  
  @ResponseBody
  @RequestMapping(value = "/review/list_memberno.do",
                            method = RequestMethod.GET,
                            produces = "text/plain;charset=UTF-8")
  public String list_memberno(int memberno) {
    List<ReviewVO> list = reviewProc.list_memberno(memberno);
    
    JSONObject obj = new JSONObject();
    obj.put("list", list);
 
    return obj.toString(); 

  }
  
  /**
   * {"list":[
          {"memberno":1,
        "rdate":"2019-12-18 16:46:35",
      "passwd":"123",
      "replyno":1,
      "id":"user1",
      "content":"댓글 1",
      "contentsno":1}
    ,
        {"memberno":1,
       "rdate":"2019-12-18 16:46:35",
       "passwd":"123",
       "replyno":1,
       "id":"user1",
       "content":"댓글 1",
       "contentsno":1}
    ]
}

/**
   * 회원만 목록 확인 가능
   * @param session
   * @return
   */
  @RequestMapping(value="/review/list_join.do", method=RequestMethod.GET)
  public ModelAndView list_join(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (memberProc.isMember(session)) {
      int memberno = (int)session.getAttribute("memberno");
      List<ReviewMemberVO> list = reviewProc.list_member_join(memberno);
      
      mav.addObject("list", list);
      mav.setViewName("/review/list_join"); // /webapp/review/list_join.jsp

    } else {
      mav.addObject("return_url", "/review/list_join.do"); // 로그인 후 이동할 주소 ★
      
      mav.setViewName("redirect:/member/login.do"); // /WEB-INF/views/member/login_ck_form.jsp
    }
    
    return mav;
  }
  
  @ResponseBody
  @RequestMapping(value = "/review/list_by_productid_join.do",
                              method = RequestMethod.GET,
                              produces = "text/plain;charset=UTF-8")
  public String list_by_productid_join(int productid) {    
    
    List<ReviewMemberVO> list = reviewProc.list_by_productid_join(productid);
    JSONObject obj = new JSONObject();
    obj.put("list", list);
 
    return obj.toString();     
  }
  
  @ResponseBody
  @RequestMapping(value = "/review/list_by_productid_join_add.do",
                              method = RequestMethod.GET,
                              produces = "text/plain;charset=UTF-8")
  public String list_by_productid_join_add(int productid) {    
    
    List<ReviewMemberVO> list = reviewProc.list_by_productid_join_add(productid);
    JSONObject obj = new JSONObject();
    obj.put("list", list);
 
    return obj.toString();     
  }
  @ResponseBody
  @RequestMapping(value = "/review/delete.do", 
                              method = RequestMethod.POST,
                              produces = "text/plain;charset=UTF-8")
  public String delete(int reviewno, String memberno) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("reviewno", reviewno);
    map.put("memberno", memberno);
    
    int passwd_cnt = reviewProc.checkPasswd(map); // 패스워드 일치 여부, 1: 일치, 0: 불일치
    int delete_cnt = 0;                                    // 삭제된 댓글
    if (passwd_cnt == 1) { // 패스워드가 일치할 경우
      delete_cnt = reviewProc.delete(reviewno); // 댓글 삭제
    }
    
    JSONObject obj = new JSONObject();
    obj.put("passwd_cnt", passwd_cnt); // 패스워드 일치 여부, 1: 일치, 0: 불일치
    obj.put("delete_cnt", delete_cnt); // 삭제된 댓글

    return obj.toString();
  }
  


  
  
}
