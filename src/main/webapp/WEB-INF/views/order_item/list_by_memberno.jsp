<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
  $(function(){
 
  });
</script>
</head> 
 
<body>
 
  <DIV class='title_line'>
    ${sessionScope.id }님 주문 결재 상세 내역
  </DIV>

  <DIV class='content_body' style='width: 100%;'>

    <ASIDE class="aside_right">
      <A href="javascript:location.reload();">새로고침</A>
    </ASIDE> 
   
    <div class='menu_line'></div>
   
   
    <table class="table table-striped" style='width: 100%;'>
    <colgroup>
      <col style='width: 5%;'/>
      <col style='width: 5%;'/>
      <col style='width: 10%;'/>
      <col style='width: 10%;'/>
      <col style='width: 30%;'/>
      <col style='width: 5%;'/>
      <col style='width: 5%;'/>
      <col style='width: 5%;'/>
      <col style='width: 10%;'/>
      <col style='width: 15%;'/>
     
    </colgroup>
    <TR>
      <TH class='th_bs'>주문<br>결재</TH>
      <TH class='th_bs'>주문<br>상세</TH>
      <TH class='th_bs'>회원<br>번호</TH>
      <TH class='th_bs'>컨텐츠<br>번호</TH>
      <TH class='th_bs'>상품명</TH>
      <TH class='th_bs'>가격</TH>
      <TH class='th_bs'>수량</TH>
      <TH class='th_bs'>금액</TH>
      <TH class='th_bs'>배송상태</TH>
      <TH class='th_bs'>주문일</TH>
    </TR>
   
    <c:forEach var="order_itemVO" items="${list }">
      <c:set var="order_payno" value ="${order_itemVO.order_payno}" />
      <c:set var="order_itemno" value ="${order_itemVO.order_itemno}" />
      <c:set var="memberno" value ="${order_itemVO.memberno}" />
      <c:set var="productid" value ="${order_itemVO.productid}" />
      <c:set var="pname" value ="${order_itemVO.pname}" />
      <c:set var="price" value ="${order_itemVO.price}" />
      <c:set var="cnt" value ="${order_itemVO.cnt}" />
      <c:set var="tot" value="${order_itemVO.price * order_itemVO.cnt}" /> <!-- Calculate the total for each item -->
      <c:set var="stateno" value ="${order_itemVO.stateno}" />
      <c:set var="rdate" value ="${order_itemVO.rdate}" />
         
    <TR>
      <TD class=td_basic>${order_payno}</TD>
      <TD class=td_basic>${order_itemno}</TD>
      <TD class=td_basic><A href="/member/read.do?memberno=${memberno}">${memberno}</A></TD>
      <TD class=td_basic><A href="/products/read.do?productid=${productid}">${productid}</A></TD>
      <TD class='td_left'>${pname}</TD>
      <TD class='td_left'><fmt:formatNumber value="${price }" pattern="#,###" /></TD>
      <TD class='td_basic'>${cnt }</TD>
      <TD class='td_basic'><fmt:formatNumber value="${tot }" pattern="#,###" /></TD>
      <TD class='td_basic'>
        <c:choose>
          <c:when test="${stateno == 1}">결재 완료</c:when>
          <c:when test="${stateno == 2}">상품 준비중</c:when>
          <c:when test="${stateno == 3}">배송 시작</c:when>
          <c:when test="${stateno == 4}">배달중</c:when>
          <c:when test="${stateno == 5}">오늘 도착</c:when>
          <c:when test="${stateno == 6}">배달 완료</c:when>
        </c:choose>
      </TD>
      
      <TD class='td_basic'>${rdate.substring(2,16) }</TD>
      
    </TR>
    </c:forEach>
    
  </TABLE>
  
  <table class="table table-striped" style='width: 100%;'>
    <TR>
      <TD colspan="10"  style="text-align: right; font-size: 1.3em;">
        배송비: <fmt:formatNumber value="${baesong_tot }" pattern="#,###" />  
        총 주문 금액: <fmt:formatNumber value="${total_order }" pattern="#,###" />  
      </TD>
    </TR>  
  </table>    
   
  <DIV class='bottom_menu'>
    <button type='button' onclick="location.reload();" class="btn btn-primary">새로 고침</button>
    <button type='button' onclick="location.href='/order_pay/list_by_memberno.do?memberno=${memberno}'" class="btn btn-primary">돌아가기</button>
  </DIV>
</DIV>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>