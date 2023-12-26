<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.ArrayList" %>
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
  <div class='title_line'>배송현황</div>
    <aside class="aside_right">
    <a href="./list_all.do">목록형</a>    
    <span class='menu_divide' >│</span>
    <a href="./list_by_tripno_grid.do?now_page=${param.now_page == null ? 1 : param.now_page }&word=${param.word }">갤러리형</a> 
    <span class='menu_divide' >│</span>
    <a href="javascript:location.reload();">새로고침</a>
  </aside>
  <div class="menu_line"></div> 
  
  <form name='frm' method='post' action='/shipping/create.do' enctype="multipart/form-data">
    <div style="text-align: center;">
      
       <label>이미지</label>
        <input type='file' class="form-control" name='file1MF' id='file1MF' value='' placeholder="파일 선택"style="width: 10%">
    
      <label>배송 상태</label>
    <select id="deliveryStatus" name="deliveryStatus" size="1" style="overflow-y: auto;">
        <option value="주문완료" >주문완료</option>
        <option value="배송중" >배송중</option>
        <option value="배송완료" >배송완료</option>
        <option value="반품" >반품</option>
        <option value="취소" >취소</option>
    </select>
    
      <label>배송 타입</label>
    <select id="shippingType" name="shippingType" size="1" style="overflow-y: auto;">
        <option value="우체국택배" >우체국택배</option>
        <option value="롯데택배" >롯데택배</option>
        <option value="로젠택배" >로젠택배</option>
        <option value="한진택배" >한진택배</option>
        <option value="cj대한통운택배" >cj대한통운택배</option>
        <option value="직접입력" >직접입력</option>
    </select>
      
    
    
    <label>주문 ID</label>
    <input type="text" name="order_payno" value="${shippingVO.order_payno }" required="required" autofocus="autofocus" 
               class="" style="width: 10%">
               
    <label>송장번호</label>
    <input type="text" name="trackingNumber" value="${shippingVO.trackingNumber }" required="required" autofocus="autofocus" 
               class="" style="width: 10%">
               
    <label>배송 예정일</label>
      <input type='date' id='currentDate' name="estimatedDeliveryDate" required="required"  
               class="" style="width: 9%">
    <script>
      document.getElementById('currentDate').value = new Date().toISOString().substring(0, 10);
    </script>
               
      <button type="submit" class="btn btn-secondary btn-sm" style="height: 28px; margin-bottom: 5px;">등록</button>
      <button type="button" onclick="location.href='./list_all.do'" class="btn btn-secondary btn-sm" 
                  style="height: 28px; margin-bottom: 5px;">목록</button> 
    </div>
  </form>
  
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
              <a href="./update_file.do?shippingID=${shippingID}&now_page=${param.now_page == null ? 1 : param.now_page }" title="파일수정"><img src="/shipping/images/update.png" class="icon"></a>
              <a href="./update.do?shippingID=${shippingID }" title="수정"><img src="/category/images/update.png" class="icon"></a>
              <a href="./delete.do?shippingID=${shippingID }" title="삭제"><img src="/category/images/delete.png" class="icon"></a>
            </td>
          </tr>
        </c:forEach>
      </tbody>
      
  </table>
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>
