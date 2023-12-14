package dev.mvc.supplier;

public class SupplierVO {
	  
	  /** 공급업체 ID */
	  private int supplierid;
	  /** 관리자 번호 */
	  private int adminno;
	  /** 공급업체 이름 */
	  private String sname;
	  /** 전화번호 */
	  private String contactinfo;
	  /** 주소 */
	  private String saddress;
	  /** 날짜 */
	  private String rdate;
	  
	  
	public int getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(int supplierid) {
		this.supplierid = supplierid;
	}
	public int getAdminno() {
		return adminno;
	}
	public void setAdminno(int adminno) {
		this.adminno = adminno;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getContactinfo() {
		return contactinfo;
	}
	public void setContactinfo(String contactinfo) {
		this.contactinfo = contactinfo;
	}
	public String getSaddress() {
		return saddress;
	}
	public void setSaddress(String saddress) {
		this.saddress = saddress;
	}
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	@Override
	public String toString() {
		return "SupplierVO [supplierid=" + supplierid + ", adminno=" + adminno + ", sname=" + sname + ", contactinfo="
				+ contactinfo + ", saddress=" + saddress + ", rdate=" + rdate + "]";
	}
	
	  
	  
}
