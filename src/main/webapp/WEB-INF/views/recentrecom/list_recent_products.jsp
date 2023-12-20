<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="dev.mvc.recentrecom.RecentRecomVO" %>

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

  <h2>회원님께 추천하는 가장 최근 상품들</h2>

  <div style="display: flex;">
  <%-- 여기서 recentProducts는 Controller에서 전달한 최근 제품 리스트입니다. --%>
  <c:forEach var="product" items="${recentProducts}">
    <div style="margin: 30px auto; text-align: center;">
      <%-- <img src="/products/storage/${product.imagefile}" style='width: 50%; float: left; margin-top: 0.5%; margin-right: 1%;'>--%>
      <img src="/products/storage/${product.imagefile}" alt="${product.pname}" width="100" height="100">
      <p>${product.pname}</p>
    </div>
  </c:forEach>
</div>

</body>
</html>