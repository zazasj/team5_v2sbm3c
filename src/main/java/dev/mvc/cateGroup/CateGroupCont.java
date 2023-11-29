package dev.mvc.cateGroup;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.tool.Tool;

@Controller
public class CateGroupCont {
  
  @Autowired
  @Qualifier("dev.mvc.cateGroup.CateGrouopProc")
  private CateGroupProcInter cateGroupProc;
  
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") // "dev.mvc.admin.AdminProc"라고 명명된 클래스
  private AdminProcInter adminProc; // AdminProcInter를 구현한 AdminProc 클래스의 객체를 자동으로 생성하여 할당
  
  
  public CateGroupCont() {
    System.out.println("-> CateGroupCont created.");
  }
  
  //FORM 출력, http://localhost:9093/cateGroup/create.do
 @RequestMapping(value="/cateGroup/create.do", method = RequestMethod.GET)
 public ModelAndView create() {
   ModelAndView mav = new ModelAndView();
   mav.setViewName("/cateGroup/create"); // /WEB-INF/views/cate/create.jsp
   
   return mav;
 }
  
 //FORM 데이터 처리, http://localhost:9093/cateGroup/create.do
  @RequestMapping(value="/cateGroup/create.do", method = RequestMethod.POST)
  public ModelAndView create(CateGroupVO cateGroupVO) { // 자동으로 cateVO 객체가 생성되고 폼의 값이 할당됨
  ModelAndView mav = new ModelAndView();
  
  int cnt = this.cateGroupProc.create(cateGroupVO);
  System.out.println("-> cnt: " + cnt);
  
  if (cnt == 1) {
    mav.setViewName("redirect:/cateGroup/list_all.do");
  } else {
    mav.addObject("code", "create_fail");
    mav.setViewName("/cateGroup/msg");
  }
  
  mav.addObject("cnt", cnt);
  
  return mav;
}
  
  /**
   * 전체목록
   * http://localhost:9093/cateGroup/list_all.do
   * @return
   */
  @RequestMapping(value="/cateGroup/list_all.do", method = RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/cateGroup/list_all");
      
      ArrayList<CateGroupVO> list = this.cateGroupProc.list_all();
      mav.addObject("list", list);
      
    } else {
      mav.setViewName("/admin/login_need");
      
    }
    
