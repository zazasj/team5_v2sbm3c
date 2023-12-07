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
<title>NO KID ZONE</title>
<link rel="shortcut icon" href="/images/whisky.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
  
</head>
<body>

<h2>회원님께 추천하는 가장 최근 상품들</h2>

<div style="display: flex;">

  <%-- 여기서 recentProducts는 Controller에서 전달한 최근 제품 리스트입니다. --%>
  <c:forEach var="product" items="${recentProducts}">
    <div style="margin-right: 20px;">
      <img src="<c:url value='${product.imageFile}'/>" alt="${product.pname}" width="100" height="100">
      <p>${product.pname}</p>
    </div>
  </c:forEach>

</div>

</body>
</html>