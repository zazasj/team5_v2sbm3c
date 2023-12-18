<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="eventno" value="${eventVO.eventno }" />
<c:set var="thumb1" value="${eventVO.thumb1 }" />
<c:set var="file1saved" value="${eventVO.file1saved }" />
<c:set var="title" value="${eventVO.title }" />
<c:set var="contents" value="${eventVO.contents }" />
<c:set var="rdate" value="${eventVO.rdate }" />
<c:set var="file1" value="${eventVO.file1 }" />
<c:set var="size1_label" value="${eventVO.size1_label }" />
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Resort world</title>
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

</head> 
 
<body>
<c:import url="/menu/top.do" />
  <div class='title_line'>이벤트 목록
       <c:if test="${eventVO.title.length() > 0 }">
          > ${eventVO.title }
       </c:if> 
   </div>
  <aside class="aside_right">
    <%-- 관리자로 로그인해야 메뉴가 출력됨 --%>
    <c:if test="${sessionScope.admin_id != null }">
      <%--
      http://localhost:9091/contents/create.do?cateno=1
      http://localhost:9091/contents/create.do?cateno=2
      http://localhost:9091/contents/create.do?cateno=3
      --%>
      <a href="./create.do">등록</a>
      <span class='menu_divide' >│</span>
      <a href="./update_text.do?eventno=${eventno }">글 수정</a>
      <span class='menu_divide' >│</span>
      <a href="./update_file.do?eventno=${eventno }">파일 수정</a>  
      <span class='menu_divide' >│</span>
      <a href="./delete.do?eventno=${eventno }">삭제</a>  
      <span class='menu_divide' >│</span>
    </c:if>
    <a href="./list_by_eventno.do?now_page=${param.now_page == null ? 1 : param.now_page }&word=${param.word }">목록</a>
    <span class='menu_divide' >│</span>
    <a href="javascript:location.reload();">새로고침</a>  
  </aside> 
  
  <DIV class='menu_line'></DIV>

  <fieldset class="fieldset_basic">
    <ul>
      <li class="li_none">
        <DIV style="width: 100%; word-break: break-all;">
          <c:choose>
            <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}">
              <%-- /static/contents/storage/ --%>
              <img src="/event/storage/${file1saved }" style='width: 50%; float: left; margin-top: 0.5%; margin-right: 1%;'> 
            </c:when>
            <c:otherwise> <!-- 기본 이미지 출력 -->
              <img src="/event/images/none1.png" style='width: 50%; float: left; margin-top: 0.5%; margin-right: 1%;'> 
            </c:otherwise>
          </c:choose>

          <span style="font-size: 1.5em; font-weight: bold;">${title }</span>
          <span style="font-size: 1em;"> ${rdate }</span><br>
          ${contents }
        </DIV>
      </li>

      <li class="li_none">
        <div>
          <c:if test="${file1.trim().length() > 0 }">
            첨부 파일: <a href='/download?dir=/event/storage&filename=${file1saved}&downname=${file1}'>${file1}</a> (${size1_label}) 
            <a href='/download?dir=/event/storage&filename=${file1saved}&downname=${file1}'><img src="/contents/images/download.png"></a>
          </c:if>
        </div>
      </li>   
    </ul>
  </fieldset>

      
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>

