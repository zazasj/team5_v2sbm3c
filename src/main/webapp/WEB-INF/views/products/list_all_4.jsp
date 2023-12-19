<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9093/products/list_all_4.do</title>
<link rel="shortcut icon" href="/images/whisky.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

</head>
<body>
<c:import url="/menu/top.do" />

  <div class='title_line'>리큐르 상품 목록</div>
  
  <aside class="aside_left">
  <c:forEach var="categoryVO" items="${list_4 }">
    <c:set var="categoryGrpID" value="${categoryVO.grpID}" />
    <c:set var="categoryName" value="${categoryVO.categoryName}" />
    <c:if test="${categoryGrpID eq 4}">
      <a href="/products/list_by_categoryID.do?categoryID=${categoryVO.categoryID}&now_page=1">${categoryName} | </a>
    </c:if>
  </c:forEach>
</aside>
  
  <aside class="aside_right">
    <a href="javascript:location.reload();">새로고침</a>
  </aside>
  <div class="menu_line"></div> 
  
  <table class="table table-hover">
    <colgroup>
      <col style="width: 10%;"></col>
      <col style="width: 80%;"></col>
      <col style="width: 10%;"></col>
    </colgroup>
    <thead>
      <tr>
        <th style='text-align: center;'>파일</th>
        <th style='text-align: center;'>제목</th>
        <th style='text-align: center;'>기타</th>
      </tr>
    </thead>
    <tbody>
        <c:forEach var="productsVO" items="${list }" varStatus="info">
          <c:set var="productID" value="${productsVO.productID }" />
          <c:set var="thumb" value="${productsVO.thumb }" />
    
          <tr onclick="location.href='./read.do?productID=${productID}'" style="cursor: pointer;">
            <td>
              <c:choose>
                <c:when test="${thumb.endsWith('jpg') || thumb.endsWith('png') || thumb.endsWith('gif')}"> <%-- 이미지인지 검사 --%>
                  <%-- registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Contents.getUploadDir()); --%>
                  <img src="/products/storage/${thumb }" style="width: 120px; height: 90px;">
                </c:when>
                <c:otherwise> <!-- 이미지가 없는 경우 기본 이미지 출력: /static/contents/images/none1.png -->
                  <img src="/prodcuts/images/none1.png" style="width: 120px; height: 90px;">
                </c:otherwise>
              </c:choose>
            </td>
            <td class="td_bs_left">
              <span style="font-weight: bold;">${productsVO.pName }</span><br>
              <c:choose>
                <c:when test="${productsVO.description.length() > 160 }">
                  ${productsVO.description.substring(0, 160) }...
                </c:when>
                <c:otherwise>
                  ${productsVO.description }
                </c:otherwise>
              </c:choose>
            </td>
            <td class="td_bs">
              <a href="#" title="삭제"><img src="/products/images/delete.png" class="icon"></a>
            </td>
          </tr>
        </c:forEach>
    </tbody>
      
  </table>
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>
