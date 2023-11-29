<%@page import="dev.mvc.cateGroup.CateGroupVO"%>
<%@ page contentType="text/html; charset=UTF-8" %>

 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9093/cateGroup/update.do?GrpID=2</title>
<link rel="shortcut icon" href="/images/shortcut.png" />
<link href="/css/style.css" rel="Stylesheet" type="text/css">

</head>
<body>

<jsp:include page="../menu/top.jsp" flush='false' />

<div class='title_line'>카테고리그룹 수정</div>

<%
CateGroupVO cateGroupVO = (CateGroupVO)request.getAttribute("cateGroupVO");
int GrpID = cateGroupVO.getGrpID();
%>

<form name='fm' method='post' action='/cateGroup/update.do'>
  <input type="hidden" name="Grp" value="<%=GrpID%>">
  <div>
    <label>카테고리그룹 이름</label>
    <input type="text" name="gname" value="<%=cateGroupVO.getGname() %>" required="required" autofocus="autofocus" class="form-control" style="width: 50%">
  </div>
  
  <div class="content_body_bottom">
    <button type="submit" class="btn btn-secondary btn-sm">저장</button>
    <button type="button" onclick="history.back();" class="btn btn-secondary btn-sm">취소</button>
  </div>
</form>

<jsp:include page="../menu/bottom.jsp" flush='false' />
 
</body>
</html>