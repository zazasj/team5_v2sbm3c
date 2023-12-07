package dev.mvc.products;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.category.CategoryProcInter;
import dev.mvc.category.CategoryVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class ProductsCont {
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") // @Component("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc;
  
  @Autowired
  @Qualifier("dev.mvc.category.CategoryProc")  // @Component("dev.mvc.cate.CateProc")
  private CategoryProcInter categoryProc;
  
  @Autowired
  @Qualifier("dev.mvc.products.ProductsProc") // @Component("dev.mvc.products.ContentsProc")
  private ProductsProcInter productsProc;
  
  public ProductsCont () {
    System.out.println("-> ProductsCont created.");
  }
  
  /**
   * POST 요청시 JSP 페이지에서 JSTL 호출 기능 지원, 새로고침 방지, EL에서 param으로 접근
   * POST → url → GET → 데이터 전송
   * @return
   */
  @RequestMapping(value="/products/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();

    mav.setViewName(url); // forward
    
    return mav; // forward
  }
  
  // 등록 폼
  @RequestMapping(value="/products/create.do", method = RequestMethod.GET)
  public ModelAndView create(int CategoryID) {
//  public ModelAndView create(HttpServletRequest request,  int CategoryID) {
    ModelAndView mav = new ModelAndView();

    CategoryVO categoryVO = this.categoryProc.read(CategoryID); // create.jsp에 카테고리 정보를 출력하기위한 목적
    mav.addObject("categoryVO", categoryVO);
//    request.setAttribute("categoryVO", categoryVO);
    
    mav.setViewName("/products/create"); // /webapp/WEB-INF/views/products/create.jsp
    
    return mav;
  }
  
  /**
   * 등록 처리 http://localhost:9091/products/create.do
   * 
   * @return
   */
  @RequestMapping(value = "/products/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpServletRequest request, HttpSession session, ProductsVO productsVO) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
      // ------------------------------------------------------------------------------
      // 파일 전송 코드 시작
      // ------------------------------------------------------------------------------
      String ImageFile = "";          // 원본 파일명 image
      String ImageFileSaved = "";   // 저장된 파일명, image
      String Thumbs = "";     // preview image

      String upDir =  Products.getUploadDir(); // 파일을 업로드할 폴더 준비
      System.out.println("-> upDir: " + upDir);
      
      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      MultipartFile mf = productsVO.getFileMF();
      
      ImageFile = mf.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
      System.out.println("-> 원본 파일명 산출 file1: " + ImageFile);
      
      if (Tool.checkUploadFile(ImageFile) == true) { // 업로드 가능한 파일인지 검사
        long sizes = mf.getSize();  // 파일 크기
        
        if (sizes > 0) { // 파일 크기 체크
          // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
          ImageFileSaved = Upload.saveFileSpring(mf, upDir); 
          
          if (Tool.isImage(ImageFileSaved)) { // 이미지인지 검사
            // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
            Thumbs = Tool.preview(upDir, ImageFileSaved, 200, 150); 
          }
          
        }    
        
        productsVO.setImageFile(ImageFile);   // 순수 원본 파일명
        productsVO.setImageFileSaved(ImageFileSaved); // 저장된 파일명(파일명 중복 처리)
        productsVO.setThumbs(Thumbs);      // 원본이미지 축소판
        productsVO.setSizes(sizes);  // 파일 크기
        // ------------------------------------------------------------------------------
        // 파일 전송 코드 종료
        // ------------------------------------------------------------------------------
        
        // Call By Reference: 메모리 공유, Hashcode 전달
        int Adminno = (int)session.getAttribute("Adminno"); // adminno FK
        productsVO.setAdminno(Adminno);
        int cnt = this.productsProc.create(productsVO); 
        
        // ------------------------------------------------------------------------------
        // PK의 return
        // ------------------------------------------------------------------------------
        // System.out.println("--> ProductID: " + productsVO.getProductID());
        // mav.addObject("ProductID", productsVO.getProductID()); // redirect parameter 적용
        // ------------------------------------------------------------------------------
        
        if (cnt == 1) {
            mav.addObject("code", "create_success");
            // categoryProc.increaseCnt(productsVO.getCategoryID()); // 글수 증가
        } else {
            mav.addObject("code", "create_fail");
        }
        mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
        
        // System.out.println("--> CategoryID: " + productsVO.getCategoryID());
        // redirect시에 hidden tag로 보낸것들이 전달이 안됨으로 request에 다시 저장
        mav.addObject("CategoryID", productsVO.getCategoryID()); // redirect parameter 적용
        
        mav.addObject("url", "/products/msg"); // msg.jsp, redirect parameter 적용
        mav.setViewName("redirect:/products/msg.do"); // Post -> Get - param...        
      } else {
        mav.addObject("cnt", "0"); // 업로드 할 수 없는 파일
        mav.addObject("code", "check_upload_file_fail"); // 업로드 할 수 없는 파일
        mav.addObject("url", "/products/msg"); // msg.jsp, redirect parameter 적용
        mav.setViewName("redirect:/products/msg.do"); // Post -> Get - param...        
      }
    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/products/msg.do"); 
    }
    
    return mav; // forward
  }

  /**
   * 전체 목록, 관리자만 사용 가능
   * http://localhost:9091/products/list_all.do
   * @return
   */
  @RequestMapping(value="/products/list_all.do", method = RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/products/list_all"); // /WEB-INF/views/products/list_all.jsp
      
      ArrayList<ProductsVO> list = this.productsProc.list_all();
     
      // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
      for (ProductsVO productsVO : list) {
        String PName = productsVO.getPName();
        String Description = productsVO.getDescription();
        
        PName = Tool.convertChar(PName);  // 특수 문자 처리
        Description = Tool.convertChar(Description); 
        
        productsVO.setPName(PName);
        productsVO.setDescription(Description);  

      }
      
      mav.addObject("list", list);
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      
    }
    
    return mav;
  }
  
