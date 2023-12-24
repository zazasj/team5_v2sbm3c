package dev.mvc.shipping;

import org.springframework.web.multipart.MultipartFile;

//CREATE TABLE Shipping(
//    ShippingID VARCHAR(10) PRIMARY KEY,
//    ProductID NUMERIC(10)
//        SupplierID NUMERIC(10),
//    Quantity NUMERIC(10), 
//    LastUpdated DATE
//  --FOREIGN KEY (ProductID) REFERENCES Products (ProductID),
//  --FOREIGN KEY (ImageFileSaved) REFERENCES Products (ImageFileSaved),
//  --FOREIGN KEY (SupplierID) REFERENCES Suppliers (SupplierID)
//);
public class ShippingVO {
  private int managerno;
  private int shippingID;
  private int order_payno;
  private String shippingType;
  private int cost;
  private int deliveryPrice;
  private String estimatedDeliveryDate;
  private int trackingNumber;
  private String deliveryStatus;
  /** 검색어 */
  private String word = "";
  private MultipartFile file1MF;
  /** 메인 이미지 크기 단위, 파일 크기 */
  private String size1_label = "";
  /** 메인 이미지 */
  private String file1 = "";
  /** 실제 저장된 메인 이미지 */
  private String file1saved = "";
  /** 메인 이미지 preview */
  private String thumb1 = "";
  /** 메인 이미지 크기 */
  private long size1;
  
  // 페이징 관련
  // -----------------------------------------------------------------------------------
  /** 시작 rownum */
  private int start_num;    
  /** 종료 rownum */
  private int end_num;    
  /** 현재 페이지 */
  private int now_page = 1;
  
  public int getShippingID() {
    return shippingID;
  }
  public void setShippingID(int shippingID) {
    this.shippingID = shippingID;
  }
  public int getOrder_payno() {
    return order_payno;
  }
  public void setOrder_payno(int order_payno) {
    this.order_payno = order_payno;
  }
  public String getShippingType() {
    return shippingType;
  }
  public void setShippingType(String shippingType) {
    this.shippingType = shippingType;
  }
  public int getCost() {
    return cost;
  }
  public void setCost(int cost) {
    this.cost = cost;
  }
  public int getDeliveryPrice() {
    return deliveryPrice;
  }
  public void setDeliveryPrice(int deliveryPrice) {
    this.deliveryPrice = deliveryPrice;
  }
  public String getEstimatedDeliveryDate() {
    return estimatedDeliveryDate;
  }
  public void setEstimatedDeliveryDate(String estimatedDeliveryDate) {
    this.estimatedDeliveryDate = estimatedDeliveryDate;
  }
  public int getTrackingNumber() {
    return trackingNumber;
  }
  public void setTrackingNumber(int trackingNumber) {
    this.trackingNumber = trackingNumber;
  }
  public String getDeliveryStatus() {
    return deliveryStatus;
  }
  public void setDeliveryStatus(String deliveryStatus) {
    this.deliveryStatus = deliveryStatus;
  }
  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }
  public String getFile1() {
    return file1;
  }

  public void setFile1(String file1) {
    this.file1 = file1;
  }

  public String getFile1saved() {
    return file1saved;
  }

  public void setFile1saved(String file1saved) {
    this.file1saved = file1saved;
  }

  public String getThumb1() {
    return thumb1;
  }

  public void setThumb1(String thumb1) {
    this.thumb1 = thumb1;
  }

  public long getSize1() {
    return size1;
  }

  public void setSize1(long size1) {
    this.size1 = size1;
  }
  
  public MultipartFile getFile1MF() {
    return file1MF;
  }

  public void setFile1MF(MultipartFile file1mf) {
    file1MF = file1mf;
  }

  public String getSize1_label() {
    return size1_label;
  }

  public void setSize1_label(String size1_label) {
    this.size1_label = size1_label;
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
  public int getManagerno() {
    return managerno;
  }

  public void setManagerno(int managerno) {
    this.managerno = managerno;
  }
  
   
}

