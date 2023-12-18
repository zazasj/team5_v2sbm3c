package dev.mvc.like;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LikeCont {
  
  @Autowired
  @Qualifier("dev.mvc.like.LikeProc")
  private LikeProcInter likeProc;
  
  @ResponseBody
  @RequestMapping(value = "/like/create.do", 
                              method = RequestMethod.POST,
                              produces = "text/plain;charset=UTF-8")
  public int create(int productid, String memberno) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("productid", productid);
    map.put("memberno", memberno);
 
    int cnt = likeProc.create(map);   
    
    return cnt;
  }
  
  @ResponseBody
  @RequestMapping(value = "/like/delete.do", 
                              method = RequestMethod.POST,
                              produces = "text/plain;charset=UTF-8")
  public int delete(int productid, String memberno) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("productid", productid);
    map.put("memberno", memberno);
 
    int cnt = likeProc.delete(map);   
    
    return cnt;
  }
  
  @ResponseBody
  @RequestMapping(value = "/like/like_check.do", 
                              method = RequestMethod.POST,
                              produces = "text/plain;charset=UTF-8")
  public ModelAndView like_check(int productid, int memberno,String word,String cate) {
    ModelAndView mav = new ModelAndView();
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("productid", productid);
    map.put("memberno", memberno);
 
    int cnt = likeProc.like_check(map);
    
    //있으면 없애고 없으면 만들어야함. 
    if(cnt == 1) {
      int de_com = likeProc.de_recom(productid);
      likeProc.delete(map);
      int recomnum = likeProc.read(productid);
      mav.addObject("cnt", de_com);
      mav.addObject("recom", recomnum);
    }else {
      int in_com = likeProc.in_recom(productid);
      int createnum = likeProc.create(map);
      int recomnum = likeProc.read(productid);
      
      mav.addObject("cnt", in_com);
      mav.addObject("recom", recomnum);  
    }
    String wor = word;
    if(word == null ) {
      wor = "";
    }
    String category = cate;
    if(category == null ) {
      category = "";
    }
    String url = "/products/read.do?productID="+productid+"&word="+wor+"&now_page=1&categoryID="+category;
    mav.setViewName("redirect:" + url);
    
    return mav;
  }
  
  



}
