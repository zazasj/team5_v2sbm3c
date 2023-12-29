<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9092/inventory/list_all.do</title>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

</head>
<body>
<c:import url="/menu/top.do" />

  <div class='title_line'>재고 내역 삭제</div>
  
  <aside class="aside_right">
    <a href="./list.do">현 재고</a>    
    <span class='menu_divide' >│</span>
    <a href="./list_by_inventoryStatus.do?word=&now_page=1&inventoryStatus=입고">입고</a>    
    <span class='menu_divide' >│</span>
    <a href="./list_by_inventoryStatus.do?word=&now_page=1&inventoryStatus=출고">출고</a>
    <span class='menu_divide' >│</span>
    <a href="javascript:location.reload();">새로고침</a>
  </aside>
  
  <div style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_all.do'>
      
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
                    onclick="location.href='./list_all.do">검색 취소</button>
      </c:if>    
    </form>
  </div>
  <div class="menu_line"></div> 
  
  <div id='panel_delete' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
    <form name='frm_delete' id='frm_delete' method='post' action='./delete.do'>
      <input type="hidden" name="inventoryID" value="${param.inventoryID }"> <%-- 삭제할 카테고리 번호 --%>

      <c:choose>
        <c:when test="${count_by_inventoryID >= 1 }"> <%-- 자식 레코드가 있는 상황 --%>
          <div class="msg_warning">
            관련 자료 ${count_by_inventoryID } 건이 발견되었습니다.<br>
            관련 자료와 카테고리를 모두 삭제하시겠습니까?
          </div>
            
          <label>관련 카테고리 이름</label>:  ${inventoryVO.inventoryStatus } 
          <a href="../country/list_by_inventoryID.do?inventoryID=${inventoryVO.inventoryID }&now_page=1" title="관련 카테고리로 이동"><img src='/inventory/images/link.png'></a>
          &nbsp;      
          <button type="submit" id='submit' class='btn btn-danger btn-sm' style='height: 28px; margin-bottom: 5px;'>관련 자료와 함게 카테고리 삭제</button>
          
        </c:when>
        <c:otherwise> <%-- 자식 레코드가 없는 상황 --%>
          <div class="msg_warning">카테고리를 삭제하면 복구 할 수 없습니다.</div>
          <label>재고 ID</label>: ${inventoryVO.inventoryID }
      
          <button type="submit" id='submit' class='btn btn-warning btn-sm' style='height: 28px; margin-bottom: 5px;'>삭제</button>          
        </c:otherwise>
      </c:choose>      

      <button type="button" onclick="location.href='/inventory/list_all.do'" class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>취소</button>
    </form>
  </div>
  
   <table class="table table-hover">
    <colgroup>
        <col style='width: 10%;'/>
        <col style='width: 10%;'/>
        <col style='width: 10%;'/>    
        <col style='width: 10%;'/>
        <col style='width: 10%;'/>
        <col style='width: 13%;'/>
        <col style='width: 10%;'/>
        <col style='width: 10%;'/>
        <col style='width: 10%;'/>
      </colgroup>
      <thead>
        <tr>
          <th class="th_bs">재고 ID</th>
          <th class="th_bs">재고 상태</th>
          <th class="th_bs">제품 ID</th>
          <th class="th_bs">제품 이름</th>
          <th class="th_bs">공급업체 ID</th>
          <th class="th_bs">공급업체 회사명</th>
          <th class="th_bs">제품 수량</th>
          <th class="th_bs">입/출고 날짜</th>
          <th class="th_bs">수정/삭제</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach var="inventoryVO" items="${list}" varStatus="info">
    <c:set var="inventoryID" value="${inventoryVO.inventoryID}" />
    <c:set var="inventoryStatus" value="${inventoryVO.inventoryStatus}" />
    <c:set var="productID" value="${inventoryVO.productID}" />
    <c:set var="supplierID" value="${inventoryVO.supplierID}" />
    <c:set var="addQuantity" value="${inventoryVO.addQuantity}" />
    <c:set var="lastUpdated" value="${inventoryVO.lastUpdated.substring(0, 10)}" />

    <tr>
        <td class="td_bs">${inventoryID}</td>
        <td class="td_bs">${inventoryStatus}</td>
        <td class="td_bs">${productID}</td>
        <!-- Loop through the list1 to find matching pName -->
        <c:set var="matchingProduct" value="false" />

<c:forEach var="productVO" items="${list1}" varStatus="info1">
    <c:if test="${productVO.productID eq inventoryVO.productID}">
        <td class="td_bs">${productVO.pName}</td>
        <c:set var="matchingProduct" value="true" />
    </c:if>
</c:forEach>

<c:choose>
    <c:when test="${matchingProduct}">
        <!-- Matching found, do nothing here as it has already been handled in the loop -->
    </c:when>
    <c:otherwise>
        <td class="td_bs">제품명 미지정</td>
    </c:otherwise>
</c:choose>

        <td class="td_bs">${supplierID}</td>
        <c:set var="matching" value="false" />

<c:forEach var="supplierVO" items="${list2}" varStatus="info1">
    <c:if test="${supplierVO.supplierid eq inventoryVO.supplierID}">
        <td class="td_bs">${supplierVO.sname}</td>
        <c:set var="matching" value="true" />
    </c:if>
</c:forEach>

<c:choose>
    <c:when test="${matching}">
        <!-- Matching found, do nothing here as it has already been handled in the loop -->
    </c:when>
    <c:otherwise>
        <td class="td_bs">공급업체 미지정</td>
    </c:otherwise>
</c:choose>
        <td class="td_bs">${addQuantity}</td>
        <td class="td_bs">${lastUpdated}</td>
        <td class="td_bs">
            <a href="./update.do?inventoryID=${inventoryID}" title="수정"><img src="/category/images/update.png" class="icon"></a>
            <a href="./delete.do?inventoryID=${inventoryID}" title="삭제"><img src="/category/images/delete.png" class="icon"></a>
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