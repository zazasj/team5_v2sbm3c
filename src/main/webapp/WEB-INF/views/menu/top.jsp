<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/JavaScript" src="http://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>

<style>
  .top_menu_link:link{  /* 방문전 상태 */
    text-decoration: none; /* 밑줄 삭제 */
    color: #FFFFFF;
    font-weight: bold;
  }

  .top_menu_link:visited{  /* 방문후 상태 */
    text-decoration: none; /* 밑줄 삭제 */
    color: #FFFFFF;
    font-weight: bold;
  }

  .top_menu_link:hover{  /* A 태그에 마우스가 올라간 상태 */
    text-decoration: none; /* 밑줄 출력 */
    color: #FFFF00;
    font-size: 1.05em;
  }
</style> 

<div id="logo" style="display: flex; align-items: center;">
  <a href="/"><img src="/css/images/logo4.jpg"></a>
  <input type="text" placeholder="검색할 제품을 입력해주세요." style = "width : 40%; height : 50px;  margin-left: 350px; border: 5px solid black; font-size: 20px; font-weight: bold;" >
  <input type = "button"  class="btn btn-dark" value="검색"  style="width: 100px; height: 50px; font-size: 18px; font-weight: bold;  ">
</div>

<div class='container_main'>

  <nav class="navbar navbar-expand-md navbar-dark bg-dark">
      <a class="navbar-brand" style='padding-left: 20px;'></a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle Navigation">
        <span class="navbar-toggler-icon"></span>
      </button>    

      <div class="collapse navbar-collapse" id="navbarCollapse">
          <ul class="navbar-nav mr-auto">
            <%-- 게시판 목록 출력 --%>
            <c:forEach var="cateGroupVO" items="${list_top}">
            <c:set var="grpID" value="${cateGroupVO.grpID}" />
            <c:set var="gname" value="${cateGroupVO.gname}" />
                              
            <li class="nav-item">
              <c:choose>
                <c:when test="${grpID ge 1 and grpID le 5}">
                  <a class="nav-link top_menu_link" href="/products/list_all_${grpID}.do">${gname}</a>
                </c:when>
              </c:choose>
            </li>
            <li class = "nav-item dropdown">
              <c:choose>
                <c:when test="${grpID eq 6}">
                  <a class="nav-link top_menu_link dropdown-toggle" data-bs-toggle="dropdown" href="#">${gname}</a>
                  <div class="dropdown-menu">
                    <c:forEach var="categoryVO" items="${list_top2}">
                      <c:set var="categoryGrpID" value="${categoryVO.grpID}" />
                      <c:set var="categoryName" value="${categoryVO.categoryName}" />
                      <c:set var="categoryID" value="${categoryVO.categoryID}" />
                      <c:if test="${categoryGrpID eq 6}">
                        <a class="dropdown-item" href="/products/list_by_categoryID.do?categoryID=${categoryID}&now_page=1">${categoryName}</a>
                      </c:if>
                    </c:forEach>
                  </div>
                </c:when>
              </c:choose>
            </li>
            </c:forEach>
        
            <li class="nav-item"> <%-- 서브 메뉴가 없는 독립메뉴 --%>
              <a class="nav-link top_menu_link" href="/event/list_by_eventno.do">이벤트</a>
            </li>
            <li class="nav-item"> <%-- 서브 메뉴가 없는 독립메뉴 --%>
              <a class="nav-link top_menu_link" href="/chatting/list_all.do">챗봇 기록</a>
            </li>
        
            <li class="nav-item dropdown"> <%-- 회원 서브 메뉴 --%>
              <a class="nav-link top_menu_link dropdown-toggle" data-bs-toggle="dropdown" href="#">회원</a>
              <div class="dropdown-menu">
                <c:choose>
                  <c:when test="${sessionScope.id == null }">
                    <a class="dropdown-item" href="/member/create.do">회원 가입</a>
                    <a class="dropdown-item" href="/member/findid.do">아이디 찾기</a>
                    <a class="dropdown-item" href="/member/findpwd.do">비밀번호 찾기</a>
                  </c:when>
                  <c:otherwise>
                    <a class="dropdown-item" href="http://localhost:8001/ais/recommend_form/?memberno=${sessionScope.memberno }">관심분야 등록하고 추천받기</a>
                    <a class="dropdown-item" href="/orderdetails/list_by_memberno.do">회원 주문 상세 목록</a>
                    <a class="dropdown-item" href="/member/read.do">가입 정보</a>
                    <a class="dropdown-item" href="/carts/list_by_memberno.do">장바구니</a>
                    <a class="dropdown-item" href="#">주문 결제</a>
                    <a class="dropdown-item" href="/member/passwd_update.do">비밀번호 변경</a>
                    <a class="dropdown-item" href="/member/read.do">회원 정보 수정</a>
                    <a class="dropdown-item" href="/review/list_join.do">리뷰 내역</a>
                    <a class="dropdown-item" href="/member/delete.do">회원 탈퇴</a>
                  </c:otherwise>
                </c:choose>
              </div>
            </li>
          
            <c:choose>
              <c:when test="${sessionScope.admin_id == null }">
                <!-- <li class="nav-item">
                  <a class="nav-link top_menu_link" href="/admin/login.do">관리자 로그인</a>
                </li> -->
              </c:when>
              <c:otherwise>
                <li class="nav-item"> <%-- 서브 메뉴가 없는 독립메뉴 --%>
                    <a class="nav-link top_menu_link" href="/cateGroup/list_all.do">전체 글 목록</a>
                </li>
              
                <li class="nav-item dropdown"> <%-- 관리자 서브 메뉴 --%>
                  <a class="nav-link top_menu_link dropdown-toggle" data-bs-toggle="dropdown" href="#">관리자</a>
                  <div class="dropdown-menu">
                    <a class="dropdown-item" href='/cateGroup/list_all.do'>카테고리그룹 전체 목록</a>
                    <a class="dropdown-item" href='/member/list.do'>회원 목록</a>
                    <a class="dropdown-item" href='/review/list.do'>리뷰 전체 목록</a>
                    <a class="dropdown-item" href='/supplier/list_all_adminno.do'>공급 업체</a>                  
                    <a class="dropdown-item" href='/admin/logout.do'>관리자 ${sessionScope.admin_id } 로그아웃</a>
                  </div>
                </li>
              </c:otherwise>
            </c:choose>
            
            <li class="nav-item"> <%-- 서브 메뉴가 없는 독립메뉴 --%>
              <c:choose>
                  <c:when test="${sessionScope.id == null}">
                      <a class="nav-link top_menu_link" href="/member/login.do">로그인</a>
                  </c:when>
                  <c:otherwise>
                      <a class="nav-link top_menu_link" href='/member/logout.do'>${sessionScope.id } 로그아웃</a>
                  </c:otherwise>
              </c:choose>
            </li>  
          </ul>
      </div>    
  </nav>
    

  <!-- Offcanvas Sidebar -->
