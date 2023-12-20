<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=5.0, width=device-width" /> 
<title>술기운</title>
<link rel="shortcut icon" href="/images/sulic-resize36.png" />
<link href="/css/style.css" rel="Stylesheet" type="text/css">

<script type="text/javascript">
  window.onload = function() {
    document.querySelector('#id').addEventListener('keypress', (event) => {
    // document.getElementById('passwd').addEventListener('keypress', (event) => {
      if(event.key === 'Enter') {
        document.getElementById('btn_checkID').focus();
      }
    }); 

    document.querySelector('#passwd').addEventListener('keypress', (event) => {
    // document.getElementById('passwd').addEventListener('keypress', (event) => {
      if(event.key === 'Enter') {
        document.getElementById('passwd2').focus();
      }
    }); 

    document.querySelector('#passwd2').addEventListener('keypress', (event) => {
    // document.getElementById('passwd').addEventListener('keypress', (event) => {
      if(event.key === 'Enter') {
        document.getElementById('mname').focus();
      }
    }); 

    document.querySelector('#mname').addEventListener('keypress', (event) => {
    // document.getElementById('passwd').addEventListener('keypress', (event) => {
      if(event.key === 'Enter') {
        document.getElementById('tel').focus();
      }
    });
    
    document.querySelector('#tel').addEventListener('keypress', (event) => {
    // document.getElementById('passwd').addEventListener('keypress', (event) => {
      if(event.key === 'Enter') {
        document.getElementById('btn_DaumPostcode').focus();
      }
    }); 
    
    document.querySelector('#address2').addEventListener('keypress', (event) => {
    // document.getElementById('passwd').addEventListener('keypress', (event) => {
      if(event.key === 'Enter') {
        document.getElementById('btn_send').focus();
      }
    }); 
      
    
  }

  // jQuery ajax 요청
  function checkID() {
    let id = document.getElementById('id');
    let id_msg = document.getElementById('id_msg');

    if (id.value.trim().length == 0) {
      id_msg.innerHTML= 'ID가 누락됬습니다. ID 입력은 필수 입니다. ID(이메일)는 3자이상 권장합니다.';
      id_msg.classList.add('span_warning');    // class 적용
      id.focus();

      return false;  // 회원 가입 진행 중지
      
    } else {  // when ID is entered
      id_msg.classList.remove('span_warning'); // class 삭제

      params = 'id=' + id.value;
      // var params = $('#frm').serialize(); // 직렬화, 폼의 데이터를 키와 값의 구조로 조합
      // alert('params: ' + params);
  
      $.ajax({
        url: './checkID.do', // spring execute, http://localhost:9091/member/checkID.do?id=user1@gmail.com
        type: 'get',  // post
        cache: false, // 응답 결과 임시 저장 취소
        async: true,  // true: 비동기 통신
        dataType: 'json', // 응답 형식: json, html, xml...
        data: params,      // 데이터
        success: function(rdata) { // 서버로부터 성공적으로 응답이 온경우, 통신 성공: {"cnt":1}
          if (rdata.cnt > 0) { // 아이디 중복
            id_msg.innerHTML= '이미 사용중인 ID(이메일) 입니다. 다른 ID(이메일)을 지정해주세요.';
            id_msg.classList.add('span_warning');
            id.focus();
            
          } else { // 아이디 중복 안됨.
            id_msg.innerHTML= '사용 가능한 ID(이메일) 입니다.';
            id_msg.classList.add('span_info');
            document.getElementById('passwd').focus(); 
            // $.cookie('checkId', 'TRUE'); // Cookie 기록
          }
          
        },
        // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
        error: function(request, status, error) { // callback 함수
          console.log(error);
        }
      });
      
      // 처리중 출력
      id_msg.innerHTML="<img src='/member/images/ani04.gif' style='width: 6%;'>"; // static 기준
      
    }
  }

  function send() { // 회원 가입 처리
    let id = document.getElementById('id');
    let id_msg = document.getElementById('id_msg');

    if (id.value.trim().length == 0) {
      id_msg.innerHTML= 'ID가 누락됬습니다. ID 입력은 필수 입니다. ID(이메일)는 3자이상 권장합니다.';
      id_msg.classList.add('span_warning');    // class 적용
      id.focus();

      return false;  // 회원 가입 진행 중지
      
    }

    // 패스워드를 정상적으로 2번 입력했는지 확인
    let passwd = document.getElementById('passwd');
    let passwd2 = document.getElementById('passwd2');
    let passwd2_msg = document.getElementById('passwd2_msg');

    if (passwd.value != passwd2.value) {
      passwd2_msg.innerHTML= '입력된 패스워드가 일치하지 않습니다.';
      passwd2_msg.classList.add('span_warning');    // class 적용
      passwd.focus();  // 첫번째 패스워드로 focus 설정

      return false;  // 회원 가입 진행 중지
    }

    let mname = document.getElementById('mname');
    let mname_msg = document.getElementById('mname_msg');

    if (mname.value.length == 0) {
      mname_msg.innerHTML= '이름 입력은 필수입니다.';
      mname_msg.classList.add('span_warning');    // class 적용
      mname.focus();

      return false;  // 회원 가입 진행 중지
    }

    document.getElementById('frm').submit(); // required="required" 작동 안됨.
  }  
