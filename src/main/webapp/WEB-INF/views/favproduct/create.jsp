<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>POST Form</title>
</head>
<body>

<form action="http://localhost:9093/favproduct/create.do" method="POST">
    Product ID: <input type="text" name="productID"><br>
    Member No: <input type="text" name="memberno"><br>
    <input type="submit" value="Submit">
</form>

</body>
</html>