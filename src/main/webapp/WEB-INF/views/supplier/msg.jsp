<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Resort world</title>
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>

</head> 
<body>
<c:import url="/menu/top.do" />

<DIV class='title_line'>알림</DIV>

<c:set var="code" value="${param.code }" /> <%--mav.addObject("code", "create_success"); --%>
<c:set var="cnt" value="${param.cnt }" />     <%-- mav.addObject("cnt", cnt); --%>
<c:set var="supplierid" value="${param.supplierid }" /> <%-- mav.addObject("cateno", contentsVO.getTripno()); // redirect parameter 적용 --%>

<DIV class='message'>
  <fieldset class='fieldset_basic'>
    <UL>
      <c:choose>
        <c:when test="${code == 'create_success'}"> <%-- Java if --%>
          <LI class='li_none'>
            <span class="span_success">새로운 업체를 등록했습니다.</span>
          </LI> 
        </c:when>
        <c:when test="${code == 'create_fail'}"> <%-- Java if --%>
          <LI class='li_none'>
            <span class="span_fail">새로운 업체 등록에 실패했습니다.</span>
          </LI>                                                                      
        </c:when>
        
        <c:when test="${code == 'update_fail'}"> <%-- Java if --%>
          <LI class='li_none'>
            <span class="span_fail">업체 수정에 실패했습니다.</span>
          </LI>                                                                      
        </c:when>
        
        <c:when test="${code == 'delete_success'}"> <%-- Java if --%>
          <LI class='li_none'>
            <span class="span_success">업체 삭제에 성공했습니다.</span>
          </LI>                                                                      
        </c:when>        
        
        <c:when test="${code == 'delete_fail'}"> <%-- Java if --%>
          <LI class='li_none'>
            <span class="span_fail">업체 삭제에 실패했습니다.</span>
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
      <LI class='li_none'>
        <br>
        <c:choose>
            <c:when test="${cnt == 0 }">
                <button type='button' onclick="history.back()" class="btn btn-secondary btn-sm">다시 시도</button>    
            </c:when>
            <c:otherwise>
                <button type='button' onclick="location.href='./create.do?'" class="btn btn-secondary btn-sm">새로운 업체 등록</button>
                <button type='button' onclick="location.href='./list_by_adminno.do'" class="btn btn-secondary btn-sm">목록</button>
            </c:otherwise>
        </c:choose>
        
       
      </LI>
    </UL>
  </fieldset>

</DIV>

<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>

</html>

