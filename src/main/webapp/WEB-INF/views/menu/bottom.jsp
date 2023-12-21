<%@ page contentType="text/html; charset=UTF-8" %>
  </div> <!-- content_body 내용 종료 -->
  <style>
	  .fixed-button {
	  position: fixed;
	  bottom: 40px; /* 원하는 위치로 조정 */
	  right: 40px; /* 원하는 위치로 조정 */
	  z-index: 999; /* 다른 요소 위에 표시하기 위해 z-index 설정 */
	}
	.fixed-button button {
	  width: 75px; /* 버튼의 너비 */
	  height: 75px; /* 버튼의 높이 */
	  border-radius: 50%; /* 동그란 모양을 위해 반지름 값 설정 */
	  color: white; /* 텍스트 색상 */
	  border: none; /* 테두리 없음 */
	  font-size: 16px; /* 텍스트 크기 */
	  /* 원하는 스타일링 추가 */
	}	
	.fixed-button button img {
	  display: block; /* 가운데 정렬을 위해 block 요소로 설정 */
	  position: absolute; /* 위치 조정 */
	  top: 50%; /* 부모 요소의 50% 높이 위치로 이동 */
	  left: 50%; /* 부모 요소의 50% 너비 위치로 이동 */
	  transform: translate(-50%, -50%); /* 이미지의 중앙 정렬을 위한 변형 */
	}	  
  </style>
 <div class="fixed-button">
    <a href="http://15.165.163.8:5000/chatbot?memberno=${sessionScope.memberno }">    
        <button>
            <img src='/css/images/chat.png' title="chat">
        </button>
    </a>
</div>
  <div class='bottom_menu'>
    <hr>
    Powered by Spring Boot + MyBATIS + AWS EC2 + Docker + Oracle + Ubuntu 20.00.
  </div>
</div> <!-- container_main 종료 -->   
