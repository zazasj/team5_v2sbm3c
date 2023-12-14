<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>http://localhost:9093/supplier/create.do</title>
<link rel="shortcut icon" href="/images/whiskypng" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

</head>
 
<body>
<jsp:include page="../menu/top.jsp" flush='false' />
<div class='title_line'>공급업체 등록</div>
  <form name='frm' method='post' action='/supplier/create.do' enctype="multipart/form-data">     
    
    <div>
       <label>공급업체 이름</label>
       <input type='text' name='sname' value='공급 업체 이름을 적으시오.' required="required" 
                 autofocus="autofocus" class="form-control" style='width: 100%;'>
    </div>
    <br>
    <div>
       <label>연락처</label>
       <input type='text' name='contactinfo' value='연락처를 적으시오.' required="required" 
                 autofocus="autofocus" class="form-control" style='width: 100%;'>
    </div>
    <br>
    <div>
       <label>주소</label>
       <input type='text' name='saddress' value='주소를 적으시오.' required="required" 
                 autofocus="autofocus" class="form-control" style='width: 100%;'>
    </div>
    <br>
         
    <div class="content_body_bottom">
      <button type="submit" class="btn btn-secondary btn-sm">등록</button>
      <button type="button" onclick="location.href='./list_all_adminno.do'" class="btn btn-secondary btn-sm">목록</button>
    </div>
  
  </form>

 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>
