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
  
<script type="text/javascript">
  function delete_func(cartID) {  // GET -> POST 전송, 상품 삭제
    var frm = $('#frm_post');
    frm.attr('action', './delete.do');
    $('#cartID',  frm).val(cartID);
    
    frm.submit();
  }   

  function update_cnt(cartID) {  // 수량 변경
    var frm = $('#frm_post');
    frm.attr('action', './update_cnt.do');
    $('#cartID',  frm).val(cartID);
    
    var new_cnt = $('#' + cartID + '_cnt').val();  // $('#1_cnt').val()로 변환됨.
    $('#cnt',  frm).val(new_cnt);

    // alert('cnt: ' + $('#cnt',  frm).val());
    // alert('cartno: ' + $('#cartno',  frm).val());
    // return;
    
    frm.submit();
    
  }
</script>

<style type="text/css">

    
</style>  
  
</head>
<body>

<%-- GET -> POST: 상품 삭제, 수량 변경용 폼 --%>
<form name='frm_post' id='frm_post' action='' method='post'>
  <input type='hidden' name='cartID' id='cartID'>
  <input type='hidden' name='cnt' id='cnt'>
</form>
 
<DIV class='title_line'>
  쇼핑 카트
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
      <col style="width: 10%;"></col>
      <col style="width: 40%;"></col>
      <col style="width: 20%;"></col>
      <col style="width: 10%;"></col> <%-- 수량 --%>
      <col style="width: 10%;"></col> <%-- 합계 --%>
      <col style="width: 10%;"></col>
    </colgroup>
    <%-- table 컬럼 --%>
<!--     <thead>
      <tr>
        <th style='text-align: center;'>파일</th>
        <th style='text-align: center;'>상품명</th>
        <th style='text-align: center;'>정가, 할인률, 판매가, 포인트</th>
        <th style='text-align: center;'>기타</th>
      </tr>
    
    </thead> -->
    
    <%-- table 내용 --%>
    <tbody>
      <c:choose>
        <c:when test="${list.size() > 0 }"> <%-- 상품이 있는지 확인 --%>
          <c:forEach var="cartsVO" items="${list }">  <%-- 상품 목록 출력 --%>
            <c:set var="cartID" value="${cartsVO.cartID }" />
            <c:set var="productID" value="${cartsVO.productID }" />
            <c:set var="pName" value="${cartsVO.pName }" />
            <c:set var="thumb" value="${cartsVO.thumb }" />
            <c:set var="price" value="${cartsVO.price }" />
            <c:set var="memberno" value="${cartsVO.memberno }" />
            <c:set var="cnt" value="${cartsVO.cnt }" />
            <c:set var="tot" value="${cartsVO.tot }" />
            <c:set var="cdate" value="${cartsVO.cdate }" />
            
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
              <td style='vertical-align: middle;'>
                <a href="/products/read.do?productID=${productID}"><strong>${pName}</strong></a> 
              </td> 
              <td style='vertical-align: middle; text-align: center;'>
                <a><fmt:formatNumber value="${price}" pattern="#,###" /></a><br>
              </td>
              <td style='vertical-align: middle; text-align: center;'>
                <input type='number' id='${cartID }_cnt' min='1' max='100' step='1' value="${cnt }" 
                  style='width: 52px;'><br>
                <button type='button' onclick="update_cnt(${cartID})" class='btn' style='margin-top: 5px;'>변경</button>
              </td>
              <td style='vertical-align: middle; text-align: center;'>
                <fmt:formatNumber value="${tot}" pattern="#,###" />
              </td>
              <td style='vertical-align: middle; text-align: center;'>
                <A href="javascript: delete_func(${cartID })"><IMG src="/cart/images/delete3.png"></A>
              </td>
            </tr>
          </c:forEach>
        
        </c:when>
        <c:otherwise>
          <tr>
            <td colspan="6" style="text-align: center; font-size: 1.3em;">쇼핑 카트에 상품이 없습니다.</td>
          </tr>
        </c:otherwise>
      </c:choose>
      
      
    </tbody>
  </table>
  
  <table class="table table-striped" style='margin-top: 50px; margin-bottom: 50px; width: 100%;'>
    <tbody>
      <tr>
        <td style='width: 50%;'>
          <div class='cart_label'>상품 금액</div>
          <div class='cart_price'><fmt:formatNumber value="${tot_sum }" pattern="#,###" /> 원</div>
          
          <div class='cart_label'>배송비</div>
          <div class='cart_price'><fmt:formatNumber value="${baesong_tot }" pattern="#,###" /> 원</div>
        </td>
        <td style='width: 50%;'>
          <div class='cart_label' style='font-size: 2.0em;'>전체 주문 금액</div>
          <div class='cart_price'  style='font-size: 2.0em; color: #FF0000;'><fmt:formatNumber value="${total_ordering }" pattern="#,###" /> 원</div>
          
          <form name='frm' id='frm' style='margin-top: 50px;' action="/order_pay/create.do" method='get'>
            <button type='submit' id='btn_order' class='btn btn-info' style='font-size: 1.5em;'>주문하기</button>
          </form>
        <td>
      </tr>
    </tbody>
  </table>   
</DIV>
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>