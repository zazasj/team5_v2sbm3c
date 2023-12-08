package dev.mvc.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrdersCont {
  @Autowired
  @Qualifier("dev.mvc.orders.OrdersProc")
  private OrdersProcInter ordersProc;
  
  @RequestMapping("/orders/orders.do")
  public String orders(Model model, @RequestParam int OrderID) {
    OrdersVO ordersVO = ordersProc.getOrderInfo(OrderID);
    model.addAttribute("orders", ordersVO);

    return "/orders/orders";
  }
  
  // 다른 메서드들도 필요에 따라 추가
}