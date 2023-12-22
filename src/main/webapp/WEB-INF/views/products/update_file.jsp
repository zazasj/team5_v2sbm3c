<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="categoryID" value="${productsVO.categoryID }" />
<c:set var="productID" value="${productsVO.productID }" />
<c:set var="pName" value="${productsVO.pName }" />
<c:set var="imageFile" value="${productsVO.imageFile }" />
<c:set var="imageFileSaved" value="${productsVO.imageFileSaved }" />
<c:set var="thumb" value="${productsVO.thumb.toLowerCase() }" />
<c:set var="sizes" value="${productsVO.sizes }" />

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
  <DIV class='title_line'> ${categoryVO.categoryName } > ${pName } >파일 수정</DIV>
  
  <aside class="aside_right">
    <a href="javascript:location.reload();">새로고침</a>
  </aside>
  
  <div class='menu_line'></div>

  <fieldset class="fieldset_basic">
    <ul>
      <li class="li_none">
        <DIV style='text-align: center; width: 50%; float: left;'>
          <c:choose>
            <c:when test="${thumb.endsWith('jpg') || thumb.endsWith('png') || thumb.endsWith('gif')}">
              <IMG src="/products/storage/${imageFileSaved }" style='width: 90%;'> 
            </c:when>
            <c:otherwise> <!-- 이미지가 없음 -->
               <IMG src="/products/images/none1.png" style="width: 90%;"> 
            </c:otherwise>
          </c:choose>
          
        </DIV>

        <DIV style='text-align: left; width: 47%; float: left;'>
          <span style='font-size: 1.5em;'>${pName}</span>
          <br>
          <FORM name='frm' method='POST' action='./update_file.do' enctype="multipart/form-data">
            <input type="hidden" name="productID" value="${productID}">
            <input type="hidden" name="now_page" value="${param.now_page }">
                
            <br><br> 
            변경 이미지 선택<br>  
            <input type='file' name='fileMF' id='fileMF' value='' placeholder="파일 선택"><br>
            <br>
            <div style='margin-top: 20px; clear: both;'>  
              <button type="submit" class="btn btn-secondary btn-sm">파일 변경 처리</button>
              <button type="submit" class="btn btn-secondary btn-sm">파일 삭제</button>
              <button type="button" onclick="history.back();" class="btn btn-secondary btn-sm">취소</button>
            </div>  
          </FORM>
        </DIV>
      </li>
    </ul>
  </fieldset>
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>
