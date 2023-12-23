package dev.mvc.order_pay;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.carts.CartsProcInter;
import dev.mvc.carts.CartsVO;
import dev.mvc.order_item.Order_itemProcInter;
import dev.mvc.order_item.Order_itemVO;
import dev.mvc.products.ProductsProcInter;
import dev.mvc.products.ProductsVO;
 
@Controller
public class Order_payCont {
//  @Autowired
//  @Qualifier("dev.mvc.member.MemberProc")
//  private MemberProcInter memberProc = null;
  
  @Autowired 
  @Qualifier("dev.mvc.carts.CartsProc")
  private CartsProcInter cartsProc;
  
  @Autowired 
  @Qualifier("dev.mvc.order_pay.Order_payProc")
  private Order_payProc order_payProc;
  
  @Autowired 
  @Qualifier("dev.mvc.order_item.Order_itemProc")
  private Order_itemProcInter order_itemProc;

  @Autowired 
  @Qualifier("dev.mvc.products.ProductsProc")
  private ProductsProcInter ProductsProc;
  
  public Order_payCont() {
    System.out.println("-> Order_payCont created.");
  }
  
  // http://localhost:9091/order_pay/create.do
  /**
  * 등록 폼
  * @return
  */
  @RequestMapping(value = "/order_pay/create.do", method = RequestMethod.GET)
  public ModelAndView create(HttpSession session) {
      ModelAndView mav = new ModelAndView();

      int tot_sum = 0;        // 할인 금액 총 합계 = 할인 금액 총 합계 + 할인 금액 합계
      int baesong_tot = 0;    // 배송비 합계
      int total_order = 0;    // 전체 주문 금액

      int memberno = (int) session.getAttribute("memberno");

      // 쇼핑카트에 등록된 상품 목록을 가져옴
      List<CartsVO> list = this.cartsProc.list_by_memberno(memberno);

      for (CartsVO cartsVO : list) {
          int tot = cartsVO.getPrice() * cartsVO.getCnt();  // 할인 금액 합계 = 할인 금액 * 수량
          cartsVO.setTot(tot);

          // 할인 금액 총 합계 = 할인 금액 총 합계 + 할인 금액 합계
          tot_sum = tot_sum + cartsVO.getTot();
      }

      Order_payVO order_payVO = new Order_payVO();
      // (생략) orderpayVO 초기화 또는 필요한 로직 추가

      // 배송비 계산
      if (tot_sum < 30000) {
          baesong_tot = 3000;
      }

      total_order = tot_sum + baesong_tot; // 전체 주문 금액

      mav.addObject("list", list);
      mav.addObject("tot_sum", tot_sum);
      mav.addObject("point_tot", 0); // point_tot은 계산이 없어서 0으로 설정
      mav.addObject("baesong_tot", baesong_tot);
      mav.addObject("total_order", total_order);
      mav.addObject("order_payVO", order_payVO); // 주문 정보를 뷰에 전달

      mav.setViewName("/order_pay/create"); // webapp/WEB-INF/views/order_pay/create.jsp

      return mav;
  }
  
  // http://localhost:9091/order_pay/create.do
  /**
   * 주문 결재 등록 처리
   * @param order_payVO
   * @return
   */
  @RequestMapping(value = "/order_pay/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpSession session, @ModelAttribute("order_payVO") Order_payVO order_payVO) {
      ModelAndView mav = new ModelAndView();

      int memberno = (int) session.getAttribute("memberno");
      order_payVO.setMemberno(memberno);

      // 주문 결제 등록
      int cnt = this.order_payProc.create(order_payVO);
      int order_payno = order_payVO.getOrder_payno(); // 결재 번호 수집

      Order_itemVO order_itemVO = new Order_itemVO();

      if (cnt == 1) { // 정상적으로 주문 결재 정보가 등록된 경우
          List<CartsVO> list = this.cartsProc.list_by_memberno(memberno);

          for (CartsVO cartsVO : list) {
              int productID = cartsVO.getProductID();
              int cartID = cartsVO.getCartID();

              order_itemVO.setMemberno(memberno);
              order_itemVO.setOrder_payno(order_payno);
              order_itemVO.setProductid(productID);
              order_itemVO.setCnt(cartsVO.getCnt());

              ProductsVO productsVO = this.ProductsProc.read(productID);
              int tot = productsVO.getPrice() * cartsVO.getCnt();
              order_itemVO.setTot(tot);

              // 주문 상태(stateno):  1: 결재 완료, 2: 상품 준비중, 3: 배송 시작, 4: 배달중, 5: 오늘 도착, 6: 배달 완료
              order_itemVO.setStateno(1); // 신규 주문 등록임으로 1

              this.order_itemProc.create(order_itemVO);

              // 주문된 상품 cart에서 DELETE
              int delete_cnt = this.cartsProc.delete(cartID);
              System.out.println("-> delete_cnt: " + delete_cnt + " 건 주문후 cart에서 삭제됨.");
          }
      } else {
          // 결재 실패했다는 에러 페이지 등 제작 필요 (여기서는 생략)
      }

      mav.addObject("memberno", memberno);
      mav.setViewName("redirect:/order_pay/list_by_memberno.do"); // 정상일 경우만 발생한다고 가정, 에러 페이지 이동 생략

      return mav;
  }

  /**
   * 회원별 전체 목록, 로그인이 안되어 있으면 로그인 후 목록 출력
   * http://localhost:9091/order_pay/list_by_memberno.do 
   * @return
   */
  @RequestMapping(value="/order_pay/list_by_memberno.do", method=RequestMethod.GET )
  public ModelAndView list_by_memberno(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (session.getAttribute("memberno") != null) { // 회원으로 로그인을 했다면 쇼핑카트로 이동
      int memberno = (int)session.getAttribute("memberno");
      
      List<Order_payVO> list = this.order_payProc.list_by_memberno(memberno);
      mav.addObject("list", list); // request.setAttribute("list", list);

      mav.setViewName("/order_pay/list_by_memberno"); // /views/order_pay/list_by_memberno.jsp   
      
    } else { // 회원으로 로그인하지 않았다면
      mav.addObject("return_url", "/order_pay/list_by_memberno.do"); // 로그인 후 이동할 주소 ★
      
      mav.setViewName("redirect:/member/login.do"); // /WEB-INF/views/member/login_ck_form.jsp
    }

    return mav;
  }

}