package dev.mvc.recentrecom;

import java.util.List;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RecentRecomCont {
  @Autowired
  @Qualifier("dev.mvc.recentrecom.RecentRecomProc")
  private RecentRecomProcInter recentRecomProc;

  @RequestMapping("/recentrecom/list_recent_products.do")
  public String list_recent_products(Model model) {
      List<RecentRecomVO> recentProducts = recentRecomProc.list_recent_products();
      model.addAttribute("recentProducts", recentProducts);
      return "/recentrecom/list_recent_products";
  }
  
}