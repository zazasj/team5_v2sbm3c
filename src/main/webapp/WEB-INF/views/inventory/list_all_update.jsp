<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9092/inventory/list_all.do</title>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->


</head>
<body>
<c:import url="/menu/top.do" />

<div class='title_line'>재고 수정</div>

<aside class="aside_right">
  <span class='menu_divide' >│</span>
  <a href="javascript:location.reload();">새로고침</a>
</aside>
<div class="menu_line"></div> 

<form name='frm' method='post' action='/inventory/update.do'>
  <input type='hidden' name='inventoryID' value='${inventoryVO.inventoryID }'>
  <div style="text-align: center;">
      <label>수정 할 재고ID: <c:out value="${inventoryVO.inventoryID}" /></label>
      <label>재고 상태</label>
    <select id="inventoryStatus" name="inventoryStatus" size="1" style="overflow-y: auto;">
        <option value="입고" >입고</option>
        <option value="출고" >출고</option>
    </select>
    
    <label>제품 ID</label>
<script>
    var uniqueProductIDs = [];
</script>

<select id="productID" name="productID" size="1" style="width: 5%; overflow-y: auto;">
    <c:forEach var="inventoryVO" items="${list}" varStatus="info">
        <c:set var="productID" value="${inventoryVO.productID}" />

        <script>
            var productIdElement = document.getElementById("productID");
            var productIdValue = "${productID}";

            // 중복 확인 및 특정 값 제외
            if (uniqueProductIDs.indexOf(productIdValue) === -1 && productIdValue !== "1") {
                // 배열에 추가
                uniqueProductIDs.push(productIdValue);
            }
        </script>
    </c:forEach>

    <!-- Placeholder for sorting -->
    <option value="1">1</option>

    <!-- Direct user input option for productID -->
    <option value="prodCustom">직접입력</option>
</select>

<!-- Input field for direct user input for productID -->
<input type="text" id="prodCustomInput" name="productID" style="display: none; width: 6%;" placeholder="직접입력">
<script>
    // Sort the options in ascending order after the loop
    uniqueProductIDs.sort(function(a, b) {
        return a.localeCompare(b);
    });

    // Update the options in the select element in ascending order
    uniqueProductIDs.forEach(function(productIdValue) {
        var option = document.createElement("option");
        option.value = productIdValue;
        option.text = productIdValue;
        productIdElement.add(option);
    });

    // Move the "직접입력" option to the end for productID
    var prodCustomInputOption = document.querySelector('#productID option[value="prodCustom"]');
    productIdElement.add(prodCustomInputOption);

    // Event listener for prodCustomInput
    document.getElementById("prodCustomInput").addEventListener("input", function () {
        // Get the value from prodCustomInput
        var prodCustomValue = this.value;

        // Update the productID select element
        productIdElement.value = prodCustomValue;
    });
</script>

<!-- Event listener for direct user input for productID -->
<script>
    document.getElementById("productID").addEventListener("change", function () {
        var selectedValue = this.value;
        var prodCustomInput = document.getElementById("prodCustomInput");

        if (selectedValue === "prodCustom") {
            // Hide the select element
            this.style.display = "none";
            // Show the input field
            prodCustomInput.style.display = "inline-block";
        } else {
            // Show the select element
            this.style.display = "inline-block";
            // Hide the input field
            prodCustomInput.style.display = "none";
        }
    });
</script>

<label>공급업체 ID</label>
<script>
    var uniqueSupplierIDs = [];
</script>

<select id="supplierID" name="supplierID" size="1" style="width: 5%; overflow-y: auto;">
    <c:forEach var="inventoryVO" items="${list}" varStatus="info">
        <c:set var="supplierID" value="${inventoryVO.supplierID}" />

        <script>
            var supplierIdElement = document.getElementById("supplierID");
            var supplierIdValue = "${supplierID}";

            // 중복 확인 및 특정 값 제외
            if (uniqueSupplierIDs.indexOf(supplierIdValue) === -1 && supplierIdValue !== "1") {
                // 배열에 추가
                uniqueSupplierIDs.push(supplierIdValue);
            }
        </script>
    </c:forEach>

    <!-- Placeholder for sorting -->
    <option value="1">1</option>

    <!-- Direct user input option for supplierID -->
    <option value="suppCustom">직접입력</option>
</select>

