<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="categoryName" value="${categoryVO.categoryName }" />

<c:set var="categoryID" value="${productsVO.categoryID }" />
<c:set var="productID" value="${productsVO.productID }" />
<c:set var="thumb" value="${productsVO.thumb }" />
<c:set var="imageFileSaved" value="${productsVO.imageFileSaved }" />
<c:set var="pName" value="${productsVO.pName }" />
<c:set var="description" value="${productsVO.description }" />
<c:set var="imageFile" value="${productsVO.imageFile }" />
<c:set var="size_label" value="${productsVO.size_label }" />
<c:set var="word" value="${productsVO.word }" />
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Resort world</title>
<link rel="shortcut icon" href="/images/whisky.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

</head> 
 
<body>
<c:import url="/menu/top.do" />
  <DIV class='title_line'><A href="./list_by_categoryID.do?categoryID=${categoryVO.categoryID }" class='title_link'>${categoryVO.categoryName }</A></DIV>

  <aside class="aside_right">
    <%-- 관리자로 로그인해야 메뉴가 출력됨 --%>
    <c:if test="${sessionScope.admin_id != null }">
      <%--
      http://localhost:9091/contents/create.do?cateno=1
      http://localhost:9091/contents/create.do?cateno=2
      http://localhost:9091/contents/create.do?cateno=3
      --%>
      <a href="./create.do?categoryID=${categoryID }">등록</a>
      <span class='menu_divide' >│</span>
      <a href="./update_text.do?productID=${productID}&now_page=${param.now_page}&word=${param.word }">글 수정</a>
      <span class='menu_divide' >│</span>
      <a href="./update_file.do?productID=${productID}&now_page=${param.now_page}">파일 수정</a>  
      <span class='menu_divide' >│</span>
      <a href="./delete.do?productID=${productID}&now_page=${param.now_page}&categoryID=${categoryID}">삭제</a>  
      <span class='menu_divide' >│</span>
    </c:if>

    <a href="javascript:location.reload();">새로고침</a>
    <span class='menu_divide' >│</span>    
    <a href="./list_by_categoryID.do?categoryID=${categoryID}&now_page=${param.now_page}&word=${param.word }">목록형</a>    
    <span class='menu_divide' >│</span>
    <a href="./list_by_categoryID_grid.do?categoryID=${categoryID }&now_page=${param.now_page}&word=${param.word }">갤러리형</a>
  </aside> 
  
  <div style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_by_categoryID.do'>
      <input type='hidden' name='categoryID' value='${param.categoryID }'>  <%-- 게시판의 구분 --%>
      
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
                    onclick="location.href='./list_by_categoryID.do?categoryID=${param.categoryID}&word='">검색 취소</button>  
      </c:if>    
    </form>
  </div>
  
  <DIV class='menu_line'></DIV>

  <fieldset class="fieldset_basic">
    <ul>
      <li class="li_none">
        <DIV style="width: 100%; word-break: break-all;">
          <c:choose>
            <c:when test="${thumb.endsWith('jpg') || thumb.endsWith('png') || thumb.endsWith('gif')}">
              <%-- /static/contents/storage/ --%>
              <img src="/products/storage/${imageFileSaved }" style='width: 50%; float: left; margin-top: 0.5%; margin-right: 1%;'> 
            </c:when>
            <c:otherwise> <!-- 기본 이미지 출력 -->
              <img src="/products/images/none1.png" style='width: 50%; float: left; margin-top: 0.5%; margin-right: 1%;'> 
            </c:otherwise>
          </c:choose>

          <span style="font-size: 1.5em; font-weight: bold;">${pName }</span>
          ${description }
        </DIV>
      </li>
      
      <li class="li_none" style="clear: both;">
        <DIV style='text-decoration: none;'>
          <br>
          검색어(키워드): ${word }
        </DIV>
      </li>

      <li class="li_none">
        <div>
          <c:if test="${imageFile.trim().length() > 0 }">
            첨부 파일: <a href='/download?dir=/products/storage&filename=${imageFileSaved}&downname=${imageFile}'>${imageFile}</a> (${size_label}) 
            <a href='/download?dir=/products/storage&filename=${imageFileSaved}&downname=${imageFile}'><img src="/products/images/download.png"></a>
          </c:if>
        </div>
      </li>   
    </ul>
  </fieldset>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>

