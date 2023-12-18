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
<c:set var="recom" value="${productsVO.recom }" />
 <c:import url="/menu/top.do" />
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Resort world</title>
<link rel="shortcut icon" href="/images/whisky.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
<!-- 추가 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript">
  let review_list;
  $(function(){    
    // ---------------------------------------- 댓글 관련 시작 ----------------------------------------
    var frm_review = $('#frm_review');
    $('#recontent', frm_review).on('click', check_login);  // 댓글 작성시 로그인 여부 확인
    //$('#btn_create', frm_review).on('click', review_create);  // 댓글 작성시 로그인 여부 확인

     $('#btn_create').on('click', function() {
        var retitleValue = $('#retitle').val();
        var recontentValue = $('#recontent').val();
        var rating = $('#rating').val();
        
        if (retitleValue.trim() === '') {
        	var modal = new bootstrap.Modal(document.getElementById('modal_panel')); // 모달 객체 생성
            $('#modal_content').html("제목을 입력 해야 등록 할 수 있습니다."); // 내용
            modal.show(); // 모달 보이기
         // 첫 번째 버튼에 대한 닫기 기능 추가
            $('.btn-close').on('click', function() {
                modal.hide(); // 모달 닫기
            });

            // 두 번째 버튼에 대한 닫기 기능 추가
            $('.btn.btn-danger').on('click', function() {
                modal.hide(); // 모달 닫기
            });
            return; // 함수 종료
        } 

        if (recontentValue.trim() === '') {
        	var modal = new bootstrap.Modal(document.getElementById('modal_panel')); // 모달 객체 생성
            $('#modal_content').html("내용을 입력 해야 등록 할 수 있습니다."); // 내용
            modal.show(); // 모달 보이기
            // 첫 번째 버튼에 대한 닫기 기능 추가
            $('.btn-close').on('click', function() {
                modal.hide(); // 모달 닫기
            });

            // 두 번째 버튼에 대한 닫기 기능 추가
            $('.btn.btn-danger').on('click', function() {
                modal.hide(); // 모달 닫기
            });
            return; // 함수 종료
        } 
        // 여기에 원래의 review_create 함수 호출 또는 로직 추가
        review_create();
    });
    list_by_productid_join(); // 댓글 목록

    $('#btn_add').on('click', list_by_productid_join_add);  // [더보기] 버튼

    
    // ---------------------------------------- 댓글 관련 종료 ----------------------------------------
    
  });
      // 댓글 작성시 로그인 여부 확인
      function check_login() {      
        var frm_review = $('#frm_review');
        if ($('#memberno', frm_review).val().length == 0 ) { 
          var modal = new bootstrap.Modal(document.getElementById('modal_panel')); // 모달 객체 생성
          $('#modal_content').html("로그인해야 등록 할 수 있습니다."); // 내용
          modal.show(); // 모달 보이기

          // 첫 번째 버튼에 대한 닫기 기능 추가
          $('.btn-close').on('click', function() {
              modal.hide(); // 모달 닫기
          });

          // 두 번째 버튼에 대한 닫기 기능 추가
          $('.btn.btn-danger').on('click', function() {
              modal.hide(); // 모달 닫기
          });
          
          return false;  // 실행 종료
        }
      }

  // 댓글 등록
      function review_create() {
        var frm_review = $('#frm_review');
        
        if (check_login() !=false) { // 로그인 한 경우만 처리
          var params = frm_review.serialize(); // 직렬화: 키=값&키=값&...      
          
          if ($('#recontent', frm_review).val().length > 1000) {

        	  var modal = new bootstrap.Modal(document.getElementById('modal_panel')); // 모달 객체 생성
        	  $('#modal_content').html("댓글 내용은 1000자이상 입력 할 수 없습니다."); // 내용
              modal.show(); // 모달 보이기

              // 첫 번째 버튼에 대한 닫기 기능 추가
              $('.btn-close').on('click', function() {
                  modal.hide(); // 모달 닫기
              });

              // 두 번째 버튼에 대한 닫기 기능 추가
              $('.btn.btn-danger').on('click', function() {
                  modal.hide(); // 모달 닫기
              });
            return;  // 실행 종료
          }
    
          $.ajax({
            url: "../review/create.do", // action 대상 주소
            type: "post",          // get, post
            cache: false,          // 브러우저의 캐시영역 사용안함.
            async: true,           // true: 비동기
            dataType: "json",   // 응답 형식: json, xml, html...
            data: params,        // 서버로 전달하는 데이터
            success: function(rdata) { // 서버로부터 성공적으로 응답이 온경우
              // alert(rdata);
              var msg = ""; // 메시지 출력
              var tag = ""; // 글목록 생성 태그
              
              if (rdata.cnt > 0) {
            	var modal = new bootstrap.Modal(document.getElementById('modal_panel')); // 모달 객체 생성
                $('#modal_content').attr('class', 'alert alert-success'); // CSS 변경
                $('#modal_content').html("댓글 등록을 완료 하였습니다."); 
                modal.show();
                $('#recontent', frm_review).val('');

                // 첫 번째 버튼에 대한 닫기 기능 추가
                $('.btn-close').on('click', function() {
                    modal.hide(); // 모달 닫기
                    // 화면 새로고침
                    location.reload();
                });

                // 두 번째 버튼에 대한 닫기 기능 추가
                $('.btn.btn-danger').on('click', function() {
                    modal.hide(); // 모달 닫기
                    // 화면 새로고침
                    location.reload();
                });

                
                //list_by_contentsno_join(); // 댓글 목록을 새로 읽어옴
                
                $('#review_list').html(''); // 댓글 목록 패널 초기화, val(''): 안됨
                $("#review_list").attr("data-reviewpage", 1);  // 댓글이 새로 등록됨으로 1로 초기화
                
                //list_by_productid_join_add(); 
                // alert('댓글 목록 읽기 시작');
                // global_rdata = new Array(); // 댓글을 새로 등록했음으로 배열 초기화
                // global_rdata_cnt = 0; // 목록 출력 글수
                
                list_by_productid_join_add(); // 페이징 댓글
              } else {
                $('#modal_content').attr('class', 'alert alert-danger'); // CSS 변경
                msg = "댓글 등록에 실패했습니다.";
              }
              
              $('#modal_title').html('댓글 등록'); // 제목 
              $('#modal_content').html(msg);     // 내용
              $('#modal_panel').modal();           // 다이얼로그 출력
            },
            // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
            error: function(request, status, error) { // callback 함수
              var msg = 'ERROR request.status: '+request.status + '/ ' + error;
              console.log(msg); // Chrome에 출력
            }
          });
        }
  }

  
        function list_by_productid_join() {
            var params = 'productid=' + ${productID };
           
            $.ajax({
              url: "../review/list_by_productid_join.do", // action 대상 주소
              type: "get",           // get, post
              cache: false,          // 브러우저의 캐시영역 사용안함.
              async: true,           // true: 비동기
              dataType: "json",   // 응답 형식: json, xml, html...
              data: params,        // 서버로 전달하는 데이터
              success: function(rdata) { // 서버로부터 성공적으로 응답이 온경우
                // alert(rdata);
                var msg = '';
                
                $('#review_list').html(''); // 패널 초기화, val(''): 안됨

                 // -------------------- 전역 변수에 댓글 목록 추가 --------------------
                review_list = rdata.list;
                // -------------------- 전역 변수에 댓글 목록 추가 --------------------
                var last_index=1; 
                if (rdata.list.length >= 2 ) { // 글이 2건 이상이라면 2건만 출력
                  last_index = 2
                }

                 
                for (i=0; i < last_index; i++) {
                  var row = rdata.list[i];
                  
                  msg += "<DIV id='"+row.reviewno+"' style='border-bottom: solid 1px #EEEEEE; margin-bottom: 10px;'>";
                  msg += "<span style='font-weight: bold;'>" + row.id + "</span>";
                  msg += "  " + row.reviewdate;
                  
                  if ('${sessionScope.memberno}' == row.memberno) { // 글쓴이 일치여부 확인, 본인의 글만 삭제 가능함 ★
                    msg += " <A href='javascript:review_delete("+row.reviewno+")'><IMG src='/review/delete.png'></A>";
                  }
                  msg += " " + "<br>";
                  msg += "<h4>"+row.retitle+ "</h4>";
                  msg += row.recontent;
                  msg += "</DIV>";
                }
                // alert(msg);
                $('#review_list').append(msg);
              },
              // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
              error: function(request, status, error) { // callback 함수
                console.log(error);
              }
            });
        }
         
     // 댓글 삭제 레이어 출력
        function review_delete(reviewno) {
          // alert('reviewno: ' + reviewno);
          var frm_review_delete = $('#frm_review_delete');
          $('#reviewno', frm_review_delete).val(reviewno); 
          //$('#reviewno').val(reviewno); // 삭제할 댓글 번호 저장
          var delete_modal = new bootstrap.Modal(document.getElementById('modal_panel_delete')); // 모달 삭제폼 객체 생성
          delete_modal.show();
          $('.btn-close').on('click', function() {
        	  delete_modal.hide(); // 모달 닫기
          });

          // 두 번째 버튼에 대한 닫기 기능 추가
          $('.btn.btn-default').on('click', function() {
        	  delete_modal.hide(); // 모달 닫기
          });

      
          $('.btn.btn-danger').on('click', function() {
              delete_modal.hide(); 
          });

        }

     // 댓글 삭제 처리
        function review_delete_proc(reviewno) {
          // alert('reviewno: ' + reviewno);
          var params = $('#frm_review_delete').serialize();
          $.ajax({
            url: "../review/delete.do", // action 대상 주소
            type: "post",           // get, post
            cache: false,          // 브러우저의 캐시영역 사용안함.
            async: true,           // true: 비동기
            dataType: "json",   // 응답 형식: json, xml, html...
            data: params,        // 서버로 전달하는 데이터
            success: function(rdata) { // 서버로부터 성공적으로 응답이 온경우
              // alert(rdata);
              var msg = "";
              
              if (rdata.passwd_cnt ==1) { // 패스워드 일치
                if (rdata.delete_cnt == 1) { // 삭제 성공

                  $('#btn_frm_review_delete_close').trigger("click"); // 삭제폼 닫기, click 발생 
                  
                  $('#' + reviewno).remove(); // 태그 삭제
                    
                  return; // 함수 실행 종료
                } else {  // 삭제 실패
                  msg = " 댓글 삭제에 실패했습니다. <br>";
                  msg += " 다시한번 시도해주세요."
                }
              } else { // 패스워드 일치하지 않음.
                // alert('패스워드 불일치');
                // return;
                
                msg = "회원이 일치하지 않습니다.";
                $('#modal_panel_delete_msg').html(msg);

                $('#memberno', '#frm_review_delete').focus();  // frm_review_delete 폼의 passwd 태그로 focus 설정
                
              }
            },
            // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
            error: function(request, status, error) { // callback 함수
              console.log(error);
            }
          });
        }

        // // [더보기] 버튼 처리
        function list_by_productid_join_add() {
          // alert('list_by_contentsno_join_add called');
          
          let cnt_per_page = 2; // 2건씩 추가
          let reviewPage=parseInt($("#review_list").attr("data-reviewPage"))+cnt_per_page; // 2
          $("#review_list").attr("data-reviewPage", reviewPage); // 2
          
          var last_index=reviewPage + 2; // 4
          // alert('reviewPage: ' + reviewPage);
          
          var msg = '';
          for (i=reviewPage; i < last_index; i++) {
            var row = review_list[i];
            
            msg = "<DIV id='"+row.reviewno+"' style='border-bottom: solid 1px #EEEEEE; margin-bottom: 10px;'>";
            msg += "<span style='font-weight: bold;'>" + row.id + "</span>";
            msg += "  " + row.reviewdate;
            
            if ('${sessionScope.memberno}' == row.memberno) { // 글쓴이 일치여부 확인, 본인의 글만 삭제 가능함 ★
              msg += " <A href='javascript:review_delete("+row.reviewno+")'><IMG src='/review/delete.png'></A>";
            }
            msg += " " + "<br>";
            msg += "<h4>"+row.retitle+ "</h4>";
            msg += row.recontent;
            msg += "</DIV>";


            // alert('msg: ' + msg);
            $('#review_list').append(msg);
          }    
        }
       
      
