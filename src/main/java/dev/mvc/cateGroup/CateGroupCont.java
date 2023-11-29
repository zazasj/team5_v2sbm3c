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
  @Qualifier("dev.mvc.cateGroup.CateGroupProc")
  private CateGroupProcInter cateGroupProc;
  
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc;
  
  
  public CateGroupCont() {
    System.out.println("-> CateGroupCont created.");
  }
  
  //FORM �빊�뮆�젾, http://localhost:9093/cateGroup/create.do
 @RequestMapping(value="/cateGroup/create.do", method = RequestMethod.GET)
 public ModelAndView create() {
   ModelAndView mav = new ModelAndView();
   mav.setViewName("/cateGroup/create"); // /WEB-INF/views/cate/create.jsp
   
   return mav;
 }
  
 //FORM 占쎈쑓占쎌뵠占쎄숲 筌ｌ꼶�봺, http://localhost:9093/cateGroup/create.do
  @RequestMapping(value="/cateGroup/create.do", method = RequestMethod.POST)
  public ModelAndView create(CateGroupVO cateGroupVO) { // 占쎌쁽占쎈짗占쎌몵嚥∽옙 cateVO 揶쏆빘猿쒎첎占� 占쎄문占쎄쉐占쎈┷�⑨옙 占쎈쨲占쎌벥 揶쏅�れ뵠 占쎈막占쎈뼣占쎈쭡
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
   * 占쎌읈筌ｋ��걠嚥∽옙
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
   * 鈺곌퀬�돳
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
   * 占쎈땾占쎌젟占쎈쨲
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
   * 占쎈땾占쎌젟 筌ｌ꼶�봺, http://localhost:9093/cateGroup/update.do
   * @param cateGroupVO 占쎈땾占쎌젟占쎈막 占쎄땀占쎌뒠
   * @return
   */
  
  @RequestMapping(value="/cateGroup/update.do", method = RequestMethod.POST)
  public ModelAndView update(CateGroupVO cateGroupVO) { // 占쎌쁽占쎈짗占쎌몵嚥∽옙 cateVO 揶쏆빘猿쒎첎占� 占쎄문占쎄쉐占쎈┷�⑨옙 占쎈쨲占쎌벥 揶쏅�れ뵠 占쎈막占쎈뼣占쎈쭡
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.cateGroupProc.update(cateGroupVO); // 占쎈땾占쎌젟 筌ｌ꼶�봺
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
   *  占쎄텣占쎌젫FORM 
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
      
      // 占쎈뱟占쎌젟 燁삳똾�믤�⑥쥓�봺占쎈퓠 占쎈꺗占쎈립 占쎌쟿�굜遺얜굡 揶쏉옙占쎈땾�몴占� �뵳�뗪쉘
     // int count_by_cateno = this.contentsProc.count_by_cateno(cateno);
     // mav.addObject("count_by_cateno", count_by_cateno);
      
    } else {
      mav.setViewName("/admin/login_need");
   
    }
    
    return mav;
  }
  
  /**
   * 占쎄텣占쎌젫筌ｌ꼶�봺, http://localhost:9093/cateGroup/delete.do
   * @param cateGroupVO 占쎄텣占쎌젫占쎈막 占쎌쟿�굜遺얜굡 甕곕뜇�깈
   * @return 
   */
  @RequestMapping(value="/cateGroup/delete.do", method=RequestMethod.POST)
  public ModelAndView delete_proc(HttpSession session, int GrpID) { 
    
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
//      ArrayList<ContentsVO> list = this.contentsProc.list_by_cateno(cateno); // 占쎌쁽占쎈뻼 占쎌쟿�굜遺얜굡 筌뤴뫖以� 占쎌뵭疫뀐옙
//      
//      for(ContentsVO contentsVO : list) { // 占쎌쁽占쎈뻼 占쎌쟿�굜遺얜굡 �꽴占쏙옙�졃 占쎈솁占쎌뵬 占쎄텣占쎌젫
//        // -------------------------------------------------------------------
//        // 占쎈솁占쎌뵬 占쎄텣占쎌젫 占쎈뻻占쎌삂
//        // -------------------------------------------------------------------
//        String file1saved = contentsVO.getFile1saved();
//        String thumb1 = contentsVO.getThumb1();
//        
//        String uploadDir = Contents.getUploadDir();
//        Tool.deleteFile(uploadDir, file1saved);  // 占쎈뼄占쎌젫 占쏙옙占쎌삢占쎈쭆 占쎈솁占쎌뵬占쎄텣占쎌젫
//        Tool.deleteFile(uploadDir, thumb1);     // preview 占쎌뵠沃섎챷占� 占쎄텣占쎌젫
//        // -------------------------------------------------------------------
//        // 占쎈솁占쎌뵬 占쎄텣占쎌젫 �넫�굝利�
//        // -------------------------------------------------------------------
//      }
//      
//      this.contentsProc.delete_by_cateno(cateno); // 占쎌쁽占쎈뻼 占쎌쟿�굜遺얜굡 占쎄텣占쎌젫     
            
      int cnt = this.cateGroupProc.delete(GrpID); // 燁삳똾�믤�⑥쥓�봺 占쎄텣占쎌젫
      
      if (cnt == 1) {
        mav.setViewName("redirect:/cateGroup/list_all.do");       // 占쎌쁽占쎈짗 雅뚯눘�꺖 占쎌뵠占쎈짗, Spring 占쎌삺占쎌깈�빊占�
        
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
   * 占쎌뒭占쎄퐨 占쎈떄占쎌맄 占쎈꼥占쎌뿫, 10 占쎈쾻 -> 1 占쎈쾻, http://localhost:9093/cateGroup/update_seqno_forward.do?GrpID=1
   * @param cateGroupVO 占쎈땾占쎌젟占쎈막 占쎄땀占쎌뒠
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
   * 占쎌뒭占쎄퐨 占쎈떄占쎌맄 占쎄텤�빊占�, 1 占쎈쾻 -> 10 占쎈쾻, http://localhost:9093/cateGroup/update_seqno_backward.do?GrpID=1
   * @param GrpID 占쎈땾占쎌젟占쎈막 占쎌쟿�굜遺얜굡 PK 甕곕뜇�깈
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
   * 燁삳똾�믤�⑥쥓�봺 �⑤벀而� 占쎄퐬占쎌젟, http://localhost:9093/cateGroup/update_visible_y.do?GrpID=1
   * @param cateGroupID 占쎈땾占쎌젟占쎈막 占쎌쟿�굜遺얜굡 PK 甕곕뜇�깈
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
   * 燁삳똾�믤�⑥쥓�봺 �뜮袁㏓궗揶쏉옙 占쎄퐬占쎌젟, http://localhost:9093/cateGroup/update_visible_n.do?GrpID=1
   * @param GrpID 占쎈땾占쎌젟占쎈막 占쎌쟿�굜遺얜굡 PK 甕곕뜇�깈
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
