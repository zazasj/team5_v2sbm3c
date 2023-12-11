<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>NO KID ZONE</title>

<link rel="shortcut icon" href="/images/whisky.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
 
</head> 
<body>
<c:import url="/menu/top.do" />
 
  <DIV class='title_line'>
    회원 삭제(관리자 전용)
  </DIV>

  <ASIDE class="aside_right">
    <A href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span> 
    <A href='./create.do'>회원 가입</A>
    <span class='menu_divide' >│</span> 
    <A href='./list.do'>목록</A>
  </ASIDE> 
 
  <div class='menu_line'></div>
 
 
  <div class='message'>
    <form name='frm' method='post' action='./delete.do'>
      '${memberVO.mname }(${memberVO.id })' 회원을 삭제하면 복구 할 수 없습니다.<br><br>
      정말로 삭제하시겠습니까?<br><br>         
      <input type='hidden' name='memberno' value='${memberVO.memberno}'>     
          
      <button type="submit" class="btn btn-primary btn-sm">삭제</button>
      <button type="button"  onclick="history.back();" class="btn btn-primary btn-sm">취소(목록)</button>
   
    </form>
  </div>

<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>

