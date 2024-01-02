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
  <style>
    .product-container {
      display: flex;
      justify-content: space-around;
      flex-wrap: wrap;
    }

    .product-item {
      margin: 10px;
      text-align: center;
      width: 15%; /* Adjust the width as needed */
      box-sizing: border-box;
      border: 1px solid #ddd; /* Add a border for separation */
      padding: 10px;
    }

    .product-image {
      width: 80%;
      height: auto;
    }
  </style>
</head>
<body>

  <h2>회원님께 추천하는 가장 최근 상품들</h2>

  <div class="product-container">
    <%-- 여기서 recentProducts는 Controller에서 전달한 최근 제품 리스트입니다. --%>
    <c:forEach var="product" items="${recentProducts}">
      <div class="product-item">
        <img src="/products/storage/${product.imagefile}" alt="${product.pname}" class="product-image">
        <p>${product.pname}</p>
      </div>
    </c:forEach>
  </div>

</body>
</html>