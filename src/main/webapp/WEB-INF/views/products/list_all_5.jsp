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

  $(function() {
    // var productID = 0;
    // $('#btn_cart').on('click', function() { cart_ajax(productID)});
    $('#btn_login').on('click', login_ajax);
    $('#btn_loadDefault').on('click', loadDefault);
  });
  
  function recom_ajax(productID, status_count) {
    console.log("-> recom_" + status_count + ": " + $('#recom_' + status_count).html());  // A tag body      
    var params = "";
    // params = $('#frm').serialize(); // 직렬화, 폼의 데이터를 키와 값의 구조로 조합
    params = 'productID=' + productID; // 공백이 값으로 있으면 안됨.
    $.ajax(
      {
        url: '/products/update_recom_ajax.do',
        type: 'post',  // get, post
        cache: false, // 응답 결과 임시 저장 취소
        async: true,  // true: 비동기 통신
        dataType: 'json', // 응답 형식: json, html, xml...
        data: params,      // 데이터
        success: function(rdata) { // 응답이 온경우
          var str = '';
          if (rdata.cnt == 1) {
            // $('#span_animation_' + status_count).hide();   // SPAN 태그에 animation 출력
            $('#recom_' + status_count).html('♥('+rdata.recom+')');     // A 태그에 animation 출력
          } else {
            // $('#span_animation_' + status_count).html("X");
            $('#recom_' + status_count).html('♥(X)');
          }
        },
        // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
        error: function(request, status, error) { // callback 함수
          console.log(error);
        }
      }
    );  //  $.ajax END

    $('#recom_' + status_count).html("<img src='/products/images/ani04.gif' style='width: 10%;'>");
    // $('#span_animation_' + status_count).css('text-align', 'center');
    // $('#span_animation_' + status_count).html("<img src='/products/images/ani04.gif' style='width: 10%;'>");
    // $('#span_animation_' + status_count).show(); // 숨겨진 태그의 출력
      
  }  

  function loadDefault() {
    $('#id').val('user1');
    $('#passwd').val('1234');
  } 
  
  <%-- 로그인 --%>
  function login_ajax() {
    var params = "";
    params = $('#frm_login').serialize(); // 직렬화, 폼의 데이터를 키와 값의 구조로 조합
    // params += '&${ _csrf.parameterName }=${ _csrf.token }';
    // console.log(params);
    // return;
    
    $.ajax(
      {
        url: '/member/login_ajax.do',
        type: 'post',  // get, post
        cache: false, // 응답 결과 임시 저장 취소
        async: true,  // true: 비동기 통신
        dataType: 'json', // 응답 형식: json, html, xml...
        data: params,      // 데이터
        success: function(rdata) { // 응답이 온경우
          var str = '';
          console.log('-> login cnt: ' + rdata.cnt);  // 1: 로그인 성공
          
          if (rdata.cnt == 1) {
            // 쇼핑카트에 insert 처리 Ajax 호출
            $('#div_login').hide();
            // alert('로그인 성공');
            $('#login_yn').val('YES'); // 로그인 성공 기록
            carts_ajax_post(); // 쇼핑카트에 insert 처리 Ajax 호출     
            
          } else {
            alert('로그인에 실패했습니다.<br>잠시후 다시 시도해주세요.');
            
          }
        },
        // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
        error: function(request, status, error) { // callback 함수
          console.log(error);
        }
      }
    );  //  $.ajax END

  }

  <%-- 쇼핑 카트에 상품 추가 --%>
  function carts_ajax(productID) {
    var f = $('#frm_login');
    $('#productID', f).val(productID);  // 쇼핑카트 등록시 사용할 상품 번호를 저장.
    
    // console.log('-> productID: ' + $('#productID', f).val()); 
    
    // console.log('-> id:' + '${sessionScope.id}');
    if ('${sessionScope.id}' != '' || $('#login_yn').val() == 'YES') {  // 로그인이 되어 있다면
        carts_ajax_post();  // 쇼핑카트에 바로 상품을 담음
    } else { // 로그인 안된 경우
        $('#div_login').show(); // 로그인폼 출력
    }

  }

  <%-- 쇼핑카트 상품 등록 --%>
  function carts_ajax_post() {
    var f = $('#frm_login');
    var productID = $('#productID', f).val();  // 쇼핑카트 등록시 사용할 상품 번호.
    
    var params = "";
    // params = $('#frm_login').serialize(); // 직렬화, 폼의 데이터를 키와 값의 구조로 조합
    params += 'productID=' + productID;
    params += '&${ _csrf.parameterName }=${ _csrf.token }';
    // console.log('-> cart_ajax_post: ' + params);
    // return;
    
    $.ajax(
      {
        url: '/carts/create.do',
        type: 'post',  // get, post
        cache: false, // 응답 결과 임시 저장 취소
        async: true,  // true: 비동기 통신
        dataType: 'json', // 응답 형식: json, html, xml...
        data: params,      // 데이터
        success: function(rdata) { // 응답이 온경우
          var str = '';
          console.log('-> carts_ajax_post cnt: ' + rdata.cnt);  // 1: 쇼핑카트 등록 성공
          
          if (rdata.cnt == 1) {
            var sw = confirm('선택한 상품이 장바구니에 담겼습니다.\n장바구니로 이동하시겠습니까?');
            if (sw == true) {
              // 쇼핑카트로 이동
              location.href='/carts/list_by_memberno.do';
            }           
          } else {
            alert('선택한 상품을 장바구니에 담지못했습니다.<br>잠시후 다시 시도해주세요.');
          }
        },
        // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
        error: function(request, status, error) { // callback 함수
          console.log(error);
        }
      }
    );  //  $.ajax END

  }

  
