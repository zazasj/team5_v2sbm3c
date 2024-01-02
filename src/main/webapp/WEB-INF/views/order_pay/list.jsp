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
    모든 회원분들의 주문결재 내역
  </DIV>

  <DIV class='content_body' style='width: 100%;'>
    
    <DIV style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_by_memberno.do'>
        <A href="javascript:location.reload();">새로고침</A>    
    </form>
    </DIV>
    
  
    <div class='menu_line'></div>
   
   
    <table class="table table-striped" style='width: 100%;'>
    <colgroup>
      <col style='width: 5%;'/>
      <col style='width: 5%;'/>
      <col style='width: 7%;'/>
      <col style='width: 12%;'/>
      <col style='width: 30%;'/>
      <col style='width: 10%;'/>
      <col style='width: 10%;'/>
      <col style='width: 13%;'/>
      <col style='width: 5%;'/>
      <col style='width: 5%;'/>
    </colgroup>
    <TR>
      <TH class='th_bs'>주문<br>번호</TH>
      <TH class='th_bs'>회원<br>번호</TH>
      <TH class='th_bs'>수취인<br>성명</TH>
      <TH class='th_bs'>수취인<br>전화번호</TH>
      <TH class='th_bs'>수취인<br>주소</TH>
      <TH class='th_bs'>결재 타입</TH>
      <TH class='th_bs'>결재 금액</TH>
      <TH class='th_bs'>주문일</TH>
      <TH class='th_bs'>조회</TH>
      <TH class='th_bs'>기타</TH>
    </TR>
   
    <c:forEach var="order_payVO" items="${list }">
      <c:set var="order_payno" value ="${order_payVO.order_payno}" />
      <c:set var="memberno" value ="${order_payVO.memberno}" />
      <c:set var="rname" value ="${order_payVO.rname}" />
      <c:set var="rtel" value ="${order_payVO.rtel}" />
      <c:set var="address" value ="(${order_payVO.rzipcode}) ${order_payVO.raddress1} ${order_payVO.raddress2}" />
      <c:set var="paytype" value ="${order_payVO.paytype}" />
      <c:set var="amount" value ="${order_payVO.amount}" />
      <c:set var="rdate" value ="${order_payVO.rdate}" />
         
       
    <TR>
      <TD class=td_basic>${order_payno}</TD>
      <TD class=td_basic><A href="/member/read.do?memberno=${memberno}">${memberno}</A></TD>
      <TD class='td_basic'>${rname}</TD>
      <TD class='td_left'>${rtel}</TD>
      <TD class='td_basic'>${address}</TD>
      <TD class='td_basic'>
        <c:choose>
          <c:when test="${paytype == 1}">신용 카드</c:when>
          <c:when test="${paytype == 2}">모바일</c:when>
          <c:when test="${paytype == 3}">포인트</c:when>
          <c:when test="${paytype == 4}">계좌 이체</c:when>
          <c:when test="${paytype == 5}">직접 입금</c:when>
        </c:choose>
      </TD>
      <TD class='td_basic'><fmt:formatNumber value="${amount }" pattern="#,###" /></TD>
      <TD class='td_basic'>${rdate.substring(2,16) }</TD>
      <TD class='td_basic'>
        <A href="/order_item/list_by_admin.do?order_payno=${order_payno}&memberno=${memberno}">
        <img src="/orderpay/images/bu6.png" title="주문 내역 상세 조회">
        </A>
      </TD>
      <td style='text-align: center; vertical-align: middle;'>
        <a href="./admin_delete_combined.do?order_payno=${order_payVO.order_payno}">
          <img src="/orderpay/images/delete.png" title="삭제" border='0' />
        </a>
      </td>
      
    </TR>
    </c:forEach>
    
  </TABLE>
   
   <DIV class='bottom_menu'>${paging }</DIV>
   
  <DIV class='bottom_menu'>
    <button type='button' onclick="location.reload();" class="btn btn-primary">새로 고침</button>
    <button type='button' onclick="location.href='http://localhost:9093/';" class="btn btn-primary">돌아가기</button>
  </DIV>
</DIV>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>