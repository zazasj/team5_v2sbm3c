package dev.mvc.chatting;

public class ChattingVO {
	private int chattingno;
	private int memberno;
	private String msg="";
	private String rdate="";
	  
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
	  
	@Override
	public String toString() {
		return "Chatting [chattingno=" + chattingno + ", memberno=" + memberno + ", msg=" + msg + ", rdate=" + rdate
				+ "]";
	}
	   
}