    return mav;
  }

  /**
   * 조회
   * http://localhost:9093/cateGroup/read.do?GrpID=1
   * @return
   */
  @RequestMapping(value="/cateGroup/read.do", method = RequestMethod.GET)
  public ModelAndView read(int GrpID) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/cateGroup/read");
    
    CateGroupVO cateGroupVO = this.cateGroupProc.read(GrpID);
    mav.addObject("cateGroupVO", cateGroupVO);
    
    return mav;
  }
  
  /**
   * 수정폼
   * http://localhost:9093/cateGroup/update.do?GrpID=1
   * @return
   */
  @RequestMapping(value="/cateGroup/update.do", method = RequestMethod.GET)
  public ModelAndView update(HttpSession session, int GrpID) { 
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/cateGroup/list_all_update");
      
      CateGroupVO cateGroupVO = this.cateGroupProc.read(GrpID);
      mav.addObject("cateGroupVO", cateGroupVO);
      
      ArrayList<CateGroupVO> list = this.cateGroupProc.list_all();
      mav.addObject("list", list);
      
    } else {
      mav.setViewName("/admin/login_need");
      
    }
        
    return mav;
  }
  
  /**
   * 수정 처리, http://localhost:9093/cateGroup/update.do
   * @param cateGroupVO 수정할 내용
   * @return
   */
  
  @RequestMapping(value="/cateGroup/update.do", method = RequestMethod.POST)
  public ModelAndView update(CateGroupVO cateGroupVO) { // 자동으로 cateVO 객체가 생성되고 폼의 값이 할당됨
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.cateGroupProc.update(cateGroupVO); // 수정 처리
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      mav.setViewName("redirect:/cateGroup/list_all.do"); 
      
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/cate/msg");
    }
    
    mav.addObject("cnt", cnt);
    
    return mav;
  }
  
  /**
   *  삭제FORM 
   *  http://localhost:9093/cateGroup/delete.do?GrpID=1
   * @param GrpID
   * @return
   */
  @RequestMapping(value="/cateGroup/delete.do", method = RequestMethod.GET)
  public ModelAndView delete(HttpSession session, int GrpID) { // int cateno = (int)request.getParameter("cateno");
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/cateGroup/list_all_delete");
      
      CateGroupVO cateGroupVO = this.cateGroupProc.read(GrpID);
      mav.addObject("cateGroupVO", cateGroupVO);
      
      ArrayList<CateGroupVO> list = this.cateGroupProc.list_all();
      mav.addObject("list", list);
      
      // 특정 카테고리에 속한 레코드 갯수를 리턴
     // int count_by_cateno = this.contentsProc.count_by_cateno(cateno);
     // mav.addObject("count_by_cateno", count_by_cateno);
      
    } else {
      mav.setViewName("/admin/login_need");
   
    }
    
    return mav;
  }
  
  /**
   * 삭제처리, http://localhost:9093/cateGroup/delete.do
   * @param cateGroupVO 삭제할 레코드 번호
   * @return 
   */
  @RequestMapping(value="/cateGroup/delete.do", method=RequestMethod.POST)
  public ModelAndView delete_proc(HttpSession session, int GrpID) { 
    
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
//      ArrayList<ContentsVO> list = this.contentsProc.list_by_cateno(cateno); // 자식 레코드 목록 읽기
//      
//      for(ContentsVO contentsVO : list) { // 자식 레코드 관련 파일 삭제
//        // -------------------------------------------------------------------
//        // 파일 삭제 시작
//        // -------------------------------------------------------------------
//        String file1saved = contentsVO.getFile1saved();
//        String thumb1 = contentsVO.getThumb1();
//        
//        String uploadDir = Contents.getUploadDir();
//        Tool.deleteFile(uploadDir, file1saved);  // 실제 저장된 파일삭제
//        Tool.deleteFile(uploadDir, thumb1);     // preview 이미지 삭제
//        // -------------------------------------------------------------------
//        // 파일 삭제 종료
//        // -------------------------------------------------------------------
//      }
//      
//      this.contentsProc.delete_by_cateno(cateno); // 자식 레코드 삭제     
            
      int cnt = this.cateGroupProc.delete(GrpID); // 카테고리 삭제
      
      if (cnt == 1) {
        mav.setViewName("redirect:/cateGroup/list_all.do");       // 자동 주소 이동, Spring 재호출
        
      } else {
        mav.addObject("code", "delete_fail");
        mav.setViewName("/cateGroup/msg"); // /WEB-INF/views/cate/msg.jsp
      }
      
      mav.addObject("cnt", cnt);
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
    
    return mav;
  }
  
  /**
   * 우선 순위 높임, 10 등 -> 1 등, http://localhost:9093/cateGroup/update_seqno_forward.do?GrpID=1
   * @param cateGroupVO 수정할 내용
   * @return
   */
  @RequestMapping(value="/cateGroup/update_seqno_forward.do", method = RequestMethod.GET)
  public ModelAndView update_seqno_forward(int GrpID) {
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.cateGroupProc.update_seqno_forward(GrpID);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      mav.setViewName("redirect:/cateGroup/list_all.do"); 
      
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/cateGroup/msg");
    }
    
    mav.addObject("cnt", cnt);
    
    return mav;
  }
  
  /**
   * 우선 순위 낮춤, 1 등 -> 10 등, http://localhost:9093/cateGroup/update_seqno_backward.do?GrpID=1
   * @param GrpID 수정할 레코드 PK 번호
   * @return
   */
  @RequestMapping(value="/cateGroup/update_seqno_backward.do", method = RequestMethod.GET)
  public ModelAndView update_seqno_backward(int GrpID) {
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.cateGroupProc.update_seqno_backward(GrpID);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      mav.setViewName("redirect:/cateGroup/list_all.do"); 
      
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/cateGroup/msg");
    }
    
    mav.addObject("cnt", cnt);
    
    return mav;
  }
  
  /**
   * 카테고리 공개 설정, http://localhost:9093/cateGroup/update_visible_y.do?GrpID=1
   * @param cateGroupID 수정할 레코드 PK 번호
   * @return
   */
  @RequestMapping(value="/cateGroup/update_visible_y.do", method = RequestMethod.GET)
  public ModelAndView update_visible_y(int GrpID) { 
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.cateGroupProc.update_visible_y(GrpID);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      mav.setViewName("redirect:/cateGroup/list_all.do"); 
      
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/cateGroup/msg");
    }
    
    mav.addObject("cnt", cnt);
    
    return mav;
  }
  
  /**
   * 카테고리 비공개 설정, http://localhost:9093/cateGroup/update_visible_n.do?GrpID=1
   * @param GrpID 수정할 레코드 PK 번호
   * @return
   */
  @RequestMapping(value="/cateGroup/update_visible_n.do", method = RequestMethod.GET)
  public ModelAndView update_visible_n(int GrpID) { 
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.cateGroupProc.update_visible_n(GrpID);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      mav.setViewName("redirect:/cateGroup/list_all.do"); 
      
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/cateGroup/msg");
    }
    
    mav.addObject("cnt", cnt);
    
    return mav;
  }
  
}
