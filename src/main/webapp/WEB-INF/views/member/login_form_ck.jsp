<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>NO KID ZONE</title>
 
<link rel="shortcut icon" href="/images/whisky.png" /><%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
 
<script type="text/javascript">
  window.onload = ()=> {
    document.getElementById('btn_create').addEventListener('click', () => {
      location.href="./create.do";
    });

    document.getElementById('btn_loadDefault').addEventListener('click', () => {
      document.getElementById('id').value = 'user1@naver.com';
      document.getElementById('passwd').value = '1234';
    });
  }
    
</script> 

</head> 
 
<body>
<c:import url="/menu/top.do" />
 
  <DIV class='title_line'>로그인</DIV>

  <DIV class='content_body'> 
    <DIV style='width: 40%; margin: 0px auto;'>
      <FORM name='frm' method='POST' action='./login.do'>
      
        <div class="form_input">
          <input type='text' class="form-control" name='id' id='id' 
                    value="${ck_id }" required="required" 
                    style='width: 80%;' placeholder="아이디" autofocus="autofocus">
          <Label>   
            <input type='checkbox' name='id_save' value='Y' ${ck_id_save == 'Y' ? "checked='checked'" : "" }> 저장
          </Label>    
        </div>   
     
        <div class="form_input">
          <input type='password' class="form-control" name='passwd' id='passwd' 
                    value='${ck_passwd }' required="required" style='width: 80%;' placeholder="패스워드">
          <Label>
            <input type='checkbox' name='passwd_save' value='Y' ${ck_passwd_save == 'Y' ? "checked='checked'" : "" }> 저장
          </Label>                    
        </div>   
     
        <div class="bottom_menu">
          <button type="submit" class="btn btn-secondary btn-sm">로그인</button>
          <button type='button' id='btn_create' class="btn btn-secondary btn-sm">회원가입</button>
          <button type='button' id='btn_loadDefault' class="btn btn-secondary btn-sm">테스트 계정</button>
        </div>   
        
      </FORM>
    </DIV>
  </DIV> <%-- <DIV class='content_body'> END --%>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>