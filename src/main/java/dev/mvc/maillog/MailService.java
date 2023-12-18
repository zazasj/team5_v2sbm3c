package dev.mvc.maillog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class MailService {
  
  @Autowired
  private MailDAOInter mailDAO;
  
  public void createMailRecord(int memberno, String id, String actname) {
    MailVO mailVO = new MailVO();
    mailVO.setMemberno(memberno);
    mailVO.setId(id);
    mailVO.setActname(actname);

    mailDAO.create(mailVO);
  }
}


