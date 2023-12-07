<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9093/products/list_all_gallery.do</title>
<link rel="shortcut icon" href="/images/whisky.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

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
           
      <c:forEach var="contentsVO" items="${list }" varStatus="info">
        <c:set var="ImageFileSaved" value="${productsVO.ImageFileSaved }" />
        
        <c:if test="${ImageFileSaved.endsWith('jpg') || ImageFileSaved.endsWith('png') || ImageFileSaved.endsWith('gif')}"> <%-- 이미지인지 검사 --%>
          <%-- registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Contents.getUploadDir()); --%>
          <img src="/products/storage/${ImageFileSaved }">
        </c:if>  
      
      </c:forEach>

    </div>
  </div>

<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>


