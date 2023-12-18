<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>http://localhost:9093/event/msg.do</title>
<link rel="shortcut icon" href="/images/whisky.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

</head> 
<body>
<c:import url="/menu/top.do" />

<DIV class='title_line'>알림</DIV>

<c:set var="code" value="${param.code }" /> <%--mav.addObject("code", "create_success"); --%>
<c:set var="cnt" value="${param.cnt }" />     <%-- mav.addObject("cnt", cnt); --%>

<DIV class='message'>
  <fieldset class='fieldset_basic'>
    <UL>
      <c:choose>
        <c:when test="${code == 'passwd_fail'}">
          <LI class='li_none'>
            <span class="span_fail">패스워드가 일치하지 않습니다.</span>
          </LI> 
        </c:when>
        
        <c:when test="${code == 'create_success'}"> <%-- Java if --%>
          <li class='li_none'>
            <span class="span_success">새로운 컨텐츠를 등록했습니다.</span>
          </li> 
        </c:when>

        <c:when test="${code == 'check_upload_file_fail'}"> <%-- Java if --%>
          <li class='li_none'>
            <span class="span_fail">업로드 할 수 없는 파일 형식입니다.</span><br>
            <span>가능한 파일 형식[jpg, jpef, png, gif, txt, hwp, doc, ppt, pptx, xls, xlsx, zip, tar, gz, ipynb]</span>
          </li> 
        </c:when>
                
        <c:when test="${code == 'create_fail'}"> <%-- Java if --%>
          <LI class='li_none'>
            <span class="span_fail">새로운 컨텐츠 등록에 실패했습니다.</span>
          </LI>                                                                      
        </c:when>
        
        <c:when test="${code == 'update_fail'}"> <%-- Java if --%>
          <LI class='li_none'>
            <span class="span_fail">컨텐츠 수정에 실패했습니다.</span>
          </LI>                                                                      
        </c:when>
        
        <c:when test="${code == 'delete_success'}"> <%-- Java if --%>
          <LI class='li_none'>
            <span class="span_success">컨텐츠 삭제에 성공했습니다.</span>
          </LI>                                                                      
        </c:when>        
        
        <c:when test="${code == 'delete_fail'}"> <%-- Java if --%>
          <LI class='li_none'>
            <span class="span_fail">컨텐츠 삭제에 실패했습니다.</span>
          </LI>                                                                      
        </c:when> 
        
        <c:otherwise>
          <LI class='li_none_left'>
            <span class="span_fail">알 수 없는 에러로 작업에 실패했습니다.</span>
          </LI>
          <LI class='li_none_left'>
            <span class="span_fail">다시 시도해주세요.</span>
          </LI>
        </c:otherwise>
      </c:choose>
      <li class='li_none'>
        <br>
        <c:choose>
            <c:when test="${cnt == 0 }">
                <button type='button' onclick="history.back()" class="btn btn-secondary btn-sm">다시 시도</button>    
            </c:when>
        </c:choose>
        
        <button type='button' onclick="location.href='./create.do'" class="btn btn-secondary btn-sm">새로운 컨텐츠 등록</button>
        <button type='button' onclick="location.href='./list_by_eventno.do?now_page=${param.now_page == null ? 1 : param.now_page }&word=${param.word }'" class="btn btn-secondary btn-sm">목록</button>
        </li>
    </UL>
  </fieldset>

</DIV>

<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>

</html>

