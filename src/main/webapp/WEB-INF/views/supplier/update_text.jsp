<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="supplierid" value="${supplierVO.supplierid }" />
<c:set var="thumb1" value="${supplierVO.thumb1 }" />
<c:set var="file1saved" value="${supplierVO.file1saved }" />
<c:set var="sname" value="${supplierVO.sname }" />
<c:set var="contactinfo" value="${supplierVO.contactinfo }" />
<c:set var="email" value="${supplierVO.email }" />
<c:set var="saddress" value="${supplierVO.saddress }" />
<c:set var="rdate" value="${supplierVO.rdate }" />
<c:set var="file1" value="${supplierVO.file1 }" />
<c:set var="size1_label" value="${supplierVO.size1_label }" />
 
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

	<DIV class='title_line'> ${supplierVO.sname } > 글 수정</DIV>
    <aside class="aside_right">
      <a href="./create.do">등록</a>
      <span class='menu_divide' >│</span>
      <a href="./update_text.do?supplierid=${supplierid }">글 수정</a>
      <span class='menu_divide' >│</span>
      <a href="./update_file.do?supplierid=${supplierid }">파일 수정</a>  
      <span class='menu_divide' >│</span>
      <a href="./list_by_supplierid.do?now_page=${param.now_page == null ? 1 : param.now_page }&word=${param.word }">목록형</a>
      <span class='menu_divide' >│</span>
	  <a href="./list_by_supplierid_gird.do?now_page=${param.now_page == null ? 1 : param.now_page }&word=${param.word }">갤러리형</a>    
	  <span class='menu_divide' >│</span>
	  <a href="javascript:location.reload();">새로고침</a>  
    </aside>
    <div style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_by_supplierid.do'>
      
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
                    onclick="location.href='./list_all_adminno.do?word='">검색 취소</button>  
      </c:if>    
    </form>
  </div>
  
  <div class='menu_line'></div>
  
  <form name='frm' method='post' action='./update_text.do'>
    <input type="hidden" name="supplierid" value="${supplierid }">
    
    <div>
       <label>공급업체 이름</label>
       <input type='text' name='sname' value='${sname }' required="required" 
                 autofocus="autofocus" class="form-control" style='width: 100%;'>
    </div>
    <br>
    <div>
       <label>연락처</label>
       <input type='text' name='contactinfo' value='${contactinfo }' required="required" 
                 autofocus="autofocus" class="form-control" style='width: 100%;'>
    </div>
    <br>
    <div>
       <label>이메일</label>
       <input type='text' name='email' value='${email }' required="required" 
                 autofocus="autofocus" class="form-control" style='width: 100%;'>
    </div>
    <br>
    <div>
       <label>주소</label>
       <input type='text' name='saddress' value='${saddress }' required="required" 
                 autofocus="autofocus" class="form-control" style='width: 100%;'>
    </div>
    <br>
    <div class="content_body_bottom">
      <button type="submit" class="btn btn-secondary btn-sm">저장</button>
      <button type="button" onclick="history.back();" class="btn btn-secondary btn-sm">취소</button>
    </div>
    
   </form>
    
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>
