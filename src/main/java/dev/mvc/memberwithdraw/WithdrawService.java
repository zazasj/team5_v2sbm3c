package dev.mvc.memberwithdraw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WithdrawService {
  
  @Autowired
  private WithdrawDAOInter withdrawDAO;
  
  public void createWithdrawRecord(int memberNo, String id, int grade) {
    
    WithdrawVO withdrawVO = new WithdrawVO();
    withdrawVO.setMemberno(memberNo);
    withdrawVO.setId(id);
    withdrawVO.setGrade(grade);

    // DAO�� ���� �α��� ��� ����
    withdrawDAO.create(withdrawVO);
}

}
