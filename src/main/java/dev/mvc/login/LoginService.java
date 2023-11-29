package dev.mvc.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginDAOInter loginDAO; // 예시로 사용한 DAO 인터페이스

    public void createLoginRecord(int memberNo, String ip) {
        // LoginVO 생성
        LoginVO loginVO = new LoginVO();
        loginVO.setMemberno(memberNo);
        loginVO.setIp(ip);

        // DAO를 통해 로그인 기록 생성
        loginDAO.create(loginVO);
    }
}

