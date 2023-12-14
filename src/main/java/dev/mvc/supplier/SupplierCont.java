package dev.mvc.supplier;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.chatting.ChattingProcInter;
import dev.mvc.supplier.SupplierVO;

@Controller
public class SupplierCont {

	@Autowired
	  @Qualifier("dev.mvc.supplier.SupplierProc") // @Component("dev.mvc.supplier.SupplierProc")
	  private SupplierProcInter supplierProc;
	
	 @Autowired
	  @Qualifier("dev.mvc.admin.AdminProc")
	  private AdminProcInter adminProc;
	

	 
	// form 출력, http://localhost:9093/supplier/create.do
	@RequestMapping(value="/supplier/create.do", method = RequestMethod.GET)
	public ModelAndView create() {
	ModelAndView mav = new ModelAndView();
	mav.setViewName("/supplier/create"); // /WEB-INF/views/supplier/create.jsp
		    
	return mav;
	}
	
	  // FORM 데이터 처리, http://localhost:9093/supplier/create.do
	  @RequestMapping(value="/supplier/create.do", method = RequestMethod.POST)
	  public ModelAndView create(SupplierVO supplierVO) { // 자동으로 supplierVO 객체가 생성되고 폼의 값이 할당됨
	    ModelAndView mav = new ModelAndView();
	    
	    int cnt = this.supplierProc.create(supplierVO);
	    System.out.println("-> cntr: " + cnt);
	    
	    if (cnt == 1) {
	      //mav.addObject("code", "create_success"); // 키, 값
	      // mav.addObject("name", tripVO.getName()); // 카테고리 이름 jsp로 전송
	      mav.setViewName("redirect:/supplier/list_all_adminno.do"); // 주소 자동 이동
	    } else {
	      mav.addObject("code", "create_fail");
	      //mav.setViewName("/supplier/msg"); // /WEB-INF/views/supplier/msg.jsp
	    }
	    
	    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt);
	    //	    mav.addObject("cnt", 0); // request.setAttribute("cnt", cnt);
	    
	    return mav;
	  }
	  
	  /**
	    * 전체목록
	    * @return
	    */
	   @RequestMapping(value="/supplier/list_all_adminno.do",method = RequestMethod.GET)
	   public ModelAndView list_all(HttpSession session) {
	     ModelAndView mav = new ModelAndView();
	     
	     int adminno=0;
	     if(this.adminProc.isAdmin(session)) {
	       mav.setViewName("/supplier/list_all_adminno");// /WEB-INF/views/supplier/list_all_adminno.jsp
	       
	       adminno = (int)session.getAttribute("adminno");
	       mav.addObject("adminno", adminno);
	       
	       ArrayList<SupplierVO> list = this.supplierProc.list_all_adminno(adminno);
	       mav.addObject("list", list);
	     }
	     else {
	       mav.setViewName("/admin/login_need"); // /WEB-INF/views/manager/login_need.jsp
	     }
	     return mav;
	   }
	  
	  /**
	   * 수정
	   * http://localhost:9093/supplier/update.do?supplierid=1
	   * @return
	   */
	  @RequestMapping(value="/supplier/update.do",method = RequestMethod.GET)
	  public ModelAndView update(HttpSession session,int supplierid) { 
	    ModelAndView mav = new ModelAndView();
	    
	    int adminno = 0;
	    if(this.adminProc.isAdmin(session)) {
	      mav.setViewName("/supplier/list_all_update");// /WEB-INF/views/supplier/list_all_update.jsp
	      
	      SupplierVO supplierVO = this.supplierProc.read(supplierid);
	      mav.addObject("supplierVO", supplierVO);
	      
	      adminno = (int)session.getAttribute("adminno");

	      ArrayList<SupplierVO> list = this.supplierProc.list_all_adminno(adminno);
	      mav.addObject("list",list);
	    }
	    else {
	      mav.setViewName("/admin/login_need");
	    }
	    
	    return mav;
	  }
	  
	  // FORM 데이터 처리 http://localhost:9093/supplier/update.do
	  @RequestMapping(value="/supplier/update.do",method = RequestMethod.POST)
	  public ModelAndView update(SupplierVO supplierVO) { 
	    ModelAndView mav = new ModelAndView();
	    System.out.println("십!: " + supplierVO);    
	    int cnt = this.supplierProc.update(supplierVO); // 수정 처리
	    System.out.println("-> cnt:"+cnt);
	    
	    if(cnt == 1) {
	          mav.addObject("sname",supplierVO.getSname());
	          mav.addObject("contactinfo",supplierVO.getContactinfo());
	          mav.addObject("saddress",supplierVO.getSaddress());
	          mav.setViewName("redirect:/supplier/list_all_adminno.do"); 
	        }
	    else {
	      mav.addObject("code","update_fail");     
	      mav.setViewName("/supplier/msg");  // /WEB-INF/views/supplier/msg.jsp
	    }
	    
	    mav.addObject("cnt",cnt); 
	    return mav;
	  }
	  
		 // 삭제폼
	   @RequestMapping(value="/supplier/delete.do",method = RequestMethod.GET)
	   public ModelAndView delete(HttpSession session, int supplierid) { // 
	     ModelAndView mav = new ModelAndView();
	     
	     int adminno=0;
	     if(this.adminProc.isAdmin(session)) {
	       mav.setViewName("/supplier/list_all_delete");// /WEB-INF/views/supplier/list_all_delete.jsp
	       
	       adminno = (int)session.getAttribute("adminno");
	       
	       SupplierVO supplierVO = this.supplierProc.read(supplierid);
	       mav.addObject("supplierVO",supplierVO);
	       
	       ArrayList<SupplierVO> list = this.supplierProc.list_all_adminno(adminno);
	       mav.addObject("list",list);
	       
	     }
	     else {
	       mav.setViewName("/admin/login_need");
	     }
	     
	     return mav;
	   }
	   
	// FORM 데이터 처리 http://localhost:9093/supplier/delete.do
	  @RequestMapping(value="/supplier/delete.do",method = RequestMethod.POST)
	  public ModelAndView delete(int supplierid) {
	    ModelAndView mav = new ModelAndView();
	        
	    int cnt = this.supplierProc.delete(supplierid); // 수정 처리
	    System.out.println("-> cnt:"+cnt);
	    
	    if(cnt == 1) {
	      mav.setViewName("redirect:/supplier/list_all_adminno.do"); 
	    }
	    else {
	      mav.addObject("code","delete_fail");     
	      mav.setViewName("/supplier/msg");  // /WEB-INF/views/supplier/msg.jsp
	    }
	    
	    mav.addObject("cnt",cnt); 
	    return mav;
	  }
}