<div class="offcanvas offcanvas-start" id="demo">
  <div class="offcanvas-header">
    <h1 class="offcanvas-title">Event</h1>
    <a class="btn btn-secondary" href='/event/list_by_eventno.do' style="color: white; margin-top: 10px;">이벤트 목록</a>
    <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas"></button>
  </div>
  <hr>
  <div class="offcanvas-body">
    <a href="/event/read.do?eventno=1">
    <img src="/images/whiskyresize.jpg" style="width: 350px; height: 300px;">
</a>

<a href="/event/read.do?eventno=2">
    <img src="/images/wineresize.jpg" style="width: 350px; height: 300px; margin-top: 20px;">
</a>

    
  </div>
</div>

 <div id="parent" style="text-align: center;">
  <div id="" style="border: 0pt none; display: inline-block; width: 779px; height: 150px;">
    <img id="myImage" src="/images/irish-whiskey_1280.jpg" class="rounded" alt="Cinque Terre" style="width: 100%; height: auto; margin-top: 20px;">
  </div>
 </div>

<script>
  document.getElementById('myImage').addEventListener('click', function() {
    var offcanvas = new bootstrap.Offcanvas(document.querySelector('#demo'));
    offcanvas.toggle();
  });
</script>

<script>
  var images = [
    "/images/irish-whiskey_1280.jpg",
    "/images/red-wine_1280.jpg"
    // ... (원하는 이미지 경로들 추가)
  ];

  var currentImageIndex = 0;
  var imageElement = document.getElementById('myImage');

  function changeImage() {
    imageElement.src = images[currentImageIndex];
    currentImageIndex = (currentImageIndex + 1) % images.length;
  }

  setInterval(changeImage, 8000); // 10초마다 이미지 변경
</script>

  <div class='content_body'> <!--  내용 시작 -->