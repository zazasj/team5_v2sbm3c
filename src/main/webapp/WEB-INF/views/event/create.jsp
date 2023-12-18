<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>http://localhost:9093/event/create.do</title>
<link rel="shortcut icon" href="/images/whiskypng" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

</head>
 
<body>
<jsp:include page="../menu/top.jsp" flush='false' />

<div class='title_line'>이벤트 등록</div>
  <form name='frm' method='post' action='/event/create.do' enctype="multipart/form-data">     
    <div>
       <label>제목</label>
       <input type='text' name='title' value='주말 오지 탐험' required="required" 
                 autofocus="autofocus" class="form-control" style='width: 100%;'>
    </div>
    <div>
       <label>내용</label>
       <textarea name='contents' required="required" class="form-control" rows="12" style='width: 100%;'>오지 탐험은 언제나 즐거운 시간이었어요! </textarea>
    </div>     
    <div>
       <label>이미지</label>
       <input type='file' class="form-control" name='file1MF' id='file1MF' value='' placeholder="파일 선택">
    </div>     
    <div class="content_body_bottom">
      <button type="submit" class="btn btn-secondary btn-sm">등록</button>
      <button type="button" onclick="location.href='./list_by_eventno.do?now_page=${param.now_page == null ? 1 : param.now_page }&word=${param.word }'" class="btn btn-secondary btn-sm">목록</button>
    </div>
  
  </form>

 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>

