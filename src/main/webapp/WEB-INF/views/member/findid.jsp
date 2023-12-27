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

<script type="text/javascript">
  window.onload = ()=> {
    
    document.getElementById('btn_loadDefault').addEventListener('click', () => {
      document.getElementById('name').value = '회원1';
      document.getElementById('tel').value = '01099887766';
    });
  }
    
</script> 
<style>
    /* 로딩 GIF를 감싸는 부모 요소의 스타일 */
    #loadingGif {
        position: fixed; /* 페이지 스크롤에 영향을 받지 않도록 고정 */
        top: 50%; /* 화면 상단으로부터 50% 위치 */
        left: 50%; /* 화면 좌측으로부터 50% 위치 */
        transform: translate(-50%, -50%); /* 이미지를 중앙으로 이동 */
        display: none;
    }

    /* 로딩 GIF 이미지의 스타일 */
    #loadingGif img {
        display: block; /* 이미지를 블록 요소로 설정하여 가운데 정렬 적용 */
        margin: 0 auto; /* 이미지를 수평 중앙에 정렬 */
    }
</style>
</head> 
 
<body>
<c:import url="/menu/top.do" />
 
  <DIV class='title_line'>아이디 찾기</DIV>

  <DIV class='content_body'> 
    <DIV style='width: 40%; margin: 0px auto;'>
      <form name='frm' method='POST' action='./findid.do'>
      
      <!-- 로딩 GIF -->
        <div id="loadingGif" style="display: none;">
            <img src="/member/images/Spinner-2.gif" alt="로딩 중...">
        </div>
      
        <div class="form-group">
          <label>이름</label>    
          <input type='text' class="form-control" name='name' id='name' 
                    value='' required="required" style='width: 80%;' placeholder="이름">
        </div> 
      
        <div class="form-group">
          <label>전화 번호</label>    
          <input type='text' class="form-control" name='tel' id='tel' 
                    value='' required="required" 
                    style='width: 80%;' placeholder="전화번호" autofocus="autofocus">
        </div>   
     
          
     
        <div class="bottom_menu">
          <button type='button' id='btn_loadDefault' class="btn btn-secondary btn-sm">회원 테스트 계정</button>
          <button type="submit" id = "sm_button" class="btn btn-secondary btn-sm">입력</button>
        </div>   
        <script>
            document.getElementById("sm_button").addEventListener("click", function() {
                // 버튼 클릭 시 로딩 GIF를 보이도록 설정
                document.getElementById("loadingGif").style.display = "block";
            });
       </script>
        
      </form>
    </DIV>
  </DIV> <%-- <DIV class='content_body'> END --%>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>