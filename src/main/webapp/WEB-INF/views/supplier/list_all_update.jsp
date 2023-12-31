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

</head>
<body>
<c:import url="/menu/top.do" />

<div class='title_line'>공급업체 수정 목록 > ${supplierVO.sname } </div>

<aside class="aside_right">
  <a href="./create.do">등록</a>
  <span class='menu_divide' >│</span>
  <a href="javascript:location.reload();">새로고침</a>
</aside>
<div class="menu_line"></div> 
		

<form name='frm' method='post' action='/supplier/update.do'>
  <input type='hidden' name='supplierid' value='${supplierVO.supplierid }'>
  <div style="text-align: center;">
    <label>공급업체 이름</label>
    <input type="text" name="sname" value="${supplierVO.sname }" required="required" autofocus="autofocus" 
               class="" style="width: 20%">

    <label>연락처</label>
    <input type="text" name="contactinfo" value="${supplierVO.contactinfo }" required="required" autofocus="autofocus" 
               class="" style="width: 20%">
               
    <label>이메일</label>
    <input type="text" name="email" value="${supplierVO.email }" required="required" autofocus="autofocus" 
               class="" style="width: 20%">
    
    <label>주소</label>
    <input type="text" name="saddress" value="${supplierVO.saddress }" required="required" autofocus="autofocus" 
               class="" style="width: 20%"><br><br>
    <button type="submit" class="btn btn-secondary btn-sm">저장</button>
    <button type="button" onclick="history.back();" class="btn btn-secondary btn-sm">취소</button> 
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
     <th class="th_bs">공급업체 사진</th>
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
      <c:set var="thumb1" value="${supplierVO.thumb1 }" />
		    <tr> 
		      <td style='vertical-align: middle; text-align: center;'>
		        <div onclick="location.href='./read.do?supplierid=${supplierid}'"  
          		style=' float: left; margin: 0.5%; padding: 0.5%; background-color: #EEEFFF; text-align: center; cursor: pointer;'>
		   
    			<c:choose>
        			<c:when test="${supplierVO.thumb1.endsWith('jpg') || supplierVO.thumb1.endsWith('png') || supplierVO.thumb1.endsWith('gif')}">
            			<%-- /static/products/storage/ --%>
            			<img src="/supplier/storage/${supplierVO.thumb1}" style="width: 120px; height: 80px;"></a> 
        			</c:when>
        		<c:otherwise> <!-- 이미지가 아닌 일반 파일 -->
            		<img src="/event/images/none1.png" style="width: 100%; height: 500px;">
        		</c:otherwise>
    		  </c:choose>
    		  </div>
		      <td class="td_bs">${supplierVO.sname }</td>
		      <td class="td_bs">${supplierVO.contactinfo }</td>
		      <td class="td_bs">${supplierVO.email }</td>
		      <td class="td_bs">${supplierVO.saddress }</td>
		      <td class="td_bs">${supplierVO.rdate.substring(0,10) }
		      
		      <td class="td_bs">
		        <a href="./update.do?supplierid=${supplierid }"><img src="/category/images/update.png" class="icon"></a>
		        <a href="./delete.do?supplierid=${supplierid }"><img src="/category/images/delete.png" class="icon"></a>
		      </td>
		    </tr>
		    
    </c:forEach>

  </tbody>
</table>

<!-- 페이지 목록 출력 부분 시작 -->
  <DIV class='bottom_menu'>${paging }</DIV> <%-- 페이지 리스트 --%>
<!-- 페이지 목록 출력 부분 종료 -->
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>