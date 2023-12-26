<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="categoryID" value="${categoryVO.categoryID}" />

<c:set var="productID" value="${productsVO.productID }" />
<c:set var="pName" value="${productsVO.pName}" />
<c:set var="description" value="${productsVO.description }" />
<c:set var="price" value="${productsVO.price }" />
<c:set var="volume" value="${productsVO.volume }" />
<c:set var="alcoholContent" value="${productsVO.alcoholContent }" />
<c:set var="origin" value="${productsVO.origin }" />

 
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

 
  <DIV class='title_line'> ${categoryVO.categoryName } > ${pName} > 수정</DIV>
  
  <aside class="aside_right">
    <a href="javascript:location.reload();">새로고침</a>
  </aside>

  
  <div class='menu_line'></div>
  
  <form name='frm' method='post' action='./update_text.do'>
    <input type="hidden" name="categoryID" value="${categoryID}">
    <input type="hidden" name="productID" value="${productID }">
    <input type="hidden" name="now_page" value="${param.now_page }">
    
    <div>
       <label>상품 명</label>
       <input type='text' name='pName' value='${pName }' required="required" 
                 autofocus="autofocus" class="form-control" style='width: 100%;'>
    </div>
    <div>
       <label>내용</label>
       <textarea name='description' required="required" class="form-control" rows="12" style='width: 100%;'>${description }</textarea>
    </div>
    <div>
      <label>용량</label>
      <input type="text" name='volume' value='${volume }' required="required"class="form-control" onkeypress="onlyNumbers(event)" placeholder="숫자만 입력가능">
    </div>
    <div>
      <label>알코올 도수</label>
      <input type="text" name='alcoholContent' value='${alcoholContent }' required="required"class="form-control" onkeypress="onlyNumbers(event)" placeholder="숫자만 입력가능">
    </div>
    <div>
      <label>가격</label>
      <input type="text" name='price' value='${price }' required="required"class="form-control" onkeypress="onlyNumbers(event)" placeholder="숫자만 입력가능">
    </div>
    <div>
    <label>공급사</label>
      <select name="supplierID" required="required"class="form-control" size="1">
          <option value="${productsVO.supplierID }">현재 공급사 id : ${productsVO.supplierID }</option>
          <c:forEach var="supplierVO" items="${list_sup}">
              <c:set var="supplierid" value="${supplierVO.supplierid}"/>
              <c:set var="sname" value="${supplierVO.sname}"/>
              <option value="${supplierid}">${sname} 공급사id : ${supplierid}</option>
          </c:forEach>
      </select>
    </div>
    <div>
      <label>국가</label>
      <input type='text' name='origin' value='${origin }' required="required" placeholder="해당 주류 제조국" class="form-control">
    </div>
       
    <div class="content_body_bottom">
      <button type="submit" class="btn btn-secondary btn-sm">저장</button>
      <button type="button" onclick="history.back();" class="btn btn-secondary btn-sm">취소</button>
    </div>
  
  </FORM>

 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>

