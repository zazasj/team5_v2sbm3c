<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.List" %>

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
<c:import url="/menu/top.do" />
    <div>
        <h2>[추천시스템] 추천 제품 목록</h2>
        <c:forEach var="product" items="${products}" varStatus="status">
            <c:if test="${status.index % 5 eq 0}">
                <div style="clear:both;"></div>
            </c:if>
            <div style="float:left; margin-right: 10px;">
                <p>${product.pname}</p>
                <img src="${product.imagefile}" alt="${product.pname}" />
            </div>
        </c:forEach>

        <!-- 페이징 처리 -->
        <div>
            <c:if test="${not empty products}">
                <ul class="pagination">
                    <!-- 현재 페이지 주변의 페이지 번호 표시 -->
                    <c:forEach var="i" begin="${currentPage - 5}" end="${currentPage + 5}">
                        <c:if test="${i > 0 and i <= totalPages}">
                            <li class="<c:if test='${currentPage eq i}'>active</c:if>">
                                <a href="?page=${i}&grpid=${grpid}">${i}</a>
                            </li>
                        </c:if>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
    </div>
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>