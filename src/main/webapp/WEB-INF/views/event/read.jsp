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
  <DIV class='title_line'>
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
    <a href="./list_all.do">목록</a>
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

</DIV>

<c:set var="listSize" value="${list.size()}" />
<div style="display: flex; align-items: center; margin-left: 100px; margin-right: 100px;">
    <span style="font-size: 1.5em; font-weight: bold;">${listSize}개의 리뷰</span>
</div>
<br>
<DIV class='menu_line'></DIV>

<!-- 리뷰 입력 폼 -->
<div style="display: flex; align-items: center; border: 1px solid #000; margin-left: 100px; margin-right: 100px;">
    <fieldset class="fieldset_basic">
    
    <form action="/your_server_url/add_review" method="POST">
        <label for="rating">별점 선택:</label>
		<select id="rating" name="rating" size="5" style="overflow-y: auto;">
		    <option value="1">1점</option>
		    <option value="1.5">1.5점</option>
		    <option value="2">2점</option>
		    <option value="2.5">2.5점</option>
		    <option value="3">3점</option>
		    <option value="3.5">3.5점</option>
		    <option value="4">4점</option>
		    <option value="4.5">4.5점</option>
		    <option value="5">5점</option>
		    <option value="5.5">5.5점</option>
		    <option value="6">6점</option>
		    <option value="6.5">6.5점</option>
		    <option value="7">7점</option>
            <option value="7.5">7.5점</option>
            <option value="8">8점</option>
            <option value="8.5">8.5점</option>
            <option value="9">9점</option>
            <option value="9.5">9.5점</option>
            <option value="10">10점</option>
		    <!-- 추가적인 옵션들 -->
	    </select>	       
        <textarea name="review_content" rows="6" cols="150" style="margin-top: 20px;" placeholder="        리뷰를 입력하세요. 
        다른 사람에게 불쾌감을 주는 욕설, 혐오, 비하의 표현이나 타인의 권리를 침해하는 내용은 주의해주세요.
        모든 작성자는 본인이 작성한 의견에 대해 법적인 책임을 갖는다는 점 유의하시기 바랍니다." ></textarea>
        <input type="hidden" name="eventno" value="${eventno}"> <!-- 현재 이벤트의 ID를 전달 -->
        <div style="text-align: right; margin-right: 50px;">
        <button type="submit"  id = "sm_button" class="btn btn-secondary btn-sm" >입력</button>
        </div>
    </form>
    </fieldset>
</div>
    
        <table class="table table-hover">
    <colgroup>
      <col style="width: 10%;" />
      <col style="width: 45%;" />
      <col style="width: 10%;" />
      <col style="width: 20%;" />
      <col style="width: 15%;" />
    </colgroup>
    <thead>
      <tr>
        <th class="th_bs">작성자</th>
        <th class="th_bs">리뷰</th>
        <th class="th_bs">평점</th>
        <th class="th_bs">등록일</th>
        <th class="th_bs">기타</th>
      </tr>
    </thead>

    <tbody>
        <c:forEach var="ReviewVO" items="${list }" varStatus="info">
          <c:set var="reviewno" value="${ReviewVO.reviewno }" />
    
          <tr>
            <td class="td_bs">${info.count }</td>
            <td> ${ReviewVO.reuser }</td>
            <td class="td_bs">${ReviewVO.retitle }</td>
        <td class="td_bs">${ReviewVO.rating }</td>
            <td class="td_bs">${ReviewVO.reviewdate.substring(0, 10) }</td>
            
          </tr>
        </c:forEach>
      </tbody>

  </table>
      
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>

