package dev.mvc.event;

import java.io.File;

public class Events {
	
  public static synchronized String getUploadDir() {
    String path = "";
    if (File.separator.equals("\\")) { // windows, 개발 환경의 파일 업로드 폴더
        // path = "C:/kd/deploy/resort_v2sbm3c/contents/storage/";
        path="C:\\kd\\ws_java\\team5_v2sbm3c\\storage\\";
        // System.out.println("Windows 10: " + path);
        
    } else { // Linux, AWS, 서비스용 배치 폴더 
        // System.out.println("Linux");
        path = "/home/ubuntu/deploy/resort_v4sbm3c/contents/storage/";
    }
    
    return path;
}

}
