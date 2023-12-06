package dev.mvc.orders;

import org.springframework.web.multipart.MultipartFile;

public class OrdersVO {
  
  private int OrderID;
  private int memberno;
  private String OrderDate;
  private int ProductID;
  private int ShippingID;
  private int Price;
  
  public int getPrice() {
    return Price;
  }
  public void setPrice(int price) {
    Price = price;
  }
  
  public int getOrderID() {
    return OrderID;
  }
  public void setOrderID(int orderID) {
    OrderID = orderID;
  }
  public int getMemberno() {
    return memberno;
  }
  public void setMemberno(int memberno) {
    this.memberno = memberno;
  }
  public String getOrderDate() {
    return OrderDate;
  }
  public void setOrderDate(String orderDate) {
    OrderDate = orderDate;
  }
  public int getProductID() {
    return ProductID;
  }
  public void setProductID(int productID) {
    ProductID = productID;
  }
  public int getShippingID() {
    return ShippingID;
  }
  public void setShippingID(int shippingID) {
    ShippingID = shippingID;
  }
  
  @Override
  public String toString() {
    return "OrdersVO [OrderID=" + OrderID + ", memberno=" + memberno + ", OrderDate=" + OrderDate + ", ProductID="
        + ProductID + ", ShippingID=" + ShippingID + ", Price=" + Price + "]";
  }
  
}
