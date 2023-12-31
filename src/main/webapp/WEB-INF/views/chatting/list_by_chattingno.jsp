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

<c:import url="/menu/top.do" />
<script type="text/javascript">
    $(document).ready(function() {
    	$("tr").on("click", function() {
    		var row = $(this); // 클릭한 행
    	    var chattingno = row.find("td:eq(0)").text().trim(); // 첫 번째 td 요소의 텍스트 가져오기
    	    
    	    // 이미 답변이 있는지 확인
    	    if (row.next().hasClass('answer-row')) {
    	        row.next().remove(); // 이미 답변이 있으면 삭제
    	    } else {
    	        // 새로운 행 추가
    	        var newRow = $('<tr class="answer-row"><td colspan="4"><span class="gpt-text">GPT: <span class="answer-text"></span></td></tr>');
    	        row.after(newRow);
    	    }
        $.ajax({
            url: '/chatting/getAnswer_by_chattingno.do',
            type: 'GET',
            data: { chattingno: chattingno },
            dataType: 'json',
            success: function(response) {
                if (response.answer) {
                	row.next().find('.answer-text').text(response.answer); // 답변을 새로운 행에 표시
                } else if (response.error) {
                    alert(response.error); // 에러 메시지를 화면에 표시
                }
            },
            error: function(error) {
                console.log(error);
            }
        });
    });
});
</script>

</head>
<body>

	<div class='title_line'>챗봇 목록
	<c:if test="${param.word.length() > 0 }">
      > 「${param.word }」 검색 ${search_count } 건
    </c:if> 
    </div>
	
	<aside class="aside_right">
	  <a href="javascript:location.reload();">새로고침</a>
	</aside>
	<div style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_by_chattingno.do'>
      
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
                    onclick="location.href='./list_by_chattingno.do?word='">검색 취소</button>  
      </c:if>    
    </form>
  </div>
	<div class="menu_line"></div> 


<table class="table table-hover">
    <colgroup>
        <col style='width: 15%;'/>
        <col style='width: 65%;'/>
        <col style='width: 10%;'/>    
        <col style='width: 10%;'/>
      </colgroup>
      <thead>
        <tr>
          <th class="th_bs">챗봇 번호</th>
          <th class="th_bs">MSG</th>
          <th class="th_bs">등록일</th>
          <th class="th_bs">삭제</th>
        </tr>
      </thead>
      <tbody>
	      <c:forEach var="chattingVO" items="${list }" varStatus="info">
	      <c:if test="${chattingVO.chattingno % 2 != 0 }"> <%-- chattingno가 홀수인 경우만 출력 --%>
	      <tr>
	      	  <td class="td_bs">${chattingVO.chattingno }</td>
	          <td class="td_bs">${chattingVO.msg.replace('Q.', '<span class="q-text">Q.</span>')}</td>
	          <td class="td_bs">${chattingVO.rdate }</td>
	          <td class="td_bs">
	          <a href="./delete.do?chattingno=${chattingVO.chattingno}"><IMG src='/category/images/delete.png' title='삭제' class="icon"></a>
	        </td>
	      </tr>
	      </c:if>
	    </c:forEach>
    </tbody>
      
      
  </table>

<!-- 페이지 목록 출력 부분 시작 -->
  <DIV class='bottom_menu'>${paging }</DIV> <%-- 페이지 리스트 --%>
<!-- 페이지 목록 출력 부분 종료 -->
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>