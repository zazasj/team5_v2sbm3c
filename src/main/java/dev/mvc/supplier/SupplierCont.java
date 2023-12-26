package dev.mvc.supplier;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.category.CategoryVO;
import dev.mvc.event.EventVO;
import dev.mvc.event.Events;
import dev.mvc.products.ProductsVO;
import dev.mvc.supplier.SupplierVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class SupplierCont {

	@Autowired
	  @Qualifier("dev.mvc.supplier.SupplierProc") // @Component("dev.mvc.supplier.SupplierProc")
	  private SupplierProcInter supplierProc;
	
	 @Autowired
	  @Qualifier("dev.mvc.admin.AdminProc")
	  private AdminProcInter adminProc;
	 
	 public SupplierCont () {
		    System.out.println("-> SupplierCont created.");
		  }
	

	@RequestMapping(value="/supplier/msg.do", method=RequestMethod.GET)
	public ModelAndView msg(String url){
	  ModelAndView mav = new ModelAndView();

	  mav.setViewName(url); // forward
	    
	  return mav; // forward
	}
	 
	// form 출력, http://localhost:9093/supplier/create.do
	@RequestMapping(value="/supplier/create.do", method = RequestMethod.GET)
	public ModelAndView create() {
	ModelAndView mav = new ModelAndView();
	mav.setViewName("/supplier/create"); // /WEB-INF/views/supplier/create.jsp
		    
	return mav;
	}
	
	    
	 // FORM 데이터 처리, http://localhost:9093/supplier/create.do
		  @RequestMapping(value="/supplier/create.do", method = RequestMethod.POST)
		  public ModelAndView create(HttpServletRequest request, HttpSession session, SupplierVO supplierVO) { // 자동으로 supplierVO 객체가 생성되고 폼의 값이 할당됨
		    ModelAndView mav = new ModelAndView();
		    
		    if (adminProc.isAdmin(session)) {
			      
			      String file1 = "";          
			      String file1saved = "";   
			      String thumb1 = "";     

			      String upDir =  Suppliers.getUploadDir();
			      System.out.println("-> upDir: " + upDir);
			      
			      MultipartFile mf = supplierVO.getFile1MF();
			      
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
			        
			        supplierVO.setFile1(file1);   
			        supplierVO.setFile1saved(file1saved); 
			        supplierVO.setThumb1(thumb1);     
			        supplierVO.setSize1(size1);  
			        
			        
			        int adminno = (int)session.getAttribute("adminno"); 
			        supplierVO.setAdminno(adminno);
			        int cnt = this.supplierProc.create(supplierVO); 
			        
			        // ------------------------------------------------------------------------------
			        // PK return
			        // ------------------------------------------------------------------------------
			        // System.out.println("--> contentsno: " + contentsVO.getContentsno());
			        // mav.addObject("contentsno", contentsVO.getContentsno());
			        // ------------------------------------------------------------------------------
			        
			        if (cnt == 1) {
			            mav.addObject("code", "create_success");
			        } else {
			            mav.addObject("code", "create_fail");
			        }
			        mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
			        
			        //mav.addObject("cateno", contentsVO.getCateno()); 
			        
			        mav.addObject("url", "/supplier/msg"); 
			        mav.setViewName("redirect:/supplier/msg.do");       
			      } else {
			        mav.addObject("cnt", "0"); 
			        mav.addObject("code", "check_upload_file_fail"); 
			        mav.addObject("url", "/supplier/msg"); 
			        mav.setViewName("redirect:/supplier/msg.do");      
			      }
			    } else {
			      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
			      mav.setViewName("redirect:/supplier/msg.do"); 
			    }
			    
			    return mav; // forward
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
	   
	   @RequestMapping(value="/supplier/read.do", method = RequestMethod.GET)
		  public ModelAndView read(int supplierid) { 
		    ModelAndView mav = new ModelAndView();
		    mav.setViewName("/supplier/read"); // /WEB-INF/views/contents/read.jsp
		    
		    SupplierVO supplierVO = this.supplierProc.read(supplierid);
		    
		    String sname = supplierVO.getSname();
		    
		    sname = Tool.convertChar(sname);  // �듅�닔 臾몄옄 泥섎━
		    
		    supplierVO.setSname(sname);
		    
		    long size1 = supplierVO.getSize1();
		    String size1_label = Tool.unit(size1);
		    supplierVO.setSize1_label(size1_label);
		     
		    //System.out.println(reviewVO);
		    mav.addObject("supplierVO", supplierVO);
		    //System.out.println(eventVO);
		    return mav;
		  }
	   
//	   @RequestMapping(value="/supplier/list_by_supplierid.do", method = RequestMethod.GET)
//		  public ModelAndView list_by_supplierid(String word) {
//		    ModelAndView mav = new ModelAndView();
//
//		    mav.setViewName("/supplier/list_by_supplierid"); // /WEB-INF/views/contents/list_by_cateno.jsp
//		    
//		    // request.setAttribute("cateVO", cateVO);
//		    
//		    // 검색하지 않는 경우
//		    // ArrayList<ContentsVO> list = this.contentsProc.list_by_cateno(cateno);
//
//		    // 검색하는 경우
//		    HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		    hashMap.put("word", word);
//		    int search_count = this.supplierProc.search_count(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
//		    mav.addObject("search_count", search_count);
//		    
//		    ArrayList<SupplierVO> list = this.supplierProc.list_by_supplierid_search(hashMap);
//		    
//		    // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
//		    for (SupplierVO supplierVO : list) {
//		      String sname = supplierVO.getSname();
//		      String contactinfo = supplierVO.getContactinfo();
//		      String email = supplierVO.getEmail();
//		      
//		      sname = Tool.convertChar(sname);  // 특수 문자 처리
//		      contactinfo = Tool.convertChar(contactinfo); 
//		      email = Tool.convertChar(email);  // 특수 문자 처리
//		      
//		      supplierVO.setSname(sname);
//		      supplierVO.setContactinfo(contactinfo);  
//		      supplierVO.setEmail(email);
//		      
//		    }
//		    mav.addObject("list", list);
//		    
//		    return mav;
//	   }
	   
	   @RequestMapping(value="/supplier/list_by_supplierid.do", method = RequestMethod.GET)
		  public ModelAndView list_by_supplierid(SupplierVO supplierVO) {
		    ModelAndView mav = new ModelAndView();

		    
		    ArrayList<SupplierVO> list = supplierProc.list_by_supplierid_search_paging(supplierVO);	 
		    // 검색하는 경우	    
		    System.out.println("List size: " + list.size());
		    for (SupplierVO vo : list) {
		        System.out.println(vo.toString()); // 또는 필요한 속성만 출력
		    }
		    // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
		    for (SupplierVO suppliervo2 : list) {
		      String sname = suppliervo2.getSname();
		      String contactinfo = suppliervo2.getContactinfo();
		      String email = suppliervo2.getEmail();
		      String saddress = suppliervo2.getSaddress();
		      
		      sname = Tool.convertChar(sname);  // 특수 문자 처리
		      contactinfo = Tool.convertChar(contactinfo); 
		      email = Tool.convertChar(email);  // 특수 문자 처리
		      saddress = Tool.convertChar(saddress);
		      
		      suppliervo2.setSname(sname);
		      suppliervo2.setContactinfo(contactinfo);  
		      suppliervo2.setEmail(email);
		      suppliervo2.setSaddress(saddress);
		    }
		    
		    mav.addObject("list", list);
		    
		    HashMap<String, Object> hashMap = new HashMap<String, Object>();	     
		    hashMap.put("word", supplierVO.getWord());
		    int search_count = this.supplierProc.search_count(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
		    mav.addObject("search_count", search_count);
		     
		    String paging = supplierProc.pagingBox(supplierVO.getNow_page(), supplierVO.getWord(), "list_by_supplierid.do", search_count);
		    mav.addObject("paging", paging);
		  
		    // mav.addObject("now_page", now_page);
		    
		    mav.setViewName("/supplier/list_by_supplierid");  // /contents/list_by_cateno.jsp
		    
		    
		    return mav;
		  }  
	   
	   @RequestMapping(value="/supplier/list_by_supplierid_grid.do", method = RequestMethod.GET)
		  public ModelAndView list_by_supplierid_grid(SupplierVO supplierVO) {
		    ModelAndView mav = new ModelAndView();

		    
		    ArrayList<SupplierVO> list = supplierProc.list_by_supplierid_search_paging(supplierVO);	 
		    // 검색하는 경우	    
		    // System.out.println("List size: " + list.size());
		    for (SupplierVO vo : list) {
		        System.out.println(vo.toString()); // 또는 필요한 속성만 출력
		    }
		    // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
		    for (SupplierVO suppliervo2 : list) {
		      String sname = suppliervo2.getSname();
		      String contactinfo = suppliervo2.getContactinfo();
		      String email = suppliervo2.getEmail();
		      String saddress = suppliervo2.getSaddress();
		      
		      sname = Tool.convertChar(sname);  // 특수 문자 처리
		      contactinfo = Tool.convertChar(contactinfo); 
		      email = Tool.convertChar(email);  // 특수 문자 처리
		      saddress = Tool.convertChar(saddress);
		      
		      suppliervo2.setSname(sname);
		      suppliervo2.setContactinfo(contactinfo);  
		      suppliervo2.setEmail(email);
		      suppliervo2.setSaddress(saddress);
		    }
		    
		    mav.addObject("list", list);
		    
		    HashMap<String, Object> hashMap = new HashMap<String, Object>();	     
		    hashMap.put("word", supplierVO.getWord());
		    int search_count = this.supplierProc.search_count(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
		    mav.addObject("search_count", search_count);
		     
		    String paging = supplierProc.pagingBox(supplierVO.getNow_page(), supplierVO.getWord(), "list_by_supplierid_grid.do", search_count);
		    mav.addObject("paging", paging);
		  
		    // mav.addObject("now_page", now_page);
		    
		    mav.setViewName("/supplier/list_by_supplierid_grid");  // /contents/list_by_cateno.jsp
		    
		    
		    return mav;
		  }
	   
	   /**
	   * 수정 폼
	   * http://localhost:9093/supplier/update_text.do?supplierid=1
	   * 
	   * @return
	   */
	  @RequestMapping(value = "/supplier/update_text.do", method = RequestMethod.GET)
	  public ModelAndView update_text(HttpSession session, int supplierid) {
	    ModelAndView mav = new ModelAndView();
	    
	    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
	      SupplierVO supplierVO = this.supplierProc.read(supplierid);
	      mav.addObject("supplierVO", supplierVO);
	      
	      mav.setViewName("/supplier/update_text"); // /WEB-INF/views/contents/update_text.jsp
	      // String content = "장소:\n인원:\n준비물:\n비용:\n기타:\n";
	      // mav.addObject("content", content);

	    } else {
	      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
	      mav.setViewName("redirect:/supplier/msg.do"); 
	    }

	    return mav; // forward
	  }
	  
	  /**
	   * 수정 처리
	   * http://localhost:9091/contents/update_text.do?contentsno=1
	   * 
	   * @return
	   */
	  @RequestMapping(value = "/supplier/update_text.do", method = RequestMethod.POST)
	  public ModelAndView update_text(HttpSession session, SupplierVO supplierVO) {
	    ModelAndView mav = new ModelAndView();
	    
	    // System.out.println("-> word: " + contentsVO.getWord());
	    
	    if (this.adminProc.isAdmin(session)) { // 관리자 로그인 확인
	        HashMap<String, Object> hashMap = new HashMap<String, Object>();
	        hashMap.put("supplierid", supplierVO.getSupplierid());

	        this.supplierProc.update_text(supplierVO); // 글수정  
	         
	        // mav 객체 이용
	        mav.addObject("supplierid", supplierVO.getSupplierid());
	        mav.setViewName("redirect:/supplier/read.do"); // 페이지 자동 이동

	      } else { // 정상적인 로그인이 아닌 경우 로그인 유도
	        mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
	        mav.setViewName("redirect:/supplier/msg.do"); 
	      }
	      
	      // mav.addObject("now_page", supplierVO.getNow_page()); // POST -> GET: 데이터 분실이 발생함으로 다시하번 데이터 저장 ★
	      
	      // URL에 파라미터의 전송
	      // mav.setViewName("redirect:/contents/read.do?contentsno=" + contentsVO.getContentsno() + "&categoryID=" + contentsVO.getCateno());             
	      
	      return mav; // forward
	    }
	  
	  /**
	   * 파일 수정 폼
	   * http://localhost:9091/contents/update_file.do?contentsno=1
	   * 
	   * @return
	   */
	  @RequestMapping(value = "/supplier/update_file.do", method = RequestMethod.GET)
	  public ModelAndView update_file(HttpSession session, int supplierid) {
	    ModelAndView mav = new ModelAndView();
	    
	    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
	        SupplierVO supplierVO = this.supplierProc.read(supplierid);
	        mav.addObject("supplierVO", supplierVO);
	        
	        mav.setViewName("/supplier/update_file"); // /WEB-INF/views/contents/update_file.jsp
	        
	      } else {
	        mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
	        mav.setViewName("redirect:/supplier/msg.do"); 
	      }

	      return mav; // forward
	    }
	  
	  /**
	   * 파일 수정 처리 http://localhost:9091/contents/update_file.do
	   * 
	   * @return
	   */
	  @RequestMapping(value = "/supplier/update_file.do", method = RequestMethod.POST)
	  public ModelAndView update_file(HttpSession session, SupplierVO supplierVO) {
	    ModelAndView mav = new ModelAndView();
	    
	    if (this.adminProc.isAdmin(session)) {
	        // 삭제할 파일 정보를 읽어옴, 기존에 등록된 레코드 저장용
	        SupplierVO supplierVO_old = supplierProc.read(supplierVO.getSupplierid());
	        
	        // -------------------------------------------------------------------
	        // 파일 삭제 시작
	        // -------------------------------------------------------------------
	        String file1saved = supplierVO_old.getFile1saved();  // 실제 저장된 파일명
	        String thumb1 = supplierVO_old.getThumb1();       // 실제 저장된 preview 이미지 파일명
	        long size1 = 0;
	           
	        String upDir =  Suppliers.getUploadDir(); // C:/kd/deploy/resort_v2sbm3c/contents/storage/
	        
	        Tool.deleteFile(upDir, file1saved);  // 실제 저장된 파일삭제
	        Tool.deleteFile(upDir, thumb1);     // preview 이미지 삭제
	        // -------------------------------------------------------------------
	        // 파일 삭제 종료
	        // -------------------------------------------------------------------
	            
	        // -------------------------------------------------------------------
	        // 파일 전송 시작
	        // -------------------------------------------------------------------
	        String file1 = "";          // 원본 파일명 image

	        // 전송 파일이 없어도 file1MF 객체가 생성됨.
	        // <input type='file' class="form-control" name='file1MF' id='file1MF' 
	        //           value='' placeholder="파일 선택">
	        MultipartFile mf = supplierVO.getFile1MF();
	            
	        file1 = mf.getOriginalFilename(); // 원본 파일명
	        size1 = mf.getSize();  // 파일 크기
	            
	        if (size1 > 0) { // 폼에서 새롭게 올리는 파일이 있는지 파일 크기로 체크 ★
	          // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
	          file1saved = Upload.saveFileSpring(mf, upDir); 
	          
	          if (Tool.isImage(file1saved)) { // 이미지인지 검사
	            // thumb 이미지 생성후 파일명 리턴됨, width: 250, height: 200
	            thumb1 = Tool.preview(upDir, file1saved, 250, 200); 
	          }
	          
	        } else { // 파일이 삭제만 되고 새로 올리지 않는 경우
	          file1="";
	          file1saved="";
	          thumb1="";
	          size1=0;
	        }
	            
	        supplierVO.setFile1(file1);
	        supplierVO.setFile1saved(file1saved);
	        supplierVO.setThumb1(thumb1);
	        supplierVO.setSize1(size1);
	        // -------------------------------------------------------------------
	        // 파일 전송 코드 종료
	        // -------------------------------------------------------------------
	            
	        this.supplierProc.update_file(supplierVO); // Oracle 처리

	        mav.addObject("supplierid", supplierVO.getSupplierid());
	        mav.setViewName("redirect:/supplier/read.do"); // request -> param으로 접근 전환
	                  
	      } else {
	        mav.addObject("url", "/admin/login_need"); // login_need.jsp, redirect parameter 적용
	        mav.setViewName("redirect:/supplier/msg.do"); // GET
	      }
	      
	      return mav; // forward
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
	          mav.addObject("email",supplierVO.getEmail());
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
	     
	     if(adminProc.isAdmin(session)) {
	       SupplierVO supplierVO = this.supplierProc.read(supplierid);
	       mav.addObject("supplierVO", supplierVO);
	       
	       mav.setViewName("/supplier/delete"); // /WEB-INF/views/contents/delete.jsp
	     }
	     else {
	       mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
	       mav.setViewName("redirect:/supplier/msg.do"); 
	     }
	     
	     return mav;
	   }
	   
	// FORM 데이터 처리 http://localhost:9093/supplier/delete.do
	  @RequestMapping(value="/supplier/delete.do",method = RequestMethod.POST)
	  public ModelAndView delete(SupplierVO supplierVO) {
	    ModelAndView mav = new ModelAndView();
	        
	    String file1saved = supplierVO.getFile1saved();
	    String thumb1 = supplierVO.getThumb1();
	    
	    String uploadDir = Suppliers.getUploadDir();
	    Tool.deleteFile(uploadDir, file1saved);  
	    Tool.deleteFile(uploadDir, thumb1);    
	    
	    this.supplierProc.delete(supplierVO.getSupplierid()); 
	 
	    mav.setViewName("redirect:/supplier/list_by_supplierid.do"); 
	    
	    return mav;
}
}
