package dev.mvc.supplier;

import org.springframework.web.multipart.MultipartFile;

public class SupplierVO {
	  
	  /** 공급업체 ID */
	  private int supplierid;
	  /** 관리자 번호 */
	  private int adminno;
	  /** 공급업체 이름 */
	  private String sname="";
	  /** 전화번호 */
	  private String contactinfo="";
	  /** 이메일 */
	  private String email="";
	  /** 주소 */
	  private String saddress="";
	  /** 날짜 */
	  private String rdate="";
	  private String word = "";
	  
	    // 파일 업로드 관련
	    // -----------------------------------------------------------------------------------
	    /**
	    이미지 파일
	    <input type='file' class="form-control" name='file1MF' id='file1MF' 
	               value='' placeholder="파일 선택">
	    */
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
	  public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	@Override
	public String toString() {
		return "SupplierVO [supplierid=" + supplierid + ", adminno=" + adminno + ", sname=" + sname + ", contactinfo="
				+ contactinfo + ", email=" + email + ", saddress=" + saddress + ", rdate=" + rdate + ", word=" + word
				+ ", file1MF=" + file1MF + ", size1_label=" + size1_label + ", file1=" + file1 + ", file1saved="
				+ file1saved + ", thumb1=" + thumb1 + ", size1=" + size1 + ", start_num=" + start_num + ", end_num="
				+ end_num + ", now_page=" + now_page + "]";
	}
	
	

	
	  
	  
}
