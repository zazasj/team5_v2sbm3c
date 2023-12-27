<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page language="java" %>

<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>술기운</title>
<link rel="shortcut icon" href="/images/sulic-resize36.png" />
<link href="/css/style.css" rel="Stylesheet" type="text/css">
<script type="text/JavaScript" src="http://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>


</head> 
<body>
<c:import url="/menu/top.do" />

<div class='title_line'>알림</div>

<div class='message'>
  <fieldset class='fieldset_basic'>
    <ul>
      <c:choose>
        <c:when test="${param.code == 'create_success'}"> <%-- Java if --%>
          <li class='li_none'>
            <span class="span_success">${param.mname }님(${param.id }) 회원 가입을 축하합니다.</span>
          </li>  
          <li class='li_none'>
            <button type='button' 
                         onclick="location.href='./login.do?id=${param.id}'"
                         class="btn btn-primary btn-sm">로그인</button>
          </li> 
        </c:when>
        
        <c:when test="${param.code == 'deletemember_msg'}"> <%-- Java if --%>
           <li class='li_none'>
            <span class="span_fail">삭제된 회원의 아이디입니다.</span>
            </li>
            <li class='li_none'>
            <button type='button' 
                         onclick="location.href='/'"
                         class="btn btn-primary btn-sm">시작화면으로</button>
          </li>                                                                             
        </c:when>
        
        <c:when test="${param.code == 'create_fail'}"> <%-- Java if --%>
          <li class='li_none'>
            <span class="span_fail">회원 가입에 실패했습니다. 다시 시도해주세요.</span>
          </li>                                                                      
        </c:when>

        <c:when test="${param.code == 'duplicate_fail'}"> <%-- Java if --%>
          <li class='li_none'>
            <span class="span_fail">ID가 이미 사용중입니다. 회원 가입에 실패했습니다. <br>다시 시도해주세요.</span>
          </li>                                                                      
        </c:when>
        
        <c:when test="${param.code == 'update_success'}"> <%-- Java if --%>
          <li class='li_none'>
            <span class="span_success">${param.mname }님(아이디: ${param.id }) 회원 정보를 변경했습니다.</span>
          </li>
          <li class='li_none'>
            <button type='button' 
                         onclick="location.href='/'"
                         class="btn btn-primary btn-sm">시작 화면</button>
            <button type='button' 
                         onclick="location.href='/member/list.do'"
                         class="btn btn-primary btn-sm">회원 목록</button>                   
          </li>                                                                       
        </c:when>
                
        <c:when test="${param.code == 'update_fail'}"> <%-- Java if --%>
          <li class='li_none'>
            <span class="span_fail">${param.mname }님(${param.id }) 회원 정보 수정에 실패했습니다.</span>
          </li>                                                                      
        </c:when>
        
        <c:when test="${param.code == 'delete_success'}"> <%-- Java if --%>
          <li class='li_none'>
            <span class="span_success"> 회원 정보 삭제에 성공했습니다.</span>
          </li>   
          <li class='li_none'>
            <button type='button' 
                         onclick="location.href='/member/logout.do'"
                         class="btn btn-primary btn-sm">확인</button>
          </li>                                                                     
        </c:when>    
        
        <c:when test="${param.code == 'delete_success_admin'}"> <%-- Java if --%>
          <li class='li_none'>
            <span class="span_success"> 회원 정보 삭제에 성공했습니다.</span>
          </li>   
          <li class='li_none'>
          <button type='button' 
                         onclick="location.href='/'"
                         class="btn btn-primary btn-sm">시작 화면</button>
            <button type='button' 
                         onclick="location.href='/member/list.do'"
                         class="btn btn-primary btn-sm">확인</button>
          </li>                                                                     
        </c:when> 
            
        <c:when test="${code == 'delete_fail'}"> <%-- Java if --%>
          <li class='li_none'>
            <span class="span_fail"> 회원 정보 삭제에 실패했습니다.</span>
          </li>                                                                      
        </c:when> 
        
        <c:when test="${param.code == 'passwd_update_success'}"> <%-- Java if --%>
          <li class='li_none'>
            <span class="span_success">${param.mname }님(${param.id }) 패스워드를 변경했습니다.</span>
          </li>   
          <li class='li_none'>
            <button type='button' 
                         onclick="location.href='/'"
                         class="btn btn-primary btn-sm">확인</button>
          </li>                                                                     
        </c:when>   
        
        <c:when test="${param.code == 'passwd_update_fail'}"> <%-- Java if --%>
          <li class='li_none'>
            <span class="span_fail">${param.mname }님(${param.id }) 패스워드 변경에 실패했습니다.</span>
          </li>                                                                      
        </c:when>  

        <c:when test="${param.code == 'passwd_fail'}"> <%-- Java if --%>
          <li class='li_none'>
            <span class="span_fail">패스워드가 일치하지 않습니다.</span>
          </li>                                                                      
        </c:when>  
        
        <c:when test="${param.code == 'findid_success'}"> <%-- Java if --%>
          <li class='li_none'>
            <span class="span_success">${param.userid }에 메일을 전송 하였습니다.</span>
          </li>
          <li class='li_none'>
          <input type='text' class="form-control" name='mailcode' id='mailcode' 
       value='' required="required"  maxlength="6" style='width: 80%; margin-bottom: 20px;' placeholder="인증번호">
          <button type='button' class="btn btn-primary btn-sm" onclick="checkAndRedirect()">확인</button>

	       <script>
				function checkAndRedirect() {
				    var userInputCode = document.getElementById('mailcode').value; // 입력된 값 가져오기
				
				    // 세션에 저장된 verify_code 값을 가져와서 비교
				    var verifyCode = '<%= session.getAttribute("verify_code") %>'; // JSP 코드로 세션값 가져오기
				   
				    // 입력된 코드와 세션의 코드를 비교하여 페이지 이동
				    if (userInputCode == verifyCode) {
				        window.location.href = './msg2.do'; // 일치할 경우 페이지 이동
				    } else {
				        alert('인증번호가 일치하지 않습니다.'); // 불일치할 경우 알림창 표시
				    }
				}
		   </script>              
          </li>                                                                      
        </c:when>  
        
        <c:when test="${param.code == 'findid_fail'}"> <%-- Java if --%>
          <li class='li_none'>
            <span class="span_fail">이름 혹은 전화번호가 일치하지 않습니다.</span>
          </li>                                                                      
        </c:when>  
        
        <c:when test="${param.code == 'findpwd_success'}"> <%-- Java if --%>
          <li class='li_none'>
            <span class="span_success">${param.userid }에 새로운 임시 비밀번호를 전송 하였습니다. 해당 비밀번호를 통해 로그인하세요.</span>
          </li>
          <li class='li_none'>
            <button type='button' 
                         onclick="location.href='/'"
                         class="btn btn-primary btn-sm">시작 화면</button>
            <button type='button' 
                         onclick="location.href='/member/login.do'"
                         class="btn btn-primary btn-sm">로그인</button>                   
          </li>                                                                      
        </c:when>  
        
        <c:when test="${param.code == 'findpwd_fail'}"> <%-- Java if --%>
          <li class='li_none'>
            <span class="span_fail">ID 혹은 성명이 일치하지 않습니다.</span>
          </li>                                                                      
        </c:when>  
                
        <c:otherwise>
          <li class='li_none_left'>
            <span class="span_fail">알 수 없는 에러로 작업에 실패했습니다.</span>
          </li>
          <li class='li_none_left'>
            <span class="span_fail">다시 시도해주세요.</span>
          </li>
        </c:otherwise>
        
      </c:choose>
      <li class='li_none'>
        <br>
        <c:choose>
            <c:when test="${param.cnt == 0 }">
                <button type='button' onclick="history.back()" class="btn btn-primary btn-sm">다시 시도</button>    
            </c:when>
        </c:choose>
        
        <%-- <a href="./list_by_cateno.do?cateno=${param.cateno}" class="btn btn-primary">목록</a> --%>
        <%-- <button type='button' onclick="location.href='./list_by_cateno_search.do?cateno=${param.cateno}'" class="btn btn-primary">목록</button> --%>
        <%-- <button type='button' onclick="location.href='./list_by_cateno_search_paging.do?cateno=${param.cateno}'" class="btn btn-primary">목록</button> --%>

      </li>
    </ul>
  </fieldset>

</div>

<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>

</html>

