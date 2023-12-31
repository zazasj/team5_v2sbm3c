package dev.mvc.chatting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.chatting.ChattingVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.supplier.SupplierVO;
import dev.mvc.admin.AdminProcInter;
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
	  
	  @Autowired
	  @Qualifier("dev.mvc.admin.AdminProc") // @Component("dev.mvc.admin.AdminProc")
	  private AdminProcInter adminProc;
	  
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
	     if (adminProc.isAdmin(session) == true) {
	       mav.setViewName("/chatting/delete");
	         
	       ChattingVO chattingVO = this.chattingProc.read(chattingno);
	       mav.addObject("chattingVO", chattingVO);
	         
	       ArrayList<ChattingVO> list = this.chattingProc.list_all();
	       mav.addObject("list", list);
	     } else {
	       mav.setViewName("/admin/login_need"); 
	     }
	  
	     return mav;
	   }
	   
	   // 삭제 처리
	   @RequestMapping(value="/chatting/delete.do", method=RequestMethod.POST)
	   public ModelAndView delete_proc(HttpSession session, int chattingno) { // <form> 태그의 값이 자동으로 저장됨
	     ModelAndView mav = new ModelAndView();
	     
	     if (adminProc.isAdmin(session) == true) {
	       int cnt = this.chattingProc.delete(chattingno); // 카테고리 삭제
	       
	       if (cnt == 1) {
	         mav.setViewName("redirect:/chatting/list_by_chattingno.do");       // 자동 주소 이동, Spring 재호출 
	       } else {
	         mav.addObject("code", "delete_fail");
	         mav.setViewName("/chatting/msg"); // /WEB-INF/views/genre/msg.jsp
	       }
	       mav.addObject("cnt", cnt);   
	     } else {
	       mav.setViewName("/admin/login_need"); // /WEB-INF/views/manager/login_need.jsp
	     }
	     
	     return mav;
	   }
	   
//	   /**
//	    * 특정 카테고리의 검색 목록
//	    * http://localhost:9091/contents/list_by_cateno.do?cateno=1
//	    * @return
//	    */
//	   @RequestMapping(value="/chatting/list_by_chattingno.do", method = RequestMethod.GET)
//	   public ModelAndView list_by_chattingno(String word) {
//	     ModelAndView mav = new ModelAndView();
//
//	     mav.setViewName("/chatting/list_by_chattingno"); // /WEB-INF/views/contents/list_by_cateno.jsp
//	     
//	     // request.setAttribute("cateVO", cateVO);
//	     
//	     // 검색하지 않는 경우
//	     // ArrayList<ContentsVO> list = this.contentsProc.list_by_cateno(cateno);
//
//	     HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		 hashMap.put("word", word);
//		 int search_count = this.chattingProc.search_count(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
//		 mav.addObject("search_count", search_count);
//		    
//		 ArrayList<ChattingVO> list = this.chattingProc.list_by_chattingno_search(hashMap);
//		    
//	     
//	     // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
//	     for (ChattingVO chattingVO : list) {
//	       String msg = chattingVO.getMsg();
//	       
//	       msg = Tool.convertChar(msg);  // 특수 문자 처리
//	       
//	       chattingVO.setMsg(msg);  
//
//	     }
//	     
//	     mav.addObject("list", list);
//	     
//	     return mav;
//	   }  
	   
	   @RequestMapping(value="/chatting/list_by_chattingno.do", method = RequestMethod.GET)
		  public ModelAndView list_by_chattingno(ChattingVO chattingVO) {
		    ModelAndView mav = new ModelAndView();

		    ArrayList<ChattingVO> list = chattingProc.list_by_chattingno_search_paging(chattingVO);	 
		    // 검색하는 경우	    
		    System.out.println("List size: " + list.size());
		    for (ChattingVO vo : list) {
		        System.out.println(vo.toString()); // 또는 필요한 속성만 출력
		    }
		    // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
		    for (ChattingVO chattingvo2 : list) {
		      String msg = chattingvo2.getMsg();
		      
		      msg = Tool.convertChar(msg);
		      
		      chattingvo2.setMsg(msg);
		    }
		    
		    mav.addObject("list", list);
		    
		    HashMap<String, Object> hashMap = new HashMap<String, Object>();	     
		    hashMap.put("word", chattingVO.getWord());
		    int search_count = this.chattingProc.search_count(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
		    mav.addObject("search_count", search_count);
		     
		    String paging = chattingProc.pagingBox(chattingVO.getNow_page(), chattingVO.getWord(), "list_by_chattingno.do", search_count);
		    mav.addObject("paging", paging);
		  
		    // mav.addObject("now_page", now_page);
		    
		    mav.setViewName("/chatting/list_by_chattingno");  // /contents/list_by_cateno.jsp
		    
		    
		    return mav;
		  }  
	   
	   @GetMapping("/chatting/getAnswer_by_chattingno.do")
	   public ResponseEntity<Map<String, Object>> getAnswerByChattingNo(@RequestParam(value = "chattingno", required = false) String chattingnoStr) {
	       Map<String, Object> response = new HashMap<>();

	       if (chattingnoStr == null || chattingnoStr.trim().isEmpty()) {
	           response.put("error", "chattingno is required");
	           return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	       }

	       try {
	           int chattingno = Integer.parseInt(chattingnoStr);
	           int answerNo = (chattingno % 2 == 1) ? chattingno + 1 : chattingno;

	           // 여기서 Oracle DB에서 answerNo에 해당하는 답변 메시지를 가져오는 로직을 작성합니다.
	           String answerMsg = getAnswerFromOracleDB(answerNo); // 이 함수는 실제로 Oracle DB에서 데이터를 가져오는 로직을 포함해야 합니다.

	           if (answerMsg != null) {
	               response.put("answer", answerMsg);
	               return new ResponseEntity<>(response, HttpStatus.OK);
	           } else {
	               response.put("error", "Answer not found");
	               return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	           }
	       } catch (NumberFormatException e) {
	           response.put("error", "Invalid chattingno format");
	           return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	       }
	   }
	   
	   private String getAnswerFromOracleDB(int answerNo) {
		    String answerMsg = null;

		    // JDBC 연결 정보 설정
		    String jdbcUrl = "jdbc:oracle:thin:@54.66.10.137:1521:xe"; // Oracle DB URL
		    String username = "team5"; // Oracle DB 사용자 이름
		    String password = "1234"; // Oracle DB 비밀번호

		    Connection connection = null;
		    PreparedStatement preparedStatement = null;
		    ResultSet resultSet = null;

		    try {
		        // JDBC 드라이버 로드
		        Class.forName("oracle.jdbc.driver.OracleDriver");

		        // 데이터베이스 연결
		        connection = DriverManager.getConnection(jdbcUrl, username, password);

		        // SQL 쿼리 준비
		        String sql = "SELECT msg FROM chatting WHERE chattingno = ?";
		        preparedStatement = connection.prepareStatement(sql);
		        preparedStatement.setInt(1, answerNo);

		        // 쿼리 실행 및 결과 가져오기
		        resultSet = preparedStatement.executeQuery();

		        if (resultSet.next()) {
		            answerMsg = resultSet.getString("msg");
		        }

		    } catch (ClassNotFoundException | SQLException e) {
		        e.printStackTrace();
		    } finally {
		        // 리소스 해제
		        try {
		            if (resultSet != null) resultSet.close();
		            if (preparedStatement != null) preparedStatement.close();
		            if (connection != null) connection.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return answerMsg;
		}

	 }
		   
	 
