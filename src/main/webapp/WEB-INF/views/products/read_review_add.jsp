<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="categoryName" value="${categoryVO.categoryName }" />

<c:set var="categoryID" value="${productsVO.categoryID }" />
<c:set var="productID" value="${productsVO.productID }" />
<c:set var="price" value="${productsVO.price}" />
<c:set var="origin" value="${productsVO.origin }" />
<c:set var="volume" value="${productsVO.volume }" />
<c:set var="alcoholContent" value="${productsVO.alcoholContent }" />
<c:set var="imageFileSaved" value="${productsVO.imageFileSaved }" />
<c:set var="pName" value="${productsVO.pName }" />
<c:set var="thumb" value="${productsVO.thumb }" />
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
<title>술기운</title>
<link rel="shortcut icon" href="/images/sulic-resize36.png" />
<link href="/css/style.css" rel="Stylesheet" type="text/css">
<!-- 추가 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript">
$(function() {
    var favform= $('#favform');
    $('#btn_login').on('click', login_ajax);
    $('#btn_loadDefault').on('click', loadDefault);
    $('#btn_favproduct', favform).on('click', favproduct_ajax);
  });
  
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
          //console.log('-> login cnt: ' + rdata.cnt);  // 1: 로그인 성공
          
          if (rdata.cnt == 1) {
            // 관심목록에 insert 처리 Ajax 호출
            $('#div_login').hide();
            // alert('로그인 성공');
            $('#login_yn').val('YES'); // 로그인 성공 기록
            location.reload();
            productID_ajax(); // 관심목록에 insert 처리 Ajax 호출 
            
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

  
  <%-- 찜한 목록에 상품 추가 --%>
  function favproduct_ajax() {
    var favform= $('#favform');
    var productID = $('#productID', favform).val();
    var memberno= $('#memberno', favform).val();
    
    console.log('-> productID: ' + productID); 

  //이미 관심상품인지 확인하는 AJAX 요청
    $.ajax({
      url: '/favproduct/check.do',
      type: 'GET',
      data: { productID: productID, memberno: memberno },
      dataType: 'json',
      success: function(response) {
        if (response.error){
		  // memberno가 없을때 로그인 폼 표시
          $('#div_login').show();
          }
        else if (response.isFavorite) {
          // 이미 관심상품인 경우
          alert('이미 관심상품 목록에 있습니다.');
        } else {
          // 관심상품에 추가
          if ('${sessionScope.id}' != '' || $('#login_yn').val() == 'YES') {
            productID_ajax_post();  // 실제로 관심상품에 상품을 추가
          } else {
            $('#div_login').show(); // 로그인폼 출력
          }
        }
      },
      error: function(error) {
        console.error('에러 발생:', error);
      }
    });
  }

<%-- 찜한 목록에 상품 등록 --%>
function productID_ajax_post() {
  var params = "";
  params = $('#favform').serialize(); // 직렬화, 폼의 데이터를 키와 값의 구조로 조합
  console.log('-> params: '+params)
  $.ajax(
    {
      url: '../favproduct/create.do',
      type: 'post',  // get, post
      cache: false, // 응답 결과 임시 저장 취소
      async: true,  // true: 비동기 통신
      dataType: 'json', // 응답 형식: json, html, xml...
      data: params,      // 데이터
      beforeSend: function() {
          console.log('Sending data:', params);
      },
      success: function(rdata) { // 응답이 온경우
        var str = '';
        // console.log('-> cart_ajax_post cnt: ' + rdata.cnt);  // 1: 쇼핑카트 등록 성공
        
        if (rdata.favID == 1) {
          var sw = confirm('선택한 상품이 관심상품에 담겼습니다.\n관심상품으로 이동하시겠습니까?');
          if (sw == true) {
            // 관심상품으로 이동
            location.href='/favproduct/list_by_memberno.do';
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
                  msg += "<h4>"+row.retitle+ "</h4>" + "  (평점 : " + row.rating + ")" + "<br>";
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
    <!--  <form name='favform' id='favform' >
      <input type='hidden' name='productID' id='productID' value='${productID}'>
      <input type='hidden' name='memberno' id='memberno' value='${sessionScope.memberno}'>
      
      <DIV style='text-align: center; text-decoration: none;'>      
        <button type='button' id='btn_favproduct' class="btn btn-outline-danger" 
        style="padding: 2px 8px 3px 8px; margin: 0px 0px 2px 0px; 
        width: 100px; height: 50px; font-size: 18px; font-weight: bold; ">
        찜하기</button><br>
    </DIV>
  </form> -->
    
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


  <%-- ******************** Ajax 기반 로그인 폼 시작 ******************** --%>
  <DIV id='div_login' style='display: none;'>
    <div style='width: 80%; margin: 0px 0px 0px 40%;'>
        <FORM name='frm_login' id='frm_login' method='POST' action='/member/login_ajax.do' class="form-horizontal">
          <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }">
          <input type="hidden" name="productID" id="productID" value="productID">
          <input type='hidden' name='memberno' id='memberno' value='${sessionScope.memberno}'>
    
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
  <%-- ******************** Ajax 기반 로그인 폼 종료 ******************** --%>
  
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

          <span style="font-size: 1.5em; font-weight: bold;">${pName }</span><br><br>
          ${description }
          <br><br>
          용량 : ${volume } 
          <br>
          알코올 도수 : ${alcoholContent }
          <br>
          가격 : ${price }
        </DIV>
      </li>        
    </ul>  
  </fieldset>
  <div class="alert alert-warning alert-dismissible fade show" id="loginAlert" style="display: none;">
    <button type="button" class="btn-close" id = "warning_close" data-bs-dismiss="alert"></button>
    <strong>로그인 필요 기능</strong>  회원 로그인 후에 좋아요가 가능합니다.
  </div>
  	<DIV style='text-align: center; text-decoration: none;'>               
  		<form name='likeform' id='likeform' method='post' action='/like/like_check.do' style="display: inline-block; margin-right: 10px;">
  		<input type='hidden' name='productid' id='productid' value='${productID }'>
        <input type='hidden' name='memberno' id='memberno' value='${sessionScope.memberno}'>
        <input type='hidden' name='word ' id='word ' value='${word }'>
        <input type='hidden' name='categoryID' id='categoryID' value='${categoryID }'>
              
          <button type='submit' name='likebtn' id='likebtn' class='btn btn-outline-danger' 
          style="padding: 2px 8px 3px 8px; margin: 0px 0px 2px 0px; 
          width: 100px; height: 50px; font-size: 18px; font-weight: bold; ">
          ♥(${recom })</button>
        </form>
    	<form name='favform' id='favform' style="display: inline-block;">
    	<input type='hidden' name='productID' id='productID' value='${productID}'>
    	<input type='hidden' name='memberno' id='memberno' value='${sessionScope.memberno}'>      
    	<button type='button' id='btn_favproduct' class="btn btn-outline-danger" 
    	style="padding: 2px 8px 3px 8px; margin: 0px 0px 2px 0px; 
          width: 100px; height: 50px; font-size: 18px; font-weight: bold;">찜하기</button><br>
    	</form>
    </DIV>
    
  
  
  
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

