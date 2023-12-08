<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="dev.mvc.orders.OrdersVO" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>주문정보</title>
<link rel="shortcut icon" href="/images/whisky.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

</head>
<body>
<c:import url="/menu/top.do" />

  <h2>주문 정보</h2>
  <hr>

  <div>
    <p>주문 번호 : ${orders.OrderID}</p>
    <p>주문 날짜 : ${orders.OrderDate}</p>
    <p>주문하신 회원님 : ${orders.Mname}</p>
    <p>현재 주문하신 상품의 가격 : ${orders.Price}</p>
  </div>

<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>