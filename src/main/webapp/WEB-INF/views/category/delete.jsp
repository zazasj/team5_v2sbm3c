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

<%
CategoryVO categoryVO = (CategoryVO)request.getAttribute("categoryVO");
int categoryID = categoryVO.getCategoryID();
String name = categoryVO.getName();
%>

<div class='title_line'>카테고리 > [<%=name%>] 삭제</div>

<div id='panel_delete' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
    <form name='frm_delete' id='frm_delete' method='POST' action='./delete.do'>
      <input type="hidden" name="categoryID" value="<%=categoryID %>">
      
      <div class="msg_warning">카테고리를 삭제하면 복구 할 수 없습니다.</div>
      <label>카테고리 이름</label>: <%=name %>
  
      <button type="submit" id='submit' class='btn btn-warning btn-sm' style='height: 28px; margin-bottom: 5px;'>삭제</button>
      <button type="button" onclick="location.href='/category/list_all.do'" class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>취소</button>
    </form>
  </div>

<jsp:include page="../menu/bottom.jsp" flush='false' />
 
</body>
</html>