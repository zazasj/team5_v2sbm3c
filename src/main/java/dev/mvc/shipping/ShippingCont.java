package dev.mvc.shipping;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.shipping.Shipping;
import dev.mvc.shipping.ShippingVO;
import dev.mvc.admin.AdminProcInter;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class ShippingCont {
  @Autowired // ShippingProcInter interface 구현한 객체를 만들어 자동으로 할당해라.
  @Qualifier("dev.mvc.shipping.ShippingProc")
  private ShippingProcInter shippingProc;
  

  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") // "dev.mvc.admin.AdminProc"라고 명명된 클래스
  private AdminProcInter adminProc; // AdminProcInter를 구현한 AdminProc 클래스의 객체를 자동으로 생성하여 할당
    
  public ShippingCont() {
    System.out.println("-> ShippingCont created.");  
  }

//  // FORM 출력, http://localhost:9092/shipping/create.do
//  @RequestMapping(value="/shipping/create.do", method = RequestMethod.GET)
//  @ResponseBody // 단순 문자열로 출력, jsp 파일명 조합이 발생하지 않음.
//  public String create() {
//    return "<h3>GET 방식 FORM 출력</h3>";
//  }
  @RequestMapping(value="/shipping/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();

    mav.setViewName(url); // forward
    
    return mav; // forward
  }
  // FORM 출력, http://localhost:9092/shipping/create.do
  @RequestMapping(value="/shipping/create.do", method = RequestMethod.GET)
  public ModelAndView create() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/shipping/create"); // /WEB-INF/views/shipping/create.jsp
    
    return mav;
  }
  
  // FORM 데이터 처리, http://localhost:9092/shipping/create.do
  @RequestMapping(value="/shipping/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpServletRequest request, HttpSession session, ShippingVO shippingVO) { // 자동으로 shippingVO 객체가 생성되고 폼의 값이 할당됨
    ModelAndView mav = new ModelAndView();
    
    
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
      // ------------------------------------------------------------------------------
      // 파일 전송 코드 시작
      // ------------------------------------------------------------------------------
      String file1 = "";          // 원본 파일명 image
      String file1saved = "";   // 저장된 파일명, image
      String thumb1 = "";     // preview image

      String upDir =  Shipping.getUploadDir(); // 파일을 업로드할 폴더 준비
      System.out.println("-> upDir: " + upDir);
      
      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      MultipartFile mf = shippingVO.getFile1MF();
      
      file1 = mf.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
      System.out.println("-> 원본 파일명 산출 file1: " + file1);
      
      if (Tool.checkUploadFile(file1) == true) { // 업로드 가능한 파일인지 검사
        long size1 = mf.getSize();  // 파일 크기
        
        if (size1 > 0) { // 파일 크기 체크
          // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
          file1saved = Upload.saveFileSpring(mf, upDir); 
          
          if (Tool.isImage(file1saved)) { // 이미지인지 검사
            // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
            thumb1 = Tool.preview(upDir, file1saved, 200, 150); 
          }
          
        }    
        
        shippingVO.setFile1(file1);   // 순수 원본 파일명
        shippingVO.setFile1saved(file1saved); // 저장된 파일명(파일명 중복 처리)
        shippingVO.setThumb1(thumb1);      // 원본이미지 축소판
        shippingVO.setSize1(size1);  // 파일 크기
        // ------------------------------------------------------------------------------
        // 파일 전송 코드 종료
        // ------------------------------------------------------------------------------
        
        // Call By Reference: 메모리 공유, Hashcode 전달
        
       
        int cnt = this.shippingProc.create(shippingVO); 
        
        // ------------------------------------------------------------------------------
        // PK의 return
        // ------------------------------------------------------------------------------
        // System.out.println("--> shippingID: " + shippingVO.getShippingno());
        // mav.addObject("shippingID", shippingVO.getShippingno()); // redirect parameter 적용
        // ------------------------------------------------------------------------------
        
        if (cnt == 1) {
            mav.addObject("code", "create_success");
            mav.setViewName("redirect:/shipping/list_by_shippingID.do"); // 주소 자동 이동
            // shippingProc.increaseCnt(shippingVO.getShippingID()); // 글수 증가
        } else {
            mav.addObject("code", "create_fail");
        }
        mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
        
        // System.out.println("--> shippingID: " + shippingVO.getShippingID());
        // redirect시에 hidden tag로 보낸것들이 전달이 안됨으로 request에 다시 저장
        //mav.addObject("shippingID", shippingVO.getShippingID()); // redirect parameter 적용
        
        //mav.addObject("url", "/shipping/msg"); // msg.jsp, redirect parameter 적용
        //mav.setViewName("redirect:/shipping/msg.do"); // Post -> Get - param...        
      } else {
        mav.addObject("cnt", "0"); // 업로드 할 수 없는 파일
        mav.addObject("code", "check_upload_file_fail"); // 업로드 할 수 없는 파일
        mav.addObject("url", "/shipping/msg"); // msg.jsp, redirect parameter 적용
        mav.setViewName("redirect:/shipping/msg.do"); // Post -> Get - param...        
      }
    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/shipping/msg.do"); 
    }
