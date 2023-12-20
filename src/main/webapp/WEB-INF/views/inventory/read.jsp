<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dev.mvc.inventory.InventoryVO" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9092/inventory/read.do?inventoryno=1</title>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

</head>
<body>
<jsp:include page="../menu/top.jsp" flush='false' />
<div class='title_line'>카테고리 조회</div>
  <%
    InventoryVO inventoryVO = (InventoryVO)request.getAttribute("inventoryVO");
  %>
  <div class="container mt-3">
    <ul class="list-group list-group-flush">
      <li class="list-group-item">인벤토리 ID: <%=inventoryVO.getInventoryID() %></li>
      <li class="list-group-item">제품 ID: <%=inventoryVO.getProductID() %></li>
      <li class="list-group-item">인벤토리 상태: <%=inventoryVO.getInventoryStatus() %></li>
      <li class="list-group-item">공급업체 ID: <%=inventoryVO.getSupplierID() %></li>
      <li class="list-group-item">재고 수량: <%=inventoryVO.getAddQuantity() %></li>
      <li class="list-group-item">인벤토리 수량: <%=inventoryVO.getQuantity() %></li>
      <li class="list-group-item">등록일: <%=inventoryVO.getLastUpdated() %></li>
      <li class="list-group-item">글수 <%=inventoryVO.getWord() %></li>
      
    </ul>
  </div>

  <div class="content_body_bottom">
    <button type="button" onclick="location.href='./create.do'" class="btn btn-secondary btn-sm">등록</button>
    <button type="button" onclick="location.href='./list_all.do'" class="btn btn-secondary btn-sm">목록</button> 
  </div>

<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>