</script>

</head>
<body>

  <div class='title_line'>전통주 상품 목록</div>
  
  <aside class="aside_left">
  <c:forEach var="categoryVO" items="${list_5 }">
    <c:set var="categoryGrpID" value="${categoryVO.grpID}" />
    <c:set var="categoryName" value="${categoryVO.categoryName}" />
    <c:if test="${categoryGrpID eq 5}">
      <a href="/products/list_by_categoryID.do?categoryID=${categoryVO.categoryID}&now_page=1">${categoryName} | </a>
    </c:if>
  </c:forEach>
</aside>
  
  <aside class="aside_right">
    <a href="./list_all_5_grid.do&now_page=${param.now_page}&word=${param.word }">갤러리형</a>
    <a>|</a>  
    <a href="javascript:location.reload();">새로고침</a>
  </aside>
 <DIV style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_all_5.do'>
      <input type='text' name='word' id='word' value='${param.word }' style='width: 20%;'>
      <button type='submit'>검색</button>
      <c:if test="${param.word.length() > 0 }">
        <button type='button' 
                     onclick="location.href='./list_all_5.do?&word='">검색 취소</button>  
      </c:if>    
    </form>
  </DIV>
  <div class="menu_line"></div> 
  <%-- ******************** Ajax 기반 로그인 폼 시작 ******************** --%>
  <DIV id='div_login' style='display: none;'>
    <div style='width: 80%; margin: 0px 0px 0px 40%;'>
        <FORM name='frm_login' id='frm_login' method='POST' action='/member/login_ajax.do' class="form-horizontal">
          <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }">
          <input type="hidden" name="productID" id="productID" value="productID">
    
          <div class="form-group">
            <label class="col-md-4 control-label" style='font-size: 0.8em;'>아이디</label>    
            <div class="col-md-8">
              <input type='text' class="form-control" name='id' id='id' 
                         value='${ck_id }' required="required" 
                         style='width: 40%;' placeholder="아이디" autofocus="autofocus">
              <Label>   
                <input type='checkbox' name='id_save' value='Y' 
                          ${ck_id_save == 'Y' ? "checked='checked'" : "" }> 저장
              </Label>                   
            </div>
       
          </div>   
       
          <div class="form-group">
            <label class="col-md-4 control-label" style='font-size: 0.8em;'>패스워드</label>    
            <div class="col-md-8">
              <input type='password' class="form-control" name='passwd' id='passwd' 
                        value='${ck_passwd }' required="required" style='width: 40%;' placeholder="패스워드">
              <Label>
                <input type='checkbox' name='passwd_save' value='Y' 
                          ${ck_passwd_save == 'Y' ? "checked='checked'" : "" }> 저장
              </Label>
            </div>
          </div>   
        </FORM>
    </div>
    <div style='text-align: center; margin: 10px auto;'>
      <button type="submit" id='btn_login' class="btn btn-info">로그인</button>
      <button type='button' onclick="location.href='../member/create.do'" class="btn btn-info">회원가입</button>
      <button type='button' id='btn_loadDefault' class="btn btn-info">테스트 계정</button>
      <button type='button' id='btn_cancel' class="btn btn-info" onclick="$('#div_login').hide();">취소</button>
    </div>
  
  </DIV>
  
  <table class="table table-hover">
    <colgroup>
      <col style="width: 10%;"></col>
      <col style="width: 80%;"></col>
      <col style="width: 10%;"></col>
    </colgroup>
    <thead>
      <tr>
        <th style='text-align: center;'>상품 사진</th>
        <th style='text-align: center;'>상품명 / 상품 설명</th>
        <th style='text-align: center;'>가격 / 구매</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="productsVO" items="${list }" varStatus="status">
        <c:set var="productID" value="${productsVO.productID }" />
        <c:set var="categoryID" value="${productsVO.categoryID }" />
        <c:set var="pName" value="${productsVO.pName }" />
        <c:set var="description" value="${productsVO.description }" />
        <c:set var="recom" value="${productsVO.recom }" />
        <c:set var="imageFile" value="${productsVO.imageFile }" />
        <c:set var="thumb" value="${productsVO.thumb }" />
        <c:set var="price" value="${productsVO.price }" />
        
        <tr> 
          <td style='vertical-align: middle; text-align: center;'>
            <c:choose>
              <c:when test="${thumb.endsWith('jpg') || thumb.endsWith('png') || thumb.endsWith('gif')}">
                <%-- /static/products/storage/ --%>
                <a href="./read.do?productID=${productID}&now_page=${param.now_page }"><IMG src="/products/storage/${thumb }" style="width: 120px; height: 80px;"></a> 
              </c:when>
              <c:otherwise> <!-- 기본 이미지 출력 -->
                <IMG src="/products/images/none1.png" style="width: 120px; height: 80px;">
              </c:otherwise>
            </c:choose>
          </td>  
          <td style='vertical-align: middle;'>
            <a href="./read.do?productID=${productID}&now_page=${param.now_page }&word=${param.word}"><strong>${pName}</strong> ${description}</a> 
          </td> 
          <td style='vertical-align: middle; text-align: center;'>
            <a><fmt:formatNumber value="${price}" pattern="#,###" /></a><br>
            <span><A id="recom_${status.count }" href="javascript:recom_ajax(${productID }, ${status.count })" class="recom_link">♥(${recom })</A></span>

            <%-- <span id="span_animation_${status.count }"></span> --%>
            <br>
            <button type='button' id='btn_carts' class="btn btn-info" style='margin-bottom: 2px;'
                        onclick="carts_ajax(${productID })">장바 구니</button><br>
            <button type='button' id='btn_ordering' class="btn btn-info" 
                        onclick="carts_ajax(${productID })">바로 구매</button>  
                                    
          </td>
          <td style='vertical-align: middle; text-align: center;'>
            <A href="./update_text.do?productID=${productID}&now_page=${param.now_page }"><img src='/products/images/update.png'></A>
            <A href="./delete.do?productID=${productID}&now_page=${param.now_page }&categoryID=${categoryID}"><img src='/products/images/delete.png'></A>
          </td>
        </tr>
      </c:forEach>
      
    </tbody>
      
  </table>
   <!-- 페이지 목록 출력 부분 시작 -->
  <DIV class='bottom_menu'>${paging }</DIV> <%-- 페이지 리스트 --%>
  <!-- 페이지 목록 출력 부분 종료 -->
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>
