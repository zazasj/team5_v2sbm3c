package dev.mvc.orders;

import org.springframework.web.multipart.MultipartFile;

public class OrdersVO {
  
  private int OrderID;
  private String FormattedOrderDate;
  private int memberno;
  private String Mname;
  private int ProductID;
  private int Price;
  
  public int getOrderID() {
    return OrderID;
  }
  public void setOrderID(int orderID) {
    OrderID = orderID;
  }
  public String getFormattedOrderDate() {
    return FormattedOrderDate;
  }
  public void setFormattedOrderDate(String formattedOrderDate) {
    FormattedOrderDate = formattedOrderDate;
  }
  public int getMemberno() {
    return memberno;
  }
  public void setMemberno(int memberno) {
    this.memberno = memberno;
  }
  public String getMname() {
    return Mname;
  }
  public void setMname(String mname) {
    Mname = mname;
  }
  public int getProductID() {
    return ProductID;
  }
  public void setProductID(int productID) {
    ProductID = productID;
  }
  public int getPrice() {
    return Price;
  }
  public void setPrice(int price) {
    Price = price;
  }
  
  @Override
  public String toString() {
    return "OrdersVO [OrderID=" + OrderID + ", FormattedOrderDate=" + FormattedOrderDate + ", memberno=" + memberno
        + ", Mname=" + Mname + ", ProductID=" + ProductID + ", Price=" + Price + "]";
  }
   
}
