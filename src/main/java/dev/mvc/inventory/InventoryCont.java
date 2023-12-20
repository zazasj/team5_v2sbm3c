package dev.mvc.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.products.ProductsProcInter;
import dev.mvc.products.ProductsVO;
import dev.mvc.supplier.SupplierProcInter;
import dev.mvc.supplier.SupplierVO;

@Controller
public class InventoryCont {
  @Autowired // InventoryProcInter interface 구현한 객체를 만들어 자동으로 할당해라.
  @Qualifier("dev.mvc.inventory.InventoryProc")
  private InventoryProcInter inventoryProc;
  
  @Autowired
  @Qualifier("dev.mvc.products.ProductsProc") // @Component("dev.mvc.products.ContentsProc")
  private ProductsProcInter productsProc;
  
  @Autowired
  @Qualifier("dev.mvc.supplier.SupplierProc") // @Component("dev.mvc.supplier.SupplierProc")
  private SupplierProcInter supplierProc;
  
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") // "dev.mvc.admin.AdminProc"라고 명명된 클래스
  private AdminProcInter adminProc; // AdminProcInter를 구현한 AdminProc 클래스의 객체를 자동으로 생성하여 할당
    
  public InventoryCont() {
    System.out.println("-> InventoryCont created.");  
  }

//  // FORM 출력, http://localhost:9092/inventory/create.do
//  @RequestMapping(value="/inventory/create.do", method = RequestMethod.GET)
//  @ResponseBody // 단순 문자열로 출력, jsp 파일명 조합이 발생하지 않음.
//  public String create() {
//    return "<h3>GET 방식 FORM 출력</h3>";
//  }

  // FORM 출력, http://localhost:9092/inventory/create.do
  @RequestMapping(value="/inventory/create.do", method = RequestMethod.GET)
  public ModelAndView create() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/inventory/create"); // /WEB-INF/views/inventory/create.jsp
    
