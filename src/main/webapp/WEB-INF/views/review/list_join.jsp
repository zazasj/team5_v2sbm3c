<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Resort world</title>
 
<link href="/css/style.css" rel="Stylesheet" type="text/css">
</head>
 
<body>
<c:import url="/menu/top.do" />
  <DIV class="title_line">
    등록된 모든 댓글
  </DIV>
  <ASIDE class='aside_right'>
    <A href="javascript:location.reload();">새로고침</A>
  </ASIDE>
   
  <div class='menu_line'></div>
  
  <div style='width: 100%;'>
    <table class="table table-striped" style='width: 100%;'>
      <colgroup>
        <col style="width: 10%;"></col>
        <col style="width: 5%;"></col>
        <col style="width: 70%;"></col>
        <col style="width: 10%;"></col>
        <col style="width: 5%;"></col>
        
      </colgroup>
      <%-- table 컬럼 --%>
      <thead>
        <tr>
          <th style='text-align: center;'>구매한<br>상품</th>
          <th style='text-align: center;'>회원<br> ID</th>
          <th style='text-align: center;'>내용</th>
          <th style='text-align: center;'>등록일</th>
          <th style='text-align: center;'>기타</th>
        </tr>
      
      </thead>
      
      <%-- table 내용 --%>
      <tbody>
        <c:forEach var="reviewMemberVO" items="${list }">
          <c:set var="reviewno" value="${reviewMemberVO.reviewno }" />
          <c:set var="productid" value="${reviewMemberVO.productid }" />
          <c:set var="memberno" value="${reviewMemberVO.memberno }" />
          <c:set var="id" value="${reviewMemberVO.id }" />
          <c:set var="recontent" value="${reviewMemberVO.recontent }" />
          <c:set var="reviewdate" value="${reviewMemberVO.reviewdate }" />
          
          <tr style='height: 50px;'> 
            <td style='text-align: center; vertical-align: middle;'>
              <button type='button' class='btn btn-secondary btn-sm'
                    onclick="location.href='../products/read.do?productID=${productid }&word=${param.word == null ? '' :  param.word}&now_page=${param.now_page == null ? 1 : param.now_page }'">제품 보기</button>
            </td>
            <td style='text-align: center; vertical-align: middle;'>
              <A>${id}</A>
            </td>
            <td style='text-align: center; vertical-align: middle;'>${recontent}</td>
            <td style='text-align: center; vertical-align: middle;'>
              ${reviewdate.substring(0, 10)}
            </td>
            <td style='text-align: center; vertical-align: middle;'>
              <a href="./delete.do?reviewno=${reviewno}"><img src="/review/delete.png" title="삭제"  border='0' /></a>
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