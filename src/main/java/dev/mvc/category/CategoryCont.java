package dev.mvc.category;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryCont {
  
  @Autowired
  @Qualifier("dev.mvc.category.CategoryProc")
  private CategoryProcInter categoryProc;
  
  public CategoryCont() {
    System.out.println("-> CategoryCont created.");
  }
  
  @RequestMapping(value = "/category/create.do", method = RequestMethod.GET)
  public ModelAndView create() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/category/create");
    
    return mav;
  }
  
  @RequestMapping(value = "/category/create.do", method = RequestMethod.POST)
  public ModelAndView create(CategoryVO categoryVO) {
    ModelAndView mav = new ModelAndView();
    
    
    int cnt = this.categoryProc.create(categoryVO);
    
    if(cnt == 1) {
//      mav.addObject("code", "create_success");
//      mav.addObject("name", categoryVO.getName());
      mav.setViewName("redirect:/category/list_all.do");
    }
    else {
      mav.addObject("code", "create_fail");
      mav.setViewName("/category/msg");
    }
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
    
    return mav;
  }
  
  /**
   * 전체목록
   * http://localhost:9093/category/list_all.do
   * @return
   */
  @RequestMapping(value = "/category/list_all.do", method = RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    

    mav.setViewName("/category/list_all"); // /WEB-INF/view/category/list_all
      
    ArrayList<CategoryVO> list = this.categoryProc.list_all();
    mav.addObject("list", list);
    
    return mav;
  }

  /**
   * 조회
   * http://localhost:9093/category/read.do?categoryID=1
   * @return
   */
  @RequestMapping(value = "/category/read.do", method = RequestMethod.GET)
  public ModelAndView read(int categoryID) { // int categoryID= (int)request.getParameter("categoryID")
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/category/read"); // /WEB-INF/view/category/read
    
    CategoryVO categoryVO = this.categoryProc.read(categoryID);
    mav.addObject("categoryVO", categoryVO);
    
    return mav;
  }
  
  /**
   * 수정폼
   * http://localhost:9093/category/update.do?categoryID=1
   * @return
   */
  @RequestMapping(value="/category/update.do", method = RequestMethod.GET)
  public ModelAndView update(HttpSession session, int categoryID) { // int genreno = (int)request.getParameter("genreno");
    ModelAndView mav = new ModelAndView();
    // mav.setViewName("/category/update"); // /WEB-INF/views/category/update.jsp
    
    mav.setViewName("/category/list_all_update"); // /WEB-INF/views/movieb/list_all_update.jsp
      
    CategoryVO categoryVO = this.categoryProc.read(categoryID);
    mav.addObject("categoryVO", categoryVO);
      
    ArrayList<CategoryVO> list = this.categoryProc.list_all();
    mav.addObject("list", list);
    
    return mav;
  }
  
  /**
   * 수정 처리, http://localhost:9093/category/update.do
   * @param categoryVO 수정할 내용
   * @return
   */
  
  @RequestMapping(value="/category/update.do", method = RequestMethod.POST)
  public ModelAndView update(CategoryVO categoryVO) { // 자동으로 moviebVO 객체가 생성되고 폼의 값이 할당됨
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.categoryProc.update(categoryVO); // 수정 처리
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      mav.setViewName("redirect:/category/list_all.do"); 
      
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/category/msg"); // /WEB-INF/views/category/msg.jsp
    }
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt);
    // mav.addObject("cnt", 0); // request.setAttribute("cnt", cnt);
    
    return mav;
  }
  
  /**
   *  삭제FORM 
   *  http://localhost:9093/category/delete.do?categoryID=1
   * @param categoryID
   * @return
   */
  @RequestMapping(value = "/category/delete.do", method = RequestMethod.GET)
  public ModelAndView delete(HttpSession session, int categoryID) { // int genreno= (int)request.getParameter("genreno")
    ModelAndView mav = new ModelAndView();
    
    mav.setViewName("/category/list_all_delete");
    //mav.setViewName("/category/delete"); // /WEB-INF/view/category/update.jsp
    
    CategoryVO categoryVO = this.categoryProc.read(categoryID);
    mav.addObject("categoryVO", categoryVO);
    
    ArrayList<CategoryVO> list = this.categoryProc.list_all();
    mav.addObject("list", list);
    
    return mav;
  }
  
  /**
   * 삭제처리, http://localhost:9093/category/delete.do
   * @param categoryVO 삭제할 레코드 번호
   * @return 
   */
  @RequestMapping(value = "/category/delete.do", method=RequestMethod.POST)
  // 자동으로 cateVO 객체 생성, 폼의 값이 할당 됨.
  public ModelAndView delete_proc(int categoryID) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/category/msg");
    
    CategoryVO categoryVO = this.categoryProc.read(categoryID); // 삭제할 레코드 정보 읽기
    
    int cnt = this.categoryProc.delete(categoryID); // 삭제 처리
    System.out.println("-> cnt : " + cnt);
    
    if(cnt == 1) {
      mav.addObject("code", "delete_success"); // 키 값
      mav.addObject("name", categoryVO.getName()); // 카테고리 이름 jsp로 전송
    }
    else {
      mav.addObject("code", "delete_fail");
    }
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
    
    return mav;
  }
  
  /**
   * 우선 순위 높임, 10 등 -> 1 등, http://localhost:9093/category/update_seqno_forward.do?genreno=1
   * @param categoryVO 수정할 내용
   * @return
   */
  @RequestMapping(value="/category/update_seqno_forward.do", method = RequestMethod.GET)
  public ModelAndView update_seqno_forward(int categoryID) {
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.categoryProc.update_seqno_forward(categoryID);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      mav.setViewName("redirect:/category/list_all.do"); 
      
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/category/msg"); // /WEB-INF/views/category/msg.jsp
    }
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt);
//    mav.addObject("cnt", 0); // request.setAttribute("cnt", cnt);
    
    return mav;
  }
  
  /**
   * 우선 순위 낮춤, 1 등 -> 10 등, http://localhost:9093/category/update_seqno_backward.do?genreno=1
   * @param categoryID 수정할 레코드 PK 번호
   * @return
   */
  @RequestMapping(value="/category/update_seqno_backward.do", method = RequestMethod.GET)
  public ModelAndView update_seqno_backward(int categoryID) {
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.categoryProc.update_seqno_backward(categoryID);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      mav.setViewName("redirect:/category/list_all.do"); 
      
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/category/msg"); // /WEB-INF/views/category/msg.jsp
    }
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt);
//    mav.addObject("cnt", 0); // request.setAttribute("cnt", cnt);
    
    return mav;
  }
  
  /**
   * 카테고리 공개 설정, http://localhost:9093/category/update_visible_y.do?genreno=1
   * @param categoryID 수정할 레코드 PK 번호
   * @return
   */
  @RequestMapping(value="/category/update_visible_y.do", method = RequestMethod.GET)
  public ModelAndView update_visible_y(int categoryID) { 
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.categoryProc.update_visible_y(categoryID);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      mav.setViewName("redirect:/category/list_all.do"); 
      
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/category/msg"); // /WEB-INF/views/category/msg.jsp
    }
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt);
    
    return mav;
  }
  
  /**
   * 카테고리 비공개 설정, http://localhost:9093/category/update_visible_n.do?genreno=1
   * @param categoryID 수정할 레코드 PK 번호
   * @return
   */
  @RequestMapping(value="/category/update_visible_n.do", method = RequestMethod.GET)
  public ModelAndView update_visible_n(int categoryID) { 
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.categoryProc.update_visible_n(categoryID);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      mav.setViewName("redirect:/category/list_all.do"); 
      
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/category/msg"); // /WEB-INF/views/category/msg.jsp
    }
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt);
    
    return mav;
  }
  
}
