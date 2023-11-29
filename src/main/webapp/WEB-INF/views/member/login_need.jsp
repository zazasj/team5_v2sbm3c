<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=5.0, width=device-width" /> 
<title>NO KID ZONE</title>
  
<link rel="shortcut icon" href="/images/whisky.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

<script type="text/javascript">

</script>
 
</head> 
 
<body>
<c:import url="/menu/top.do" />
 
  <DIV class='message'>
    <H3>회원 로그인이 필요한 페이지입니다.</H3>
    <BR><BR>
    <button type='button' onclick="location.href='/member/login.do'" class="btn btn-primary btn-sm">로그인</button>       
    <button type='button' onclick="location.href='/member/create.do'" class="btn btn-primary btn-sm">회원 가입</button>       
  </DIV>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>

