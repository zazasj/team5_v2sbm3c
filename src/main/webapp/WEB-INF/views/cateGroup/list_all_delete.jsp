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

<div class='title_line'>카테고리 삭제</div>

<aside class="aside_right">
  <a href="./create.do?GrpID=${cateGroupVO.grpID }">등록</a>
  <span class='menu_divide' >│</span>
  <a href="javascript:location.reload();">새로고침</a>
</aside>
<div class="menu_line"></div> 

<div style="text-align: center;">
  <form name='frm_delete' id='frm_delete' method='post' action='./delete.do'>
    <input type="hidden" name="GrpID" value="${cateGroupVO.grpID }">
    
    <div class="msg_warning">카테고리그룹을 삭제하면 복구 할 수 없습니다.</div>
    <label>카테고리그룹 이름</label>: ${cateGroupVO.gname }
  
    <button type="submit" id='submit' class='btn btn-warning btn-sm' style='height: 28px; margin-bottom: 5px;'>삭제</button>
    <button type="button" onclick="location.href='/cateGroup/list_all.do'" class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>취소</button>
  </form>
</div>

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
