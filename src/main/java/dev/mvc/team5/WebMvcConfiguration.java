package dev.mvc.team5;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dev.mvc.event.Events;
import dev.mvc.products.Products;
import dev.mvc.shipping.Shipping;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
      // Windows: path = "C:/kd/deploy/resort_v2sbm3c_blog/contents/storage";
      // ?�� file:///C:/kd/deploy/resort_v2sbm3c_blog/contents/storage
    
      // Ubuntu: path = "/home/ubuntu/deploy/resort_v2sbm3c_blog/contents/storage";
      // ?�� file:////home/ubuntu/deploy/resort_v2sbm3c_blog/contents/storage
    
      // JSP ?��?��?��?�� 경로: http://localhost:9091/contents/storage";
      registry.addResourceHandler("/event/storage/**").addResourceLocations("file:///" +  Events.getUploadDir());
      registry.addResourceHandler("/products/storage/**").addResourceLocations("file:///" +  Products.getUploadDir());
      registry.addResourceHandler("/shipping/storage/**").addResourceLocations("file:///" +  Shipping.getUploadDir());
      
      // JSP ?��?��?��?�� 경로: http://localhost:9091/attachfile/storage";
      // registry.addResourceHandler("/attachfile/storage/**").addResourceLocations("file:///" +  Tool.getOSPath() + "/attachfile/storage/");
      
      // JSP ?��?��?��?�� 경로: http://localhost:9091/member/storage";
      // registry.addResourceHandler("/member/storage/**").addResourceLocations("file:///" +  Tool.getOSPath() + "/member/storage/");
  }
}
