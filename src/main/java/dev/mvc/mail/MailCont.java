package dev.mvc.mail;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.tool.MailTool;

@Controller
public class MailCont {
    public MailCont() {
        System.out.println("-> MailCont created.");
      }
    
    // http://localhost:9091/mail/form.do
    /**
     * 사용자의 전화번호 입력 화면
     * @return
     */
    @RequestMapping(value = {"/mail/form.do"}, method=RequestMethod.GET)
    public ModelAndView form() {
      ModelAndView mav = new ModelAndView();
      mav.setViewName("/mail/form");  // /WEB-INF/views/mail/form.jsp

      return mav;
    }
    
    // http://localhost:9091/mail/send.do
    /**
     * 메일 전송
     * @return
     */
    @RequestMapping(value = {"/mail/send.do"}, method=RequestMethod.POST)
    public ModelAndView send(String receiver, String from, String title, String content) {
      ModelAndView mav = new ModelAndView();
      mav.setViewName("/mail/sended");  // /WEB-INF/views/mail/sended.jsp

      MailTool mailTool = new MailTool();
      mailTool.send(receiver, from, title, content); // 메일 전송
      
      return mav;
    }
    

}