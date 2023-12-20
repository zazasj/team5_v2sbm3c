<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9092/country/list_all.do</title>
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->


</head>
<body>
<c:import url="/menu/top.do" />

  <div class='title_line'>
    ${inventoryVO.inventoryStatus }
    <c:if test="${param.word.length() > 0 }">
      > 「${param.word }」 검색 ${search_count } 건
    </c:if> 
  </div>
  
  <aside class="aside_right">
    <%-- 관리자로 로그인해야 메뉴가 출력됨 --%>
    <c:if test="${sessionScope.admin_id != null }">
      <span class='menu_divide' >│</span>
    </c:if>
    <a href="javascript:location.reload();">새로고침</a>
  </aside>
  
  <div style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_by_inventoryStatus.do'>
      <input type='hidden' name='inventoryStatus' value='${param.inventoryStatus }'>  <%-- 게시판의 구분 --%>
      
      <c:choose>
        <c:when test="${param.word != '' }"> <%-- 검색하는 경우는 검색어를 출력 --%>
          <input type='text' name='word' id='word' value='${param.word }'>
        </c:when>
        <c:otherwise> <%-- 검색하지 않는 경우 --%>
          <input type='text' name='word' id='word' value=''>
        </c:otherwise>
      </c:choose>
      <button type='submit' class='btn btn-secondary btn-sm' style="padding: 2px 8px 3px 8px; margin: 0px 0px 2px 0px;">검색</button>
      <c:if test="${param.word.length() > 0 }"> <%-- 검색 상태하면 '검색 취소' 버튼을 출력 --%>
        <button type='button' class='btn btn-secondary btn-sm' style="padding: 2px 8px 3px 8px; margin: 0px 0px 2px 0px;"
                    onclick="location.href='./list_by_inventoryStatus.do?inventoryStatus=${param.inventoryStatus}&word='">검색 취소</button>  
      </c:if>    
    </form>
  </div>
    
  <div class="menu_line"></div> 
  
  <table class="table table-hover">
    <colgroup>
        <col style='width: 10%;'/>
        <col style='width: 10%;'/>
        <col style='width: 10%;'/>    
        <col style='width: 10%;'/>
        <col style='width: 10%;'/>
        <col style='width: 13%;'/>
        <col style='width: 10%;'/>
        <col style='width: 10%;'/>
        <col style='width: 10%;'/>
      </colgroup>
      <thead>
        <tr>
          <th class="th_bs">재고 ID</th>
          <th class="th_bs">재고 상태</th>
          <th class="th_bs">제품 ID</th>
          <th class="th_bs">제품 이름</th>
          <th class="th_bs">공급업체 ID</th>
          <th class="th_bs">공급업체 회사명</th>
          <th class="th_bs">제품 수량</th>
          <th class="th_bs">입/출고 날짜</th>
          <th class="th_bs">수정/삭제</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach var="inventoryVO" items="${list}" varStatus="info">
    <c:set var="inventoryID" value="${inventoryVO.inventoryID}" />
    <c:set var="inventoryStatus" value="${inventoryVO.inventoryStatus}" />
    <c:set var="productID" value="${inventoryVO.productID}" />
    <c:set var="supplierID" value="${inventoryVO.supplierID}" />
    <c:set var="addQuantity" value="${inventoryVO.addQuantity}" />
    <c:set var="lastUpdated" value="${inventoryVO.lastUpdated.substring(0, 10)}" />

    <tr>
        <td class="td_bs">${inventoryID}</td>
        <td class="td_bs">${inventoryStatus}</td>
        <td class="td_bs">${productID}</td>
        <!-- Loop through the list1 to find matching pName -->
        <c:forEach var="productsVO" items="${list1}" varStatus="info1">
            <c:if test="${productsVO.productID eq inventoryVO.productID}">
                <td class="td_bs">${productsVO.pName}</td>
            </c:if>
        </c:forEach>
        <td class="td_bs">${supplierID}</td>
        <!-- Loop through the list1 to find matching pName -->
        <c:forEach var="supplierVO" items="${list2}" varStatus="info1">
            <c:if test="${supplierVO.supplierid eq inventoryVO.supplierID}">
                <td class="td_bs">${supplierVO.sname}</td>
            </c:if>
        </c:forEach>
        <td class="td_bs">${addQuantity}</td>
        <td class="td_bs">${lastUpdated}</td>
        <td class="td_bs">
            <a href="./update.do?inventoryID=${inventoryID}" title="수정"><img src="/category/images/update.png" class="icon"></a>
            <a href="./delete.do?inventoryID=${inventoryID}" title="삭제"><img src="/category/images/delete.png" class="icon"></a>
        </td>
    </tr>
</c:forEach>
      </tbody>
      
  </table>
  
  <!-- 페이지 목록 출력 부분 시작 -->
  <DIV class='bottom_menu'>${paging }</DIV> <%-- 페이지 리스트 --%>
  <!-- 페이지 목록 출력 부분 종료 -->
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>
