package dev.mvc.products;

import org.springframework.web.multipart.MultipartFile;


//@Getter @Setter @ToString
public class ProductsVO {
    /** 제품 번호 */
    private int productID;
    /** 카테고리 번호 */
    private int categoryID;
    /** 공급자 번호 */
    private int supplierID;
    /** 관리자 번호 */
    private int adminno;
    /** 나라 이름 */
    private String origin;
    /** 이름 */
    private String pName = "";
    /** 설명 */
    private String description = "";
    /** 용량 */
    private int volume;
    /** 알코올 도수 */
    private int alcoholContent;
    /** 가격 */
    private int price;
    /** 추천수 */
    private int recom;
    /** 조회수 */
    private int pcnt = 0;
    /** 검색어 */
    private String word = "";


    // 파일 업로드 관련
    // -----------------------------------------------------------------------------------
    /**
    이미지 파일
    <input type='file' class="form-control" name='file1MF' id='file1MF' 
               value='' placeholder="파일 선택">
    */
    private MultipartFile fileMF;
    /** 메인 이미지 크기 단위, 파일 크기 */
    private String size_label = "";
    /** 메인 이미지 */
    private String imageFile = "";
    /** 실제 저장된 메인 이미지 */
    private String imageFileSaved = "";
    /** 메인 이미지 preview */
    private String thumb = "";
    /** 메인 이미지 크기 */
    private long sizes;

    // 페이징 관련
    // -----------------------------------------------------------------------------------
    /** 시작 rownum */
    private int start_num;    
    /** 종료 rownum */
    private int end_num;    
    /** 현재 페이지 */
    private int now_page = 1;
    public int getProductID() {
      return productID;
    }
    public void setProductID(int productID) {
      this.productID = productID;
    }
    public int getCategoryID() {
      return categoryID;
    }
    public void setCategoryID(int categoryID) {
      this.categoryID = categoryID;
    }
    public int getSupplierID() {
      return supplierID;
    }
    public void setSupplierID(int supplierID) {
      this.supplierID = supplierID;
    }
    
    public int getAdminno() {
      return adminno;
    }
    public void setAdminno(int adminno) {
      this.adminno = adminno;
    }
    public String getOrigin() {
      return origin;
    }
    public void setOrigin(String origin) {
      this.origin = origin;
    }
    public String getpName() {
      return pName;
    }
    public void setpName(String pName) {
      this.pName = pName;
    }
    public String getDescription() {
      return description;
    }
    public void setDescription(String description) {
      this.description = description;
    }
    public int getVolume() {
      return volume;
    }
    public void setVolume(int volume) {
      this.volume = volume;
    }
    public int getAlcoholContent() {
      return alcoholContent;
    }
    public void setAlcoholContent(int alcoholContent) {
      this.alcoholContent = alcoholContent;
    }
    public int getPrice() {
      return price;
    }
    public void setPrice(int price) {
      this.price = price;
    }
    public int getRecom() {
      return recom;
    }
    public void setRecom(int recom) {
      this.recom = recom;
    }
    public int getPcnt() {
      return pcnt;
    }
    public void setPcnt(int pcnt) {
      this.pcnt = pcnt;
    }
    public String getWord() {
      return word;
    }
    public void setWord(String word) {
      this.word = word;
    }
    public MultipartFile getFileMF() {
      return fileMF;
    }
    public void setFileMF(MultipartFile fileMF) {
      this.fileMF = fileMF;
    }
    public String getSize_label() {
      return size_label;
    }
    public void setSize_label(String size_label) {
      this.size_label = size_label;
    }
    public String getImageFile() {
      return imageFile;
    }
    public void setImageFile(String imageFile) {
      this.imageFile = imageFile;
    }
    public String getImageFileSaved() {
      return imageFileSaved;
    }
    public void setImageFileSaved(String imageFileSaved) {
      this.imageFileSaved = imageFileSaved;
    }
    public String getThumb() {
      return thumb;
    }
    public void setThumb(String thumb) {
      this.thumb = thumb;
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
      return "ProductsVO [productID=" + productID + ", categoryID=" + categoryID + ", supplierID=" + supplierID
          + ", adminno=" + adminno + ", origin=" + origin + ", pName=" + pName + ", description=" + description
          + ", volume=" + volume + ", alcoholContent=" + alcoholContent + ", price=" + price + ", recom=" + recom
          + ", pcnt=" + pcnt + ", word=" + word + ", fileMF=" + fileMF + ", size_label=" + size_label + ", imageFile="
          + imageFile + ", imageFileSaved=" + imageFileSaved + ", thumb=" + thumb + ", sizes=" + sizes + ", start_num="
          + start_num + ", end_num=" + end_num + ", now_page=" + now_page + "]";
    }

    
}


