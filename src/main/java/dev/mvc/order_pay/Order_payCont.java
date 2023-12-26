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
import dev.mvc.cateGroup.CateGroupVO;
import dev.mvc.category.CategoryVO;
import dev.mvc.order_item.Order_itemProcInter;
import dev.mvc.order_item.Order_itemVO;
import dev.mvc.products.ProductsProcInter;
import dev.mvc.products.ProductsVO;
import dev.mvc.tool.Tool;
 
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
  public ModelAndView create(HttpSession session, String rname,
      String rtel,
      String rzipcode,
      String raddress1,
      String raddress2,
      int amount) {
      ModelAndView mav = new ModelAndView();
      
      Order_payVO order_payVO = new Order_payVO();
      
      int memberno = (int) session.getAttribute("memberno");
      order_payVO.setMemberno(memberno);
      order_payVO.setRname(rname);
      order_payVO.setRtel(rtel);
      order_payVO.setRzipcode(rzipcode);
      order_payVO.setRaddress1(raddress1);
      order_payVO.setRaddress2(raddress2);
      order_payVO.setAmount(amount);
      System.out.println(order_payVO.toString());
      // 주문 결제 등록
      int cnt = this.order_payProc.create(order_payVO);
      System.out.println(cnt);
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
  @RequestMapping(value = "/order_pay/list_by_memberno.do", method = RequestMethod.GET)
  public ModelAndView list_by_memberno(HttpSession session,
                                        @RequestParam(value = "word", defaultValue = "") String word,
                                        @RequestParam(value = "now_page", defaultValue = "1") int now_page
                                        ,Order_payVO order_payVO) {

      System.out.println("-> Order_pay_list_by_memberno now_page: " + now_page);

      ModelAndView mav = new ModelAndView();

      if (session.getAttribute("memberno") != null) {
          int memberno = (int) session.getAttribute("memberno");
          
          System.out.println(memberno);
          
          // Order_payVO 객체에 값을 설정합니다.
          order_payVO.setWord(word);
          order_payVO.setNow_page(now_page);

          // Order_payVO를 HashMap으로 변환
          HashMap<String, Object> hashMap = new HashMap<String, Object>();
          hashMap.put("memberno", memberno);
          hashMap.put("word", word);
          hashMap.put("now_page", now_page);

          ArrayList<Order_payVO> list = this.order_payProc.list_by_memberno_search_paging(order_payVO);

          for (Order_payVO order_payVO1 : list) {
              String rname = order_payVO1.getRname();
              String raddress1 = order_payVO1.getRaddress1();

              rname = Tool.convertChar(rname);  // 특수 문자 처리
              raddress1 = Tool.convertChar(raddress1);

              order_payVO1.setRname(rname);
              order_payVO1.setRaddress1(raddress1);
          }

          // 검색 목록
          mav.addObject("list", list);

          int search_count = order_payProc.search_count(hashMap);
          mav.addObject("search_count", search_count);
          
          Order_payVO VO = order_payProc.read_by_memberno(order_payVO.getMemberno());
          mav.addObject("order_payVO", VO);

          String paging = order_payProc.pagingBox(order_payVO.getMemberno(), order_payVO.getNow_page(), order_payVO.getWord(), "list_by_memberno.do", search_count);

          mav.addObject("paging", paging);

          mav.addObject("now_page", now_page);

          mav.setViewName("/order_pay/list_by_memberno");

      } else {
          mav.addObject("return_url", "/order_pay/list_by_memberno.do");
          mav.setViewName("redirect:/member/login.do");
      }

      return mav;
  }

}