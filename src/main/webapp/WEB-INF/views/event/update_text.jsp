<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 <c:set var="eventno" value="${eventVO.eventno }" />
<c:set var="title" value="${eventVO.title }" />
<c:set var="contents" value="${eventVO.contents }" />

 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>술기운</title>
<link rel="shortcut icon" href="/images/sulic-resize36.png" />
<link href="/css/style.css" rel="Stylesheet" type="text/css">

</head>
 
<body>
<c:import url="/menu/top.do" />
 
  <DIV class='title_line'> ${title } >글 수정</DIV>
  
  <aside class="aside_right">
    <%-- 관리자로 로그인해야 메뉴가 출력됨 --%>
    <c:if test="${sessionScope.admin_id != null }">
      <a href="./create.do">등록</a>
      <span class='menu_divide' >│</span>
      <a href="./update_text.do?eventno=${eventno }">글 수정</a>
      <span class='menu_divide' >│</span>
      <a href="./update_file.do?eventno=${eventno }">파일 수정</a>  
      <span class='menu_divide' >│</span>
      <a href="./delete.do?eventno=${eventno }">삭제</a>  
      <span class='menu_divide' >│</span>
    </c:if>
    <a href="./list_all.do">목록</a>
    <a href="javascript:location.reload();">새로고침</a>
    <span class='menu_divide' >│</span>    
  </aside> 
   
  <div class='menu_line'></div>
  
  <form name='frm' method='post' action='./update_text.do'>
    <input type="hidden" name="eventno" value="${eventno }">
    <div>
       <label>제목</label>
       <input type='text' name='title' value='${title }' required="required" 
                 autofocus="autofocus" class="form-control" style='width: 100%;'>
    </div>
    <div>
       <label>내용</label>
       <textarea name='contents' required="required" class="form-control" rows="12" style='width: 100%;'>${contents }</textarea>
    </div>
       
    <div class="content_body_bottom">
      <button type="submit" class="btn btn-secondary btn-sm">저장</button>
      <button type="button" onclick="history.back();" class="btn btn-secondary btn-sm">취소</button>
    </div>
  
  </FORM>

 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>

