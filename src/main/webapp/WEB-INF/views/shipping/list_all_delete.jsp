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

</head>
<body>
<c:import url="/menu/top.do" />

  <div class='title_line'>재고 내역 삭제</div>
  
  <aside class="aside_right">
    <a href="./create.do?shippingID=${shippingVO.shippingID }">등록</a>
    <span class='menu_divide' >│</span>
    <a href="javascript:location.reload();">새로고침</a>
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
  
  <div id='panel_delete' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
    <form name='frm_delete' id='frm_delete' method='post' action='./delete.do'>
      <input type="hidden" name="shippingID" value="${param.shippingID }"> <%-- 삭제할 카테고리 번호 --%>

      <c:choose>
        <c:when test="${count_by_shippingID >= 1 }"> <%-- 자식 레코드가 있는 상황 --%>
          <div class="msg_warning">
            관련 자료 ${count_by_shippingID } 건이 발견되었습니다.<br>
            관련 자료와 카테고리를 모두 삭제하시겠습니까?
          </div>
            
          <label>관련 배송 ID</label>:  ${shippingVO.shippingID } 
          <a href="../country/list_by_shippingID.do?shippingID=${shippingVO.shippingID }&now_page=1" title="관련 카테고리로 이동"><img src='/shipping/images/link.png'></a>
          &nbsp;      
          <button type="submit" id='submit' class='btn btn-danger btn-sm' style='height: 28px; margin-bottom: 5px;'>관련 자료와 함게 카테고리 삭제</button>
          
        </c:when>
        <c:otherwise> <%-- 자식 레코드가 없는 상황 --%>
          <div class="msg_warning">카테고리를 삭제하면 복구 할 수 없습니다.</div>
          <label>재고 ID</label>: ${shippingVO.shippingID }
      
          <button type="submit" id='submit' class='btn btn-warning btn-sm' style='height: 28px; margin-bottom: 5px;'>삭제</button>          
        </c:otherwise>
      </c:choose>      

      <button type="button" onclick="location.href='/shipping/list_by_shippingID.do'" class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>취소</button>
    </form>
  </div>
  
   <table class="table table-hover">
    <colgroup>
        <col style='width: 10%;'/>
        <col style='width: 10%;'/>
        <col style='width: 10%;'/>    
        <col style='width: 10%;'/>
        <col style='width: 10%;'/>
        <col style='width: 10%;'/>
        <col style='width: 10%;'/>
        <col style='width: 10%;'/>
      </colgroup>
      <thead>
        <tr>
          <th class="th_bs">사진</th>
          <th class="th_bs">배송 ID</th>
          <th class="th_bs">배송 상태</th>
          <th class="th_bs">베송 타입</th>
          <th class="th_bs">주문 ID</th>
          <th class="th_bs">송장 번호</th>
          <th class="th_bs">배송 예정일</th>
          <th class="th_bs">기타</th>
        </tr>
      </thead>
      <tbody>
        
        <c:forEach var="shippingVO" items="${list }" varStatus="info">
          <c:set var="thumb1" value="${shippingVO.thumb1 }" />
          <c:set var="shippingID" value="${shippingVO.shippingID }" />
          <c:set var="deliveryStatus" value="${shippingVO.deliveryStatus }" />
          <c:set var="shippingType" value="${shippingVO.shippingType }" />
          <c:set var="order_payno" value="${shippingVO.order_payno }" />
          <c:set var="trackingNumber" value="${shippingVO.trackingNumber }" />
          <c:set var="estimatedDeliveryDate" value="${shippingVO.estimatedDeliveryDate.substring(0, 10) }" />
          <tr>
            <td>
              <c:choose>
                <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}"> <%-- 이미지인지 검사 --%>
                  <%-- registry.addResourceHandler("/country/storage/**").addResourceLocations("file:///" +  Country.getUploadDir()); --%>
                  <img src="/shipping/storage/${thumb1 }" style="width: 120px; height: 90px;">
                </c:when>
                <c:otherwise> <!-- 이미지가 없는 경우 기본 이미지 출력: /static/country/images/none1.png -->
                  <img src="/shipping/images/none1.png" style="width: 120px; height: 90px;">
                </c:otherwise>
              </c:choose>
            </td>
            <td class="td_bs">${shippingID}</td>
            <td class="td_bs">${deliveryStatus }</td>
            <td class="td_bs">${shippingType}</td>
            <td class="td_bs">${order_payno}</td>
            <td class="td_bs">${trackingNumber}</td>
            <td class="td_bs">${estimatedDeliveryDate}</td>
            <td class="td_bs">
              <a href="./update.do?shippingID=${shippingID }" title="수정"><img src="/shipping/images/update.png" class="icon"></a>
              <a href="./delete.do?shippingID=${shippingID }" title="삭제"><img src="/shipping/images/delete.png" class="icon"></a>
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