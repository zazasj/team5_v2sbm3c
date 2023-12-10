<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page language="java" %>

<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>NO KID ZONE</title>
 
<link rel="shortcut icon" href="/images/whisky.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

<script type="text/JavaScript" src="http://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>


</head> 
<body>
<c:import url="/menu/top.do" />

<div class='title_line'>알림</div>

<div class='message'>
  <fieldset class='fieldset_basic'>
    <ul>          
          <li class='li_none'>
            <span class="span_success"><%= session.getAttribute("mname") %>님의 아이디는 <%= session.getAttribute("fid") %> 입니다.</span>
          </li>
          <li class='li_none'>
            <button type='button' 
                         onclick="location.href='./login.do'"
                         class="btn btn-primary btn-sm">로그인하기</button>
            <button type='button' 
                         onclick="location.href='./findpwd.do'"
                         class="btn btn-primary btn-sm">비밀번호 찾기</button>                   
          </li>                                                                                              
    </ul>
  </fieldset>

</div>

<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>

</html>

