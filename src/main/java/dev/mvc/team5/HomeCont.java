package dev.mvc.team5;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.cateGroup.CateGroupProcInter;
import dev.mvc.cateGroup.CateGroupVO;
import dev.mvc.category.CategoryProcInter;
import dev.mvc.category.CategoryVO;
import dev.mvc.supplier.SupplierProcInter;
import dev.mvc.supplier.SupplierVO;
import lombok.extern.slf4j.Slf4j;


@RestController
public class HomeCont {
  @Autowired // CateProcInter interface 구현한 객체를 만들어 자동으로 할당해라.
  @Qualifier("dev.mvc.cateGroup.CateGroupProc")
  private CateGroupProcInter cateGroupProc;
  
  @Autowired // CateProcInter interface 구현한 객체를 만들어 자동으로 할당해라.
  @Qualifier("dev.mvc.category.CategoryProc")
  private CategoryProcInter categoryProc;
  
  @Autowired
  @Qualifier("dev.mvc.supplier.SupplierProc")
  private SupplierProcInter supplierProc;
  
  public HomeCont() {
    System.out.println("-> HomeCont created.");
  }
  
  @GetMapping(value = {"/index.do", "", "/",})
  public ModelAndView home() {
    System.out.println("-> home() ver 2.0");
    
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/index"); // /WEB-INF/views/index.jsp
        
    return mav;
  }
  
  // http://localhost:9091/menu/top.do
  @RequestMapping(value= {"/menu/top.do"}, method=RequestMethod.GET)
  public ModelAndView top() {
    ModelAndView mav = new ModelAndView();

    ArrayList<CateGroupVO> list_top = this.cateGroupProc.list_all_y();
    mav.addObject("list_top", list_top);
    
    ArrayList<CategoryVO> list_top2 = this.categoryProc.list_all();
    mav.addObject("list_top2", list_top2);
    
    mav.setViewName("/menu/top"); // /WEB-INF/views/menu/top.jsp
    
    return mav;
  }
  
}




