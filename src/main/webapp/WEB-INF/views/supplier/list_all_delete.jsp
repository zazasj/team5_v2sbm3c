<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9093/supplier/list_all_update.do</title>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>

</head>
<body>
<c:import url="/menu/top.do" />

<div class='title_line'>공급업체 목록</div>

<aside class="aside_right">
  <a href="./list_all_adminno.do">등록</a>
  <span class='menu_divide' >│</span>
  <a href="javascript:location.reload();">새로고침</a>
</aside>
<div class="menu_line"></div> 
		

<form name='frm' method='post' action='/supplier/delete.do'>
		  <input type="hidden" name="supplierid" value="${param.supplierid }">
		    <div class="msg_warning">
            업체  ${supplierVO.sname }(을/를) 삭제하시겠습니까?
          </div>
		 <div class="content_body_bottom" style="text-align: center;">
		  <button type="submit" id='submit' class='btn btn-warning btn-sm' style='height: 28px; margin-bottom: 5px;'>삭제</button>
      <button type="button" onclick="location.href='./list_all_adminno.do'" class="btn btn-primary btn-sm" style='height: 28px; margin-bottom: 5px;'>취소</button> 
		 </div>
		</form>		

<table class="table table-hover">
  <colgroup>
   <col style='width: 10%;'/>
   <col style='width: 15%;'/>
   <col style='width: 25%;'/>
   <col style='width: 20%;'/>
   <col style='width: 15%;'/>
   <col style='width: 15%;'/> 
  </colgroup>
  <thead>
    <tr><br>
     <th class="th_bs">공급업체 번호</th>
     <th class="th_bs">공급업체 이름</th>
     <th class="th_bs">연락처</th>
     <th class="th_bs">주소</th>
     <th class="th_bs">날짜</th>
     <th class="th_bs">기타</th>
      </tr>
  </thead>
  <tbody>
    <c:forEach var="supplierVO" items="${list }" varStatus="info">
      <c:set var="supplierid" value="${supplierVO.supplierid }"/>
		    <tr>
		      <td class="td_bs">${info.count }</td>
		      <td class="td_bs">${supplierVO.sname }</td>
		      <td class="td_bs">${supplierVO.contactinfo }</td>
		      <td class="td_bs">${supplierVO.saddress }
		      <td class="td_bs">${supplierVO.rdate }
		      <td class="td_bs">
		        <a href="./update.do?supplierid=${supplierid }"><img src="/category/images/update.png" class="icon"></a>
		        <a href="./delete.do?supplierid=${supplierid }"><img src="/category/images/delete.png" class="icon"></a>
		      </td>
		    </tr>
    </c:forEach>

  </tbody>
</table>
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>