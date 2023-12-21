package dev.mvc.favproduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;

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
public class FavProductCont {
	@Autowired 
	@Qualifier("dev.mvc.favproduct.FavProductProc")
	private FavProductProcInter favproductProc;
	
	public FavProductCont() {
	    System.out.println("-> FavProductCont created.");
	}
	
	@RequestMapping(value="/favproduct/create.do", method=RequestMethod.GET)
	@ResponseBody
	public String createFromGet(HttpSession session, @RequestParam int productID) {
	    // FavProductVO에 필요한 데이터를 설정
	    FavProductVO favproductVO = new FavProductVO();
	    favproductVO.setProductID(productID);
	    
	    int memberno = (Integer)session.getAttribute("memberno");
	    System.out.println("Retrieved memberno from session: " + memberno);

	    favproductVO.setMemberno(memberno);

	    // FavProductProcInter를 통해 데이터를 생성
	    int favID = this.favproductProc.create(favproductVO);

	    JSONObject json = new JSONObject();
	    json.put("favID", favID); // 생성된 favID를 JSON에 추가

	    System.out.println("-> create favproduct from GET: " + json.toString());

	    System.out.println("Received productID from GET: " + productID);
	    return json.toString();
	}
	
	@RequestMapping(value="/favproduct/create.do", method=RequestMethod.POST)
	@ResponseBody
	public String create(HttpSession session, int productID) {
		// Logger 인스턴스 생성
	    Logger logger = LoggerFactory.getLogger(this.getClass());

	    // productID 값 로깅
	    logger.debug("Received productID from request: {}", productID);
	    System.out.println("Received productID: " + productID);

		// FavProductVO에 필요한 데이터를 설정
		FavProductVO favproductVO = new FavProductVO();
		favproductVO.setProductID(productID);
		
	    int memberno = (Integer)session.getAttribute("memberno");
	    System.out.println("Retrieved memberno from session: " + memberno);

	    favproductVO.setMemberno(memberno);

	    // FavProductProcInter를 통해 데이터를 생성
	    int favID = this.favproductProc.create(favproductVO);

	    JSONObject json = new JSONObject();
	    json.put("favID", favID); // 생성된 favID를 JSON에 추가

	    System.out.println("-> create favproduct: " + json.toString());

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
	  @RequestMapping(value="/favproduct/list_by_memberno.do", method=RequestMethod.GET )
	  public ModelAndView list_by_memberno(HttpSession session,
	      @RequestParam(value="favproductID", defaultValue="0") int favID ) {
	    ModelAndView mav = new ModelAndView();

	    
	    if (session.getAttribute("memberno") != null) { // 회원으로 로그인을 했다면 쇼핑카트로 이동
	      int memberno = (int)session.getAttribute("memberno");
	      
	      // 출력 순서별 출력
	      ArrayList<FavProductVO> list = this.favproductProc.list_by_memberno(memberno);
	          
	      mav.addObject("list", list); // request.setAttribute("list", list);
	      mav.addObject("favID", favID); // 쇼핑계속하기에서 사용
	      
	      
	      mav.setViewName("/favproduct/list_by_memberno"); // /WEB-INF/views/categrp/list_by_memberno.jsp
	      
	    } else { // 회원으로 로그인하지 않았다면
	      mav.addObject("return_url", "/favproduct/list_by_memberno.do"); // 로그인 후 이동할 주소 ★
	      
	      mav.setViewName("redirect:/member/login.do"); // /WEB-INF/views/member/login_ck_form.jsp
	    }
	    return mav;
	  }
	  
	  /**
	   * 상품 삭제
	   * http://localhost:9091/favproduct/delete.do
	   * @return
	   */
	  @RequestMapping(value="/favproduct/delete.do", method=RequestMethod.POST )
	  public ModelAndView delete(HttpSession session,
	      @RequestParam(value="favID", defaultValue="0") int favID ) {
	    ModelAndView mav = new ModelAndView();
	    
	    this.favproductProc.delete(favID);      
	    mav.setViewName("redirect:/favproduct/list_by_memberno.do");
	    
	    return mav;
	  }
	  
	  
	// AJAX를 통해 관심상품 확인 및 추가 처리
	  // requestparam 뒤에 , HttpSession session 이거 추가했었음
	    @ResponseBody
	    @RequestMapping(value = "/favproduct/check.do", method = RequestMethod.GET)
	    public HashMap<String, Object> checkFavorite(@RequestParam int productID, @RequestParam(required=false) Integer memberno) {
	        HashMap<String, Object> map = new HashMap<>();
	        map.put("productID", productID);
	        map.put("memberno", memberno);

	        HashMap<String, Object> resultMap = new HashMap<>();
	        
	        if (memberno == null) {
	            resultMap.put("error", "Member number is required.");
	            return resultMap;
	        }
	        
	        // 세션에서 로그인 정보 확인
	        // int sessionmemberno = (int)session.getAttribute("memberno");

	        // 이미 관심상품인지 확인
	        int cnt = favproductProc.checkIfFavorite(map);

	        if (cnt > 0) {
	            resultMap.put("isFavorite", true);
	        } else {
	            resultMap.put("isFavorite", false);
	            // 관심상품에 추가하는 로직을 여기에 추가할 수 있습니다.
	        }

	        // resultMap.put("isLoggedIn", true);
	        return resultMap;

	    }
	  
	}

