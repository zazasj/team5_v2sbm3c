<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>술기운</title>
<link rel="shortcut icon" href="/images/sulic-resize36.png" />
<link href="/css/style.css" rel="Stylesheet" type="text/css">
<c:import url="/menu/top.do" />
<script>
        function onlyNumbers(evt) {
            // 입력된 키코드 가져오기
            var charCode = (evt.which) ? evt.which : event.keyCode;

            // 입력된 값이 숫자가 아니면 입력을 취소
            if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                evt.preventDefault();
            }
        }
</script>
</head>
 
<body>

 
  <div class='title_line'>${categoryVO.categoryName } > 글 등록</div>
  
  <aside class="aside_right">
    <a href="javascript:location.reload();">새로고침</a>
  </aside>

  
  <div class='menu_line'></div>
  
  <form name='frm' method='post' action='./create.do' enctype="multipart/form-data">
    <input type="hidden" name="categoryID" value="${param.categoryID }">
    
    <div>
       <label>상품 명</label>
       <input type='text' name='pName' required="required" 
                 autofocus="autofocus" placeholder="술 이름" class="form-control" style='width: 100%;'>
    </div>
    <div>
       <label>내용</label>
       <textarea name='description' required="required"class="form-control" rows="12" style='width: 100%;' placeholder="상품 설명"></textarea>
    </div>
    <div>
      <label>용량</label>
      <input type="text" name='volume' required="required"class="form-control" onkeypress="onlyNumbers(event)" placeholder="숫자만 입력가능">
    </div>
    <div>
      <label>알코올 도수</label>
      <input type="text" name='alcoholContent' required="required"class="form-control" onkeypress="onlyNumbers(event)" placeholder="숫자만 입력가능">
    </div>
    <div>
      <label>가격</label>
      <input type="text" name='price' required="required"class="form-control" onkeypress="onlyNumbers(event)" placeholder="숫자만 입력가능">
    </div>
    <div>
    <label>공급사</label>
      <select name="supplierid" required="required"class="form-control" size="1">
          <option value="">공급사</option>
          <c:forEach var="supplierVO" items="${list_sup}">
              <c:set var="supplierid" value="${supplierVO.supplierid}"/>
              <c:set var="sname" value="${supplierVO.sname}"/>
              <option value="${supplierid}">${sname}</option>
          </c:forEach>
      </select>
    </div>
    <div>
      <label>국가</label>
      <input type='text' name='origin' required="required" placeholder="해당 주류 제조국" class="form-control">
    </div>
    
    <div>
       <label>이미지</label>
       <input type='file' class="form-control" name='fileMF' id='fileMF' value='' placeholder="파일 선택">
    </div>   
    <div class="content_body_bottom">
      <button type="submit" class="btn btn-secondary btn-sm">등록</button>
      <button type="button" onclick="location.href='./list_by_categoryID.do?categoryID=${param.categoryID}'" class="btn btn-secondary btn-sm">목록</button>
    </div>
  
  </form>

 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>

