package dev.mvc.chatting;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.chatting.ChattingVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.chatting.ChattingProcInter;
import dev.mvc.tool.Tool;

@Controller
public class ChattingCont {
	  @Autowired
	  @Qualifier("dev.mvc.chatting.ChattingProc") // @Component("dev.mvc.chatting.ChattingProc")
	  private ChattingProcInter chattingProc;
	  
	  @Autowired
	  @Qualifier("dev.mvc.member.MemberProc")
	  private MemberProcInter memberProc;
	  
	  
	  public ChattingCont() {
	    System.out.println("-> ChattingCont created.");
	  }
	  
	  // form 출력, http://localhost:9093/chatting/create.do
	  @RequestMapping(value="/chatting/create.do", method = RequestMethod.GET)
	  public ModelAndView create() {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("/chatting/create"); // /WEB-INF/views/chatting/create.jsp
	    
	    return mav;
	  }
	   
	  // form 데이터 처리, http://localhost:9093/chatting/create.do
	  @RequestMapping(value="/chatting/create.do", method = RequestMethod.POST)
	  public ModelAndView create(ChattingVO chattingVO) { // 占쎌쁽占쎈짗占쎌몵嚥∽옙 cateVO 揶쏆빘猿쒎첎占� 占쎄문占쎄쉐占쎈┷�⑨옙 占쎈쨲占쎌벥 揶쏅�れ뵠 占쎈막占쎈뼣占쎈쭡
		  System.out.println("-> create");
		  ModelAndView mav = new ModelAndView();
		   
		  int cnt = this.chattingProc.create(chattingVO);
		  System.out.println("-> cnt: " + cnt);
		   
		  if (cnt == 1) {     
		    mav.setViewName("redirect:/chatting/list_all.do");
		     
		  } else {
		    mav.addObject("code", "create_fail");
		    mav.setViewName("/chatting/msg");
		  }
		   
		  mav.addObject("cnt", cnt);
		   
		  return mav;
		  }
	   
	   @RequestMapping(value="/chatting/msg.do", method=RequestMethod.GET)
	   public ModelAndView msg(String url){
	     ModelAndView mav = new ModelAndView();

	     mav.setViewName(url); // forward
	     
	     return mav; // forward
	   }
	   
	   /**
	    * 전체 목록
	    *  http://localhost:9093/chatting/list_all.do
	    * 
	    * @return
	    */
	   @RequestMapping(value="/chatting/list_all.do", method = RequestMethod.GET)
	   public ModelAndView list_all(HttpSession session) {
	       System.out.println("-> list_all");
		   ModelAndView mav = new ModelAndView();
		   mav.setViewName("/chatting/list_all"); //WEB-INF/views/chatting/list_all.jsp
	     
		     if (this.memberProc.isMember(session) == true) {
		       mav.setViewName("/chatting/list_all"); // /WEB-INF/views/chatting/list_all.jsp
		       
		       ArrayList<ChattingVO> list = this.chattingProc.list_all();
		       mav.addObject("list", list);
		       
		     } else {
		       mav.setViewName("/member/login_need"); // /WEB-INF/views/manager/login_need.jsp
		       
		     }
		     
		     return mav;
		   }
	   
	   /**
	    * 조회
	    * http://localhost:9093/chatting/read.do?chattingno=1
	    * @return
	    */
	   @RequestMapping(value="/chatting/read.do", method = RequestMethod.GET)
	   public ModelAndView read(int chattingno) { 
	     System.out.println("-> read");
	     ModelAndView mav = new ModelAndView();
	     mav.setViewName("/chatting/read");
	     
	     ChattingVO chattingVO = this.chattingProc.read(chattingno);
	     System.out.println("-> done");
	     
	     mav.addObject("chattingVO", chattingVO);
	     
	     return mav;
	   }
	   
	   /**
	    * 수정폼
	    * http://localhost:9093/chatting/update.do?chattingno=1
	    * @return
	    */
	   @RequestMapping(value="/chatting/update.do", method = RequestMethod.GET)
	   public ModelAndView update(HttpSession session, int chattingno) {  //int chatbotno = (int)request.getParameter("chatbotno");
	     System.out.println("->update");
	     ModelAndView mav = new ModelAndView();
	     
	     if(this.memberProc.isMember(session) == true) {
	     System.out.println("-> member");

	     ChattingVO chattingVO = this.chattingProc.read(chattingno);
	     mav.addObject("chattingVO", chattingVO);
	     mav.setViewName("/chatting/update"); // /WEB-INF/views/member/update.jsp
	     
	     }else {
	       mav.setViewName("/member/login_need");
	     }
	     
	     return mav;
	   }
	   

	// 삭제폼
	   @RequestMapping(value="/chatting/delete.do", method = RequestMethod.GET)
	   public ModelAndView delete(HttpSession session , int chattingno) { 
	     ModelAndView mav = new ModelAndView();
	     if (this.memberProc.isMember(session) == true) {
	       mav.setViewName("/chatting/delete");
	         
	       ChattingVO chattingVO = this.chattingProc.read(chattingno);
	       mav.addObject("chattingVO", chattingVO);
	         
	       ArrayList<ChattingVO> list = this.chattingProc.list_all();
	       mav.addObject("list", list);
	     } else {
	       mav.setViewName("/member/login_need"); 
	     }
	  
	     return mav;
	   }
	   
	   // 삭제 처리
	   @RequestMapping(value="/chatting/delete.do", method=RequestMethod.POST)
	   public ModelAndView delete_proc(HttpSession session, int chattingno) { // <form> 태그의 값이 자동으로 저장됨
	     ModelAndView mav = new ModelAndView();
	     
	     if (this.memberProc.isMember(session) == true) {
	       int cnt = this.chattingProc.delete(chattingno); // 카테고리 삭제
	       
	       if (cnt == 1) {
	         mav.setViewName("redirect:/chatting/list_all.do");       // 자동 주소 이동, Spring 재호출 
	       } else {
	         mav.addObject("code", "delete_fail");
	         mav.setViewName("/chatting/msg"); // /WEB-INF/views/genre/msg.jsp
	       }
	       mav.addObject("cnt", cnt);   
	     } else {
	       mav.setViewName("/member/login_need"); // /WEB-INF/views/manager/login_need.jsp
	     }
	     
	     return mav;
	   }
	   
	 }
		   
	 