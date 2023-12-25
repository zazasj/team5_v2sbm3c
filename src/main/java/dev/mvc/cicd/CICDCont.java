package dev.mvc.cicd;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CICDCont {
  // http://localhost:9091/cicd?sw=-1
  // http://localhost:9091/cicd?sw=0
  // http://localhost:9091/cicd?sw=1
  //http://localhost:9091/cicd?sw=2
  //http://localhost:9091/cicd?sw=3
  //54.66.10.137:9091/cicd?sw=3
  @GetMapping("/cicd")
  public String cicd(@RequestParam(defaultValue = "1") Integer sw) {
    String msg = "";
    
    if (sw == -1) {
      msg = "/cide 호출됨, error 로그";
      log.error(msg);
    } else if (sw == 0) {
      msg = "/cide 호출됨, warn 로그";
      log.warn(msg);
    } else if (sw == 1) {
      msg = "/cicd 호출됨, info 로그";
      log.info(msg);
    } else if (sw == 2) {
      msg = "/cide workflow 테스트";
      log.info(msg);
    }else if (sw == 3) {
      msg = "/cide Github action + EC2 최종 테스트";
      log.info(msg);
    }else if (sw == 4) {
      msg = "/cide Github action + EC2 최종 테스트2";
      log.info(msg);
    }
    
    return "<h3>" + msg + "</h3>";
  }
}