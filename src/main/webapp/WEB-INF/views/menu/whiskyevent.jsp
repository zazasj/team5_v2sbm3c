<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container mt-3"> 
  <table class="table">
    <thead> 
      <tr  class="table-danger">
        <th>할인 기준</th>
        <th>할인 금액</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>5만원 이하 구매시</td>
        <td>10% 할인, 배송비 유로</td>
      </tr>      
      <tr class="table-info">
        <td>5만원 이상 10만원 이하 구매시</td>
        <td>10% 할인, 배송비 무료</td>
      </tr>
      <tr class="table-primary">
        <td> 10만원 이상 구매시 30만원 이하</td>
        <td>12% 할인, 배송비 무료</td>
      </tr>
      <tr class="table-warning">
        <td> 30만원 이상 구매시</td>
        <td>15% 할인, 배송비 무료</td>
      </tr>
    </tbody>
  </table>
</div>

</body>
</html>