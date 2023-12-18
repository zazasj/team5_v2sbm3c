package dev.mvc.review;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.tool.Tool;

@Component("dev.mvc.review.ReviewProc")
public class ReviewProc implements ReviewProcInter{

  @Autowired
  private ReviewDAOInter reviewDAO;
  
  
  @Override
  public int create(ReviewVO reviewVO) {
    int cnt = this.reviewDAO.create(reviewVO);
    return cnt;
  }

  @Override
  public List<ReviewVO> list() {
    List<ReviewVO> list= this.reviewDAO.list();
    return list;
  }

  @Override
  public List<ReviewVO> list_by_productid(int productid) {
    List<ReviewVO> list = reviewDAO.list_by_productid(productid);
    String retitle = "";
    String recontent = "";
    
    // 특수 문자 변경
    for (ReviewVO reviewVO:list) {
      recontent = reviewVO.getRecontent();
      recontent = Tool.convertChar(recontent);
      reviewVO.setRecontent(recontent);
      retitle = reviewVO.getRetitle();
      retitle = Tool.convertChar(retitle);
      reviewVO.setRetitle(retitle);
    }
    return list;
  }
  
  @Override
  public List<ReviewVO> list_memberno(int memberno) {
    List<ReviewVO> list = reviewDAO.list_memberno(memberno);
    String retitle = "";
    String recontent = "";
    
    // 특수 문자 변경
    for (ReviewVO reviewVO:list) {
      recontent = reviewVO.getRecontent();
      recontent = Tool.convertChar(recontent);
      reviewVO.setRecontent(recontent);
      retitle = reviewVO.getRetitle();
      retitle = Tool.convertChar(retitle);
      reviewVO.setRetitle(retitle);
    }
    return list;
  }
  
  @Override
  public List<ReviewMemberVO> list_member_join(int memberno) {
    List<ReviewMemberVO> list = reviewDAO.list_member_join(memberno);
    String retitle = "";
    String recontent = "";
    
    // 특수 문자 변경
    for (ReviewMemberVO reviewMemberVO:list) {
      recontent = reviewMemberVO.getRecontent();
      recontent = Tool.convertChar(recontent);
      reviewMemberVO.setRecontent(recontent);

      retitle = reviewMemberVO.getRetitle();
      retitle = Tool.convertChar(retitle);
      reviewMemberVO.setRetitle(retitle);
    }
    return list;
  }
  
  @Override
  public List<ReviewMemberVO> list_by_productid_join(int productid) {
    List<ReviewMemberVO> list = reviewDAO.list_by_productid_join(productid);
    String retitle = "";
    String recontent = "";
    
    // 특수 문자 변경
    for (ReviewMemberVO reviewMemberVO:list) {
      recontent = reviewMemberVO.getRecontent();
      recontent = Tool.convertChar(recontent);
      reviewMemberVO.setRecontent(recontent);

      retitle = reviewMemberVO.getRetitle();
      retitle = Tool.convertChar(retitle);
      reviewMemberVO.setRetitle(retitle);
    }
    return list;
  }
  
  @Override
  public List<ReviewMemberVO> list_by_productid_join_add(int productid) {
    List<ReviewMemberVO> list = reviewDAO.list_by_productid_join_add(productid);
    String retitle = "";
    String recontent = "";
    
    // 특수 문자 변경
    for (ReviewMemberVO reviewMemberVO:list) {
      recontent = reviewMemberVO.getRecontent();
      recontent = Tool.convertChar(recontent);
      reviewMemberVO.setRecontent(recontent);

      retitle = reviewMemberVO.getRetitle();
      retitle = Tool.convertChar(retitle);
      reviewMemberVO.setRetitle(retitle);
    }
    return list;
  }
  @Override
  public int checkPasswd(Map<String, Object> map) {
    int count = reviewDAO.checkPasswd(map);
    return count;
  }
  
  @Override
  public int delete(int reviewid) {
    int cnt = this.reviewDAO.delete(reviewid);
    return cnt;
  }

}