</script>
<script>
  document.addEventListener('DOMContentLoaded', function() {
	  //추가
	  var loginAlert = document.getElementById('loginAlert');
      document.getElementById('likeform').onsubmit = function(event) {
      event.preventDefault(); // 기본 제출 동작 막기
      
      var memberNoValue = document.getElementById('memberno').value;
      if (memberNoValue.trim() === '') {     
        loginAlert.style.display = 'block'; // 알림 메시지 보이기
        alert('로그인이 필요합니다.');
        return false;
      }
      this.submit(); // 제출
    };
  });
</script>

</head> 
 
<body>

<!-- Modal 알림창 시작 -->
<div class="modal fade" id="modal_panel" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">        
        <h4 class="modal-title" id='modal_title'>댓글 등록</h4><!-- 제목 -->
        <button type="button" class="btn-close" data-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <p id='modal_content'></p>   <!-- 내용 -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div> <!-- Modal 알림창 종료 -->
   <!-- -------------------- 댓글 삭제폼 시작 -------------------- -->
<div class="modal fade" id="modal_panel_delete" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">댓글 삭제</h4><!-- 제목 -->
        <button type="button" class="btn-close" data-dismiss="modal"></button>        
      </div>
      
      <div class="modal-body">   
        <p id='modal_content'>정말 삭제 하시겠습니까?</p>
        <form name='frm_review_delete' id='frm_review_delete'>
          <input type='hidden' name='reviewno' id='reviewno' value=''>
          <input type='hidden' name='memberno' id='memberno' value='${sessionScope.memberno }'>

          <DIV id='modal_panel_delete_msg' style='color: #AA0000; font-size: 1.1em;'></DIV>
        </form>
      </div>
      <div class="modal-footer">
        <button type='button' class='btn btn-danger' onclick="review_delete_proc(frm_review_delete.reviewno.value);">
        삭제</button>
        <button type="button" class="btn btn-default" data-dismiss="modal" 
                     id='btn_frm_review_delete_close'>취소</button>         
      </div>   
    </div>
  </div>
