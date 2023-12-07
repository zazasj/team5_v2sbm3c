package dev.mvc.products;

import org.springframework.web.multipart.MultipartFile;

public class ProductsVO {
    /** 주류 번호 */
    private int ProductID;
    
    /** 카테고리 번호 */
    private int CategoryID;
    
    /** 공급자 번호 */
    private int SupplierID;
    
    /** 관리자 번호 */
    private int adminno;
    
    /** 국가 */
    private String Origin = "";
    
    /** 이름 */
    private String PName = "";
    
    /** 검색어 */
    private String word = "";
    
    /** 설명 */
    private String Description = "";
    
    /** 용량 */
    private int Volume;
    
    /** 알코올 도수 */
    private int AlcoholContent;
    
    /** 가격 */
    private int Price;
    
    /** 조회 수 */
    private int Pcnt = 0;
    
    /** 추천 수 */
    private int recom;

    // 파일 업로드 관련
    // -----------------------------------------------------------------------------------
    /**
    이미지 파일
    <input type='file' class="form-control" name='file1MF' id='file1MF' 
               value='' placeholder="파일 선택"> */
    
    private MultipartFile fileMF;
    /** 메인 이미지 크기 단위, 파일 크기 */
    private String sizes_label = "";
    /** 메인 이미지 */
    private String ImageFile = "";
    /** 실제 저장된 메인 이미지 */
    private String ImageFileSaved = "";
    /** 메인 이미지 preview */
    private String Thumbs = "";
    /** 메인 이미지 크기 */
    private long sizes;

    // 쇼핑몰 상품 관련
    // -----------------------------------------------------------------------------------
//    /** 정가 */
//    private int price;
//    /** 할인률 */
//    private int dc;
//    /** 판매가 */
//    private int saleprice;
//    /** 포인트 */
//    private int point;
//    /** 재고 수량 */
//    private int salecnt;
    
    // 페이징 관련
    // -----------------------------------------------------------------------------------
    /** 시작 rownum */
    private int start_num;    
    /** 종료 rownum */
    private int end_num;    
    /** 현재 페이지 */
    private int now_page = 1;
    public int getProductID() {
      return ProductID;
    }
    public void setProductID(int ProductID) {
      this.ProductID = ProductID;
    }
    public int getCategoryID() {
      return CategoryID;
    }
    public void setCategoryID(int CategoryID) {
      this.CategoryID = CategoryID;
    }
    public int getSupplierID() {
      return SupplierID;
    }
    public void setSupplierID(int SupplierID) {
      this.SupplierID = SupplierID;
    }
    public int getAdminno() {
      return adminno;
    }
    public void setAdminno(int adminno) {
      this.adminno = adminno;
    }
    public String getOrigin() {
      return Origin;
    }
    public void setOrigin(String Origin) {
      this.Origin = Origin;
    }
    public String getPName() {
      return PName;
    }
    public void setPName(String PName) {
      this.PName = PName;
    }
    public String getWord() {
      return word;
    }
    public void setWord(String word) {
      this.word = word;
    }
    public String getDescription() {
      return Description;
    }
    public void setDescription(String Description) {
      this.Description = Description;
    }
    public int getVolume() {
      return Volume;
    }
    public void setVolume(int Volume) {
      this.Volume = Volume;
    }
    public int getAlcoholContent() {
      return AlcoholContent;
    }
    public void setAlcoholContent(int AlcoholContent) {
      this.AlcoholContent = AlcoholContent;
    }
    public int getPrice() {
      return Price;
    }
    public void setPrice(int Price) {
      this.Price = Price;
    }
    public int getPcnt() {
      return Pcnt;
    }
    public void setPcnt(int Pcnt) {
      this.Pcnt = Pcnt;
    }
    public int getRecom() {
      return recom;
    }
    public void setRecom(int recom) {
      this.recom = recom;
    }
    public MultipartFile getFileMF() {
      return fileMF;
    }
    public String getSizes_label() {
      return sizes_label;
    }
    public void setSizes_label(String sizes_label) {
      this.sizes_label = sizes_label;
    }
    public void setFileMF(MultipartFile fileMF) {
      this.fileMF = fileMF;
    }
    public String getImageFile() {
      return ImageFile;
    }
    public void setImageFile(String ImageFile) {
      this.ImageFile = ImageFile;
    }
    public String getImageFileSaved() {
      return ImageFileSaved;
    }
    public void setImageFileSaved(String ImageFileSaved) {
      this.ImageFileSaved = ImageFileSaved;
    }
    public String getThumbs() {
      return Thumbs;
    }
    public void setThumbs(String Thumbs) {
      this.Thumbs = Thumbs;
    }
    public long getSizes() {
      return sizes;
    }
    public void setSizes(long sizes) {
      this.sizes = sizes;
    }
    public int getStart_num() {
      return start_num;
    }
    public void setStart_num(int start_num) {
      this.start_num = start_num;
    }
    public int getEnd_num() {
      return end_num;
    }
    public void setEnd_num(int end_num) {
      this.end_num = end_num;
    }
    public int getNow_page() {
      return now_page;
    }
    public void setNow_page(int now_page) {
      this.now_page = now_page;
    }
    
    @Override
    public String toString() {
      return "ProductsVO [ProductID=" + ProductID + ", CategoryID=" + CategoryID + ", SupplierID=" + SupplierID
          + ", adminno=" + adminno + ", Origin=" + Origin + ", PName=" + PName + ", word=" + word + ", Description="
          + Description + ", Volume=" + Volume + ", AlcoholContent=" + AlcoholContent + ", Price=" + Price + ", Pcnt="
          + Pcnt + ", recom=" + recom + ", fileMF=" + fileMF + ", sizes_label=" + sizes_label + ", ImageFile="
          + ImageFile + ", ImageFileSaved=" + ImageFileSaved + ", Thumbs=" + Thumbs + ", sizes=" + sizes
          + ", start_num=" + start_num + ", end_num=" + end_num + ", now_page=" + now_page + "]";
    }

 
}


