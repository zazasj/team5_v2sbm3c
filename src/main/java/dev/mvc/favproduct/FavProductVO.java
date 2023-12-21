package dev.mvc.favproduct;

public class FavProductVO {
	
	private int favID;
	private int productID;
	private String pName="";
	private String thumb="";
	private int price;
	private int memberno;
	private String createat;
	
	public int getFavID() {
		return favID;
	}
	public void setFavID(int favID) {
		this.favID = favID;
	}
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getMemberno() {
		return memberno;
	}
	public void setMemberno(int memberno) {
		this.memberno = memberno;
	}
	public String getCreateat() {
		return createat;
	}
	public void setCreateat(String createat) {
		this.createat = createat;
	}
	@Override
	public String toString() {
		return "FavProductVO [favID=" + favID + ", productID=" + productID + ", pName=" + pName + ", thumb=" + thumb
				+ ", price=" + price + ", memberno=" + memberno + ", createat=" + createat + "]";
	}

	
	
}
