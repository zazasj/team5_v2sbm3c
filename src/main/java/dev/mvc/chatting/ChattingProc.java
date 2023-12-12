package dev.mvc.chatting;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.chatting.ChattingVO;

@Component("dev.mvc.chatting.ChattingProc")
public class ChattingProc implements ChattingProcInter {

  @Autowired
  private ChattingDAOInter chattingDAO;
  
  @Override
  public int create(ChattingVO chattingVO) {
    int cnt = this.chattingDAO.create(chattingVO);
    return cnt;
  }
  
  @Override
  public ArrayList<ChattingVO> list_all() {
    ArrayList<ChattingVO> list = this.chattingDAO.list_all();
    return list;
  }
  
  @Override
  public ChattingVO read(int chattingno) {
	ChattingVO chattingVO = this.chattingDAO.read(chattingno);
    return chattingVO;
  }

  @Override
  public int delete(int chattingno) {
    int cnt = this.chattingDAO.delete(chattingno);
    return cnt;
  }
}