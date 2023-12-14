package dev.mvc.recommend;

import java.util.List;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RecommendCont {

      @Autowired
      @Qualifier("dev.mvc.recommend.RecommendProc")
      private RecommendProcInter recommendProc;

      @GetMapping("/products")
      @RequestMapping("/recommend/recommendProducts.do")
      public String getRecommendedProducts(@RequestParam(defaultValue = "1") int page, @RequestParam String grpid, Model model) {
          int pageSize = 10; // 한 페이지당 표시할 제품 수
          List<ProductVO> products = recommendProc.getRecommendProducts(grpid);
          int totalProducts = products.size();
          int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

          // 현재 페이지에 해당하는 제품 목록 추출
          int startIdx = (page - 1) * pageSize;
          int endIdx = Math.min(startIdx + pageSize, totalProducts);
          List<ProductVO> displayedProducts = products.subList(startIdx, endIdx);

          model.addAttribute("products", displayedProducts);
          model.addAttribute("totalPages", totalPages);
          model.addAttribute("currentPage", page);
          model.addAttribute("grpid", grpid);

          return "recommendProducts";
      }
}
