<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="dev.mvc.recentrecom.RecentRecomVO" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>술기운</title>
<link rel="shortcut icon" href="/images/sulic-resize36.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

</head>
<body> 
<c:import url="/menu/top.do" /> 
    <div class="container p-5 my-5 border" style="width: 75%; margin: 30px auto; text-align: center;">
       <c:import url="/recentrecom/list_recent_products.do" />
   </div>  
   <div  style="width: 100%; margin: 30px auto; text-align: center;">  
        <img src="/images/alcohol.jpg" style="width: 60%;">
   </div>


<jsp:include page="./menu/bottom.jsp" flush='false' /> 
</body>
</html>
