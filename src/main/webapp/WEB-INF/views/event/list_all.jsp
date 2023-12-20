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
</head>
<body>
<c:import url="/menu/top.do" />

  <div class='title_line'>이벤트 목록</div>
  
  <aside class="aside_right">
    <c:if test="${sessionScope.admin_id != null }">
    <a href="/event/create.do">등록</a> <span
      class='menu_divide'>│</span>
      </c:if>
    <a href="javascript:location.reload();">새로고침</a>
  </aside>
  <div style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_by_eventno.do'>     
      
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
                    onclick="location.href='./list_by_eventno.do?word='">검색 취소</button>  
      </c:if>    
    </form>
  </div>
  <div class="menu_line"></div> 
  
  <table class="table table-hover">
    <colgroup>
      <col style="width: 10%;"></col>
      <col style="width: 80%;"></col>
      <col style="width: 10%;"></col>
    </colgroup>
    
    <tbody>
        <c:forEach var="eventVO" items="${list }" varStatus="info">
          <c:set var="eventno" value="${eventVO.eventno }" />
          <c:set var="thumb1" value="${eventVO.thumb1 }" />
    
          <div onclick="location.href='./read.do?eventno=${eventno}'"  
          style='width: 32%; height: 550px; float: left; margin: 0.5%; padding: 0.5%; background-color: #EEEFFF; text-align: center; cursor: pointer;'>
                    
              <c:choose>
                <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}"> <%-- 이미지인지 검사 --%>
                  <%-- registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Contents.getUploadDir()); --%>
                  <img src="/event/storage/${thumb1 }" style="width: 100%;; height: 500px;">
                </c:when>
                <c:otherwise> <!-- 이미지가 없는 경우 기본 이미지 출력: /static/contents/images/none1.png -->
                  <img src="/event/images/none1.png" style="width: 100%; height: 500px;">
                </c:otherwise>
              </c:choose>                        
              <span style="font-weight: bold; font-size: 24px;">${eventVO.title }</span>             
           </div>
        </c:forEach>
    </tbody>
      
  </table>
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>
