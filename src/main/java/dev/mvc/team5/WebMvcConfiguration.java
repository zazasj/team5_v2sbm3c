package dev.mvc.team5;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dev.mvc.event.Events;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
      // Windows: path = "C:/kd/deploy/resort_v2sbm3c_blog/contents/storage";
      // ?ñ∂ file:///C:/kd/deploy/resort_v2sbm3c_blog/contents/storage
    
      // Ubuntu: path = "/home/ubuntu/deploy/resort_v2sbm3c_blog/contents/storage";
      // ?ñ∂ file:////home/ubuntu/deploy/resort_v2sbm3c_blog/contents/storage
    
      // JSP ?ù∏?ãù?êò?äî Í≤ΩÎ°ú: http://localhost:9091/contents/storage";
      registry.addResourceHandler("/event/storage/**").addResourceLocations("file:///" +  Events.getUploadDir());
      
      // JSP ?ù∏?ãù?êò?äî Í≤ΩÎ°ú: http://localhost:9091/attachfile/storage";
      // registry.addResourceHandler("/attachfile/storage/**").addResourceLocations("file:///" +  Tool.getOSPath() + "/attachfile/storage/");
      
      // JSP ?ù∏?ãù?êò?äî Í≤ΩÎ°ú: http://localhost:9091/member/storage";
      // registry.addResourceHandler("/member/storage/**").addResourceLocations("file:///" +  Tool.getOSPath() + "/member/storage/");
  }
}
