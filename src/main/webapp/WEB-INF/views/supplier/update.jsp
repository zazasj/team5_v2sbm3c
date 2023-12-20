<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="dev.mvc.supplier.SupplierVO" %>

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

    <div class='title_line'>공급업체 수정 목록</div>

		<aside class="aside_right">
		  <a href="./list_all_adminno.do">등록</a>
		  <span class='menu_divide' >│</span>
		  <a href="javascript:location.reload();">새로고침</a>
		</aside>
		<div class="menu_line"></div> 

<%
SupplierVO supplierVO = (SupplierVO)request.getAttribute("supplierVO");
int supplierid = supplierVO.getSupplierid();
%>
  
<form name='frm' method='post' action='/supplier/update.do'>
  <input type='hidden' name='supplierid' value='<%=supplierid %>'>
  <div>
    <label>공급업체 이름</label>
    <input type="text" name="sname" value="<%=supplierVO.getSname() %>" required="required" autofocus="autofocus" 
               class="form-control form-control-sm" style="width: 50%">
  </div>

  <div style="margin-top: 20px;">
    <label>연락처</label>
    <input type="text" name="contactinfo" value="<%=supplierVO.getContactinfo() %>" required="required" autofocus="autofocus" 
               class="form-control form-control-sm" style="width: 50%">
  </div>
  
  <div class="content_body_bottom">
    <button type="submit" class="btn btn-secondary btn-sm">저장</button>
    <button type="button" onclick="history.back();" class="btn btn-secondary btn-sm">취소</button> 
  </div>
</form>

<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>

