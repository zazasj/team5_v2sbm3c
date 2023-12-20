<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>술기운</title>
<link rel="shortcut icon" href="/images/sulic-resize36.png" />
<link href="/css/style.css" rel="Stylesheet" type="text/css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
  
</head>
<body>
<c:import url="/menu/top.do" />

  <div class='title_line'>챗봇 목록</div>
  
  <div class="menu_line"></div> 
  
  <table class="table table-hover">
    <colgroup>
        <col style='width: 15%;'/>
        <col style='width: 65%;'/>
        <col style='width: 10%;'/>    
        <col style='width: 10%;'/>
      </colgroup>
      <thead>
        <tr>
          <th class="th_bs">챗봇 번호</th>
          <th class="th_bs">MSG</th>
          <th class="th_bs">등록일</th>
          <th class="th_bs">삭제</th>
        </tr>
      </thead>
      <tbody>
	      <c:forEach var="chattingVO" items="${list }" varStatus="info">
	      <c:set var="chattingno" value="${chattingVO.chattingno }"/>
	
	      <tr>
	          <td class="td_bs">${chattingno }</td>
	          <td><a href="./read.do?chattingno=${chattingno}" style="display:block;">${chattingVO.msg }</a></td>
	          <td class="td_bs">${chattingVO.rdate}</td>
	          <td class="td_bs">
	          <a href="./delete.do?chattingno=${chattingno}"><IMG src='/category/images/delete.png' title='삭제' class="icon"></a>
	        </td>
	      </tr>
	      
	    </c:forEach>
    </tbody>
      
      
  </table>
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>
