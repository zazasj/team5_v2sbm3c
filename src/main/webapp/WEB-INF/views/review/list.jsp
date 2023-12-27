<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
  <DIV class="title_line">
    등록된 모든 댓글
  </DIV>
  <ASIDE class='aside_right'>
    <A href='../categrp/list.do'>카테고리 그룹</A>
    <span class='menu_divide' >│</span>  
    <A href='./list.do'>모든 댓글</A>
    <span class='menu_divide' >│</span> 
    <A href="javascript:location.reload();">새로고침</A>
  </ASIDE>
   
  <div class='menu_line'></div>
  
  <div style='width: 100%;'>
    <table class="table table-striped" style='width: 100%;'>
      <colgroup>
        <col style="width: 5%;"></col>
        <col style="width: 5%;"></col>
        <col style="width: 5%;"></col>
        <col style="width: 20%;"></col>
        <col style="width: 50%;"></col>
        <col style="width: 10%;"></col>
        <col style="width: 5%;"></col>
        
      </colgroup>
      <%-- table 컬럼 --%>
      <thead>
        <tr>
          <th style='text-align: center;'>댓글<br>번호</th>
          <th style='text-align: center;'>상품<br>번호</th>
          <th style='text-align: center;'>회원<br>번호</th>
          <th style='text-align: center;'>제목</th>
          <th style='text-align: center;'>내용</th>
          <th style='text-align: center;'>등록일</th>
          <th style='text-align: center;'>기타</th>
        </tr>
      
      </thead>
      
      <%-- table 내용 --%>
      <tbody>
        <c:forEach var="reviewVO" items="${list }">
          <c:set var="productid" value="${reviewVO.productid }" />
          <c:set var="reviewno" value="${reviewVO.reviewno }" />
          
          <tr style='height: 50px;'> 
            <td style='text-align: center; vertical-align: middle;'>
              ${reviewVO.reviewno }
            </td> 
            <td style='text-align: center; vertical-align: middle;'>
              <A href='../products/read.do?productID=${productid }&word=${param.word == null ? "" :  param.word}&now_page=${param.now_page == null ? 1 : param.now_page }'>${productid}</A>
            </td>
            <td style='text-align: center; vertical-align: middle;'>
              <A href='../member/read.do?memberno=${reviewVO.memberno }'>${reviewVO.memberno}</A>
            </td>
            <td style='text-align: center; vertical-align: middle;'>${reviewVO.retitle}</td>
            <td style='text-align: center; vertical-align: middle;'>${reviewVO.recontent}</td>
            <td style='text-align: center; vertical-align: middle;'>
              ${reviewVO.reviewdate.substring(0, 10)}
            </td>
            <td style='text-align: center; vertical-align: middle;'>
              <a href="./admin_delete.do?reviewno=${reviewVO.reviewno}"><img src="/review/delete.png" title="삭제"  border='0' /></a>
            </td>
          </tr>
        </c:forEach>
        
      </tbody>
    </table>
    <br><br>
  </div>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>