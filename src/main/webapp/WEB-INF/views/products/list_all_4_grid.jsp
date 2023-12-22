<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>술기운</title>
<link rel="shortcut icon" href="/images/sulic-resize36.png" />
<link href="/css/style.css" rel="Stylesheet" type="text/css">
<c:import url="/menu/top.do" />

</head>

<body>

  <div class='title_line'>와인 상품 목록</div>
  
  <aside class="aside_left">
  <c:forEach var="categoryVO" items="${list_4 }">
    <c:set var="categoryGrpID" value="${categoryVO.grpID}" />
    <c:set var="categoryName" value="${categoryVO.categoryName}" />
    <c:if test="${categoryGrpID eq 4}">
      <a href="/products/list_by_categoryID_grid.do?categoryID=${categoryVO.categoryID}&now_page=1">${categoryName} | </a>
    </c:if>
  </c:forEach>
  </aside>
  
  <aside class="aside_right">
    <%-- 관리자로 로그인해야 메뉴가 출력됨 --%>
    <c:if test="${sessionScope.admin_id != null }">
      <a href="./create.do?categoryID=${categoryVO.categoryID }">등록</a>
      <span class='menu_divide' >│</span>
    </c:if>
    <a href="javascript:location.reload();">새로고침</a>
    <span class='menu_divide' >│</span>    
    <a href="./list_all_4.do?now_page=${param.now_page == null ? 1 : param.now_page}&word=${param.word }">목록형</a>  
  </aside>
  
 <DIV style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_all_4_grid.do'>
      <input type='text' name='word' id='word' value='${param.word }' style='width: 20%;'>
      <button class="press-button" type='submit'>검색</button>
      <c:if test="${param.word.length() > 0 }">
        <button class="press-button" type='button' 
                     onclick="location.href='./list_all_4_grid.do?&word='">검색 취소</button>  
      </c:if>    
    </form>
  </DIV>
    
  <div class="menu_line"></div> 
  
  <div style='width: 100%;'> <%-- 갤러리 Layout 시작 --%>
    <c:forEach var="productsVO" items="${list }" varStatus="status">
      <c:set var="pName" value="${productsVO.pName }" />
      <c:set var="description" value="${productsVO.description }" />
      <c:set var="categoryID" value="${productsVO.categoryID }" />
      <c:set var="productID" value="${productsVO.productID }" />
      <c:set var="thumb" value="${productsVO.thumb }" />
      <c:set var="sizes" value="${productsVO.sizes }" />
        
      <!-- 4기준 하나의 이미지, 24 * 4 = 96% -->
      <!-- 5기준 하나의 이미지, 19.2 * 5 = 96% -->
      <div onclick="location.href='./read.do?productID=${productID}&word=${param.word }&now_page=${param.now_page == null ? 1 : param.now_page }&categoryID=${param.categoryID }'" 
             style='width: 19%; height: 200px; float: left; margin: 0.5%; padding: 0.5%; background-color: #EEEFFF; text-align: left; cursor: pointer;'>
        
        <c:choose> 
          <c:when test="${thumb.endsWith('jpg') || thumb.endsWith('png') || thumb.endsWith('gif')}"> <%-- 이미지인지 검사 --%>
            <%-- registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Contents.getUploadDir()); --%>
            <img src="/products/storage/${thumb }" style="width: 100%; height: 120px;">
          </c:when>
          <c:otherwise> <!-- 이미지가 없는 경우 기본 이미지 출력: /static/contents/images/none1.png -->
            <IMG src="/products/images/none1.png" style="width: 100%; height: 120px;">
          </c:otherwise>
        </c:choose>
        ${pName }
        
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
