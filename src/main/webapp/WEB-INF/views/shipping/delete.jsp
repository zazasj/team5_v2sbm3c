<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dev.mvc.shipping.ShippingVO" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9092/shipping/delete.do?shippingID=2</title>
<link rel="shortcut icon" href="/images/shortcut.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<%
ShippingVO shippingVO = (ShippingVO)request.getAttribute("shippingVO");
int shippingID = shippingVO.getShippingID();
%>
<body>
<jsp:include page="../menu/top.jsp" flush='false' />
<div class='title_line'>배송 ID > [<%=shippingID %>]삭제</div>
<div id='panel_delete' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
    <form name='frm_delete' id='frm_delete' method='post' action='./delete.do'>
      <input type="hidden" name="shippingID" value="<%=shippingID %>">
      
      <div class="msg_warning">카테고리를 삭제하면 복구 할 수 없습니다.</div>
      <label>배송 ID</label>: <%=shippingID %>
  
      <button type="submit" id='submit' class='btn btn-warning btn-sm' style='height: 28px; margin-bottom: 5px;'>삭제</button>
      <button type="button" onclick="location.href='/shipping/list_all.do'" class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>취소</button>
    </form>
  </div>

<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>

