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
<jsp:include page="../menu/top.jsp" flush='false' />
<%
String code = (String)request.getAttribute("code");
int cnt = (int)request.getAttribute("cnt");
String gname = (String)request.getAttribute("gname");
%>
<div class='title_line'>카테고리 > 알림</div>
<div class='message'>
  <fieldset class='fieldset_basic'>
    <ul>
        <%
        if(code.equals("create_success")){ %>
            <li class="li_none">
              <span class="span_success">채팅을 등록했습니다.</span><br>
              등록된 채팅그룹 : <%=gname %>
            </li>
        <%}
        else if(code.equals("create_fail")){%>
            <li class="li_none">
              <span class="span_fail">채팅 등록에 실패했습니다.</span><br>
              등록 실패된 채팅그룹 : <%=gname %>
            </li>
        <%}
        else if(code.equals("update_success")){%>
        <li class="li_none">
          <span class="span_success">카테고리그룹을 수정에 성공했습니다.</span><br>
          수정된 카테고리그룹 : <%=gname %>
        </li>
        <%}
        else if(code.equals("update_fail")){%>
        <li class="li_none">
          <span class="span_fail">카테고리그룹을 수정에 실패했습니다.</span><br>
          수정 실패된 카테고리그룹 : <%=gname %>
        </li>
        <%}
        else if(code.equals("delete_success")){%>
        <li class="li_none">
          <span class="span_success">채팅 삭제에 성공했습니다.</span><br>
          수정된 채팅 : <%=gname %>
        </li>
        <%}
        else if(code.equals("delete_fail")){%>
        <li class="li_none">
          <span class="span_fail">채팅 삭제에 실패했습니다.</span><br>
          수정 실패된 채팅 : <%=gname %>
        </li>
        <%}
        %>
        
        
        <li class = "li_none">
          <br>
          <%
          if(cnt == 0){
          %>
            <button type="button" onclick="history.back()" class="btn btn-secondary btn-sm">다시 시도</button>
          <%
          }
          %>
          
          
          <button type="button" onclick="location.href='./list_all.do'" class="btn btn-secondary btn-sm">전체 목록</button>
        </li>
    </ul>
  </fieldset>

</div>

 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>