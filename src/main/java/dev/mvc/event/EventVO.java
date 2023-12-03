package dev.mvc.event;

import org.springframework.web.multipart.MultipartFile;

public class EventVO {
	
	/*
	 * eventno NUMERIC(10) PRIMARY KEY, title VARCHAR(50), contents VARCHAR(2000),
	 * file1 VARCHAR2(100), file1saved VARCHAR2(100), thumb1 VARCHAR2(100), size1
	 * VARCHAR(1000), rdate DATE, Adminno NUMERIC(5),
	 */
	
	private int eventno;
	private String title = "";
	private String contents = "";
	private MultipartFile file1MF;
    private String size1_label = "";
    private String file1 = "";
    private String file1saved = "";  
    private String thumb1 = "";
    private long size1;
    private String rdate = "";
    private int adminno; 
    
	public int getEventno() {
		return eventno;
	}
	public void setEventno(int eventno) {
		this.eventno = eventno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
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
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	public int getAdminno() {
		return adminno;
	}
	public void setAdminno(int adminno) {
		this.adminno = adminno;
	}
	

}
