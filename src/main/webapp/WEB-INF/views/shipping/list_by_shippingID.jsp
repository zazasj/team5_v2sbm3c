<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

</head>
<body>
<c:import url="/menu/top.do" />

  <div class='title_line'>배송목록
    <c:if test="${param.word.length() > 0 }">
      > 「${param.word }」 검색 ${search_count } 건
    </c:if> 
  </div>
  
  <aside class="aside_right">
    <%-- 관리자로 로그인해야 메뉴가 출력됨 --%>

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
  <form name='frm' method='post' action='/shipping/create.do' enctype="multipart/form-data">
    <div style="text-align: center;">
      
       
        <input type='file'name='file1MF' id='file1MF' value='' placeholder="파일 선택"style="width: 15%">
    
      <label>배송 상태</label>
    <select id="deliveryStatus" name="deliveryStatus" size="1" style="width: 7%">
        <option value="주문완료" >주문완료</option>
        <option value="배송중" >배송중</option>
        <option value="배송완료" >배송완료</option>
        <option value="반품" >반품</option>
        <option value="취소" >취소</option>
    </select>
    
      <label>배송 타입</label>
    <select id="shippingType" name="shippingType" size="1" style="width: 10%">
        <option value="우체국택배" >우체국택배</option>
        <option value="롯데택배" >롯데택배</option>
        <option value="로젠택배" >로젠택배</option>
        <option value="한진택배" >한진택배</option>
        <option value="cj대한통운택배" >cj대한통운택배</option>
        <option value="직접입력" >직접입력</option>
    </select>
      
    
    
    <label>주문 ID</label>
    <input type="text" name="order_payno" value="${shippingVO.order_payno }" required="required" autofocus="autofocus" 
               class="" style="width: 5%">
               
    <label>송장번호</label>
    <input type="text" name="trackingNumber" value="${shippingVO.trackingNumber }" required="required" autofocus="autofocus" 
               class="" style="width: 5%">
               
    <label>배송 예정일</label>
      <input type='date' id='currentDate' name="estimatedDeliveryDate" required="required"  
               class="" style="width: 9%">
    <script>
      document.getElementById('currentDate').value = new Date().toISOString().substring(0, 10);
    </script>
               
      <button type="submit" class="btn btn-secondary btn-sm" style="height: 28px; margin-bottom: 5px;">등록</button>
      <button type="button" onclick="location.href='./list_by_shippingID.do'" class="btn btn-secondary btn-sm" 
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
