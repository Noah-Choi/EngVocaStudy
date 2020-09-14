<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!DOCTYPE html>

<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<title>main</title>
	<link href="${path}/resources/css/diff.css" rel="stylesheet"/> 	
</head>
<body>

	 <c:forEach items="${diffList}" var="vo">
	 	<button type="button" onClick="location.href='/web/vocastudy/list?diff=${vo.diff}';">${vo.kor}</button>
     </c:forEach>
    
</body>
</html>