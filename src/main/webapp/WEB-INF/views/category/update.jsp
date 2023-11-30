<%@page import="dev.mvc.category.CategoryVO"%>
<%@ page contentType="text/html; charset=UTF-8" %>

 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>NO KID ZONE</title>
<link rel="shortcut icon" href="/images/whisky.png" />
<link href="/css/style.css" rel="Stylesheet" type="text/css">
</head>
<body>

<jsp:include page="../menu/top.jsp" flush='false' />

<div class='title_line'>카테고리 수정</div>

<%
CategoryVO categoryVO = (CategoryVO)request.getAttribute("categoryVO");
int CategoryID = categoryVO.getCategoryID();
%>

<form name='fm' method='post' action='/categoryID/update.do'>
  <input type="hidden" name="categoryID" value="<%=CategoryID%>">
  <div>
    <label>카테고리 이름</label>
    <input type="text" name="name" value="<%=categoryVO.getCategoryName() %>" required="required" autofocus="autofocus" class="form-control" style="width: 50%">
  </div>
  
  <div style="margin-top: 20px">
    <label>글 수</label>
    <input type="text" name="cnt" value="<%=categoryVO.getCnt() %>" required="required" autofocus="autofocus" class="form-control" style="width: 50%">
  </div>
  
  <div class="content_body_bottom">
    <button type="submit" class="btn btn-secondary btn-sm">저장</button>
    <button type="button" onclick="history.back();" class="btn btn-secondary btn-sm">취소</button>
  </div>
</form>

<jsp:include page="../menu/bottom.jsp" flush='false' />
 
</body>
</html>