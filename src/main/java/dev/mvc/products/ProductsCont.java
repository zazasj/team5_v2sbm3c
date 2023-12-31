package dev.mvc.products;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.adminlog.AdlogService;
import dev.mvc.cateGroup.CateGroupProcInter;
import dev.mvc.cateGroup.CateGroupVO;
import dev.mvc.category.CategoryProcInter;
import dev.mvc.category.CategoryVO;
import dev.mvc.supplier.SupplierProcInter;
import dev.mvc.supplier.SupplierVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class ProductsCont {
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") // @Component("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc;
  
  @Autowired
  @Qualifier("dev.mvc.category.CategoryProc")  // @Component("dev.mvc.category.CategoryProc")
  private CategoryProcInter categoryProc;
  
  @Autowired
  @Qualifier("dev.mvc.cateGroup.CateGroupProc")
  private CateGroupProcInter cateGroupProc;
  
  @Autowired
  @Qualifier("dev.mvc.products.ProductsProc") // @Component("dev.mvc.products.ContentsProc")
  private ProductsProcInter productsProc;
  
  @Autowired
  @Qualifier("dev.mvc.supplier.SupplierProc")
  private SupplierProcInter supplierProc;
  
  @Autowired
  private AdlogService adlogservice;
  
  private String tablename = "Product";
  
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
  
  // 등록 폼, contents 테이블은 FK로 categoryID를 사용함.
  @RequestMapping(value="/products/create.do", method = RequestMethod.GET)
  public ModelAndView create(int categoryID) {
    ModelAndView mav = new ModelAndView();
    
    ArrayList<SupplierVO> list_sup = this.supplierProc.list_all();
    mav.addObject("list_sup", list_sup);

    CategoryVO categoryVO = this.categoryProc.read(categoryID); // create.jsp에 카테고리 정보를 출력하기위한 목적
    mav.addObject("categoryVO", categoryVO);
    
    CateGroupVO cateGroupVO = cateGroupProc.read(categoryVO.getGrpID());
    mav.addObject("cateGroupVO", cateGroupVO);
    
    mav.setViewName("/products/create"); // /webapp/WEB-INF/views/products/create.jsp
    
    return mav;
  }
  
  /**
   * 등록 처리 http://localhost:9093/products/create.do
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
      String imageFile = "";          // 원본 파일명 image
      String imageFileSaved = "";   // 저장된 파일명, image
      String thumb = "";     // preview image

      String upDir =  Products.getUploadDir(); // 파일을 업로드할 폴더 준비
      System.out.println("-> upDir: " + upDir);
      
      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      MultipartFile mf = productsVO.getFileMF();
      
      imageFile = mf.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
      System.out.println("-> 원본 파일명 산출 imageFile: " + imageFile);
      
      if (Tool.checkUploadFile(imageFile) == true) { // 업로드 가능한 파일인지 검사
        long sizes = mf.getSize();  // 파일 크기
        
        if (sizes > 0) { // 파일 크기 체크
          // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
          imageFileSaved = Upload.saveFileSpring(mf, upDir); 
          
          if (Tool.isImage(imageFileSaved)) { // 이미지인지 검사
            // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
            thumb = Tool.preview(upDir, imageFileSaved, 200, 150); 
          }
          
        }    
        
        productsVO.setImageFile(imageFile);  // 순수 원본 파일명
        productsVO.setImageFileSaved(imageFileSaved);; // 저장된 파일명(파일명 중복 처리)
        productsVO.setThumb(thumb);      // 원본이미지 축소판
        productsVO.setSizes(sizes);  // 파일 크기
        // ------------------------------------------------------------------------------
        // 파일 전송 코드 종료
        // ------------------------------------------------------------------------------
        
        // Call By Reference: 메모리 공유, Hashcode 전달
        int adminno = (int)session.getAttribute("adminno"); // adminno FK
        productsVO.setAdminno(adminno);
        int cnt = this.productsProc.create(productsVO); 
        
        // ------------------------------------------------------------------------------
        // PK의 return
        // ------------------------------------------------------------------------------
        // System.out.println("--> contentsno: " + contentsVO.getContentsno());
        // mav.addObject("contentsno", contentsVO.getContentsno()); // redirect parameter 적용
        // ------------------------------------------------------------------------------
        
        if (cnt == 1) {
            mav.addObject("code", "create_success");
            // cateProc.increaseCnt(contentsVO.getCateno()); // 글수 증가
        } else {
            mav.addObject("code", "create_fail");
        }
        //adminlog관련 
        int recordid = productsVO.getProductID();
        int adno =  (int) session.getAttribute("adminno");
        String acttype = "Create";  
        adlogservice.createLog(tablename, recordid, acttype, adno);
        
        
        mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
        
        // System.out.println("--> categoryID: " + contentsVO.getCateno());
        // redirect시에 hidden tag로 보낸것들이 전달이 안됨으로 request에 다시 저장
        mav.addObject("categoryID", productsVO.getCategoryID()); // redirect parameter 적용
        
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

//  /**
//   * 전체 목록, 관리자만 사용 가능
//   * http://localhost:9093/products/list_all.do
//   * @return
//   */
//  @RequestMapping(value="/products/list_all.do", method = RequestMethod.GET)
//  public ModelAndView list_all(HttpSession session) {
//    ModelAndView mav = new ModelAndView();
//    
//    if (this.adminProc.isAdmin(session) == true) {
//      mav.setViewName("/products/list_all"); // /WEB-INF/views/contents/list_all.jsp
//      
//      ArrayList<ProductsVO> list = this.productsProc.list_all();
//     
//      // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
//      for (ProductsVO productsVO : list) {
//        String pName = productsVO.getpName();
//        String description = productsVO.getDescription();
//        
//        pName = Tool.convertChar(pName);  // 특수 문자 처리
//        description = Tool.convertChar(description); 
//        
//        productsVO.setpName(pName);
//        productsVO.setDescription(description);  
//
//      }
//      
//      mav.addObject("list", list);
//
//      
//    } else {
//      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
//      
//    }
//    
//    return mav;
//  }
  
  /**
   * 모든 상품 목록 + 검색 + 페이징 + Cookie 지원
   * http://localhost:9091/contents/list_by_categoryID_search_paging.do?categoryID=1&word=스위스&now_page=1
   * 
   * @param categoryID
   * @param word
   * @param now_page
   * @return
   */
  @RequestMapping(value = "/products/list_all.do", method = RequestMethod.GET)
  public ModelAndView list_all(
      @RequestParam(value = "categoryID", defaultValue = "1")int categoryID,
      @RequestParam(value = "word", defaultValue = "") String word,
      @RequestParam(value = "now_page", defaultValue = "1") int now_page,
      HttpServletRequest request, ProductsVO productsVO) {
    System.out.println("-> list_all_search_paging now_page: " + now_page);

    ModelAndView mav = new ModelAndView();

    // 숫자와 문자열 타입을 저장해야함으로 Obejct 사용
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("categoryID", categoryID);
    map.put("word", word); // #{word}
    map.put("now_page", now_page); // 페이지에 출력할 레코드의 범위를 산출하기위해 사용

    // 검색 목록
    ArrayList<ProductsVO> list = productsProc.list_all_search_paging(productsVO);
    mav.addObject("list", list);

    // 검색된 레코드 갯수
    int search_count_all = productsProc.search_count_all(map);
    mav.addObject("search_count_all", search_count_all);
    
    CategoryVO categoryVO = categoryProc.read(categoryID);
    mav.addObject("categoryVO", categoryVO);

    CateGroupVO cateGroupVO = cateGroupProc.read(categoryVO.getGrpID());
    mav.addObject("cateGroupVO", cateGroupVO);

    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param categoryID 카테고리번호
     * @param search_count 검색(전체) 레코드수
     * @param now_page 현재 페이지
     * @param word 검색어
     * @return 페이징 생성 문자열
     */
    // String paging = productsProc.pagingBox(categoryID, search_count, now_page, word);
    String paging = productsProc.pagingBox1(productsVO.getNow_page(), productsVO.getWord(), "list_all.do", search_count_all);

   
    mav.addObject("paging", paging);

    mav.addObject("now_page", now_page);

    // /views/contents/list_by_categoryID_search_paging_cookie.jsp
    mav.setViewName("/products/list_all");

    // -------------------------------------------------------------------------------
    // 쇼핑 카트 장바구니에 상품 등록전 로그인 폼 출력 관련 쿠기  
    // -------------------------------------------------------------------------------
    Cookie[] cookies = request.getCookies();
    Cookie cookie = null;

    String ck_id = ""; // id 저장
    String ck_id_save = ""; // id 저장 여부를 체크
    String ck_passwd = ""; // passwd 저장
    String ck_passwd_save = ""; // passwd 저장 여부를 체크

    if (cookies != null) {  // Cookie 변수가 있다면
      for (int i=0; i < cookies.length; i++){
        cookie = cookies[i]; // 쿠키 객체 추출
        
        if (cookie.getName().equals("ck_id")){
          ck_id = cookie.getValue();                                 // Cookie에 저장된 id
        }else if(cookie.getName().equals("ck_id_save")){
          ck_id_save = cookie.getValue();                          // Cookie에 id를 저장 할 것인지의 여부, Y, N
        }else if (cookie.getName().equals("ck_passwd")){
          ck_passwd = cookie.getValue();                          // Cookie에 저장된 password
        }else if(cookie.getName().equals("ck_passwd_save")){
          ck_passwd_save = cookie.getValue();                  // Cookie에 password를 저장 할 것인지의 여부, Y, N
        }
      }
    }
    
    System.out.println("-> ck_id: " + ck_id);
    
    mav.addObject("ck_id", ck_id); 
    mav.addObject("ck_id_save", ck_id_save);
    mav.addObject("ck_passwd", ck_passwd);
    mav.addObject("ck_passwd_save", ck_passwd_save);
    // -------------------------------------------------------------------------------
    
    return mav;
  }
  /**
   * 위스키 목록 + 검색 + 페이징 지원 + Grid
   * 검색하지 않는 경우
   * http://localhost:9093/products/list_by_categoryID_grid.do?categoryID=2&word=&now_page=1
   * 검색하는 경우
   * http://localhost:9093/products/list_by_categoryID_grid.do?categoryID=2&word=탐험&now_page=1
   * 
   * @param categoryID
   * @param word
   * @param now_page
   * @return
   */
   @RequestMapping(value = "/products/list_all_grid.do", method = RequestMethod.GET)
   public ModelAndView list_all_grid(ProductsVO productsVO) {
      ModelAndView mav = new ModelAndView();
    
      // 검색 목록
    ArrayList<ProductsVO> list = productsProc.list_all_search_paging(productsVO);
    
    // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
    for (ProductsVO vo : list) {
      String pName = vo.getpName();
      String description = vo.getDescription();
      
      pName = Tool.convertChar(pName);  // 특수 문자 처리
      description = Tool.convertChar(description); 
      
      vo.setpName(pName);
      vo.setDescription(description);  
    
      }
      
      mav.addObject("list", list);
    
      CategoryVO categoryVO = categoryProc.read(productsVO.getCategoryID());
      mav.addObject("categoryVO", categoryVO);
      
      HashMap<String, Object> hashMap = new HashMap<String, Object>();
      hashMap.put("categoryID", productsVO.getCategoryID());
      hashMap.put("word", productsVO.getWord());
      
      int search_count_all = this.productsProc.search_count_all(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
      mav.addObject("search_count_all", search_count_all);
    
      /*
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
   * 18 19 20 [다음]
   * @param categoryID 카테고리번호
   * @param now_page 현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명
   * @return 페이징용으로 생성된 HTML/CSS tag 문자열
   */
    String paging = productsProc.pagingBox1(productsVO.getNow_page(), productsVO.getWord(), "list_all_grid.do", search_count_all);
    mav.addObject("paging", paging);
    
      // mav.addObject("now_page", now_page);
    
    mav.setViewName("/products/list_all_grid");  // /contents/list_by_categoryID_grid.jsp
    
      return mav;
    }
  
//  /**
//   * 위스키 전체 목록
//   * http://localhost:9093/products/list_all_1.do
//   * @return
//   */
//  @RequestMapping(value="/products/list_all_1.do", method = RequestMethod.GET)
//  public ModelAndView list_all_1(HttpSession session) {
//    ModelAndView mav = new ModelAndView();
//    
//      mav.setViewName("/products/list_all_1"); // /WEB-INF/views/contents/list_all_1.jsp
//      
//      ArrayList<ProductsVO> list = this.productsProc.list_all_1();
//     
//      // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
//      for (ProductsVO productsVO : list) {
//        String pName = productsVO.getpName();
//        String description = productsVO.getDescription();
//        
//        pName = Tool.convertChar(pName);  // 특수 문자 처리
//        description = Tool.convertChar(description); 
//        
//        productsVO.setpName(pName);
//        productsVO.setDescription(description);  
//
//      }
//      
//      mav.addObject("list", list);
//      
//      ArrayList<CategoryVO> list_1 = categoryProc.list_all();
//      mav.addObject("list_1", list_1);
//
//    
//    return mav;
//  }
  
  /**
   * 위스키 목록 + 검색 + 페이징 + Cookie 지원
   * http://localhost:9091/contents/list_by_categoryID_search_paging.do?categoryID=1&word=스위스&now_page=1
   * 
   * @param categoryID
   * @param word
   * @param now_page
   * @return
   */
  @RequestMapping(value = "/products/list_all_1.do", method = RequestMethod.GET)
  public ModelAndView list_all_1(
      @RequestParam(value = "categoryID", defaultValue = "1")int categoryID,
      @RequestParam(value = "word", defaultValue = "") String word,
      @RequestParam(value = "now_page", defaultValue = "1") int now_page,
      HttpServletRequest request, ProductsVO productsVO) {
    System.out.println("-> list_all_1_search_paging now_page: " + now_page);

    ModelAndView mav = new ModelAndView();

    ArrayList<CategoryVO> list_1 = categoryProc.list_all();
    mav.addObject("list_1", list_1);
    // 숫자와 문자열 타입을 저장해야함으로 Obejct 사용
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("categoryID", categoryID);
    map.put("word", word); // #{word}
    map.put("now_page", now_page); // 페이지에 출력할 레코드의 범위를 산출하기위해 사용

    // 검색 목록
    ArrayList<ProductsVO> list = productsProc.list_all_1_search_paging(productsVO);
    mav.addObject("list", list);

    // 검색된 레코드 갯수
    int search_count_1 = productsProc.search_count_1(map);
    mav.addObject("search_count_1", search_count_1);
    
    CategoryVO categoryVO = categoryProc.read(categoryID);
    mav.addObject("categoryVO", categoryVO);

    CateGroupVO cateGroupVO = cateGroupProc.read(categoryVO.getGrpID());
    mav.addObject("cateGroupVO", cateGroupVO);

    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param categoryID 카테고리번호
     * @param search_count 검색(전체) 레코드수
     * @param now_page 현재 페이지
     * @param word 검색어
     * @return 페이징 생성 문자열
     */
    // String paging = productsProc.pagingBox(categoryID, search_count, now_page, word);
    String paging = productsProc.pagingBox1(productsVO.getNow_page(), productsVO.getWord(), "list_all_1.do", search_count_1);

   
    mav.addObject("paging", paging);

    mav.addObject("now_page", now_page);

    // /views/contents/list_by_categoryID_search_paging_cookie.jsp
    mav.setViewName("/products/list_all_1");

    // -------------------------------------------------------------------------------
    // 쇼핑 카트 장바구니에 상품 등록전 로그인 폼 출력 관련 쿠기  
    // -------------------------------------------------------------------------------
    Cookie[] cookies = request.getCookies();
    Cookie cookie = null;

    String ck_id = ""; // id 저장
    String ck_id_save = ""; // id 저장 여부를 체크
    String ck_passwd = ""; // passwd 저장
    String ck_passwd_save = ""; // passwd 저장 여부를 체크

    if (cookies != null) {  // Cookie 변수가 있다면
      for (int i=0; i < cookies.length; i++){
        cookie = cookies[i]; // 쿠키 객체 추출
        
        if (cookie.getName().equals("ck_id")){
          ck_id = cookie.getValue();                                 // Cookie에 저장된 id
        }else if(cookie.getName().equals("ck_id_save")){
          ck_id_save = cookie.getValue();                          // Cookie에 id를 저장 할 것인지의 여부, Y, N
        }else if (cookie.getName().equals("ck_passwd")){
          ck_passwd = cookie.getValue();                          // Cookie에 저장된 password
        }else if(cookie.getName().equals("ck_passwd_save")){
          ck_passwd_save = cookie.getValue();                  // Cookie에 password를 저장 할 것인지의 여부, Y, N
        }
      }
    }
    
    System.out.println("-> ck_id: " + ck_id);
    
    mav.addObject("ck_id", ck_id); 
    mav.addObject("ck_id_save", ck_id_save);
    mav.addObject("ck_passwd", ck_passwd);
    mav.addObject("ck_passwd_save", ck_passwd_save);
    // -------------------------------------------------------------------------------
    
    return mav;
  }
  /**
   * 위스키 목록 + 검색 + 페이징 지원 + Grid
   * 검색하지 않는 경우
   * http://localhost:9093/products/list_by_categoryID_grid.do?categoryID=2&word=&now_page=1
   * 검색하는 경우
   * http://localhost:9093/products/list_by_categoryID_grid.do?categoryID=2&word=탐험&now_page=1
   * 
   * @param categoryID
   * @param word
   * @param now_page
   * @return
   */
   @RequestMapping(value = "/products/list_all_1_grid.do", method = RequestMethod.GET)
   public ModelAndView list_all_1_grid(ProductsVO productsVO) {
      ModelAndView mav = new ModelAndView();
      ArrayList<CategoryVO> list_1 = categoryProc.list_all();
      mav.addObject("list_1", list_1);
    
      // 검색 목록
    ArrayList<ProductsVO> list = productsProc.list_all_1_search_paging(productsVO);
    
    // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
    for (ProductsVO vo : list) {
      String pName = vo.getpName();
      String description = vo.getDescription();
      
      pName = Tool.convertChar(pName);  // 특수 문자 처리
      description = Tool.convertChar(description); 
      
      vo.setpName(pName);
      vo.setDescription(description);  
    
      }
      
      mav.addObject("list", list);
    
      CategoryVO categoryVO = categoryProc.read(productsVO.getCategoryID());
      mav.addObject("categoryVO", categoryVO);
      
      HashMap<String, Object> hashMap = new HashMap<String, Object>();
      hashMap.put("categoryID", productsVO.getCategoryID());
      hashMap.put("word", productsVO.getWord());
      
      int search_count_1 = this.productsProc.search_count_1(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
      mav.addObject("search_count_1", search_count_1);
    
      /*
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
   * 18 19 20 [다음]
   * @param categoryID 카테고리번호
   * @param now_page 현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명
   * @return 페이징용으로 생성된 HTML/CSS tag 문자열
   */
    String paging = productsProc.pagingBox1(productsVO.getNow_page(), productsVO.getWord(), "list_all_1_grid.do", search_count_1);
    mav.addObject("paging", paging);
    
      // mav.addObject("now_page", now_page);
    
    mav.setViewName("/products/list_all_1_grid");  // /contents/list_by_categoryID_grid.jsp
    
      return mav;
    }
//  /**
//   * 브랜디/꼬냑 전체 목록
//   * http://localhost:9093/products/list_all_2.do
//   * @return
//   */
//  @RequestMapping(value="/products/list_all_2.do", method = RequestMethod.GET)
//  public ModelAndView list_all_2(HttpSession session) {
//    ModelAndView mav = new ModelAndView();
//
//      mav.setViewName("/products/list_all_2"); // /WEB-INF/views/contents/list_all_2.jsp
//      
//      ArrayList<ProductsVO> list = this.productsProc.list_all_2();
//     
//      // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
//      for (ProductsVO productsVO : list) {
//        String pName = productsVO.getpName();
//        String description = productsVO.getDescription();
//        
//        pName = Tool.convertChar(pName);  // 특수 문자 처리
//        description = Tool.convertChar(description); 
//        
//        productsVO.setpName(pName);
//        productsVO.setDescription(description);  
//
//      }
//      
//      mav.addObject("list", list);
//      
//      ArrayList<CategoryVO> list_2 = categoryProc.list_all();
//      mav.addObject("list_2", list_2);
//
//    
//    return mav;
//  }
//  /**
//   * 와인 전체 목록
//   * http://localhost:9093/products/list_all_3.do
//   * @return
//   */
//  @RequestMapping(value="/products/list_all_3.do", method = RequestMethod.GET)
//  public ModelAndView list_all_3(HttpSession session) {
//    ModelAndView mav = new ModelAndView();
//
//      mav.setViewName("/products/list_all_3"); // /WEB-INF/views/contents/list_all_1.jsp
//      
//      ArrayList<ProductsVO> list = this.productsProc.list_all_3();
//     
//      // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
//      for (ProductsVO productsVO : list) {
//        String pName = productsVO.getpName();
//        String description = productsVO.getDescription();
//        
//        pName = Tool.convertChar(pName);  // 특수 문자 처리
//        description = Tool.convertChar(description); 
//        
//        productsVO.setpName(pName);
//        productsVO.setDescription(description);  
//
//      }
//      
//      mav.addObject("list", list);
//      
//      ArrayList<CategoryVO> list_3 = categoryProc.list_all();
//      mav.addObject("list_3", list_3);
//
//    
//    return mav;
//  }
//  /**
//   * 리큐르 전체 목록
//   * http://localhost:9093/products/list_all_4.do
//   * @return
//   */
//  @RequestMapping(value="/products/list_all_4.do", method = RequestMethod.GET)
//  public ModelAndView list_all_4(HttpSession session) {
//    ModelAndView mav = new ModelAndView();
//    
//      mav.setViewName("/products/list_all_4"); // /WEB-INF/views/contents/list_all_1.jsp
//      
//      ArrayList<ProductsVO> list = this.productsProc.list_all_4();
//     
//      // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
//      for (ProductsVO productsVO : list) {
//        String pName = productsVO.getpName();
//        String description = productsVO.getDescription();
//        
//        pName = Tool.convertChar(pName);  // 특수 문자 처리
//        description = Tool.convertChar(description); 
//        
//        productsVO.setpName(pName);
//        productsVO.setDescription(description);  
//
//      }
//      
//      mav.addObject("list", list);
//      
//      ArrayList<CategoryVO> list_4 = categoryProc.list_all();
//      mav.addObject("list_4", list_4);
//
//    
//    return mav;
//  }
//  /**
//   * 전통주 전체 목록
//   * http://localhost:9093/products/list_all_5.do
//   * @return
//   */
//  @RequestMapping(value="/products/list_all_5.do", method = RequestMethod.GET)
//  public ModelAndView list_all_5(HttpSession session) {
//    ModelAndView mav = new ModelAndView();
//    
//      mav.setViewName("/products/list_all_5"); // /WEB-INF/views/contents/list_all_1.jsp
//      
//      ArrayList<ProductsVO> list = this.productsProc.list_all_5();
//     
//      // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
//      for (ProductsVO productsVO : list) {
//        String pName = productsVO.getpName();
//        String description = productsVO.getDescription();
//        
//        pName = Tool.convertChar(pName);  // 특수 문자 처리
//        description = Tool.convertChar(description); 
//        
//        productsVO.setpName(pName);
//        productsVO.setDescription(description);  
//
//      }
//      
//      mav.addObject("list", list);
//      
//      ArrayList<CategoryVO> list_5 = categoryProc.list_all();
//      mav.addObject("list_5", list_5);
//      
//    
//    return mav;
//  }

  /**
   * 브랜디/꼬냑 목록 + 검색 + 페이징 + Cookie 지원
   * http://localhost:9091/contents/list_by_categoryID_search_paging.do?categoryID=1&word=스위스&now_page=1
   * 
   * @param categoryID
   * @param word
   * @param now_page
   * @return
   */
  @RequestMapping(value = "/products/list_all_2.do", method = RequestMethod.GET)
  public ModelAndView list_all_2(
      @RequestParam(value = "categoryID", defaultValue = "1")int categoryID,
      @RequestParam(value = "word", defaultValue = "") String word,
      @RequestParam(value = "now_page", defaultValue = "1") int now_page,
      HttpServletRequest request, ProductsVO productsVO) {
    System.out.println("-> list_all_2_search_paging now_page: " + now_page);

    ModelAndView mav = new ModelAndView();
    ArrayList<CategoryVO> list_2 = categoryProc.list_all();
    mav.addObject("list_2", list_2);

    // 숫자와 문자열 타입을 저장해야함으로 Obejct 사용
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("categoryID", categoryID);
    map.put("word", word); // #{word}
    map.put("now_page", now_page); // 페이지에 출력할 레코드의 범위를 산출하기위해 사용

    // 검색 목록
    ArrayList<ProductsVO> list = productsProc.list_all_2_search_paging(productsVO);
    mav.addObject("list", list);

    // 검색된 레코드 갯수
    int search_count_2 = productsProc.search_count_2(map);
    mav.addObject("search_count_2", search_count_2);
    
    CategoryVO categoryVO = categoryProc.read(categoryID);
    mav.addObject("categoryVO", categoryVO);

    CateGroupVO cateGroupVO = cateGroupProc.read(categoryVO.getGrpID());
    mav.addObject("cateGroupVO", cateGroupVO);

    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param categoryID 카테고리번호
     * @param search_count 검색(전체) 레코드수
     * @param now_page 현재 페이지
     * @param word 검색어
     * @return 페이징 생성 문자열
     */
    // String paging = productsProc.pagingBox(categoryID, search_count, now_page, word);
    String paging = productsProc.pagingBox1(productsVO.getNow_page(), productsVO.getWord(), "list_all_2.do", search_count_2);

   
    mav.addObject("paging", paging);

    mav.addObject("now_page", now_page);

    // /views/contents/list_by_categoryID_search_paging_cookie.jsp
    mav.setViewName("/products/list_all_2");

    // -------------------------------------------------------------------------------
    // 쇼핑 카트 장바구니에 상품 등록전 로그인 폼 출력 관련 쿠기  
    // -------------------------------------------------------------------------------
    Cookie[] cookies = request.getCookies();
    Cookie cookie = null;

    String ck_id = ""; // id 저장
    String ck_id_save = ""; // id 저장 여부를 체크
    String ck_passwd = ""; // passwd 저장
    String ck_passwd_save = ""; // passwd 저장 여부를 체크

    if (cookies != null) {  // Cookie 변수가 있다면
      for (int i=0; i < cookies.length; i++){
        cookie = cookies[i]; // 쿠키 객체 추출
        
        if (cookie.getName().equals("ck_id")){
          ck_id = cookie.getValue();                                 // Cookie에 저장된 id
        }else if(cookie.getName().equals("ck_id_save")){
          ck_id_save = cookie.getValue();                          // Cookie에 id를 저장 할 것인지의 여부, Y, N
        }else if (cookie.getName().equals("ck_passwd")){
          ck_passwd = cookie.getValue();                          // Cookie에 저장된 password
        }else if(cookie.getName().equals("ck_passwd_save")){
          ck_passwd_save = cookie.getValue();                  // Cookie에 password를 저장 할 것인지의 여부, Y, N
        }
      }
    }
    
    System.out.println("-> ck_id: " + ck_id);
    
    mav.addObject("ck_id", ck_id); 
    mav.addObject("ck_id_save", ck_id_save);
    mav.addObject("ck_passwd", ck_passwd);
    mav.addObject("ck_passwd_save", ck_passwd_save);
    // -------------------------------------------------------------------------------
    
    return mav;
  }
  /**
   * 브랜디/꼬냑 목록 + 검색 + 페이징 지원 + Grid
   * 검색하지 않는 경우
   * http://localhost:9093/products/list_by_categoryID_grid.do?categoryID=2&word=&now_page=1
   * 검색하는 경우
   * http://localhost:9093/products/list_by_categoryID_grid.do?categoryID=2&word=탐험&now_page=1
   * 
   * @param categoryID
   * @param word
   * @param now_page
   * @return
   */
   @RequestMapping(value = "/products/list_all_2_grid.do", method = RequestMethod.GET)
   public ModelAndView list_all_2_grid(ProductsVO productsVO) {
      ModelAndView mav = new ModelAndView();
      ArrayList<CategoryVO> list_2 = categoryProc.list_all();
      mav.addObject("list_2", list_2);
    
      // 검색 목록
    ArrayList<ProductsVO> list = productsProc.list_all_2_search_paging(productsVO);
    
    // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
    for (ProductsVO vo : list) {
      String pName = vo.getpName();
      String description = vo.getDescription();
      
      pName = Tool.convertChar(pName);  // 특수 문자 처리
      description = Tool.convertChar(description); 
      
      vo.setpName(pName);
      vo.setDescription(description);  
    
      }
      
      mav.addObject("list", list);
    
      CategoryVO categoryVO = categoryProc.read(productsVO.getCategoryID());
      mav.addObject("categoryVO", categoryVO);
      
      HashMap<String, Object> hashMap = new HashMap<String, Object>();
      hashMap.put("categoryID", productsVO.getCategoryID());
      hashMap.put("word", productsVO.getWord());
      
      int search_count_2 = this.productsProc.search_count_2(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
      mav.addObject("search_count_2", search_count_2);
    
      /*
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
   * 18 19 20 [다음]
   * @param categoryID 카테고리번호
   * @param now_page 현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명
   * @return 페이징용으로 생성된 HTML/CSS tag 문자열
   */
    String paging = productsProc.pagingBox1(productsVO.getNow_page(), productsVO.getWord(), "list_all_2_grid.do", search_count_2);
    mav.addObject("paging", paging);
    
      // mav.addObject("now_page", now_page);
    
    mav.setViewName("/products/list_all_2_grid");  // /contents/list_by_categoryID_grid.jsp
    
      return mav;
    }
  
  /**
   * 와인 목록 + 검색 + 페이징 + Cookie 지원
   * http://localhost:9091/contents/list_by_categoryID_search_paging.do?categoryID=1&word=스위스&now_page=1
   * 
   * @param categoryID
   * @param word
   * @param now_page
   * @return
   */
  @RequestMapping(value = "/products/list_all_3.do", method = RequestMethod.GET)
  public ModelAndView list_all_3(
      @RequestParam(value = "categoryID", defaultValue = "1")int categoryID,
      @RequestParam(value = "word", defaultValue = "") String word,
      @RequestParam(value = "now_page", defaultValue = "1") int now_page,
      HttpServletRequest request, ProductsVO productsVO) {
    System.out.println("-> list_all_3_search_paging now_page: " + now_page);

    ModelAndView mav = new ModelAndView();
    
    ArrayList<CategoryVO> list_3 = categoryProc.list_all();
    mav.addObject("list_3", list_3);
    // 숫자와 문자열 타입을 저장해야함으로 Obejct 사용
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("categoryID", categoryID);
    map.put("word", word); // #{word}
    map.put("now_page", now_page); // 페이지에 출력할 레코드의 범위를 산출하기위해 사용

    // 검색 목록
    ArrayList<ProductsVO> list = productsProc.list_all_3_search_paging(productsVO);
    mav.addObject("list", list);

    // 검색된 레코드 갯수
    int search_count_3 = productsProc.search_count_3(map);
    mav.addObject("search_count_3", search_count_3);
    
    CategoryVO categoryVO = categoryProc.read(categoryID);
    mav.addObject("categoryVO", categoryVO);

    CateGroupVO cateGroupVO = cateGroupProc.read(categoryVO.getGrpID());
    mav.addObject("cateGroupVO", cateGroupVO);

    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param categoryID 카테고리번호
     * @param search_count 검색(전체) 레코드수
     * @param now_page 현재 페이지
     * @param word 검색어
     * @return 페이징 생성 문자열
     */
    // String paging = productsProc.pagingBox(categoryID, search_count, now_page, word);
    String paging = productsProc.pagingBox1(productsVO.getNow_page(), productsVO.getWord(), "list_all_3.do", search_count_3);

   
    mav.addObject("paging", paging);

    mav.addObject("now_page", now_page);

    // /views/contents/list_by_categoryID_search_paging_cookie.jsp
    mav.setViewName("/products/list_all_3");

    // -------------------------------------------------------------------------------
    // 쇼핑 카트 장바구니에 상품 등록전 로그인 폼 출력 관련 쿠기  
    // -------------------------------------------------------------------------------
    Cookie[] cookies = request.getCookies();
    Cookie cookie = null;

    String ck_id = ""; // id 저장
    String ck_id_save = ""; // id 저장 여부를 체크
    String ck_passwd = ""; // passwd 저장
    String ck_passwd_save = ""; // passwd 저장 여부를 체크

    if (cookies != null) {  // Cookie 변수가 있다면
      for (int i=0; i < cookies.length; i++){
        cookie = cookies[i]; // 쿠키 객체 추출
        
        if (cookie.getName().equals("ck_id")){
          ck_id = cookie.getValue();                                 // Cookie에 저장된 id
        }else if(cookie.getName().equals("ck_id_save")){
          ck_id_save = cookie.getValue();                          // Cookie에 id를 저장 할 것인지의 여부, Y, N
        }else if (cookie.getName().equals("ck_passwd")){
          ck_passwd = cookie.getValue();                          // Cookie에 저장된 password
        }else if(cookie.getName().equals("ck_passwd_save")){
          ck_passwd_save = cookie.getValue();                  // Cookie에 password를 저장 할 것인지의 여부, Y, N
        }
      }
    }
    
    System.out.println("-> ck_id: " + ck_id);
    
    mav.addObject("ck_id", ck_id); 
    mav.addObject("ck_id_save", ck_id_save);
    mav.addObject("ck_passwd", ck_passwd);
    mav.addObject("ck_passwd_save", ck_passwd_save);
    // -------------------------------------------------------------------------------
    
    return mav;
  }
  /**
   * 와인 목록 + 검색 + 페이징 지원 + Grid
   * 검색하지 않는 경우
   * http://localhost:9093/products/list_by_categoryID_grid.do?categoryID=2&word=&now_page=1
   * 검색하는 경우
   * http://localhost:9093/products/list_by_categoryID_grid.do?categoryID=2&word=탐험&now_page=1
   * 
   * @param categoryID
   * @param word
   * @param now_page
   * @return
   */
   @RequestMapping(value = "/products/list_all_3_grid.do", method = RequestMethod.GET)
   public ModelAndView list_all_3_grid(ProductsVO productsVO) {
      ModelAndView mav = new ModelAndView();
      ArrayList<CategoryVO> list_3 = categoryProc.list_all();
      mav.addObject("list_3", list_3);
    
      // 검색 목록
    ArrayList<ProductsVO> list = productsProc.list_all_3_search_paging(productsVO);
    
    // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
    for (ProductsVO vo : list) {
      String pName = vo.getpName();
      String description = vo.getDescription();
      
      pName = Tool.convertChar(pName);  // 특수 문자 처리
      description = Tool.convertChar(description); 
      
      vo.setpName(pName);
      vo.setDescription(description);  
    
      }
      
      mav.addObject("list", list);
    
      CategoryVO categoryVO = categoryProc.read(productsVO.getCategoryID());
      mav.addObject("categoryVO", categoryVO);
      
      HashMap<String, Object> hashMap = new HashMap<String, Object>();
      hashMap.put("categoryID", productsVO.getCategoryID());
      hashMap.put("word", productsVO.getWord());
      
      int search_count_3 = this.productsProc.search_count_3(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
      mav.addObject("search_count_3", search_count_3);
    
      /*
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
   * 18 19 20 [다음]
   * @param categoryID 카테고리번호
   * @param now_page 현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명
   * @return 페이징용으로 생성된 HTML/CSS tag 문자열
   */
    String paging = productsProc.pagingBox1(productsVO.getNow_page(), productsVO.getWord(), "list_all_3_grid.do", search_count_3);
    mav.addObject("paging", paging);
    
      // mav.addObject("now_page", now_page);
    
    mav.setViewName("/products/list_all_3_grid");  // /contents/list_by_categoryID_grid.jsp
    
      return mav;
    }
  
  /**
   * 리큐르 목록 + 검색 + 페이징 + Cookie 지원
   * http://localhost:9091/contents/list_by_categoryID_search_paging.do?categoryID=1&word=스위스&now_page=1
   * 
   * @param categoryID
   * @param word
   * @param now_page
   * @return
   */
  @RequestMapping(value = "/products/list_all_4.do", method = RequestMethod.GET)
  public ModelAndView list_all_4(
      @RequestParam(value = "categoryID", defaultValue = "1")int categoryID,
      @RequestParam(value = "word", defaultValue = "") String word,
      @RequestParam(value = "now_page", defaultValue = "1") int now_page,
      HttpServletRequest request, ProductsVO productsVO) {
    System.out.println("-> list_all_4_search_paging now_page: " + now_page);

    ModelAndView mav = new ModelAndView();
    ArrayList<CategoryVO> list_4 = categoryProc.list_all();
    mav.addObject("list_4", list_4);

    // 숫자와 문자열 타입을 저장해야함으로 Obejct 사용
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("categoryID", categoryID);
    map.put("word", word); // #{word}
    map.put("now_page", now_page); // 페이지에 출력할 레코드의 범위를 산출하기위해 사용

    // 검색 목록
    ArrayList<ProductsVO> list = productsProc.list_all_4_search_paging(productsVO);
    mav.addObject("list", list);

    // 검색된 레코드 갯수
    int search_count_4 = productsProc.search_count_4(map);
    mav.addObject("search_count_4", search_count_4);
    
    CategoryVO categoryVO = categoryProc.read(categoryID);
    mav.addObject("categoryVO", categoryVO);

    CateGroupVO cateGroupVO = cateGroupProc.read(categoryVO.getGrpID());
    mav.addObject("cateGroupVO", cateGroupVO);

    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param categoryID 카테고리번호
     * @param search_count 검색(전체) 레코드수
     * @param now_page 현재 페이지
     * @param word 검색어
     * @return 페이징 생성 문자열
     */
    // String paging = productsProc.pagingBox(categoryID, search_count, now_page, word);
    String paging = productsProc.pagingBox1(productsVO.getNow_page(), productsVO.getWord(), "list_all_4.do", search_count_4);

   
    mav.addObject("paging", paging);

    mav.addObject("now_page", now_page);

    // /views/contents/list_by_categoryID_search_paging_cookie.jsp
    mav.setViewName("/products/list_all_4");

    // -------------------------------------------------------------------------------
    // 쇼핑 카트 장바구니에 상품 등록전 로그인 폼 출력 관련 쿠기  
    // -------------------------------------------------------------------------------
    Cookie[] cookies = request.getCookies();
    Cookie cookie = null;

    String ck_id = ""; // id 저장
    String ck_id_save = ""; // id 저장 여부를 체크
    String ck_passwd = ""; // passwd 저장
    String ck_passwd_save = ""; // passwd 저장 여부를 체크

    if (cookies != null) {  // Cookie 변수가 있다면
      for (int i=0; i < cookies.length; i++){
        cookie = cookies[i]; // 쿠키 객체 추출
        
        if (cookie.getName().equals("ck_id")){
          ck_id = cookie.getValue();                                 // Cookie에 저장된 id
        }else if(cookie.getName().equals("ck_id_save")){
          ck_id_save = cookie.getValue();                          // Cookie에 id를 저장 할 것인지의 여부, Y, N
        }else if (cookie.getName().equals("ck_passwd")){
          ck_passwd = cookie.getValue();                          // Cookie에 저장된 password
        }else if(cookie.getName().equals("ck_passwd_save")){
          ck_passwd_save = cookie.getValue();                  // Cookie에 password를 저장 할 것인지의 여부, Y, N
        }
      }
    }
    
    System.out.println("-> ck_id: " + ck_id);
    
    mav.addObject("ck_id", ck_id); 
    mav.addObject("ck_id_save", ck_id_save);
    mav.addObject("ck_passwd", ck_passwd);
    mav.addObject("ck_passwd_save", ck_passwd_save);
    // -------------------------------------------------------------------------------
    
    return mav;
  }
  
  /**
   * 리큐르 목록 + 검색 + 페이징 지원 + Grid
   * 검색하지 않는 경우
   * http://localhost:9093/products/list_by_categoryID_grid.do?categoryID=2&word=&now_page=1
   * 검색하는 경우
   * http://localhost:9093/products/list_by_categoryID_grid.do?categoryID=2&word=탐험&now_page=1
   * 
   * @param categoryID
   * @param word
   * @param now_page
   * @return
   */
   @RequestMapping(value = "/products/list_all_4_grid.do", method = RequestMethod.GET)
   public ModelAndView list_all_4_grid(ProductsVO productsVO) {
      ModelAndView mav = new ModelAndView();
      ArrayList<CategoryVO> list_4 = categoryProc.list_all();
      mav.addObject("list_4", list_4);
    
      // 검색 목록
    ArrayList<ProductsVO> list = productsProc.list_all_4_search_paging(productsVO);
    
    // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
    for (ProductsVO vo : list) {
      String pName = vo.getpName();
      String description = vo.getDescription();
      
      pName = Tool.convertChar(pName);  // 특수 문자 처리
      description = Tool.convertChar(description); 
      
      vo.setpName(pName);
      vo.setDescription(description);  
    
      }
      
      mav.addObject("list", list);
    
      CategoryVO categoryVO = categoryProc.read(productsVO.getCategoryID());
      mav.addObject("categoryVO", categoryVO);
      
      HashMap<String, Object> hashMap = new HashMap<String, Object>();
      hashMap.put("categoryID", productsVO.getCategoryID());
      hashMap.put("word", productsVO.getWord());
      
      int search_count_4 = this.productsProc.search_count_4(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
      mav.addObject("search_count_4", search_count_4);
    
      /*
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
   * 18 19 20 [다음]
   * @param categoryID 카테고리번호
   * @param now_page 현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명
   * @return 페이징용으로 생성된 HTML/CSS tag 문자열
   */
    String paging = productsProc.pagingBox1(productsVO.getNow_page(), productsVO.getWord(), "list_all_4_grid.do", search_count_4);
    mav.addObject("paging", paging);
    
      // mav.addObject("now_page", now_page);
    
    mav.setViewName("/products/list_all_4_grid");  // /contents/list_by_categoryID_grid.jsp
    
      return mav;
    }
  
  /**
   * 전통주 목록 + 검색 + 페이징 + Cookie 지원
   * http://localhost:9091/contents/list_by_categoryID_search_paging.do?categoryID=1&word=스위스&now_page=1
   * 
   * @param categoryID
   * @param word
   * @param now_page
   * @return
   */
  @RequestMapping(value = "/products/list_all_5.do", method = RequestMethod.GET)
  public ModelAndView list_all_5(
      @RequestParam(value = "categoryID", defaultValue = "1")int categoryID,
      @RequestParam(value = "word", defaultValue = "") String word,
      @RequestParam(value = "now_page", defaultValue = "1") int now_page,
      HttpServletRequest request, ProductsVO productsVO) {
    System.out.println("-> list_all_5_search_paging now_page: " + now_page);

    ModelAndView mav = new ModelAndView();
    ArrayList<CategoryVO> list_5 = categoryProc.list_all();
    mav.addObject("list_5", list_5);

    // 숫자와 문자열 타입을 저장해야함으로 Obejct 사용
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("categoryID", categoryID);
    map.put("word", word); // #{word}
    map.put("now_page", now_page); // 페이지에 출력할 레코드의 범위를 산출하기위해 사용

    // 검색 목록
    ArrayList<ProductsVO> list = productsProc.list_all_5_search_paging(productsVO);
    mav.addObject("list", list);

    // 검색된 레코드 갯수
    int search_count_5 = productsProc.search_count_5(map);
    mav.addObject("search_count_5", search_count_5);
    
    CategoryVO categoryVO = categoryProc.read(categoryID);
    mav.addObject("categoryVO", categoryVO);

    CateGroupVO cateGroupVO = cateGroupProc.read(categoryVO.getGrpID());
    mav.addObject("cateGroupVO", cateGroupVO);

    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param categoryID 카테고리번호
     * @param search_count 검색(전체) 레코드수
     * @param now_page 현재 페이지
     * @param word 검색어
     * @return 페이징 생성 문자열
     */
    // String paging = productsProc.pagingBox(categoryID, search_count, now_page, word);
    String paging = productsProc.pagingBox1(productsVO.getNow_page(), productsVO.getWord(), "list_all_5.do", search_count_5);

   
    mav.addObject("paging", paging);

    mav.addObject("now_page", now_page);

    // /views/contents/list_by_categoryID_search_paging_cookie.jsp
    mav.setViewName("/products/list_all_5");

    // -------------------------------------------------------------------------------
    // 쇼핑 카트 장바구니에 상품 등록전 로그인 폼 출력 관련 쿠기  
    // -------------------------------------------------------------------------------
    Cookie[] cookies = request.getCookies();
    Cookie cookie = null;

    String ck_id = ""; // id 저장
    String ck_id_save = ""; // id 저장 여부를 체크
    String ck_passwd = ""; // passwd 저장
    String ck_passwd_save = ""; // passwd 저장 여부를 체크

    if (cookies != null) {  // Cookie 변수가 있다면
      for (int i=0; i < cookies.length; i++){
        cookie = cookies[i]; // 쿠키 객체 추출
        
        if (cookie.getName().equals("ck_id")){
          ck_id = cookie.getValue();                                 // Cookie에 저장된 id
        }else if(cookie.getName().equals("ck_id_save")){
          ck_id_save = cookie.getValue();                          // Cookie에 id를 저장 할 것인지의 여부, Y, N
        }else if (cookie.getName().equals("ck_passwd")){
          ck_passwd = cookie.getValue();                          // Cookie에 저장된 password
        }else if(cookie.getName().equals("ck_passwd_save")){
          ck_passwd_save = cookie.getValue();                  // Cookie에 password를 저장 할 것인지의 여부, Y, N
        }
      }
    }
    
    System.out.println("-> ck_id: " + ck_id);
    
    mav.addObject("ck_id", ck_id); 
    mav.addObject("ck_id_save", ck_id_save);
    mav.addObject("ck_passwd", ck_passwd);
    mav.addObject("ck_passwd_save", ck_passwd_save);
    // -------------------------------------------------------------------------------
    
    return mav;
  }
  
  /**
   * 전통주 목록 + 검색 + 페이징 지원 + Grid
   * 검색하지 않는 경우
   * http://localhost:9093/products/list_by_categoryID_grid.do?categoryID=2&word=&now_page=1
   * 검색하는 경우
   * http://localhost:9093/products/list_by_categoryID_grid.do?categoryID=2&word=탐험&now_page=1
   * 
   * @param categoryID
   * @param word
   * @param now_page
   * @return
   */
   @RequestMapping(value = "/products/list_all_5_grid.do", method = RequestMethod.GET)
   public ModelAndView list_all_5_grid(ProductsVO productsVO) {
      ModelAndView mav = new ModelAndView();
      ArrayList<CategoryVO> list_5 = categoryProc.list_all();
      mav.addObject("list_5", list_5);
    
      // 검색 목록
    ArrayList<ProductsVO> list = productsProc.list_all_5_search_paging(productsVO);
    
    // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
    for (ProductsVO vo : list) {
      String pName = vo.getpName();
      String description = vo.getDescription();
      
      pName = Tool.convertChar(pName);  // 특수 문자 처리
      description = Tool.convertChar(description); 
      
      vo.setpName(pName);
      vo.setDescription(description);  
    
      }
      
      mav.addObject("list", list);
    
      CategoryVO categoryVO = categoryProc.read(productsVO.getCategoryID());
      mav.addObject("categoryVO", categoryVO);
      
      HashMap<String, Object> hashMap = new HashMap<String, Object>();
      hashMap.put("categoryID", productsVO.getCategoryID());
      hashMap.put("word", productsVO.getWord());
      
      int search_count_5 = this.productsProc.search_count_5(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
      mav.addObject("search_count_5", search_count_5);
    
      /*
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
   * 18 19 20 [다음]
   * @param categoryID 카테고리번호
   * @param now_page 현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명
   * @return 페이징용으로 생성된 HTML/CSS tag 문자열
   */
    String paging = productsProc.pagingBox1(productsVO.getNow_page(), productsVO.getWord(), "list_all_5_grid.do", search_count_5);
    mav.addObject("paging", paging);
    
      // mav.addObject("now_page", now_page);
    
    mav.setViewName("/products/list_all_5_grid");  // /contents/list_by_categoryID_grid.jsp
    
      return mav;
    }
  
//   /**
//   * 목록 + 검색 + 페이징 지원
//   * 검색하지 않는 경우
//   * http://localhost:9093/products/list_by_categoryID.do?categoryID=2&word=&now_page=1
//   * 검색하는 경우
//   * http://localhost:9091/products/list_by_categoryID.do?categoryID=2&word=탐험&now_page=1
//   * 
//   * @param categoryID
//   * @param word
//   * @param now_page
//   * @return
//   */
//  @RequestMapping(value = "/products/list_by_categoryID.do", method = RequestMethod.GET)
//  public ModelAndView list_by_categoryID(ProductsVO productsVO) {
//    ModelAndView mav = new ModelAndView();
//  
//    // 검색 목록
//    ArrayList<ProductsVO> list = productsProc.list_by_categoryID_search_paging(productsVO);
//    
//    // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
//    for (ProductsVO vo : list) {
//      String pName = productsVO.getpName();
//      String description = productsVO.getDescription();
//      
//      pName = Tool.convertChar(pName);  // 특수 문자 처리
//      description = Tool.convertChar(description); 
//      
//      productsVO.setpName(pName);
//      productsVO.setDescription(description);  
//  
//    }
//    
//    mav.addObject("list", list);
//  
//    CategoryVO categoryVO = categoryProc.read(productsVO.getCategoryID());
//    mav.addObject("categoryVO", categoryVO);
//  
//    HashMap<String, Object> hashMap = new HashMap<String, Object>();
//    hashMap.put("categoryID", productsVO.getCategoryID());
//    hashMap.put("word", productsVO.getWord());
//    
//    int search_count = this.productsProc.search_count(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
//    mav.addObject("search_count", search_count);
//    
//    /*
//     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
//     * 18 19 20 [다음]
//     * @param categoryID 카테고리번호
//     * @param now_page 현재 페이지
//     * @param word 검색어
//     * @param list_file 목록 파일명
//     * @return 페이징용으로 생성된 HTML/CSS tag 문자열
//     */
//    String paging = productsProc.pagingBox(productsVO.getCategoryID(), productsVO.getNow_page(), productsVO.getWord(), "list_by_categoryID.do", search_count);
//    mav.addObject("paging", paging);
//  
//    // mav.addObject("now_page", now_page);
//    
//    mav.setViewName("/products/list_by_categoryID");  // /contents/list_by_categoryID.jsp
//  
//    return mav;
//  }
  
  /**
   * 목록 + 검색 + 페이징 + Cookie 지원
   * http://localhost:9091/contents/list_by_categoryID_search_paging.do?categoryID=1&word=스위스&now_page=1
   * 
   * @param categoryID
   * @param word
   * @param now_page
   * @return
   */
  @RequestMapping(value = "/products/list_by_categoryID.do", method = RequestMethod.GET)
  public ModelAndView list_by_cateno_search_paging_cookie_cart(
      @RequestParam(value = "categoryID", defaultValue = "1") int categoryID,
      @RequestParam(value = "word", defaultValue = "") String word,
      @RequestParam(value = "now_page", defaultValue = "1") int now_page,
      HttpServletRequest request, ProductsVO productsVO) {
    System.out.println("-> list_by_categoryID_search_paging now_page: " + now_page);

    ModelAndView mav = new ModelAndView();

    // 숫자와 문자열 타입을 저장해야함으로 Obejct 사용
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("categoryID", categoryID); // #{categoryID}
    map.put("word", word); // #{word}
    map.put("now_page", now_page); // 페이지에 출력할 레코드의 범위를 산출하기위해 사용

    // 검색 목록
    ArrayList<ProductsVO> list = productsProc.list_by_categoryID_search_paging(productsVO);
    mav.addObject("list", list);

    // 검색된 레코드 갯수
    int search_count = productsProc.search_count(map);
    mav.addObject("search_count", search_count);

    CategoryVO categoryVO = categoryProc.read(categoryID);
    mav.addObject("categoryVO", categoryVO);

    CateGroupVO cateGroupVO = cateGroupProc.read(categoryVO.getGrpID());
    mav.addObject("cateGroupVO", cateGroupVO);

    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param categoryID 카테고리번호
     * @param search_count 검색(전체) 레코드수
     * @param now_page 현재 페이지
     * @param word 검색어
     * @return 페이징 생성 문자열
     */
    // String paging = productsProc.pagingBox(categoryID, search_count, now_page, word);
    String paging = productsProc.pagingBox(productsVO.getCategoryID(), productsVO.getNow_page(), productsVO.getWord(), "list_by_categoryID.do", search_count);

   
    mav.addObject("paging", paging);

    mav.addObject("now_page", now_page);

    // /views/contents/list_by_categoryID_search_paging_cookie.jsp
    mav.setViewName("/products/list_by_categoryID_search_paging_cookie_carts");

    // -------------------------------------------------------------------------------
    // 쇼핑 카트 장바구니에 상품 등록전 로그인 폼 출력 관련 쿠기  
    // -------------------------------------------------------------------------------
    Cookie[] cookies = request.getCookies();
    Cookie cookie = null;

    String ck_id = ""; // id 저장
    String ck_id_save = ""; // id 저장 여부를 체크
    String ck_passwd = ""; // passwd 저장
    String ck_passwd_save = ""; // passwd 저장 여부를 체크

    if (cookies != null) {  // Cookie 변수가 있다면
      for (int i=0; i < cookies.length; i++){
        cookie = cookies[i]; // 쿠키 객체 추출
        
        if (cookie.getName().equals("ck_id")){
          ck_id = cookie.getValue();                                 // Cookie에 저장된 id
        }else if(cookie.getName().equals("ck_id_save")){
          ck_id_save = cookie.getValue();                          // Cookie에 id를 저장 할 것인지의 여부, Y, N
        }else if (cookie.getName().equals("ck_passwd")){
          ck_passwd = cookie.getValue();                          // Cookie에 저장된 password
        }else if(cookie.getName().equals("ck_passwd_save")){
          ck_passwd_save = cookie.getValue();                  // Cookie에 password를 저장 할 것인지의 여부, Y, N
        }
      }
    }
    
    System.out.println("-> ck_id: " + ck_id);
    
    mav.addObject("ck_id", ck_id); 
    mav.addObject("ck_id_save", ck_id_save);
    mav.addObject("ck_passwd", ck_passwd);
    mav.addObject("ck_passwd_save", ck_passwd_save);
    // -------------------------------------------------------------------------------
    
    return mav;
  }

  /**
  * 목록 + 검색 + 페이징 지원 + Grid
  * 검색하지 않는 경우
  * http://localhost:9093/products/list_by_categoryID_grid.do?categoryID=2&word=&now_page=1
  * 검색하는 경우
  * http://localhost:9093/products/list_by_categoryID_grid.do?categoryID=2&word=탐험&now_page=1
  * 
  * @param categoryID
  * @param word
  * @param now_page
  * @return
  */
  @RequestMapping(value = "/products/list_by_categoryID_grid.do", method = RequestMethod.GET)
  public ModelAndView list_by_categoryID_grid(ProductsVO productsVO) {
     ModelAndView mav = new ModelAndView();
   
     // 검색 목록
   ArrayList<ProductsVO> list = productsProc.list_by_categoryID_search_paging(productsVO);
   
   // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
   for (ProductsVO vo : list) {
     String pName = vo.getpName();
     String description = vo.getDescription();
     
     pName = Tool.convertChar(pName);  // 특수 문자 처리
     description = Tool.convertChar(description); 
     
     vo.setpName(pName);
     vo.setDescription(description);  
   
     }
     
     mav.addObject("list", list);
   
     CategoryVO categoryVO = categoryProc.read(productsVO.getCategoryID());
     mav.addObject("categoryVO", categoryVO);
     CateGroupVO cateGroupVO = cateGroupProc.read(categoryVO.getGrpID());
     mav.addObject("cateGroupVO", cateGroupVO);
     
     HashMap<String, Object> hashMap = new HashMap<String, Object>();
     hashMap.put("categoryID", productsVO.getCategoryID());
     hashMap.put("word", productsVO.getWord());
     
     int search_count = this.productsProc.search_count(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
     mav.addObject("search_count", search_count);
   
     /*
  * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
  * 18 19 20 [다음]
  * @param categoryID 카테고리번호
  * @param now_page 현재 페이지
  * @param word 검색어
  * @param list_file 목록 파일명
  * @return 페이징용으로 생성된 HTML/CSS tag 문자열
  */
   String paging = productsProc.pagingBox(productsVO.getCategoryID(), productsVO.getNow_page(), productsVO.getWord(), "list_by_categoryID_grid.do", search_count);
   mav.addObject("paging", paging);
   
     // mav.addObject("now_page", now_page);
   
   mav.setViewName("/products/list_by_categoryID_grid");  // /contents/list_by_categoryID_grid.jsp
   
     return mav;
   }
 
 
  /**
   * 조회
   * http://localhost:9093/products/read.do?productID=17
   * @return
   */
  @RequestMapping(value="/products/read.do", method = RequestMethod.GET)
  public ModelAndView read(int productID) { // int categoryID = (int)request.getParameter("categoryID");
    ModelAndView mav = new ModelAndView();
    //mav.setViewName("/products/read"); // /WEB-INF/views/contents/read.jsp
    
    ProductsVO productsVO = this.productsProc.read(productID);
    
    String pName = productsVO.getpName();
    String description = productsVO.getDescription();
    
    pName = Tool.convertChar(pName);  // 특수 문자 처리
    description = Tool.convertChar(description); 
    
    productsVO.setpName(pName);
    productsVO.setDescription(description);  
    
    long sizes = productsVO.getSizes();
    String size_label = Tool.unit(sizes);
    productsVO.setSize_label(size_label);
    
    mav.addObject("productsVO", productsVO);
    
    CategoryVO categoryVO = this.categoryProc.read(productsVO.getCategoryID());
    mav.addObject("categoryVO", categoryVO);

    /* 1214 세진 추가(리뷰관련) */
    // 댓글 기능 추가 
    mav.setViewName("/products/read_review_add");
    
    return mav;
  }
  
  /**
   * 수정 폼
   * http://localhost:9093/products/update_text.do?productID=1
   * 
   * @return
   */
  @RequestMapping(value = "/products/update_text.do", method = RequestMethod.GET)
  public ModelAndView update_text(HttpSession session, int productID) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
      ProductsVO productsVO = this.productsProc.read(productID);
      mav.addObject("productsVO", productsVO);
      
      ArrayList<SupplierVO> list_sup = this.supplierProc.list_all();
      mav.addObject("list_sup", list_sup);
      
      CategoryVO categoryVO = this.categoryProc.read(productsVO.getCategoryID());
      mav.addObject("categoryVO", categoryVO);
      
      mav.setViewName("/products/update_text"); // /WEB-INF/views/contents/update_text.jsp
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
   * http://localhost:9093/products/update_text.do?productID=1
   * 
   * @return
   */
  @RequestMapping(value = "/products/update_text.do", method = RequestMethod.POST)
  public ModelAndView update_text(HttpSession session, ProductsVO productsVO) {
    ModelAndView mav = new ModelAndView();
    
    // System.out.println("-> word: " + contentsVO.getWord());
    
    if (this.adminProc.isAdmin(session)) { // 관리자 로그인 확인
      HashMap<String, Object> hashMap = new HashMap<String, Object>();
      hashMap.put("productID", productsVO.getProductID());


      this.productsProc.update_text(productsVO); // 글수정  
      
      //adminlog관련 
      int recordid = productsVO.getProductID();
      int adno =  (int) session.getAttribute("adminno");
      String acttype = "Update_Text";  
      adlogservice.createLog(tablename, recordid, acttype, adno);
      
      // mav 객체 이용
      mav.addObject("productID", productsVO.getProductID());
      mav.addObject("categoryID", productsVO.getCategoryID());
      mav.setViewName("redirect:/products/read.do"); // 페이지 자동 이동

    } else { // 정상적인 로그인이 아닌 경우 로그인 유도
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/products/msg.do"); 
    }
    
    mav.addObject("now_page", productsVO.getNow_page()); // POST -> GET: 데이터 분실이 발생함으로 다시하번 데이터 저장 ★
    
    // URL에 파라미터의 전송
    // mav.setViewName("redirect:/contents/read.do?contentsno=" + contentsVO.getContentsno() + "&categoryID=" + contentsVO.getCateno());             
    
    return mav; // forward
  }

  /**
   * 파일 수정 폼
   * http://localhost:9093/products/update_file.do?productID=1
   * 
   * @return
   */
  @RequestMapping(value = "/products/update_file.do", method = RequestMethod.GET)
  public ModelAndView update_file(HttpSession session, int productID) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
      ProductsVO productsVO = this.productsProc.read(productID);
      mav.addObject("productsVO", productsVO);
      
      CategoryVO categoryVO = this.categoryProc.read(productsVO.getCategoryID());
      mav.addObject("categoryVO", categoryVO);
      
      mav.setViewName("/products/update_file"); // /WEB-INF/views/contents/update_file.jsp
      
    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/products/msg.do"); 
    }


    return mav; // forward
  }
  
  /**
   * 파일 수정 처리 http://localhost:9093/products/update_file.do
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
      String imageFileSaved = productsVO_old.getImageFileSaved();  // 실제 저장된 파일명
      String thumb = productsVO_old.getThumb();       // 실제 저장된 preview 이미지 파일명
      long sizes = 0;
         
      String upDir =  Products.getUploadDir(); // C:/kd/deploy/resort_v3sbm3c/contents/storage/
      
      Tool.deleteFile(upDir, imageFileSaved);  // 실제 저장된 파일삭제
      Tool.deleteFile(upDir, thumb);     // preview 이미지 삭제
      // -------------------------------------------------------------------
      // 파일 삭제 종료
      // -------------------------------------------------------------------
          
      // -------------------------------------------------------------------
      // 파일 전송 시작
      // -------------------------------------------------------------------
      String imageFile = "";          // 원본 파일명 image

      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      MultipartFile mf = productsVO.getFileMF();
          
      imageFile = mf.getOriginalFilename(); // 원본 파일명
      sizes = mf.getSize();  // 파일 크기
          
      if (sizes > 0) { // 폼에서 새롭게 올리는 파일이 있는지 파일 크기로 체크 ★
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
        imageFileSaved = Upload.saveFileSpring(mf, upDir); 
        
        if (Tool.isImage(imageFileSaved)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 250, height: 200
          thumb = Tool.preview(upDir, imageFileSaved, 250, 200); 
        }
        
      } else { // 파일이 삭제만 되고 새로 올리지 않는 경우
        imageFile="";
        imageFileSaved="";
        thumb="";
        sizes=0;
      }
          
      productsVO.setImageFile(imageFile);
      productsVO.setImageFileSaved(imageFileSaved);
      productsVO.setThumb(thumb);
      productsVO.setSizes(sizes);
      // -------------------------------------------------------------------
      // 파일 전송 코드 종료
      // -------------------------------------------------------------------
          
      this.productsProc.update_file(productsVO); // Oracle 처리
      
      //adminlog관련 
      int recordid = productsVO.getProductID();
      int adno =  (int) session.getAttribute("adminno");
      String acttype = "Update_File";  
      adlogservice.createLog(tablename, recordid, acttype, adno);
      

      mav.addObject("productID", productsVO.getProductID());
      mav.addObject("categoryID", productsVO.getCategoryID());
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
   * http://localhost:9093/products/delete.do?productID=1
   * 
   * @return
   */
  @RequestMapping(value = "/products/delete.do", method = RequestMethod.GET)
  public ModelAndView delete(HttpSession session, int productID) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
      ProductsVO productsVO = this.productsProc.read(productID);
      mav.addObject("productsVO", productsVO);
      
      CategoryVO categoryVO = this.categoryProc.read(productsVO.getCategoryID());
      mav.addObject("categoryVO", categoryVO);
      
      mav.setViewName("/products/delete"); // /WEB-INF/views/contents/delete.jsp
      
    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/products/msg.do"); 
    }


    return mav; // forward
  }
  
  /**
   * 삭제 처리 http://localhost:9093/products/delete.do
   * 
   * @return
   */
  @RequestMapping(value = "/products/delete.do", method = RequestMethod.POST)
  public ModelAndView delete(HttpSession session, ProductsVO productsVO) {
    ModelAndView mav = new ModelAndView();
    
 // 삭제할 파일 정보를 읽어옴, 기존에 등록된 레코드 저장용
    ProductsVO productsVO_old = productsProc.read(productsVO.getProductID());
    
    // -------------------------------------------------------------------
    // 파일 삭제 시작
    // -------------------------------------------------------------------
    String imageFileSaved = productsVO_old.getImageFileSaved();  // 실제 저장된 파일명
    String thumb = productsVO_old.getThumb();       // 실제 저장된 preview 이미지 파일명
    long sizes = 0;
       
    String upDir =  Products.getUploadDir(); // C:/kd/deploy/resort_v3sbm3c/contents/storage/
    
    Tool.deleteFile(upDir, imageFileSaved);  // 실제 저장된 파일삭제
    Tool.deleteFile(upDir, thumb);     // preview 이미지 삭제
    // -------------------------------------------------------------------
    // 파일 삭제 종료
    // --------------------------------------------------------------------
        
    this.productsProc.delete(productsVO.getProductID()); // DBMS 삭제
    
    //adminlog관련 
    int recordid = productsVO.getProductID();
    int adno =  (int) session.getAttribute("adminno");
    String acttype = "Delete";  
    adlogservice.createLog(tablename, recordid, acttype, adno);
    
        
    // -------------------------------------------------------------------------------------
    // 마지막 페이지의 마지막 레코드 삭제시의 페이지 번호 -1 처리
    // -------------------------------------------------------------------------------------    
    // 마지막 페이지의 마지막 10번째 레코드를 삭제후
    // 하나의 페이지가 3개의 레코드로 구성되는 경우 현재 9개의 레코드가 남아 있으면
    // 페이지수를 4 -> 3으로 감소 시켜야함, 마지막 페이지의 마지막 레코드 삭제시 나머지는 0 발생
    int now_page = productsVO.getNow_page();
    
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("categoryID", productsVO.getCategoryID());
    hashMap.put("word", productsVO.getWord());
    
    if (productsProc.search_count(hashMap) % Products.RECORD_PER_PAGE == 0) {
      now_page = now_page - 1; // 삭제시 DBMS는 바로 적용되나 크롬은 새로고침등의 필요로 단계가 작동 해야함.
      if (now_page < 1) {
        now_page = 1; // 시작 페이지
      }
    }
    // -------------------------------------------------------------------------------------

    mav.addObject("categoryID", productsVO.getCategoryID());
    mav.addObject("now_page", now_page);
    mav.setViewName("redirect:/products/list_by_categoryID.do"); 
    
    return mav;
  }   
      
  // http://localhost:9093/products/delete_by_categoryID.do?categoryID=1
  // 파일 삭제 -> 레코드 삭제
  @RequestMapping(value = "/products/delete_by_categoryID.do", method = RequestMethod.GET)
  public String delete_by_categoryID(int categoryID) {
    ArrayList<ProductsVO> list = this.productsProc.list_by_categoryID(categoryID);
    
    for(ProductsVO productsVO : list) {
      // -------------------------------------------------------------------
      // 파일 삭제 시작
      // -------------------------------------------------------------------
      String imageFileSaved = productsVO.getImageFileSaved();
      String thumb = productsVO.getThumb();
      
      String uploadDir = Products.getUploadDir();
      Tool.deleteFile(uploadDir, imageFileSaved);  // 실제 저장된 파일삭제
      Tool.deleteFile(uploadDir, thumb);     // preview 이미지 삭제
      // -------------------------------------------------------------------
      // 파일 삭제 종료
      // -------------------------------------------------------------------
    }
    
    int cnt = this.productsProc.delete_by_categoryID(categoryID);
    System.out.println("-> count: " + cnt);
    
    return "";
  
  }
  
  /**
   * Gallery 전체 이미지 출력
   * http://localhost:9093/products/list_all_gallery.do
   * @return
   */
  @RequestMapping(value="/products/list_all_gallery.do", method = RequestMethod.GET)
  public ModelAndView list_all_gallery(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/products/list_all_gallery"); // /WEB-INF/views/contents/list_all_gallery.jsp
      
      ArrayList<ProductsVO> list = this.productsProc.list_all();
      mav.addObject("list", list);
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      
    }
    
    return mav;
  }
  
}