//  /**
//   * 특정 카테고리의 검색 목록
//   * http://localhost:9091/products/list_by_CategoryID.do?CategoryID=1
//   * @return
//   */
//  @RequestMapping(value="/products/list_by_CategoryID.do", method = RequestMethod.GET)
//  public ModelAndView list_by_CategoryID(int CategoryID, String word) {
//    ModelAndView mav = new ModelAndView();
//
//    mav.setViewName("/products/list_by_CategoryID"); // /WEB-INF/views/products/list_by_CategoryID.jsp
//    
//    CategoryVO categoryVO = this.categoryProc.read(CategoryID); // create.jsp에 카테고리 정보를 출력하기위한 목적
//    mav.addObject("categoryVO", categoryVO);
//    // request.setAttribute("categoryVO", categoryVO);
//    
//    // 검색하지 않는 경우
//    // ArrayList<ContentsVO> list = this.productsProc.list_by_CategoryID(CategoryID);
//
//    // 검색하는 경우
//    HashMap<String, Object> hashMap = new HashMap<String, Object>();
//    hashMap.put("CategoryID", CategoryID);
//    hashMap.put("word", word);
//    
//    ArrayList<ContentsVO> list = this.productsProc.list_by_CategoryID_search(hashMap);
//    
//    // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
//    for (ContentsVO productsVO : list) {
//      String title = productsVO.getTitle();
//      String content = productsVO.getContent();
//      
//      title = Tool.convertChar(title);  // 특수 문자 처리
//      content = Tool.convertChar(content); 
//      
//      productsVO.setTitle(title);
//      productsVO.setContent(content);  
//
//    }
//    
//    mav.addObject("list", list);
//    
//    return mav;
//  }
  
   /**
   * 목록 + 검색 + 페이징 지원
   * 검색하지 않는 경우
   * http://localhost:9091/products/list_by_CategoryID.do?CategoryID=2&word=&now_page=1
   * 검색하는 경우
   * http://localhost:9091/products/list_by_CategoryID.do?CategoryID=2&word=탐험&now_page=1
   * 
   * @param CategoryID
   * @param word
   * @param now_page
   * @return
   */
  @RequestMapping(value = "/products/list_by_categoryID.do", method = RequestMethod.GET)
  public ModelAndView list_by_CategoryID(ProductsVO productsVO) {
    ModelAndView mav = new ModelAndView();
  
    // 검색 목록
    ArrayList<ProductsVO> list = productsProc.list_by_categoryID_search_paging(productsVO);
    
    // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
    for (ProductsVO vo : list) {
      String PName = vo.getPName();
      String Description = vo.getDescription();
      
      PName = Tool.convertChar(PName);  // 특수 문자 처리
      Description = Tool.convertChar(Description); 
      
      vo.setPName(PName);
      vo.setDescription(Description);  
  
    }
    
    mav.addObject("list", list);
  
    CategoryVO categoryVO = categoryProc.read(productsVO.getCategoryID());
    mav.addObject("categoryVO", categoryVO);
  
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("categoryID", productsVO.getCategoryID());
    hashMap.put("word", productsVO.getWord());
    
    int search_count = this.productsProc.search_count(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
    mav.addObject("search_count", search_count);
    
    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param CategoryID 카테고리번호
     * @param now_page 현재 페이지
     * @param word 검색어
     * @param list_file 목록 파일명
     * @return 페이징용으로 생성된 HTML/CSS tag 문자열
     */
    String paging = productsProc.pagingBox(productsVO.getCategoryID(), productsVO.getNow_page(), productsVO.getWord(), "list_by_categoryID.do", search_count);
    mav.addObject("paging", paging);
  
    // mav.addObject("now_page", now_page);
    
    mav.setViewName("/products/list_by_categoryID");  // /products/list_by_CategoryID.jsp
  
    return mav;
  }

  /**
  * 목록 + 검색 + 페이징 지원 + Grid
  * 검색하지 않는 경우
  * http://localhost:9091/products/list_by_CategoryID_grid.do?CategoryID=2&word=&now_page=1
  * 검색하는 경우
  * http://localhost:9091/products/list_by_CategoryID_grid.do?CategoryID=2&word=탐험&now_page=1
  * 
  * @param CategoryID
  * @param word
  * @param now_page
  * @return
  */
  @RequestMapping(value = "/products/list_by_categoryID_grid.do", method = RequestMethod.GET)
  public ModelAndView list_by_CategoryID_grid(ProductsVO productsVO) {
     ModelAndView mav = new ModelAndView();
   
     // 검색 목록
   ArrayList<ProductsVO> list = productsProc.list_by_categoryID_search_paging(productsVO);
   
   // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
   for (ProductsVO vo : list) {
     String PName = vo.getPName();
     String Description = vo.getDescription();
     
     PName = Tool.convertChar(PName);  // 특수 문자 처리
     Description = Tool.convertChar(Description); 
       
       vo.setPName(PName);
       vo.setDescription(Description);  
   
     }
     
     mav.addObject("list", list);
   
     CategoryVO categoryVO = categoryProc.read(productsVO.getCategoryID());
     mav.addObject("categoryVO", categoryVO);
     
     HashMap<String, Object> hashMap = new HashMap<String, Object>();
     hashMap.put("categoryID", productsVO.getCategoryID());
     hashMap.put("word", productsVO.getWord());
     
     int search_count = this.productsProc.search_count(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
     mav.addObject("search_count", search_count);
   
     /*
  * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
  * 18 19 20 [다음]
  * @param CategoryID 카테고리번호
  * @param now_page 현재 페이지
  * @param word 검색어
  * @param list_file 목록 파일명
  * @return 페이징용으로 생성된 HTML/CSS tag 문자열
  */
   String paging = productsProc.pagingBox(productsVO.getCategoryID(), productsVO.getNow_page(), productsVO.getWord(), "list_by_categoryID_grid.do", search_count);
   mav.addObject("paging", paging);
   
     // mav.addObject("now_page", now_page);
   
   mav.setViewName("/products/list_by_categoryID_grid");  // /products/list_by_CategoryID_grid.jsp
   
     return mav;
   }
 
 
  /**
   * 조회
   * http://localhost:9091/products/read.do?ProductID=17
   * @return
   */
  @RequestMapping(value="/products/read.do", method = RequestMethod.GET)
  public ModelAndView read(int ProductID) { // int CategoryID = (int)request.getParameter("CategoryID");
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/products/read"); // /WEB-INF/views/products/read.jsp
    
    ProductsVO productsVO = this.productsProc.read(ProductID);
    
    String PName = productsVO.getPName();
    String Description = productsVO.getDescription();
    
    PName = Tool.convertChar(PName);  // 특수 문자 처리
    Description = Tool.convertChar(Description); 
    
    productsVO.setPName(PName);
    productsVO.setDescription(Description);  
    
    long size = productsVO.getSizes();
    String sizes_label = Tool.unit(size);
    productsVO.setSizes_label(sizes_label);
    
    mav.addObject("productsVO", productsVO);
    
    CategoryVO categoryVO = this.categoryProc.read(productsVO.getCategoryID());
    mav.addObject("categoryVO", categoryVO);
    
    return mav;
  }
  
  /**
   * 수정 폼
   * http://localhost:9091/products/update_text.do?ProductID=1
   * 
   * @return
   */
  @RequestMapping(value = "/products/update_text.do", method = RequestMethod.GET)
  public ModelAndView update_text(HttpSession session, int ProductID) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
      ProductsVO productsVO = this.productsProc.read(ProductID);
      mav.addObject("productsVO", productsVO);
      
      CategoryVO categoryVO = this.categoryProc.read(productsVO.getCategoryID());
      mav.addObject("categoryVO", categoryVO);
      
      mav.setViewName("/products/update_text"); // /WEB-INF/views/products/update_text.jsp
      // String content = "장소:\n인원:\n준비물:\n비용:\n기타:\n";
      // mav.addObject("content", content);

    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/products/msg.do"); 
    }

    return mav; // forward
  }
  
  /**
   * 수정 처리
   * http://localhost:9091/products/update_text.do?ProductID=1
   * 
   * @return
   */
  @RequestMapping(value = "/products/update_text.do", method = RequestMethod.POST)
  public ModelAndView update_text(HttpSession session, ProductsVO productsVO) {
    ModelAndView mav = new ModelAndView();
    
    // System.out.println("-> word: " + productsVO.getWord());
    
    if (this.adminProc.isAdmin(session)) { // 관리자 로그인 확인
      HashMap<String, Object> hashMap = new HashMap<String, Object>();
      hashMap.put("ProductID", productsVO.getProductID());
      this.productsProc.update_text(productsVO); // 글수정  
       
      // mav 객체 이용
      mav.addObject("ProductID", productsVO.getProductID());
      mav.addObject("CategoryID", productsVO.getCategoryID());
      mav.setViewName("redirect:/products/read.do"); // 페이지 자동 이동

    
    mav.addObject("now_page", productsVO.getNow_page()); // POST -> GET: 데이터 분실이 발생함으로 다시하번 데이터 저장 ★
    }
    // URL에 파라미터의 전송
    // mav.setViewName("redirect:/products/read.do?ProductID=" + productsVO.getProductID() + "&CategoryID=" + productsVO.getCategoryID());             
    
    return mav; // forward
  }

  /**
   * 파일 수정 폼
   * http://localhost:9091/products/update_file.do?ProductID=1
   * 
   * @return
   */
  @RequestMapping(value = "/products/update_file.do", method = RequestMethod.GET)
  public ModelAndView update_file(HttpSession session, int ProductID) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
      ProductsVO productsVO = this.productsProc.read(ProductID);
      mav.addObject("productsVO", productsVO);
      
      CategoryVO categoryVO = this.categoryProc.read(productsVO.getCategoryID());
      mav.addObject("categoryVO", categoryVO);
      
      mav.setViewName("/products/update_file"); // /WEB-INF/views/products/update_file.jsp
      
    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/products/msg.do"); 
    }


    return mav; // forward
  }
  
  /**
   * 파일 수정 처리 http://localhost:9091/products/update_file.do
   * 
   * @return
   */
  @RequestMapping(value = "/products/update_file.do", method = RequestMethod.POST)
  public ModelAndView update_file(HttpSession session, ProductsVO productsVO) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session)) {
      // 삭제할 파일 정보를 읽어옴, 기존에 등록된 레코드 저장용
      ProductsVO productsVO_old = productsProc.read(productsVO.getProductID());
      
      // -------------------------------------------------------------------
      // 파일 삭제 시작
      // -------------------------------------------------------------------
      String ImageFileSaved = productsVO_old.getImageFileSaved();  // 실제 저장된 파일명
      String Thumbs = productsVO_old.getThumbs();       // 실제 저장된 preview 이미지 파일명
      long sizes = 0;
         
      String upDir =  Products.getUploadDir(); // C:/kd/deploy/resort_v3sbm3c/products/storage/
      
      Tool.deleteFile(upDir, ImageFileSaved);  // 실제 저장된 파일삭제
      Tool.deleteFile(upDir, Thumbs);     // preview 이미지 삭제
      // -------------------------------------------------------------------
      // 파일 삭제 종료
      // -------------------------------------------------------------------
          
      // -------------------------------------------------------------------
      // 파일 전송 시작
      // -------------------------------------------------------------------
      String ImageFile = "";          // 원본 파일명 image

      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      MultipartFile mf = productsVO.getFileMF();
          
      ImageFile = mf.getOriginalFilename(); // 원본 파일명
      sizes = mf.getSize();  // 파일 크기
          
      if (sizes > 0) { // 폼에서 새롭게 올리는 파일이 있는지 파일 크기로 체크 ★
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
        ImageFileSaved = Upload.saveFileSpring(mf, upDir); 
        
        if (Tool.isImage(ImageFileSaved)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 250, height: 200
          Thumbs = Tool.preview(upDir, ImageFileSaved, 250, 200); 
        }
        
      } else { // 파일이 삭제만 되고 새로 올리지 않는 경우
        ImageFile="";
        ImageFileSaved="";
        Thumbs="";
        sizes=0;
      }
          
      productsVO.setImageFile(ImageFile);
      productsVO.setImageFileSaved(ImageFileSaved);
      productsVO.setThumbs(Thumbs);
      productsVO.setSizes(sizes);
      // -------------------------------------------------------------------
      // 파일 전송 코드 종료
      // -------------------------------------------------------------------
          
      this.productsProc.update_file(productsVO); // Oracle 처리

      mav.addObject("ProductID", productsVO.getProductID());
      mav.addObject("CategoryID", productsVO.getCategoryID());
      mav.setViewName("redirect:/products/read.do"); // request -> param으로 접근 전환
                
    } else {
      mav.addObject("url", "/admin/login_need"); // login_need.jsp, redirect parameter 적용
      mav.setViewName("redirect:/products/msg.do"); // GET
    }

    // redirect하게되면 전부 데이터가 삭제됨으로 mav 객체에 다시 저장
    mav.addObject("now_page", productsVO.getNow_page());
    
    return mav; // forward
  }   
  
  /**
   * 파일 삭제 폼
   * http://localhost:9091/products/delete.do?ProductID=1
   * 
   * @return
   */
  @RequestMapping(value = "/products/delete.do", method = RequestMethod.GET)
  public ModelAndView delete(HttpSession session, int ProductID) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
      ProductsVO productsVO = this.productsProc.read(ProductID);
      mav.addObject("productsVO", productsVO);
      
      CategoryVO categoryVO = this.categoryProc.read(productsVO.getCategoryID());
      mav.addObject("categoryVO", categoryVO);
      
      mav.setViewName("/products/delete"); // /WEB-INF/views/products/delete.jsp
      
    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/products/msg.do"); 
    }


    return mav; // forward
  }
  
  /**
   * 삭제 처리 http://localhost:9091/products/delete.do
   * 
   * @return
   */
  @RequestMapping(value = "/products/delete.do", method = RequestMethod.POST)
  public ModelAndView delete(ProductsVO productsVO) {
    ModelAndView mav = new ModelAndView();
    
    // -------------------------------------------------------------------
    // 파일 삭제 시작
    // -------------------------------------------------------------------
    // 삭제할 파일 정보를 읽어옴.
    ProductsVO productsVO_read = productsProc.read(productsVO.getProductID());
        
    String ImageFileSaved = productsVO.getImageFileSaved();
    String Thumbs = productsVO.getThumbs();
    
    String uploadDir = Products.getUploadDir();
    Tool.deleteFile(uploadDir, ImageFileSaved);  // 실제 저장된 파일삭제
    Tool.deleteFile(uploadDir, Thumbs);     // preview 이미지 삭제
    // -------------------------------------------------------------------
    // 파일 삭제 종료
    // -------------------------------------------------------------------
        
    this.productsProc.delete(productsVO.getProductID()); // DBMS 삭제
        
    // -------------------------------------------------------------------------------------
    // 마지막 페이지의 마지막 레코드 삭제시의 페이지 번호 -1 처리
    // -------------------------------------------------------------------------------------    
    // 마지막 페이지의 마지막 10번째 레코드를 삭제후
    // 하나의 페이지가 3개의 레코드로 구성되는 경우 현재 9개의 레코드가 남아 있으면
    // 페이지수를 4 -> 3으로 감소 시켜야함, 마지막 페이지의 마지막 레코드 삭제시 나머지는 0 발생
    int now_page = productsVO.getNow_page();
    
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("CategoryID", productsVO.getCategoryID());
    hashMap.put("word", productsVO.getWord());
    
    if (productsProc.search_count(hashMap) % Products.RECORD_PER_PAGE == 0) {
      now_page = now_page - 1; // 삭제시 DBMS는 바로 적용되나 크롬은 새로고침등의 필요로 단계가 작동 해야함.
      if (now_page < 1) {
        now_page = 1; // 시작 페이지
      }
    }
    // -------------------------------------------------------------------------------------

    mav.addObject("CategoryID", productsVO.getCategoryID());
    mav.addObject("now_page", now_page);
    mav.setViewName("redirect:/products/list_by_CategoryID.do"); 
    
    return mav;
  }   
      
  // http://localhost:9091/products/delete_by_CategoryID.do?CategoryID=1
  // 파일 삭제 -> 레코드 삭제
  @RequestMapping(value = "/products/delete_by_CategoryID.do", method = RequestMethod.GET)
  public String delete_by_CategoryID(int CategoryID) {
    ArrayList<ProductsVO> list = this.productsProc.list_by_categoryID(CategoryID);
    
    for(ProductsVO productsVO : list) {
      // -------------------------------------------------------------------
      // 파일 삭제 시작
      // -------------------------------------------------------------------
      String ImageFileSaved = productsVO.getImageFileSaved();
      String Thumbs = productsVO.getThumbs();
      
      String uploadDir = Products.getUploadDir();
      Tool.deleteFile(uploadDir, ImageFileSaved);  // 실제 저장된 파일삭제
      Tool.deleteFile(uploadDir, Thumbs);     // preview 이미지 삭제
      // -------------------------------------------------------------------
      // 파일 삭제 종료
      // -------------------------------------------------------------------
    }
    
    int cnt = this.productsProc.delete_by_categoryID(CategoryID);
    System.out.println("-> count: " + cnt);
    
    return "";
  
  }
  
  /**
   * Gallery 전체 이미지 출력
   * http://localhost:9091/products/list_all_gallery.do
   * @return
   */
  @RequestMapping(value="/products/list_all_gallery.do", method = RequestMethod.GET)
  public ModelAndView list_all_gallery(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/products/list_all_gallery"); // /WEB-INF/views/products/list_all_gallery.jsp
      
      ArrayList<ProductsVO> list = this.productsProc.list_all();
      mav.addObject("list", list);
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      
    }
    
    return mav;
  }
  
}


