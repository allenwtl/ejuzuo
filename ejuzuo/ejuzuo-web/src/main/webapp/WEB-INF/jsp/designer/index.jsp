<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>申请的首页</title>
</head>
<body>
这里是申请首页

想成为啥？
<select id="designerType">
  <c:forEach items="${designerType}" var="item" >
    <option value="${item.index}">${item.description}</option>
  </c:forEach>
</select>
<input value="下一步" onclick="nextStep()" type="button"></input>
<script type="text/javascript" src="${resUrl}/plugin/jquery/jquery-2.2.2.min.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-multidownload/jquery-multidownload.js"></script>
<script type="text/javascript">
  function nextStep (){
    var designerType = $("#designerType").val();
    if(designerType== null ){
      alert("请选择类型");
      return ;
    }
    window.location.href = "/designer/personalInfo/"+designerType;
  }
</script>
</body>
</html>
