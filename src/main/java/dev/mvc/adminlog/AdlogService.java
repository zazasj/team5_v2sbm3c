package dev.mvc.adminlog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdlogService {

    @Autowired
    private AdlogDAOInter adlogDAO; // 

    public void createLog(String tablename, int recordid, String acttype, int adminno) {
        // LoginVO ����
        AdlogVO adlogVO = new AdlogVO();
        adlogVO.setTablename(tablename);
        adlogVO.setRecordid(recordid);
        adlogVO.setActtype(acttype);
        adlogVO.setAdminno(adminno);
        adlogDAO.create(adlogVO);
    }
}