</div>
<!-- -------------------- 댓글 삭제폼 종료 -------------------- -->

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
  <div class="alert alert-warning alert-dismissible fade show" id="loginAlert" style="display: none;">
    <button type="button" class="btn-close" id = "warning_close" data-bs-dismiss="alert"></button>
    <strong>로그인 필요 기능</strong>  회원 로그인 후에 좋아요가 가능합니다.
  </div>
  <form name='likeform' id='likeform' method='post' action='/like/like_check.do'>
        <input type='hidden' name='productid' id='productid' value='${productID }'>
        <input type='hidden' name='memberno' id='memberno' value='${sessionScope.memberno}'>
        <input type='hidden' name='word ' id='word ' value='${word }'>
        <input type='hidden' name='categoryID' id='categoryID' value='${categoryID }'>
              
        <DIV style='text-decoration: none;'>      
          <br>
          좋아요 
          <button type='submit' name='likebtn' id='likebtn' class='btn btn-secondary' style="padding: 2px 8px 3px 8px; margin: 0px 0px 2px 0px;">♥(${recom })</button>
        </DIV>
 </form>
  
  <!-- ------------------------------ 댓글 영역 시작 ------------------------------ -->

  <DIV style="display: flex; align-items: center; border: 1px solid #000; margin-left: 100px; margin-right: 100px;">
  
  <fieldset class="fieldset_basic">     
  <div class="container p-5 my-5 bg-dark text-white">
      <h2>리뷰</h2>
      </div>
      <FORM name='frm_review' id='frm_review' class="was-validated"> <%-- 댓글 등록 폼 --%>     
          <input type='hidden' name='productid' id='productid' value='${productID}'>
          <input type='hidden' name='memberno' id='memberno' value='${sessionScope.memberno}'>
          <input type='hidden' name='reuser' id='reuser' value='${sessionScope.reuser}'>       
           <div class="mb-3">
            <label for="title" class="form-label">제목</label>
            <input type='text' name='retitle' id='retitle'  class="form-control" placeholder="제목을 입력하세요." value='' required>   
            <div class="valid-feedback">제목 입력 완료</div>
            <div class="invalid-feedback">제목을 입력해주세요.</div>   
           </div>         
           <div class="mb-3" >
           <label for="title" class="form-label">평점</label>
	          <select class="form-select" name ='rating' id = 'rating' required>
	            <option disabled>평점 선택</option>
	            <option>1</option>
	            <option>2</option>
	            <option>3</option>
	            <option>4</option>
	            <option >5</option>
	            <option>6</option>
	            <option selected>7</option>
	            <option>8</option>
	            <option>9</option>
	            <option>10</option>
	          </select>      
	        <div class="valid-feedback">평점 입력 완료</div>
            <div class="invalid-feedback" id="ratingValidation">평점을 입력해주세요.</div>	         
          </div>
          <div class="mb-3">
          <label for="title" class="form-label">내용</label>
          <textarea name='recontent' id='recontent' style='width: 100%; height: 100px;' placeholder="댓글 작성, 로그인해야 등록 할 수 있습니다." required></textarea>    
            <div class="valid-feedback">내용 입력 완료</div>
            <div class="invalid-feedback">내용을 입력해주세요.</div>  
          </div>     
          <button type='button' id='btn_create' class="btn btn-outline-success" style='width: 40%; margin-left: 30%;'>리뷰 등록</button>
      </FORM>
      <HR>
      <HR>
      <DIV id='review_list' data-reviewpage='0'>  <%-- 댓글 목록 --%>
      
      </DIV>
      <DIV id='review_list_btn' style='border: solid 1px #EEEEEE; margin: 0px auto; width: 100%; background-color: #EEFFFF;'>
          <button id='btn_add' class="btn btn-info" style='width: 100%;'>더보기 ▽</button>
      </DIV>  
    </fieldset>
  </DIV>
  
  
  <!-- ------------------------------ 댓글 영역 종료 ------------------------------  -->

 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>