//    mav.addObject("cnt", 0); // request.setAttribute("cnt", cnt);
    
    return mav;
  }
  
  /**
   * 전체 목록
   * http://localhost:9092/shipping/list_all.do
   * @return
   */
  @RequestMapping(value="/shipping/list_all.do", method = RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/shipping/list_all"); // /WEB-INF/views/shipping/list_all.jsp
      
      ArrayList<ShippingVO> list = this.shippingProc.list_all();
     
      // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
      /*
       * for (ShippingVO shippingVO : list) { String shippingType =
       * shippingVO.getShippingType(); String deliveryStatus =
       * shippingVO.getDeliveryStatus();
       * 
       * shippingType = Tool.convertChar(shippingType); // 특수 문자 처리 deliveryStatus =
       * Tool.convertChar(deliveryStatus);
       * 
       * shippingVO.setShippingType(shippingType);
       * shippingVO.setDeliveryStatus(deliveryStatus);
       * 
       * }
       */
      /*
       * long size1 = shippingVO.getSize1(); String size1_label = Tool.unit(size1);
       * shippingVO.setSize1_label(size1_label);
       * 
       * mav.addObject("shippingVO", shippingVO);
       */
      mav.addObject("list", list);
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      
    }
    
    return mav;
  }
  
  /**
  * 목록 + 검색 + 페이징 지원 + Grid
  * 검색하지 않는 경우
  * http://localhost:9092/shipping/list_by_shippingID_grid.do?shippingID=2&word=&now_page=1
  * 검색하는 경우
  * http://localhost:9092/shipping/list_by_shippingID_grid.do?shippingID=2&word=탐험&now_page=1
  * 
  * @param shippingID
  * @param word
  * @param now_page
  * @return
  */
  @RequestMapping(value = "/shipping/list_by_shippingID_grid.do", method = RequestMethod.GET)
  public ModelAndView list_by_shippingID_grid(ShippingVO shippingVO) {
     ModelAndView mav = new ModelAndView();
   
     // 검색 목록
   ArrayList<ShippingVO> list = shippingProc.list_by_shippingID_search_paging(shippingVO);
   
   // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
   for (ShippingVO vo : list) {
     String shippingType = vo.getShippingType();
     String deliveryStatus = vo.getDeliveryStatus();
     
     shippingType = Tool.convertChar(shippingType);  // 특수 문자 처리
     deliveryStatus = Tool.convertChar(deliveryStatus); 
     
     vo.setShippingType(shippingType);
     vo.setDeliveryStatus(deliveryStatus);  
   
     }
     
     mav.addObject("list", list);
     
     HashMap<String, Object> hashMap = new HashMap<String, Object>();
     hashMap.put("shippingID", shippingVO.getShippingID());
     hashMap.put("word", shippingVO.getWord());
     
     int search_count = this.shippingProc.search_count(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
     mav.addObject("search_count", search_count);
   
     /*
  * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
  * 18 19 20 [다음]
  * @param shippingID 카테고리번호
  * @param now_page 현재 페이지
  * @param word 검색어
  * @param list_file 목록 파일명
  * @return 페이징용으로 생성된 HTML/CSS tag 문자열
  */
   String paging = shippingProc.pagingBox(shippingVO.getNow_page(), shippingVO.getWord(), "list_by_shippingID_grid.do", search_count);
   mav.addObject("paging", paging);
   
     // mav.addObject("now_page", now_page);
   
   mav.setViewName("/shipping/list_by_shippingID_grid");  // /shipping/list_by_shippingID_grid.jsp
   
     return mav;
   }

  /**
   * 조회
   * http://localhost:9092/shipping/read.do?shippingID=1
   * @return
   */
  @RequestMapping(value="/shipping/read.do", method = RequestMethod.GET)
  public ModelAndView read(int shippingID) { // int shippingID = (int)request.getParameter("shippingID");
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/shipping/read"); // /WEB-INF/views/shipping/read.jsp
    
    ShippingVO shippingVO = this.shippingProc.read(shippingID);
    mav.addObject("shippingVO", shippingVO);
    
    return mav;
  }
  /**
   * 파일 수정 폼
   * http://localhost:9092/shipping/update_file.do?shippingID=1
   * 
   * @return
   */
  @RequestMapping(value = "/shipping/update_file.do", method = RequestMethod.GET)
  public ModelAndView update_file(HttpSession session, int shippingID) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
      ShippingVO shippingVO = this.shippingProc.read(shippingID);
      mav.addObject("shippingVO", shippingVO);
      
      mav.setViewName("/shipping/update_file"); // /WEB-INF/views/shipping/update_file.jsp
      
    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/shipping/msg.do"); 
    }


    return mav; // forward
  }
  
  /**
   * 파일 수정 처리 http://localhost:9092/shipping/update_file.do
   * 
   * @return
   */
  @RequestMapping(value = "/shipping/update_file.do", method = RequestMethod.POST)
  public ModelAndView update_file(HttpSession session, ShippingVO shippingVO) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session)) {
      // 삭제할 파일 정보를 읽어옴, 기존에 등록된 레코드 저장용
      ShippingVO shippingVO_old = shippingProc.read(shippingVO.getShippingID());
      
      // -------------------------------------------------------------------
      // 파일 삭제 시작
      // -------------------------------------------------------------------
      String file1saved = shippingVO_old.getFile1saved();  // 실제 저장된 파일명
      String thumb1 = shippingVO_old.getThumb1();       // 실제 저장된 preview 이미지 파일명
      long size1 = 0;
         
      String upDir =  Shipping.getUploadDir(); // C:/kd/deploy/resort_v3sbm3c/shipping/storage/
      
      Tool.deleteFile(upDir, file1saved);  // 실제 저장된 파일삭제
      Tool.deleteFile(upDir, thumb1);     // preview 이미지 삭제
      // -------------------------------------------------------------------
      // 파일 삭제 종료
      // -------------------------------------------------------------------
          
      // -------------------------------------------------------------------
      // 파일 전송 시작
      // -------------------------------------------------------------------
      String file1 = "";          // 원본 파일명 image

      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      MultipartFile mf = shippingVO.getFile1MF();
          
      file1 = mf.getOriginalFilename(); // 원본 파일명
      size1 = mf.getSize();  // 파일 크기
          
      if (size1 > 0) { // 폼에서 새롭게 올리는 파일이 있는지 파일 크기로 체크 ★
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
        file1saved = Upload.saveFileSpring(mf, upDir); 
        
        if (Tool.isImage(file1saved)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 250, height: 200
          thumb1 = Tool.preview(upDir, file1saved, 250, 200); 
        }
        
      } else { // 파일이 삭제만 되고 새로 올리지 않는 경우
        file1="";
        file1saved="";
        thumb1="";
        size1=0;
      }
          
      shippingVO.setFile1(file1);
      shippingVO.setFile1saved(file1saved);
      shippingVO.setThumb1(thumb1);
      shippingVO.setSize1(size1);
      // -------------------------------------------------------------------
      // 파일 전송 코드 종료
      // -------------------------------------------------------------------
          
      this.shippingProc.update_file(shippingVO); // Oracle 처리

      mav.addObject("shippingID", shippingVO.getShippingID());
      mav.setViewName("redirect:/shipping/read.do"); // request -> param으로 접근 전환
                
    } else {
      mav.addObject("url", "/admin/login_need"); // login_need.jsp, redirect parameter 적용
      mav.setViewName("redirect:/shipping/msg.do"); // GET
    }

    // redirect하게되면 전부 데이터가 삭제됨으로 mav 객체에 다시 저장
    mav.addObject("now_page", shippingVO.getNow_page());
    
    return mav; // forward
  }


  /**
   * 수정폼
   * http://localhost:9092/shipping/update.do?shippingID=1
   * @return
   */
  @RequestMapping(value="/shipping/update.do", method = RequestMethod.GET)
  public ModelAndView update(HttpSession session, int shippingID, ShippingVO shippingVO) { // int shippingID = (int)request.getParameter("shippingID");
    ModelAndView mav = new ModelAndView();
    ArrayList<ShippingVO> list1 = this.shippingProc.list_all();
    mav.addObject("list", list1);
    
    ArrayList<ShippingVO> list = shippingProc.list_by_shippingID_search_paging(shippingVO); 
 // Check for null elements before processing
    if (list != null) {
        // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
        for (ShippingVO shippingvo2 : list) {
            if (shippingvo2 != null) {
                String deliveryStatus = shippingvo2.getDeliveryStatus();
                String shippingType = shippingvo2.getShippingType();
                int trackingNumber = shippingvo2.getTrackingNumber();
                int order_payno= shippingvo2.getOrder_payno();

                deliveryStatus = Tool.convertChar(deliveryStatus);  // 특수 문자 처리
                shippingType = Tool.convertChar(shippingType);

                shippingvo2.setDeliveryStatus(deliveryStatus);
                shippingvo2.setShippingType(shippingType);
                shippingvo2.setTrackingNumber(trackingNumber);
                shippingvo2.setOrder_payno(order_payno);
            }
        }
    }

    mav.addObject("list", list);

    HashMap<String, Object> hashMap = new HashMap<String, Object>();       
    hashMap.put("word", shippingVO.getWord());
    int search_count = this.shippingProc.search_count(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
    mav.addObject("search_count", search_count);

    String paging = shippingProc.pagingBox2(shippingVO.getShippingID(), shippingVO.getNow_page(), shippingVO.getWord(), "update.do", search_count);
    mav.addObject("paging", paging);
    
    if (this.adminProc.isAdmin(session) == true) {
      // mav.setViewName("/shipping/update"); // /WEB-INF/views/shipping/update.jsp
      mav.setViewName("/shipping/list_all_update"); // /WEB-INF/views/shipping/list_all_update.jsp
      
      ShippingVO shippingVO1 = this.shippingProc.read(shippingID);
      mav.addObject("shippingVO", shippingVO1);
      
      
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      
    }
        
    return mav;
  }
  
  /**
   * 수정 처리, http://localhost:9092/shipping/update.do
   * @param shippingVO 수정할 내용
   * @return
   */
  @RequestMapping(value="/shipping/update.do", method = RequestMethod.POST)
  public ModelAndView update(ShippingVO shippingVO) { // 자동으로 shippingVO 객체가 생성되고 폼의 값이 할당됨
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.shippingProc.update(shippingVO); // 수정 처리
    System.out.println("-> cnt: " + cnt);
    ShippingVO shippingVO_old = shippingProc.read(shippingVO.getShippingID());
    
    // -------------------------------------------------------------------
    // 파일 삭제 시작
    // -------------------------------------------------------------------
    String file1saved = shippingVO_old.getFile1saved();  // 실제 저장된 파일명
    String thumb1 = shippingVO_old.getThumb1();       // 실제 저장된 preview 이미지 파일명
    long size1 = 0;
       
    String upDir =  Shipping.getUploadDir(); // C:/kd/deploy/resort_v3sbm3c/shipping/storage/
    Tool.deleteFile(upDir, file1saved);  // 실제 저장된 파일삭제
    Tool.deleteFile(upDir, thumb1);     // preview 이미지 삭제
    // -------------------------------------------------------------------
    // 파일 삭제 종료
    // -------------------------------------------------------------------
        
    // -------------------------------------------------------------------
    // 파일 전송 시작
    // -------------------------------------------------------------------
    String file1 = "";          // 원본 파일명 image

    // 전송 파일이 없어도 file1MF 객체가 생성됨.
    // <input type='file' class="form-control" name='file1MF' id='file1MF' 
    //           value='' placeholder="파일 선택">
    MultipartFile mf = shippingVO.getFile1MF();
        
    file1 = mf.getOriginalFilename(); // 원본 파일명
    size1 = mf.getSize();  // 파일 크기
        
    if (size1 > 0) { // 폼에서 새롭게 올리는 파일이 있는지 파일 크기로 체크 ★
      // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
      file1saved = Upload.saveFileSpring(mf, upDir); 
      
      if (Tool.isImage(file1saved)) { // 이미지인지 검사
        // thumb 이미지 생성후 파일명 리턴됨, width: 250, height: 200
        thumb1 = Tool.preview(upDir, file1saved, 250, 200); 
      }
      
    } else { // 파일이 삭제만 되고 새로 올리지 않는 경우
      file1="";
      file1saved="";
      thumb1="";
      size1=0;
    }
        
    shippingVO.setFile1(file1);
    shippingVO.setFile1saved(file1saved);
    shippingVO.setThumb1(thumb1);
    shippingVO.setSize1(size1);
    // -------------------------------------------------------------------
    // 파일 전송 코드 종료
    // -------------------------------------------------------------------
        
    this.shippingProc.update(shippingVO); // Oracle 처리

    mav.addObject("shippingID", shippingVO.getShippingID());

  // redirect하게되면 전부 데이터가 삭제됨으로 mav 객체에 다시 저장
  mav.addObject("now_page", shippingVO.getNow_page());
    if (cnt == 1) {
      // mav.addObject("code", "update_success"); // 키, 값
      // mav.addObject("name", shippingVO.getName()); // 카테고리 이름 jsp로 전송
      mav.setViewName("redirect:/shipping/list_by_shippingID.do"); 
      
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/shipping/msg"); // /WEB-INF/views/shipping/msg.jsp
    }
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt);
//    mav.addObject("cnt", 0); // request.setAttribute("cnt", cnt);
    
    return mav;
  }
  
  
  
 /**
  * 파일 삭제 폼
  * http://localhost:9092/shipping/delete.do?shippingID=1
  * 
  * @return
  */
 @RequestMapping(value = "/shipping/delete.do", method = RequestMethod.GET)
 public ModelAndView delete(HttpSession session, int shippingID, ShippingVO shippingVO) {
   ModelAndView mav = new ModelAndView();
   ArrayList<ShippingVO> list1 = this.shippingProc.list_all();
   mav.addObject("list", list1);
   ArrayList<ShippingVO> list = shippingProc.list_by_shippingID_search_paging(shippingVO); 
   // Check for null elements before processing
      if (list != null) {
          // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
          for (ShippingVO shippingvo2 : list) {
              if (shippingvo2 != null) {
                  String deliveryStatus = shippingvo2.getDeliveryStatus();
                  String shippingType = shippingvo2.getShippingType();
                  int trackingNumber = shippingvo2.getTrackingNumber();
                  int order_payno= shippingvo2.getOrder_payno();

                  deliveryStatus = Tool.convertChar(deliveryStatus);  // 특수 문자 처리
                  shippingType = Tool.convertChar(shippingType);

                  shippingvo2.setDeliveryStatus(deliveryStatus);
                  shippingvo2.setShippingType(shippingType);
                  shippingvo2.setTrackingNumber(trackingNumber);
                  shippingvo2.setOrder_payno(order_payno);
              }
          }
      }

      mav.addObject("list", list);

      HashMap<String, Object> hashMap = new HashMap<String, Object>();       
      hashMap.put("word", shippingVO.getWord());
      int search_count = this.shippingProc.search_count(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
      mav.addObject("search_count", search_count);

      String paging = shippingProc.pagingBox2(shippingVO.getShippingID(), shippingVO.getNow_page(), shippingVO.getWord(), "delete.do", search_count);
      mav.addObject("paging", paging);
   if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
     ShippingVO shippingVO1 = this.shippingProc.read(shippingID);
     mav.addObject("shippingVO", shippingVO1);

     
     mav.setViewName("/shipping/list_all_delete"); // /WEB-INF/views/shipping/delete.jsp
     
   } else {
     mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
     mav.setViewName("redirect:/shipping/msg.do"); 
   }


   return mav; // forward
 }
 
 /**
  * 삭제 처리 http://localhost:9092/shipping/delete.do
  * 
  * @return
  */
 @RequestMapping(value = "/shipping/delete.do", method = RequestMethod.POST)
 public ModelAndView delete(ShippingVO shippingVO) {
   ModelAndView mav = new ModelAndView();
   
   // -------------------------------------------------------------------
   // 파일 삭제 시작
   // -------------------------------------------------------------------
   // 삭제할 파일 정보를 읽어옴.
   ShippingVO shippingVO_read = shippingProc.read(shippingVO.getShippingID());
   
   String file1saved = shippingVO_read.getFile1saved();
   String thumb1 = shippingVO_read.getThumb1();
   
   String uploadDir = Shipping.getUploadDir();
   Tool.deleteFile(uploadDir, file1saved);  // 실제 저장된 파일삭제
   Tool.deleteFile(uploadDir, thumb1);     // preview 이미지 삭제
   // -------------------------------------------------------------------
   // 파일 삭제 종료
   // -------------------------------------------------------------------
       
   this.shippingProc.delete(shippingVO.getShippingID()); // DBMS 삭제
       
   // -------------------------------------------------------------------------------------
   // 마지막 페이지의 마지막 레코드 삭제시의 페이지 번호 -1 처리
   // -------------------------------------------------------------------------------------    
   // 마지막 페이지의 마지막 10번째 레코드를 삭제후
   // 하나의 페이지가 3개의 레코드로 구성되는 경우 현재 9개의 레코드가 남아 있으면
   // 페이지수를 4 -> 3으로 감소 시켜야함, 마지막 페이지의 마지막 레코드 삭제시 나머지는 0 발생
   int now_page = shippingVO.getNow_page();
   
   HashMap<String, Object> hashMap = new HashMap<String, Object>();
   hashMap.put("shippingID", shippingVO.getShippingID());
   hashMap.put("word", shippingVO.getWord());
   
   if (shippingProc.search_count(hashMap) % Shipping.RECORD_PER_PAGE == 0) {
     now_page = now_page - 1; // 삭제시 DBMS는 바로 적용되나 크롬은 새로고침등의 필요로 단계가 작동 해야함.
     if (now_page < 1) {
       now_page = 1; // 시작 페이지
     }
   }
   // -------------------------------------------------------------------------------------

   mav.addObject("shippingID", shippingVO.getShippingID());
   mav.addObject("now_page", now_page);
   mav.setViewName("redirect:/shipping/list_by_shippingID.do"); 
   
   return mav;
 }
 @RequestMapping(value="/shipping/list_by_shippingID.do", method = RequestMethod.GET)
 public ModelAndView list_by_shippingID(HttpSession session, ShippingVO shippingVO) {
   ModelAndView mav = new ModelAndView();

   ArrayList<ShippingVO> list1 = this.shippingProc.list_all();
   mav.addObject("list", list1);
   ArrayList<ShippingVO> list = shippingProc.list_by_shippingID_search_paging(shippingVO); 
// Check for null elements before processing
   if (list != null) {
       // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
       for (ShippingVO shippingvo2 : list) {
           if (shippingvo2 != null) {
               String deliveryStatus = shippingvo2.getDeliveryStatus();
               String shippingType = shippingvo2.getShippingType();
               int trackingNumber = shippingvo2.getTrackingNumber();
               int order_payno= shippingvo2.getOrder_payno();

               deliveryStatus = Tool.convertChar(deliveryStatus);  // 특수 문자 처리
               shippingType = Tool.convertChar(shippingType);

               shippingvo2.setDeliveryStatus(deliveryStatus);
               shippingvo2.setShippingType(shippingType);
               shippingvo2.setTrackingNumber(trackingNumber);
               shippingvo2.setOrder_payno(order_payno);
           }
       }
   }

   mav.addObject("list", list);

   HashMap<String, Object> hashMap = new HashMap<String, Object>();       
   hashMap.put("word", shippingVO.getWord());
   int search_count = this.shippingProc.search_count(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
   mav.addObject("search_count", search_count);

   String paging = shippingProc.pagingBox(shippingVO.getNow_page(), shippingVO.getWord(), "list_by_shippingID.do", search_count);
   mav.addObject("paging", paging);

   mav.setViewName("/shipping/list_by_shippingID");  // /contents/list_by_cateno.jsp

   return mav;
}
  
}






