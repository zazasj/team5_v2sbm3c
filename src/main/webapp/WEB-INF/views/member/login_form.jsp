<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>술기운</title>
<link rel="shortcut icon" href="/images/sulic-resize36.png" />
<link href="/css/style.css" rel="Stylesheet" type="text/css">
 

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

  // jQuery
  // $(function() { // click 이벤트 핸들러 등록
  //   $('#btn_create').on('click', create); // 회원 가입
  //   $('#btn_loadDefault').on('click', loadDefault); // 기본 로그인 정보 설정
  // });

  // 회원 가입  
  // function create() {
  //   location.href="./create.do";
  // }

  // 테스트용 기본값 로딩
  // function loadDefault() {
  //   $('#id').val('user1@gmail.com');
  //   $('#passwd').val('1234');
  // }
    
</script> 

</head> 
 
<body>
<c:import url="/menu/top.do" />
 
  <DIV class='title_line'>로그인</DIV>

  <DIV class='content_body'> 
    <DIV style='width: 40%; margin: 0px auto;'>
      <form name='frm' method='POST' action='./login.do'>
      
        <div class="form-group">
          <label>아이디</label>    
          <input type='text' class="form-control" name='id' id='id' 
                    value='' required="required" 
                    style='width: 80%;' placeholder="아이디" autofocus="autofocus">
        </div>   
     
        <div class="form-group">
          <label>패스워드</label>    
          <input type='password' class="form-control" name='passwd' id='passwd' 
                    value='' required="required" style='width: 80%;' placeholder="패스워드">
        </div>   
     
        <div class="bottom_menu">
          <button type="submit" class="btn btn-secondary btn-sm">로그인</button>
          <button type='button' id='btn_create' class="btn btn-secondary btn-sm">회원가입</button>
          <button type='button' id='btn_loadDefault' class="btn btn-secondary btn-sm">테스트 계정</button>
        </div>   
        
      </form>
    </DIV>
  </DIV> <%-- <DIV class='content_body'> END --%>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>