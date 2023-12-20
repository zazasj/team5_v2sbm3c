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
<!-- Fotorama -->
<script type="text/JavaScript" src="http://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link href="/jquery/fotorama/fotorama.css" rel="stylesheet"> <!-- /static 기준 -->
<script src="/jquery/fotorama/fotorama.js"></script>
    
</head>
<body>
<c:import url="/menu/top.do" />

  <div class='title_line'>Gallery</div>
  
  <aside class="aside_right">
    <a href="javascript:location.reload();">새로고침</a>
  </aside>
  <div class="menu_line"></div> 
  
  <div style='margin: 0px auto; width: 800px;' >
    <!-- Fotorama data-ratio="100%/66%" -->
    <div class="fotorama"
           data-autoplay="5000"
           data-nav="thumbs"
           data-ratio="800/520"
           data-width="100%">
           
      <c:forEach var="productsVO" items="${list }" varStatus="info">
        <c:set var="imageFileSaved" value="${productsVO.imageFileSaved }" />
        
        <c:if test="${imageFileSaved.endsWith('jpg') || imageFileSaved.endsWith('png') || imageFileSaved.endsWith('gif')}"> <%-- 이미지인지 검사 --%>
          <%-- registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Contents.getUploadDir()); --%>
          <img src="/products/storage/${imageFileSaved }">
        </c:if>  
      
      </c:forEach>

    </div>
  </div>

<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>


