<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9093/category/list_all.do</title>
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
  
</head>
<body>
<c:import url="/menu/top.do" />

<div class='title_line'>카테고리 수정</div>

<aside class="aside_right">
  <a href="./create.do?categoryID=${categoryVO.categoryID }">등록</a>
  <span class='menu_divide' >│</span>
  <a href="javascript:location.reload();">새로고침</a>
</aside>
<div class="menu_line"></div> 

<form name='frm' method='post' action='/category/update.do'>
  <input type='hidden' name='categoryID' value='${categoryVO.categoryID }'>
  <div style="text-align: center;">
    <label>카테고리 이름</label>
    <input type="text" name="name" value="${categoryVO.name }" required="required" autofocus="autofocus" 
               class="" style="width: 30%">

    <label>글수</label>
    <input type="text" name="cnt" value="${categoryVO.cnt }" required="required" autofocus="autofocus" 
               class="" style="width: 20%">
    <button type="submit" class="btn btn-secondary btn-sm">저장</button>
    <button type="button" onclick="history.back();" class="btn btn-secondary btn-sm">취소</button> 
  </div>
</form>

<table class="table table-hover">
  <colgroup>
      <col style='width: 10%;'/>
      <col style='width: 40%;'/>
      <col style='width: 10%;'/>    
      <col style='width: 20%;'/>
      <col style='width: 20%;'/>
    </colgroup>
    <thead>
      <tr>
        <th class="th_bs">순서</th>
        <th class="th_bs">카테고리 이름</th>
        <th class="th_bs">자료수</th>
        <th class="th_bs">등록일</th>
        <th class="th_bs">기타</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="categoryVO" items="${list }" varStatus="info">
        <c:set var="categoryID" value="${categoryVO.categoryID }" />
  
        <tr>
          <td class="td_bs">${info.count }</td>
          <td><a href="./read.do?categoryID=${categoryID }" style="display: block;">${categoryVO.name }</a></td>
          <td class="td_bs">${categoryVO.cnt }</td>
          <td class="td_bs">${categoryVO.rdate.substring(0, 10) }</td>
          <td class="td_bs">
            <img src="/category/images/show.png" class="icon">
            <a href="./update_seqno_forward.do?categoryID=${categoryID }" title="우선 순위 높임"><img src="/category/images/decrease.png" class="icon"></a>
            <a href="./update_seqno_backward.do?categoryID=${categoryID }" title="우선 순위 낮춤"><img src="/category/images/increase.png" class="icon"></a>
            <a href="./update.do?categoryID=${categoryID }" title="수정"><img src="/category/images/update.png" class="icon"></a>
            <a href="./delete.do?categoryID=${categoryID }" title="삭제"><img src="/category/images/delete.png" class="icon"></a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
    
</table>
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>
