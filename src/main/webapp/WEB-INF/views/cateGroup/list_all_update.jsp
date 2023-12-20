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

<div class='title_line'>카테고리 수정</div>

<aside class="aside_right">
  <a href="./create.do?GrpID=${cateGroupVO.grpID }">등록</a>
  <span class='menu_divide' >│</span>
  <a href="javascript:location.reload();">새로고침</a>
</aside>
<div class="menu_line"></div> 

<form name='frm' method='post' action='/cateGroup/update.do'>
  <input type='hidden' name='GrpID' value='${cateGroupVO.grpID }'>
  <div style="text-align: center;">
    <label>카테고리그룹 이름</label>
    <input type="text" name="gname" value="${cateGroupVO.gname }" required="required" autofocus="autofocus" 
               class="" style="width: 30%">

    <button type="submit" class="btn btn-secondary btn-sm">저장</button>
    <button type="button" onclick="history.back();" class="btn btn-secondary btn-sm">취소</button> 
  </div>
</form>

<table class="table table-hover">
  <colgroup>
      <col style='width: 10%;'/>
      <col style='width: 40%;'/>
      <col style='width: 20%;'/>
      <col style='width: 20%;'/>
    </colgroup>
    <thead>
      <tr>
        <th class="th_bs">순서</th>
        <th class="th_bs">카테고리그룹 이름</th>
        <th class="th_bs">등록일</th>
        <th class="th_bs">기타</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="cateGroupVO" items="${list }" varStatus="info">
        <c:set var="GrpID" value="${cateGroupVO.grpID }" />
  
        <tr>
          <td class="td_bs">${info.count }</td>
          <td><a href="./read.do?GrpID=${cateGroupVO.grpID }" style="display: block;">${cateGroupVO.gname }</a></td>
          <td class="td_bs">${cateGroupVO.rdate.substring(0, 10) }</td>
          <td class="td_bs">
            <img src="/cateGroup/images/show.png" class="icon">
            <a href="./update_seqno_forward.do?GrpID=${cateGroupVO.grpID }" title="우선 순위 높임"><img src="/cateGroup/images/decrease.png" class="icon"></a>
            <a href="./update_seqno_backward.do?GrpID=${cateGroupVO.grpID }" title="우선 순위 낮춤"><img src="/cateGroup/images/increase.png" class="icon"></a>
            <a href="./update.do?GrpID=${cateGroupVO.grpID }" title="수정"><img src="/cateGroup/images/update.png" class="icon"></a>
            <a href="./delete.do?GrpID=${cateGroupVO.grpID }" title="삭제"><img src="/cateGroup/images/delete.png" class="icon"></a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
    
</table>
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>
