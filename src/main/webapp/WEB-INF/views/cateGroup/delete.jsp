<%@page import="dev.mvc.cateGroup.CateGroupVO"%>
<%@ page contentType="text/html; charset=UTF-8" %>

 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9093/cateGroup/delete.do?GrpID=2</title>
<link rel="shortcut icon" href="/images/shortcut.png" />
<link href="/css/style.css" rel="Stylesheet" type="text/css">

</head>
<body>

<jsp:include page="../menu/top.jsp" flush='false' />

<%
CateGroupVO cateGroupVO = (CateGroupVO)request.getAttribute("cateGroupVO");
int GrpID = cateGroupVO.getGrpID();
String gname = cateGroupVO.getGname();
%>

<div class='title_line'>카테고리그룹 > [<%=gname%>] 삭제</div>

<div id='panel_delete' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
    <form name='frm_delete' id='frm_delete' method='POST' action='./delete.do'>
      <input type="hidden" name="GrpID" value="<%=GrpID %>">
      
      <div class="msg_warning">카테고리를 삭제하면 복구 할 수 없습니다.</div>
      <label>카테고리그룹 이름</label>: <%=gname %>
  
      <button type="submit" id='submit' class='btn btn-warning btn-sm' style='height: 28px; margin-bottom: 5px;'>삭제</button>
      <button type="button" onclick="location.href='/cateGroup/list_all.do'" class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>취소</button>
    </form>
  </div>

<jsp:include page="../menu/bottom.jsp" flush='false' />
 
</body>
</html>