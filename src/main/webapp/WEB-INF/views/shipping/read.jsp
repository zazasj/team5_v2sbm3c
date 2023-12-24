<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dev.mvc.shipping.ShippingVO" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9092/shipping/read.do?shippingno=1</title>
<link rel="shortcut icon" href="/images/shortcut.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
  
</head>
<body>
<jsp:include page="../menu/top.jsp" flush='false' />
<div class='title_line'>카테고리 조회</div>
  <%
    ShippingVO shippingVO = (ShippingVO)request.getAttribute("shippingVO");
  %>
  <div class="container mt-3">
    <ul class="list-group list-group-flush">
      <li class="list-group-item">배송 ID: <%=shippingVO.getShippingID() %></li>
      <li class="list-group-item">주문 ID: <%=shippingVO.getOrder_payno() %></li>
      <li class="list-group-item">배송 타입: <%=shippingVO.getShippingType() %></li>
      <li class="list-group-item">배송 번호: <%=shippingVO.getTrackingNumber() %></li>
      <li class="list-group-item">배송 상태: <%=shippingVO.getDeliveryStatus() %></li>
      <li class="list-group-item">배송비: <%=shippingVO.getDeliveryPrice() %></li>
      <li class="list-group-item">배송 예정일: <%=shippingVO.getEstimatedDeliveryDate() %></li>
      <li class="list-group-item">배송 예정일: <%=shippingVO.getFile1MF() %></li>
      <li class="list-group-item">배송 예정일: <%=shippingVO.getSize1_label() %></li>
      <li class="list-group-item">배송 예정일: <%=shippingVO.getSize1() %></li>
      <li class="list-group-item">배송 예정일: <%=shippingVO.getThumb1() %></li>
      <li class="list-group-item">배송 예정일: <%=shippingVO.getFile1saved() %></li>
      <li class="list-group-item">배송 예정일: <%=shippingVO.getFile1() %></li>
      <li class="list-group-item">배송 예정일: <%=shippingVO.getWord() %></li>
    </ul>
  </div>

  <div class="content_body_bottom">
    <button type="button" onclick="location.href='./create.do'" class="btn btn-secondary btn-sm">등록</button>
    <button type="button" onclick="location.href='./list_all.do'" class="btn btn-secondary btn-sm">목록</button> 
  </div>

<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>

