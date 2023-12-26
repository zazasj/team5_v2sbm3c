package dev.mvc.chatting;

public class ChattingVO {
	private int chattingno;
	private int memberno;
	private String msg="";
	private String rdate="";
	private String word = "";
	
	// 페이징 관련
    // -----------------------------------------------------------------------------------
    /** 시작 rownum */
    private int start_num;    
    /** 종료 rownum */
    private int end_num;    
    /** 현재 페이지 */
    private int now_page = 1;
    
	  
	public int getMemberno() {
	  return memberno;
	}
	public void setMemberno(int memberno) {
	  this.memberno = memberno;
	}
	public int getChattingno() {
		return chattingno;
	}
	public void setChattingno(int chattingno) {
		this.chattingno = chattingno;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
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
		return "ChattingVO [chattingno=" + chattingno + ", memberno=" + memberno + ", msg=" + msg + ", rdate=" + rdate
				+ ", word=" + word + ", start_num=" + start_num + ", end_num=" + end_num + ", now_page=" + now_page
				+ "]";
	}
	
}
	
	
	   
