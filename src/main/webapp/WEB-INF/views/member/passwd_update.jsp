<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=5.0, width=device-width" /> 
<title>술기운</title>
<link rel="shortcut icon" href="/images/sulic-resize36.png" />
<link href="/css/style.css" rel="Stylesheet" type="text/css">
<c:import url="/menu/top.do" />
</head> 
 <script>
document.addEventListener("DOMContentLoaded", function() {
  const newPasswd = document.getElementById('new_passwd');
  const newPasswd2 = document.getElementById('new_passwd2');

  function checkPasswordMatch() {
    if (newPasswd.value !== newPasswd2.value) {
      document.getElementById('new_passwd2_msg').innerText = '비밀번호가 일치하지 않습니다';
    } else {
      document.getElementById('new_passwd2_msg').innerText = '일치합니다';
    }
  }

  newPasswd.addEventListener('input', checkPasswordMatch);
  newPasswd2.addEventListener('input', checkPasswordMatch);
});
</script>
 
<body>

 
  <DIV class='title_line'>
    회원 패스워드 변경
  </DIV>

  <ASIDE class="aside_right">
    <A href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span> 
    <A href='./create.do'>회원 가입</A>
    <span class='menu_divide' >│</span> 
    <A href='./list.do'>목록</A>
  </ASIDE> 
  
  <div class='menu_line'></div>
  
  <div style='width: 60%; margin: 0px auto;'>  
  <form name='frm' id='frm' method='POST' action='./passwd_update.do'>

    <div class="form-floating mb-1 mt-3" style="width: 50%; margin:0px auto;">
      <input type="password" class="form-control" id="current_passwd" name="current_passwd" placeholder="현재 패스워드" autofocus="autofocus">
      <label for="current_passwd">현재 패스워드</label>
    </div>
    <div id='current_passwd_msg' style="width: 100%; text-align: center;"></div>

    <div class="form-floating mb-1 mt-3" style="width: 50%; margin:0px auto;">
      <input type="password" class="form-control" id="new_passwd" name="new_passwd" placeholder="새로운 패스워드">
      <label for="new_passwd">새로운 패스워드</label>
    </div>

    <div class="form-floating mb-1 mt-3" style="width: 50%; margin:0px auto;">
      <input type="password" class="form-control" id="new_passwd2" name="new_passwd2" placeholder="새로운 패스워드 확인">
      <label for="new_passwd2">새로운 패스워드 확인</label>
    </div>
    <div id='new_passwd2_msg' style="width: 100%; text-align: center;"></div>
  
    <div class="bottom_menu">
      <button type="submit" id='btn_send' class="btn btn-primary btn-sm">변경</button>
      <button type="button" onclick="history.back()" class="btn btn-primary btn-sm">취소</button>
    </div>   

  </form>
  </div>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>
 