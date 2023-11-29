package dev.mvc.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginDAOInter loginDAO; // ���÷� ����� DAO �������̽�

    public void createLoginRecord(int memberNo, String ip) {
        // LoginVO ����
        LoginVO loginVO = new LoginVO();
        loginVO.setMemberno(memberNo);
        loginVO.setIp(ip);

        // DAO�� ���� �α��� ��� ����
        loginDAO.create(loginVO);
    }
}

