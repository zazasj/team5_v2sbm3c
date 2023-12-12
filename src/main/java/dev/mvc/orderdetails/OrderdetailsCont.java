package dev.mvc.orderdetails;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderdetailsCont {
  @Autowired 
  @Qualifier("dev.mvc.orderdetails.OrderdetailsProc")
  private OrderdetailsProcInter orderdetailsProc;
  
  public OrderdetailsCont() {
    System.out.println("-> OrderdetailsCont created.");
  }
  /**
   * 주문 결재/회원별 목록
   * http://localhost:9091/order_item/list_by_memberno.do 
   * @return
   */
  @RequestMapping(value="/orderdetails/list_by_memberno.do", method=RequestMethod.GET )
  public ModelAndView list_by_memberno(HttpSession session,
                                                        int order_payno) {
    ModelAndView mav = new ModelAndView();
    
    int baesong_tot = 0;   // 배송비 합계
    int tot_sum = 0;        // 할인 금액 총 합계(금액)
    int total_order = 0;     // 전체 주문 금액
    
    if (session.getAttribute("memberno") != null) { // 회원으로 로그인을 했다면 쇼핑카트로 이동
      int memberno = (int)session.getAttribute("memberno");
      
      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("order_payno", order_payno);
      map.put("memberno", memberno);
      
      List<OrderdetailsVO> list = this.orderdetailsProc.list_by_memberno(map);
      
      for (OrderdetailsVO orderdetailsVO: list) {
        tot_sum += orderdetailsVO.getTot();
      }
      
      if (tot_sum < 30000) { // 상품 주문 금액이 30,000 원 이하이면 배송비 3,000 원 부여
        baesong_tot = 3000;
      }
      
      total_order = tot_sum + baesong_tot; // 전체 주문 금액
      
      mav.addObject("baesong_tot", baesong_tot); // 배송비
      mav.addObject("total_order", total_order);     // 할인 금액 총 합계(금액)
      mav.addObject("list", list); // request.setAttribute("list", list);

      mav.setViewName("/orderdetails/list_by_memberno"); // /views/order_item/list_by_memberno.jsp
    } else { // 회원으로 로그인하지 않았다면
      mav.addObject("return_url", "/orderdetails/list_by_memberno.do"); // 로그인 후 이동할 주소 ★
      
      mav.setViewName("redirect:/member/login.do"); // /WEB-INF/views/member/login_ck_form.jsp
    }
    
    return mav;
  }
  
}