<!-- Input field for direct user input for supplierID -->
<input type="text" id="suppCustomInput" name="supplierID" style="display: none; width: 6%;" placeholder="직접입력">
<script>
    // Sort the options in ascending order after the loop
    uniqueSupplierIDs.sort(function(a, b) {
        return a.localeCompare(b);
    });

    // Update the options in the select element in ascending order
    uniqueSupplierIDs.forEach(function(supplierIdValue) {
        var option = document.createElement("option");
        option.value = supplierIdValue;
        option.text = supplierIdValue;
        supplierIdElement.add(option);
    });

    // Move the "직접입력" option to the end for supplierID
    var suppCustomInputOption = document.querySelector('#supplierID option[value="suppCustom"]');
    supplierIdElement.add(suppCustomInputOption);

    // Event listener for suppCustomInput
    document.getElementById("suppCustomInput").addEventListener("input", function () {
        // Get the value from suppCustomInput
        var suppCustomValue = this.value;

        // Update the supplierID select element
        supplierIdElement.value = suppCustomValue;
    });
</script>

<!-- Event listener for direct user input for supplierID -->
<script>
    document.getElementById("supplierID").addEventListener("change", function () {
        var selectedValue = this.value;
        var suppCustomInput = document.getElementById("suppCustomInput");

        if (selectedValue === "suppCustom") {
            // Hide the select element
            this.style.display = "none";
            // Show the input field
            suppCustomInput.style.display = "inline-block";
        } else {
            // Show the select element
            this.style.display = "inline-block";
            // Hide the input field
            suppCustomInput.style.display = "none";
        }
    });
</script>
               
    <label>제품 수량</label>
    <input type="text" name="addQuantity" value="입/출고 갯수" required="required"  
               class="" style="width: 10%" onfocus="clearInputaddQuantity(this)">
    <script>
    function clearInputaddQuantity(input) {
        if (input.value === '입/출고 갯수') {
            input.value = ''; // Already empty, do nothing
        }
    }
</script>
    <label>입/출고 날짜</label>
  <input type='date' id='currentDate' name="lastUpdated" required="required"  
               class="" style="width: 9%">

<script>
  document.getElementById('currentDate').value = new Date().toISOString().substring(0, 10);
</script>
     <button type="submit"  class='btn btn-info btn-sm' >수정</button>
    <button type="button" onclick="history.back();" class="btn btn-secondary btn-sm">취소</button>
</div>
</form>

<table class="table table-hover">
    <colgroup>
        <col style='width: 4%;'/>
        <col style='width: 10%;'/>
        <col style='width: 4%;'/>    
        <col style='width: 16%;'/>
        <col style='width: 4%;'/>
        <col style='width: 14%;'/>
        <col style='width: 5%;'/>
        <col style='width: 7%;'/>
        <col style='width: 5%;'/>
      </colgroup>
      <thead>
        <tr>
          <th class="th_bs">재고 ID</th>
          <th class="th_bs">재고 상태</th>
          <th class="th_bs">제품 ID</th>
          <th class="th_bs">제품 이름</th>
          <th class="th_bs">업체 ID</th>
          <th class="th_bs">공급업체 회사명</th>
          <th class="th_bs">제품 수량</th>
          <th class="th_bs">입/출고 날짜</th>
          <th class="th_bs">수정/삭제</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach var="inventoryVO" items="${list}" varStatus="info">
    <c:set var="inventoryID" value="${inventoryVO.inventoryID}" />
    <c:set var="inventoryStatus" value="${inventoryVO.inventoryStatus}" />
    <c:set var="productID" value="${inventoryVO.productID}" />
    <c:set var="supplierID" value="${inventoryVO.supplierID}" />
    <c:set var="addQuantity" value="${inventoryVO.addQuantity}" />
    <c:set var="lastUpdated" value="${inventoryVO.lastUpdated.substring(0, 10)}" />

    <tr>
        <td class="td_bs">${inventoryID}</td>
        <td class="td_bs">${inventoryStatus}</td>
        <td class="td_bs">${productID}</td>
        <!-- Loop through the list1 to find matching pName -->
        <c:forEach var="productsVO" items="${list1}" varStatus="info1">
            <c:if test="${productsVO.productID eq inventoryVO.productID}">
                <td class="td_bs">${productsVO.pName}</td>
            </c:if>
        </c:forEach>
        <td class="td_bs">${supplierID}</td>
        <!-- Loop through the list1 to find matching pName -->
        <c:forEach var="supplierVO" items="${list2}" varStatus="info1">
            <c:if test="${supplierVO.supplierid eq inventoryVO.supplierID}">
                <td class="td_bs">${supplierVO.sname}</td>
            </c:if>
        </c:forEach>
        <td class="td_bs">${addQuantity}</td>
        <td class="td_bs">${lastUpdated}</td>
        <td class="td_bs">
            <a href="./update.do?inventoryID=${inventoryID}" title="수정"><img src="/category/images/update.png" class="icon"></a>
            <a href="./delete.do?inventoryID=${inventoryID}" title="삭제"><img src="/category/images/delete.png" class="icon"></a>
        </td>
    </tr>
</c:forEach>
      </tbody>
      
  </table>
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>