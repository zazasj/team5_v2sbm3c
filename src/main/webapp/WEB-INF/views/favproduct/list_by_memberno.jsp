<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9091/</title>
<link rel="shortcut icon" href="/images/whisky.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script type="text/javascript">
  function delete_func(favID) {  // GET -> POST 전송, 상품 삭제
    var frm = $('#frm_post');
    frm.attr('action', './delete.do');
    $('#favID',  frm).val(favID);
    
    frm.submit();
  }   
</script>
  
<style type="text/css">

</style>  
  
</head>
<body>
<c:import url="/menu/top.do" />

<%-- GET -> POST: 상품 삭제, 수량 변경용 폼 --%>
<form name='frm_post' id='frm_post' action='' method='post'>
  <input type='hidden' name='favID' id='favID'>
  <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }">
</form>

<DIV class='title_line'>
  찜한 상품
</DIV>

<DIV class='content_body'>
  <ASIDE class="aside_right">
    <A href="/products/list_shopping_main.do">쇼핑 계속하기</A>
    <span class='menu_divide' >│</span>    
    <A href="javascript:location.reload();">새로고침</A>
    </ASIDE> 

  <DIV class='menu_line'></DIV>

  <table class="table table-striped" style='width: 100%;'>
    <colgroup>
      <col style="width: 15%;"></col>
      <col style="width: 40%;"></col>
      <col style="width: 30%;"></col>
      <col style="width: 15%;"></col>
    </colgroup>
    
    <%-- table 컬럼 --%>
	<thead>
      <tr>
        <th style='text-align: center;'>파일</th>
        <th style='text-align: center;'>상품명</th>
        <th style='text-align: center;'>가격</th>
        <th style='text-align: center;'>기타</th>
      </tr>
    
    </thead>

    <%-- table 내용 --%>
    <tbody>
      <c:choose>
        <c:when test="${list.size() > 0 }"> <%-- 상품이 있는지 확인 --%>
          <c:forEach var="favproductVO" items="${list }">  <%-- 상품 목록 출력 --%>
            <c:set var="favID" value="${favproductVO.favID }" />
            <c:set var="productID" value="${favproductVO.productID }" />
            <c:set var="pName" value="${favproductVO.pName }" />
            <c:set var="thumb" value="${favproductVO.thumb }" />
            <c:set var="price" value="${favproductVO.price }" />
            <c:set var="memberno" value="${favproductVO.memberno }" />
            <c:set var="createat" value="${favproductVO.createat }" />
            
            <tr> 
              <td style='vertical-align: middle; text-align: center;'>
                <c:choose>
                  <c:when test="${thumb.endsWith('jpg') || thumb.endsWith('png') || thumb.endsWith('gif')}">
                    <%-- /static/products/storage/ --%>
                    <a href="/products/read.do?productID=${productID}"><IMG src="/products/storage/${thumb }" style="width: 120px; height: 80px;"></a> 
                  </c:when>
                  <c:otherwise> <!-- 이미지가 아닌 일반 파일 -->
                    ${productsVO.imageFile}
                  </c:otherwise>
                </c:choose>
              </td>  
              <td style='vertical-align: middle; text-align: center;'>
                <a href="/products/read.do?productID=${productID}"><strong>${pName}</strong></a> 
              </td> 
              <td style='vertical-align: middle; text-align: center;'>
                <a><fmt:formatNumber value="${price}" pattern="#,###" /></a><br>
              </td>
              <td style='vertical-align: middle; text-align: center;'>
                <A href="javascript: delete_func(${favID })"><IMG src="/category/images/delete.png"></A>
              </td>
            </tr>
          </c:forEach>
        
        </c:when>
        <c:otherwise>
          <tr>
            <td colspan="6" style="text-align: center; font-size: 1.3em;">찜한 상품에 상품이 없습니다.</td>
          </tr>
        </c:otherwise>
      </c:choose>
      
      
    </tbody>
  </table>
  
  
</DIV>
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>
    
