package dev.mvc.team5;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.mvc.tool.Download;

// 서블릿을 등록함.
@Configuration
public class ServletRegister {
  @Bean
  public ServletRegistrationBean getServletRegistrationBean() {

      // dir: 파일이 저장되어 있는 폴더, filename: 실제 서버에 저장된 파일명, downname: 사용자에게 저장될 파일명
      // urlPatterns: /download?dir=/contents/storage&filename=winter_1.jpg&downname=winter.jpg
      // urlPatterns: /download?dir=/attachfile/storage&filename=winter_1.jpg&downname=winter.jpg
      ServletRegistrationBean registrationBean = new ServletRegistrationBean(new Download());
      registrationBean.addUrlMappings("/download"); // 접근 주소

      return registrationBean;
  }
}
