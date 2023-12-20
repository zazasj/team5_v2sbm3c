<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9092/inventory/list.do</title>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

</head>
<body>
<c:import url="/menu/top.do" />

  <div class='title_line'>재고현황</div>
  
  <aside class="aside_right">
    <span class='menu_divide' >│</span>
    <a href="javascript:location.reload();">새로고침</a>
  </aside>
  <div class="menu_line"></div> 
  
  <form name='frm' method='post' action='/inventory/updatequantity.do'>
    <div style="text-align: center;">
    <button type="submit" class="btn btn-secondary btn-sm" style="height: 28px; margin-bottom: 5px;">총 수량 최신화</button>
    </div>
    </form>
    
  <table class="table table-hover">
    <colgroup>
        <col style='width: 30%;'/>
        <col style='width: 30%;'/>    
        <col style='width: 30%;'/>
      </colgroup>
      <thead>
        <tr>
          <th class="th_bs">제품 ID</th>
          <th class="th_bs">제품 이름</th>
          <th class="th_bs">제품 수량</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="inventoryVO" items="${list }" varStatus="info">
          <c:set var="productID" value="${inventoryVO.productID }" />
          <c:set var="quantity" value="${inventoryVO.quantity }" />
          <tr>
            <td class="td_bs">${productID}</td>
            <c:forEach var="productsVO" items="${list1}" varStatus="info1">
            <c:if test="${productsVO.productID eq inventoryVO.productID}">
                <td class="td_bs">${productsVO.pName}</td>
            </c:if>
        </c:forEach>
            <td class="td_bs">${quantity }</td>
          </tr>
        </c:forEach>
      </tbody>
      
  </table>
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>
