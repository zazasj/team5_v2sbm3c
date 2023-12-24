<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9092/shipping/list_all.do</title>
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
  
</head>
<body>
<c:import url="/menu/top.do" />

  <div class='title_line'>배송 목록(갤러리형)
    <c:if test="${param.word.length() > 0 }">
      > 「${param.word }」 검색 ${search_count } 건
    </c:if> 
  </div>
  
  <aside class="aside_right">
    <a href="javascript:location.reload();">새로고침</a>
    <span class='menu_divide' >│</span>    
    <a href="./list_by_shippingID.do">목록형</a>    
    <span class='menu_divide' >│</span>
    <a href="./list_by_shippingID_grid.do?now_page=${param.now_page == null ? 1 : param.now_page }&word=${param.word }">갤러리형</a>    
  </aside>
  
  <div style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_by_shippingID.do'>
      
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
                    onclick="location.href='./list_by_shippingID.do">검색 취소</button>  
      </c:if>    
    </form>
  </div>
    
  <div class="menu_line"></div> 
  
  <div style='width: 100%;'> <%-- 갤러리 Layout 시작 --%>
    <c:forEach var="shippingVO" items="${list }" varStatus="status">
       <c:set var="thumb1" value="${shippingVO.thumb1 }" />
       <c:set var="shippingID" value="${shippingVO.shippingID }" />
       <c:set var="deliveryStatus" value="${shippingVO.deliveryStatus }" />
       <c:set var="shippingType" value="${shippingVO.shippingType }" />
       <c:set var="order_payno" value="${shippingVO.order_payno }" />
       <c:set var="trackingNumber" value="${shippingVO.trackingNumber }" />
       <c:set var="estimatedDeliveryDate" value="${shippingVO.estimatedDeliveryDate.substring(0, 10) }" />
       <c:set var="size1" value="${shippingVO.size1 }" />
        
      <!-- 4기준 하나의 이미지, 24 * 4 = 96% -->
      <!-- 5기준 하나의 이미지, 19.2 * 5 = 96% -->
      <div onclick="location.href='./read.do?shippingID=${shippingID}&word=${param.word }&now_page=${param.now_page == null ? 1 : param.now_page }&tripno=${param.tripno }'" 
             style='width: 19%; height: 200px; float: left; margin: 0.5%; padding: 0.5%; background-color: #EEEFFF; text-align: left; cursor: pointer;'>
        
        <c:choose> 
          <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}"> <%-- 이미지인지 검사 --%>
            <%-- registry.addResourceHandler("/shipping/storage/**").addResourceLocations("file:///" +  shipping.getUploadDir()); --%>
            <img src="/shipping/storage/${thumb1 }" style="width: 100%; height: 120px;">
          </c:when>
          <c:otherwise> <!-- 이미지가 없는 경우 기본 이미지 출력: /static/shipping/images/none1.png -->
            <IMG src="/shipping/images/none1.png" style="width: 100%; height: 120px;">
          </c:otherwise>
        </c:choose>
        배송 ID: ${shippingID }<br>
        배송 상태: ${deliveryStatus }<br>
        송장 번호: ${trackingNumber }
        
        
      </div>
      
      <%-- 하나의 행에 이미지를 5개씩 출력후 행 변경, index는 0부터 시작 --%>
      <c:if test="${status.count % 5 == 0}"> 
        <HR class='menu_line'> <%-- 줄바꿈 --%>
      </c:if>
      
    </c:forEach>
  </div>
  
  <!-- 페이지 목록 출력 부분 시작 -->
  <DIV class='bottom_menu'>${paging }</DIV> <%-- 페이지 리스트 --%>
  <!-- 페이지 목록 출력 부분 종료 -->
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>
