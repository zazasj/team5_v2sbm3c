package dev.mvc.inventory;

//CREATE TABLE Inventory(
//    InventoryID VARCHAR(10) PRIMARY KEY,
//    ProductID NUMERIC(10)
//        SupplierID NUMERIC(10),
//    Quantity NUMERIC(10), 
//    LastUpdated DATE
//  --FOREIGN KEY (ProductID) REFERENCES Products (ProductID),
//  --FOREIGN KEY (ImageFileSaved) REFERENCES Products (ImageFileSaved),
//  --FOREIGN KEY (SupplierID) REFERENCES Suppliers (SupplierID)
//);
public class InventoryVO {
  private int inventoryID;
  private int productID;
  private int supplierID;
  private int quantity;
  private int addQuantity;
  private String inventoryStatus;
  private String lastUpdated;
  private String word;
  //페이징 관련
  // -----------------------------------------------------------------------------------
  /** 시작 rownum */
  private int start_num;    
  /** 종료 rownum */
  private int end_num;    
  /** 현재 페이지 */
  private int now_page = 1;
  
  public int getInventoryID() {
    return inventoryID;
  }
  public void setInventoryID(int inventoryID) {
    this.inventoryID = inventoryID;
  }
  public int getProductID() {
    return productID;
  }
  public void setProductID(int productID) {
    this.productID = productID;
  }
  public int getSupplierID() {
    return supplierID;
  }
  public void setSupplierID(int supplierID) {
    this.supplierID = supplierID;
  }
  public int getQuantity() {
    return quantity;
  }
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
  public int getAddQuantity() {
    return addQuantity;
  }
  public void setAddQuantity(int addQuantity) {
    this.addQuantity = addQuantity;
  }
  public String getInventoryStatus() {
    return inventoryStatus;
  }
  public void setInventoryStatus(String inventoryStatus) {
    this.inventoryStatus = inventoryStatus;
  }
  public String getLastUpdated() {
    return lastUpdated;
  }
  public void setLastUpdated(String lastUpdated) {
    this.lastUpdated = lastUpdated;
  }
  public String getWord() {
    return word;
  }
  public void setWord(String word) {
    this.word = word;
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
    return "InventoryVO [inventoryID=" + inventoryID + ", addQuantity =" + addQuantity +", inventoryStatus=" + inventoryStatus +", productID=" + productID + ", supplierID=" + supplierID + ", lastUpdated=" + lastUpdated + ",word = " + word +","
        + "start_num=" + start_num + ", end_num=" + end_num + ", now_page=" + now_page + "]";
  }
   
}

