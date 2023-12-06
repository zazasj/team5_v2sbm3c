package dev.mvc.event;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.adminlog.AdlogService;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class EventCont {
	
	@Autowired
	@Qualifier("dev.mvc.admin.AdminProc") 
	private AdminProcInter adminProc;
	
	 @Autowired
	  @Qualifier("dev.mvc.event.EventProc") 
	  private EventProcInter eventProc;
	 
	 @Autowired
	  private AdlogService adlogservice;

	 private String tablename = "Event";
	
	 
	 @RequestMapping(value="/event/msg.do", method=RequestMethod.GET)
	  public ModelAndView msg(String url){
	    ModelAndView mav = new ModelAndView();

	    mav.setViewName(url); // forward
	    
	    return mav; // forward
	  }
	 
	 @RequestMapping(value="/event/create.do", method = RequestMethod.GET)
	  public ModelAndView create() {
	    ModelAndView mav = new ModelAndView();

	    //CateVO cateVO = this.cateProc.read(cateno); // create.jsp�뿉 移댄뀒怨좊━ �젙蹂대�� 異쒕젰�븯湲곗쐞�븳 紐⑹쟻
	    //mav.addObject("cateVO", cateVO);
	    
	    mav.setViewName("/event/create"); // /webapp/WEB-INF/views/contents/create.jsp
	    
	    return mav;
	  }
	 
	 @RequestMapping(value = "/event/create.do", method = RequestMethod.POST)
	  public ModelAndView create(HttpServletRequest request, HttpSession session, EventVO eventVO) {
	    ModelAndView mav = new ModelAndView();
	    
	    //adminlog관련 
	    int recordid = eventVO.getEventno();
	    int adno =  (int) session.getAttribute("adminno");
	    
	    if (adminProc.isAdmin(session)) {
	      
	      String file1 = "";          
	      String file1saved = "";   
	      String thumb1 = "";     

	      String upDir =  Events.getUploadDir();
	      System.out.println("-> upDir: " + upDir);
	      
	      MultipartFile mf = eventVO.getFile1MF();
	      
	      file1 = mf.getOriginalFilename(); 
	      System.out.println("-> file1: " + file1);
	      
	      if (Tool.checkUploadFile(file1) == true) { 
	        long size1 = mf.getSize();  
	        
	        if (size1 > 0) {        
	          file1saved = Upload.saveFileSpring(mf, upDir); 
	          
	          if (Tool.isImage(file1saved)) { 
	            thumb1 = Tool.preview(upDir, file1saved, 480, 500); 
	          }
	          
	        }    
	        
	        eventVO.setFile1(file1);   
	        eventVO.setFile1saved(file1saved); 
	        eventVO.setThumb1(thumb1);     
	        eventVO.setSize1(size1);  
	        
	        
	        int adminno = (int)session.getAttribute("adminno"); 
	        eventVO.setAdminno(adminno);
	        int cnt = this.eventProc.create(eventVO); 
	        
	        // ------------------------------------------------------------------------------
	        // PK return
	        // ------------------------------------------------------------------------------
	        // System.out.println("--> contentsno: " + contentsVO.getContentsno());
	        // mav.addObject("contentsno", contentsVO.getContentsno());
	        // ------------------------------------------------------------------------------
	        
	        if (cnt == 1) {
	          String acttype = "Create";  
	          adlogservice.createLog(tablename, recordid, acttype, adno);
	            mav.addObject("code", "create_success");
	        } else {
	            mav.addObject("code", "create_fail");
	        }
	        mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
	        
	        //mav.addObject("cateno", contentsVO.getCateno()); 
	        
	        mav.addObject("url", "/event/msg"); 
	        mav.setViewName("redirect:/event/msg.do");       
	      } else {
	        mav.addObject("cnt", "0"); 
	        mav.addObject("code", "check_upload_file_fail"); 
	        mav.addObject("url", "/event/msg"); 
	        mav.setViewName("redirect:/event/msg.do");      
	      }
	    } else {
	      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
	      mav.setViewName("redirect:/event/msg.do"); 
	    }
	    
	    return mav; // forward
	  }
	 
	 @RequestMapping(value="/event/list_all.do", method = RequestMethod.GET)
	  public ModelAndView list_all(HttpSession session) {
	    ModelAndView mav = new ModelAndView();
	    
	    
	      mav.setViewName("/event/list_all"); 
	      
	      ArrayList<EventVO> list = this.eventProc.list_all();
	     
	       for (EventVO eventVO : list) {
	        String title = eventVO.getTitle();
	        String content = eventVO.getContents();
	        
	        title = Tool.convertChar(title); 
	        content = Tool.convertChar(content); 
	        
	        eventVO.setTitle(title);
	        eventVO.setContents(content);  

	      }
	      
	      mav.addObject("list", list);
	      
	    
	    
	    return mav;
	  }
	 
	 @RequestMapping(value="/event/read.do", method = RequestMethod.GET)
	  public ModelAndView read(int eventno) { 
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("/event/read"); // /WEB-INF/views/contents/read.jsp
	    
	    EventVO eventVO = this.eventProc.read(eventno);
	    
	    String title = eventVO.getTitle();
	    String content = eventVO.getContents();
	    
	    title = Tool.convertChar(title);  // �듅�닔 臾몄옄 泥섎━
	    content = Tool.convertChar(content); 
	    
	    eventVO.setTitle(title);
	    eventVO.setContents(content);  
	    
	    long size1 = eventVO.getSize1();
	    String size1_label = Tool.unit(size1);
	    eventVO.setSize1_label(size1_label);
	    
	    mav.addObject("eventVO", eventVO);
	    return mav;
	  }
	 
	 @RequestMapping(value = "/event/delete.do", method = RequestMethod.GET)
	  public ModelAndView delete(HttpSession session, int eventno) {
	    ModelAndView mav = new ModelAndView();
	    
	    int adno =  (int) session.getAttribute("adminno");
	    int recordid = eventno;
	    
	    if (adminProc.isAdmin(session)) {
	      EventVO eventVO = this.eventProc.read(eventno);
	      mav.addObject("eventVO", eventVO);
	      
	      String acttype = "Delete";  
	      adlogservice.createLog(tablename, recordid, acttype, adno);
	      
	      mav.setViewName("/event/delete"); // /WEB-INF/views/contents/delete.jsp
	      
	    } else {
	      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
	      mav.setViewName("redirect:/event/msg.do"); 
	    }
	    return mav; 
	  }
	  
	  @RequestMapping(value = "/event/delete.do", method = RequestMethod.POST)
	  public ModelAndView delete(EventVO eventVO) {
	    ModelAndView mav = new ModelAndView();
    
	        
	    String file1saved = eventVO.getFile1saved();
	    String thumb1 = eventVO.getThumb1();
	    
	    String uploadDir = Events.getUploadDir();
	    Tool.deleteFile(uploadDir, file1saved);  
	    Tool.deleteFile(uploadDir, thumb1);    
	    
	    this.eventProc.delete(eventVO.getEventno()); 
	 
	    mav.setViewName("redirect:/event/list_all.do"); 
	    
	    return mav;
	  }
	  
	  @RequestMapping(value = "/event/update_text.do", method = RequestMethod.GET)
	  public ModelAndView update_text(HttpSession session, int eventno) {
	    ModelAndView mav = new ModelAndView();
	    
      int adno =  (int) session.getAttribute("adminno");
      int recordid = eventno;
      
	    if (adminProc.isAdmin(session)) { 
	      EventVO eventVO = this.eventProc.read(eventno);    
	      mav.addObject("eventVO", eventVO);       
	      
	      String acttype = "Update_text";  
        adlogservice.createLog(tablename, recordid, acttype, adno);
        
	      mav.setViewName("/event/update_text"); 
	    
	    } else {
	      mav.addObject("url", "/admin/login_need"); 
	      mav.setViewName("redirect:/event/msg.do"); 
	    }

	    return mav; // forward
	  }
	  
	  @RequestMapping(value = "/event/update_text.do", method = RequestMethod.POST)
	  public ModelAndView update_text(HttpSession session, EventVO eventVO) {
	    ModelAndView mav = new ModelAndView();
	    
	    if (this.adminProc.isAdmin(session)) { 
	        this.eventProc.update_text(eventVO);        
	        mav.addObject("eventno", eventVO.getEventno());    
	        mav.setViewName("redirect:/event/read.do");
	    } else { 
	      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
	      mav.setViewName("redirect:/contents/msg.do"); 
	    }
	    
	    return mav; // forward
	  }

	  @RequestMapping(value = "/event/update_file.do", method = RequestMethod.GET)
	  public ModelAndView update_file(HttpSession session, int eventno) {
	    ModelAndView mav = new ModelAndView();
	    int adno =  (int) session.getAttribute("adminno");
      int recordid = eventno;
	    
	    if (adminProc.isAdmin(session)) { 
	      EventVO eventVO = this.eventProc.read(eventno);
	      mav.addObject("eventVO", eventVO);
	      
	      String acttype = "Update_file";  
        adlogservice.createLog(tablename, recordid, acttype, adno);

	      
	      mav.setViewName("/event/update_file"); // /WEB-INF/views/contents/update_file.jsp
	      
	    } else {
	      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
	      mav.setViewName("redirect:/event/msg.do"); 
	    }
	    return mav; // forward
	  }
	  
	  @RequestMapping(value = "/event/update_file.do", method = RequestMethod.POST)
	  public ModelAndView update_file(HttpSession session, EventVO eventVO) {
	    ModelAndView mav = new ModelAndView();
	    
	    if (this.adminProc.isAdmin(session)) {
	      EventVO eventVO_old = eventProc.read(eventVO.getEventno());
	      
	      String file1saved = eventVO_old.getFile1saved();  
	      String thumb1 = eventVO_old.getThumb1();       
	      long size1 = 0;
	         
	      String upDir =  Events.getUploadDir(); 
	      
	      Tool.deleteFile(upDir, file1saved); 
	      Tool.deleteFile(upDir, thumb1);    
	     
	      String file1 = "";          

	      MultipartFile mf = eventVO.getFile1MF();
	          
	      file1 = mf.getOriginalFilename(); 
	      size1 = mf.getSize();  
	          
	      if (size1 > 0) { 
	        file1saved = Upload.saveFileSpring(mf, upDir); 
	        
	        if (Tool.isImage(file1saved)) {      
	          thumb1 = Tool.preview(upDir, file1saved, 480, 500); 
	        }        
	      } else { 
	        file1="";
	        file1saved="";
	        thumb1="";
	        size1=0;
	      }         
	      eventVO.setFile1(file1);
	      eventVO.setFile1saved(file1saved);
	      eventVO.setThumb1(thumb1);
	      eventVO.setSize1(size1);
	     
	      this.eventProc.update_file(eventVO); 

	      mav.addObject("eventno", eventVO.getEventno());
	      mav.setViewName("redirect:/event/read.do"); 
	               
	    } else {
	      mav.addObject("url", "/admin/login_need"); 
	      mav.setViewName("redirect:/event/msg.do"); 
	    }
	    
	    return mav; // forward
	  }     
	

}
