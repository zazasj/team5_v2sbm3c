package dev.mvc.carts;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.cateGroup.CateGroupVO;

@Controller
public class CartsCont {
  @Autowired 
  @Qualifier("dev.mvc.carts.CartsProc")
  private CartsProcInter cartsProc;
  
  public CartsCont() {
    System.out.println("-> CartsCont created.");
  }
 
  // http://localhost:9091/cart/create.do
  /**
   * Ajax 등록 처리
   * INSERT INTO cart(cartno, contentsno, memberno, cnt, rdate)
   * VALUES(cart_seq.nextval, #{contentsno}, #{memberno}, #{cnt}, sysdate)
   * @param categrpVO
   * @return
   */
  @RequestMapping(value="/carts/create.do", method=RequestMethod.POST )
  @ResponseBody
  public String create(HttpSession session,
                            int productID) {
    CartsVO cartsVO = new CartsVO();
    cartsVO.setProductID(productID);
    
    int memberno = (Integer)session.getAttribute("memberno");
    cartsVO.setMemberno(memberno);
    
    cartsVO.setCnt(1); // 최초 구매 수량 1개로 지정
    
    int cnt = this.cartsProc.create(cartsVO); // 등록 처리
    
    JSONObject json = new JSONObject();
    json.put("cnt", cnt);
    
    System.out.println("-> cartsCont create: " + json.toString());

    return json.toString();
  }
  
  /**
   * 회원별 목록
   * 할인 금액 합계 = 할인 금액 * 수량
   * 할인 금액 총 합계 = 할인 금액 총 합계 + 할인 금액 합계
   * 포인트 합계 = 포인트 합계 + (포인트 * 수량)
   * 배송비 = 3000
   * 전체 주문 금액 = 할인 금액 총 합계 + 배송비
   * http://localhost:9091/cart/list_by_memberno.do
   * @return
   */
  @RequestMapping(value="/carts/list_by_memberno.do", method=RequestMethod.GET )
  public ModelAndView list_by_memberno(HttpSession session,
      @RequestParam(value="cartID", defaultValue="0") int cartID ) {
    ModelAndView mav = new ModelAndView();
    
    int tot = 0;               // 할인 금액 합계 = 할인 금액 * 수량
    int tot_sum = 0;        // 할인 금액 총 합계 = 할인 금액 총 합계 + 할인 금액 합계
    int baesong_tot = 0;   // 배송비 합계
    int total_order = 0; // 전체 주문 금액
    
    if (session.getAttribute("memberno") != null) { // 회원으로 로그인을 했다면 쇼핑카트로 이동
      int memberno = (int)session.getAttribute("memberno");
      
      // 출력 순서별 출력
      ArrayList<CartsVO> list = this.cartsProc.list_by_memberno(memberno);
      
      for (CartsVO cartsVO : list) {
        tot = cartsVO.getPrice() * cartsVO.getCnt();  // 할인 금액 합계 = 할인 금액 * 수량
        cartsVO.setTot(tot);
        
        // 할인 금액 총 합계 = 할인 금액 총 합계 + 할인 금액 합계
        tot_sum = tot_sum + cartsVO.getTot();
        
      }
      
      if (tot_sum < 30000) { // 상품 주문 금액이 30,000 원 이하이면 배송비 3,000 원 부여
        if (list.size() > 0) {  // 총 주문 금액이 30,000 이하이면서 상품이 존재한다면 3,000 할당
          baesong_tot = 3000;
        }
      }
      
      total_order = tot_sum + baesong_tot; // 전체 주문 금액
          
      mav.addObject("list", list); // request.setAttribute("list", list);
      mav.addObject("cartID", cartID); // 쇼핑계속하기에서 사용
      
      mav.addObject("tot_sum", tot_sum);
      mav.addObject("baesong_tot", baesong_tot);
      mav.addObject("total_ordering", total_order);
      
      mav.setViewName("/carts/list_by_memberno"); // /WEB-INF/views/categrp/list_by_memberno.jsp
      
    } else { // 회원으로 로그인하지 않았다면
      mav.addObject("return_url", "/carts/list_by_memberno.do"); // 로그인 후 이동할 주소 ★
      
      mav.setViewName("redirect:/member/login.do"); // /WEB-INF/views/member/login_ck_form.jsp
    }
    return mav;
  }
  
  /**
   * 상품 삭제
   * http://localhost:9093/carts/delete.do
   * @return
   */
  @RequestMapping(value="/carts/delete.do", method=RequestMethod.POST )
  public ModelAndView delete(HttpSession session,
      @RequestParam(value="cartID", defaultValue="0") int cartID ) {
    ModelAndView mav = new ModelAndView();
    
    this.cartsProc.delete(cartID);      
    mav.setViewName("redirect:/carts/list_by_memberno.do");
    
    return mav;
  }
  
  /**
   * 수량 변경, http://localhost:9093/carts/delete.do
   * @param session
   * @param cartID 쇼핑카트 번호
   * @param cnt 수량
   * @return 변경된 레코드 갯수
   */
  @RequestMapping(value="/carts/update_cnt.do", method=RequestMethod.POST )
  public ModelAndView update_cnt(HttpSession session,
                                                   @RequestParam(value="cartID", defaultValue="0") int cartID,
                                                    int cnt) {
    ModelAndView mav = new ModelAndView();
    
    CartsVO cartsVO = new CartsVO();
    cartsVO.setCartID(cartID);
    cartsVO.setCnt(cnt);
    
    this.cartsProc.update_cnt(cartsVO);      
    mav.setViewName("redirect:/carts/list_by_memberno.do");
    
    return mav;
  }
  
}
  