</script>
</head> 


<body>
<c:import url="/menu/top.do" />

  <DIV class='title_line'>회원 가입(*: 필수)</DIV>

  <ASIDE class="aside_right">
    <A href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span> 
    <A href='./create.do'>회원 가입</A>
    <span class='menu_divide' >│</span> 
    <A href='./list.do'>목록</A>
  </ASIDE> 

  <div class='menu_line'></div>
  
  <div style="width: 60%; margin: 0px auto ">
  <form name='frm' id='frm' method='POST' action='./create.do' class="">
  
    <div class="form-group">
      <label>아이디*:
        <input type='text' class="form-control form-control-sm" name='id' id='id' value='user1@gmail.com' required="required" placeholder="아이디" autofocus="autofocus">
      </label>
      <button type='button' id="btn_checkID" onclick="checkID()" class="btn btn-primary btn-sm">중복확인</button>
      <span id='id_msg'></span>
    </div>   
                
    <div class="form-group">
      <label>패스워드*: 
        <input type='password' class="form-control form-control-sm" name='passwd' id='passwd' value='1234' required="required" placeholder="패스워드">
      </label>
    </div>   

    <div class="form-group">
      <label>패스워드 확인*: 
        <input type='password' class="form-control form-control-sm" name='passwd2' id='passwd2' value='1234' required="required" placeholder="패스워드 확인">
      </label>
      <span id='passwd2_msg'></span>
    </div>   
    
    <div class="form-group">
      <label>성명*:
        <input type='text' class="form-control form-control-sm" name='mname' id='mname' value='하정우' required="required" placeholder="성명">
      </label>
      <span id='mname_msg'></span>
    </div>   

    <div class="form-group">
      <label>전화 번호:
        <input type='text' class="form-control form-control-sm" name='tel' id='tel' value='010-0000-0000' required="required" placeholder="전화번호">
      </label>
      예) 010-0000-0000
    </div>   

    <div class="form-group"> 
      <label>우편 번호:
        <input type='text' class="form-control form-control-sm" name='zipcode' id='zipcode' value='' placeholder="우편번호">
      </label>
      <button type="button" id="btn_DaumPostcode" onclick="DaumPostcode()" class="btn btn-primary btn-sm">우편번호 찾기</button>
    </div>  

    <div class="form-group">
      <label style="width: 100%;">주소:</label> <%-- label의 크기를 변경하여 주소를 많이 입력받는 패턴 --%>
      <input type='text' class="form-control form-control-sm" name='address1' id='address1' value='' placeholder="주소">
    </div>   

    <div class="form-group">
      <label style="width: 100%;">상세 주소:</label>
      <input type='text' class="form-control form-control-sm" name='address2' id='address2' value='' placeholder="상세 주소">
    </div>   

    <div>

<!-- ------------------------------ DAUM 우편번호 API 시작 ------------------------------ -->
<div id="wrap" style="display:none;border:1px solid;width:500px;height:300px;margin:5px 0;position:relative">
<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnFoldWrap" style="cursor:pointer;position:absolute;right:0px;top:-1px;z-index:1" onclick="foldDaumPostcode()" alt="접기 버튼">
</div>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    // 우편번호 찾기 찾기 화면을 넣을 element
    var element_wrap = document.getElementById('wrap');

    function foldDaumPostcode() {
        // iframe을 넣은 element를 안보이게 한다.
        element_wrap.style.display = 'none';
    }

    function DaumPostcode() {
        // 현재 scroll 위치를 저장해놓는다.
        var currentScroll = Math.max(document.body.scrollTop, document.documentElement.scrollTop);
        new daum.Postcode({
            oncomplete: function(data) {
                // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                /*
                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample3_extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("sample3_extraAddress").value = '';
                }
                */

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('zipcode').value = data.zonecode; // 우편번호
                document.getElementById("address1").value = addr;  // 주소

                document.getElementById("address2").innerHTML=""; // 상세 주소 지우기
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("address2").focus();  // 상세 주소로 포커스 이동

                // iframe을 넣은 element를 안보이게 한다.
                // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
                element_wrap.style.display = 'none';

                // 우편번호 찾기 화면이 보이기 이전으로 scroll 위치를 되돌린다.
                document.body.scrollTop = currentScroll;
            },
            // 우편번호 찾기 화면 크기가 조정되었을때 실행할 코드를 작성하는 부분. iframe을 넣은 element의 높이값을 조정한다.
            onresize : function(size) {
                element_wrap.style.height = size.height+'px';
            },
            width : '100%',
            height : '100%'
        }).embed(element_wrap);

        // iframe을 넣은 element를 보이게 한다.
        element_wrap.style.display = 'block';
    }
</script>
<!-- ------------------------------ DAUM 우편번호 API 종료 ------------------------------ -->

    </div>
    
    <div class="bottom_menu">
      <button type="button" id='btn_send' onclick="send()" class="btn btn-primary btn-sm">가입</button>
      <button type="button" onclick="history.back()" class="btn btn-primary btn-sm">취소</button>
    </div>   
  </form>
  </div>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>

</html>