    return mav;
  }
  
  // FORM 데이터 처리, http://localhost:9092/inventory/create.do
  @RequestMapping(value="/inventory/create.do", method = RequestMethod.POST)
  public ModelAndView create(InventoryVO inventoryVO) { // 자동으로 inventoryVO 객체가 생성되고 폼의 값이 할당됨
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.inventoryProc.create(inventoryVO);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      // mav.addObject("code", "create_success"); // 키, 값
      // mav.addObject("name", inventoryVO.getName()); // 카테고리 이름 jsp로 전송
      mav.setViewName("redirect:/inventory/list_all.do"); // 주소 자동 이동
    } else {
      mav.addObject("code", "create_fail");
      mav.setViewName("/inventory/msg"); // /WEB-INF/views/inventory/msg.jsp
    }
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt);
//    mav.addObject("cnt", 0); // request.setAttribute("cnt", cnt);
    
    return mav;
  }
  
  /**
   * 전체 목록
   * http://localhost:9092/inventory/list_all.do
   * @return
   */
  @RequestMapping(value="/inventory/list_all.do", method = RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/inventory/list_all"); // /WEB-INF/views/inventory/list_all.jsp
        
      ArrayList<InventoryVO> list = this.inventoryProc.list_all();
      mav.addObject("list", list);
      
      ArrayList<ProductsVO> list1 = this.productsProc.list_all();
      mav.addObject("list1", list1);
      
      ArrayList<SupplierVO> list2 = this.supplierProc.list_all();
      mav.addObject("list2", list2);
    
      
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      
    }
    
    return mav;
  }
  /**
   * 전체 목록
   * http://localhost:9092/inventory/list_all.do
   * @return
   */
  @RequestMapping(value="/inventory/list.do", method = RequestMethod.GET)
  public ModelAndView list(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/inventory/list"); // /WEB-INF/views/inventory/list_all.jsp
      
      ArrayList<InventoryVO> list = this.inventoryProc.list();
      mav.addObject("list", list);
      
      ArrayList<ProductsVO> list1 = this.productsProc.list_all();
      mav.addObject("list1", list1);
      
      ArrayList<SupplierVO> list2 = this.supplierProc.list_all();
      mav.addObject("list2", list2);
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      
    }
    
    return mav;
  }
  
  /**
   * 조회
   * http://localhost:9092/inventory/read.do?inventoryID=1
   * @return
   */
  @RequestMapping(value="/inventory/read.do", method = RequestMethod.GET)
  public ModelAndView read(int inventoryID) { // int inventoryID = (int)request.getParameter("inventoryID");
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/inventory/read"); // /WEB-INF/views/inventory/read.jsp
    
    InventoryVO inventoryVO = this.inventoryProc.read(inventoryID);
    mav.addObject("inventoryVO", inventoryVO);
    
    return mav;
  }

  /**
   * 조회
   * http://localhost:9092/inventory/read.do?inventoryID=1
   * @return
   */
  @RequestMapping(value="/inventory/readString.do", method = RequestMethod.GET)
  public ModelAndView read(String inventoryStatus) { // int inventoryID = (int)request.getParameter("inventoryID");
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/inventory/read"); // /WEB-INF/views/inventory/read.jsp
    
    InventoryVO inventoryVO = this.inventoryProc.readString(inventoryStatus);
    mav.addObject("inventoryVO", inventoryVO);
    
    return mav;
  }

  /**
   * 수정폼
   * http://localhost:9092/inventory/update.do?inventoryID=1
   * @return
   */
  @RequestMapping(value="/inventory/update.do", method = RequestMethod.GET)
  public ModelAndView update(HttpSession session, int inventoryID) { // int inventoryID = (int)request.getParameter("inventoryID");
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      // mav.setViewName("/inventory/update"); // /WEB-INF/views/inventory/update.jsp
      mav.setViewName("/inventory/list_all_update"); // /WEB-INF/views/inventory/list_all_update.jsp
      
      InventoryVO inventoryVO = this.inventoryProc.read(inventoryID);
      mav.addObject("inventoryVO", inventoryVO);
      
      ArrayList<InventoryVO> list = this.inventoryProc.list_all();
      mav.addObject("list", list);
      
      ArrayList<ProductsVO> list1 = this.productsProc.list_all();
      mav.addObject("list1", list1);
      
      ArrayList<SupplierVO> list2 = this.supplierProc.list_all();
      mav.addObject("list2", list2);
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      
    }
        
    return mav;
  }
  
  /**
   * 수정 처리, http://localhost:9092/inventory/update.do
   * @param inventoryVO 수정할 내용
   * @return
   */
  @RequestMapping(value="/inventory/update.do", method = RequestMethod.POST)
  public ModelAndView update(InventoryVO inventoryVO) { // 자동으로 inventoryVO 객체가 생성되고 폼의 값이 할당됨
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.inventoryProc.update(inventoryVO); // 수정 처리
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      // mav.addObject("code", "update_success"); // 키, 값
      // mav.addObject("name", inventoryVO.getName()); // 카테고리 이름 jsp로 전송
      mav.setViewName("redirect:/inventory/list_all.do"); 
      
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/inventory/msg"); // /WEB-INF/views/inventory/msg.jsp
    }
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt);
//    mav.addObject("cnt", 0); // request.setAttribute("cnt", cnt);
    
    return mav;
  }
  /**
   * 수정 처리, http://localhost:9092/inventory/update.do
   * @param inventoryVO 수정할 내용
   * @return
   */
  @RequestMapping(value="/inventory/updatequantity.do", method = RequestMethod.POST)
  public ModelAndView updatequantity(InventoryVO inventoryVO) { // 자동으로 inventoryVO 객체가 생성되고 폼의 값이 할당됨
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.inventoryProc.updatequantity(inventoryVO); // 수정 처리
    System.out.println("-> cnt: " + cnt);
    
    mav.setViewName("redirect:/inventory/list.do"); 
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt);
//    mav.addObject("cnt", 0); // request.setAttribute("cnt", cnt);
    
    return mav;
  }
  
  /**
   * 삭제폼
   * http://localhost:9092/inventory/delete.do?inventoryID=1
   * @return
   */
  @RequestMapping(value="/inventory/delete.do", method = RequestMethod.GET)
  public ModelAndView delete(HttpSession session, int inventoryID) { // int inventoryID = (int)request.getParameter("inventoryID");
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      // mav.setViewName("/inventory/delete"); // /WEB-INF/views/inventory/delete.jsp
      mav.setViewName("/inventory/list_all_delete"); // /WEB-INF/views/inventory/list_all_delete.jsp
      
      InventoryVO inventoryVO = this.inventoryProc.read(inventoryID);
      mav.addObject("inventoryVO", inventoryVO);
      
      ArrayList<InventoryVO> list = this.inventoryProc.list_all();
      mav.addObject("list", list);
      
      ArrayList<ProductsVO> list1 = this.productsProc.list_all();
      mav.addObject("list1", list1);
      
      ArrayList<SupplierVO> list2 = this.supplierProc.list_all();
      mav.addObject("list2", list2);
   
    }
    
    return mav;
  }
  
 // 삭제 처리, 수정 처리를 복사하여 개발
 // 자식 테이블 레코드 삭제 -> 부모 테이블 레코드 삭제
 /**
  * 카테고리 삭제
  * @param session
  * @param inventoryID 삭제할 카테고리 번호
  * @return
  */
 @RequestMapping(value="/inventory/delete.do", method=RequestMethod.POST)
 public ModelAndView delete_proc(HttpSession session, int inventoryID) { // <form> 태그의 값이 자동으로 저장됨
//   System.out.println("-> inventoryID: " + inventoryVO.getInventoryID());
//   System.out.println("-> name: " + inventoryVO.getName());
   
   ModelAndView mav = new ModelAndView();  
           
     int cnt = this.inventoryProc.delete(inventoryID); // 카테고리 삭제
     
     if (cnt == 1) {
       mav.setViewName("redirect:/inventory/list_all.do");       // 자동 주소 이동, Spring 재호출
       
     } else {
       mav.addObject("code", "delete_fail");
       mav.setViewName("/inventory/msg"); // /WEB-INF/views/inventory/msg.jsp
     }
     
     mav.addObject("cnt", cnt);
     
   
   
   return mav;
 }
 @RequestMapping(value = "/inventory/list_by_inventoryStatus.do", method = RequestMethod.GET)
 public ModelAndView list_by_inventoryStatus(InventoryVO inventoryVO) {
   ModelAndView mav = new ModelAndView();
 
   // 검색 목록
   ArrayList<InventoryVO> list = inventoryProc.list_by_inventoryStatus_search_paging(inventoryVO);
   
   ArrayList<ProductsVO> list1 = this.productsProc.list_all();
   mav.addObject("list1", list1);
   
   ArrayList<SupplierVO> list2 = this.supplierProc.list_all();
   mav.addObject("list2", list2);
   
   // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
   for (InventoryVO vo : list) {
     int inventoryID = vo.getInventoryID();
     int supplierID = vo.getSupplierID();
     
    // inventoryID = Tool.convertChar(inventoryID);  // 특수 문자 처리
    // supplierID = Tool.convertChar(supplierID); 
     
     vo.setInventoryID(inventoryID);
     vo.setSupplierID(supplierID);
 
   }
   
   mav.addObject("list", list);
 

 
   HashMap<String, Object> hashMap = new HashMap<String, Object>();
   hashMap.put("inventoryStatus", inventoryVO.getInventoryStatus());
   hashMap.put("word", inventoryVO.getWord());
   
   int search_count = this.inventoryProc.search_count(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
   mav.addObject("search_count", search_count);
   
   /*
    * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
    * 18 19 20 [다음]
    * @param inventoryStatus 카테고리번호
    * @param now_page 현재 페이지
    * @param word 검색어
    * @param list_file 목록 파일명
    * @return 페이징용으로 생성된 HTML/CSS tag 문자열
    */
   String paging = inventoryProc.pagingBox(inventoryVO.getInventoryStatus(), inventoryVO.getNow_page(), inventoryVO.getWord(), "list_by_inventoryStatus.do", search_count);
   mav.addObject("paging", paging);
 
   // mav.addObject("now_page", now_page);
   
   mav.setViewName("/inventory/list_by_inventoryStatus");  // /inventory/list_by_inventoryStatus.jsp
 
   return mav;
 }
  
}






