<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>

<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<title>main</title>
	<link href="${path}/resources/css/main.css" rel="stylesheet"/> 	
	
</head>
<body>
	<div class="wrap">
		<button type="button" onClick="location.href='/web/vocastudy/diff';">단어학습</button>
		<button type="button" onClick="location.href='';">명언 보기</button>
		<button type="button" onClick="location.href='';">자주 쓰는 표현</button>
		<button type="button" onClick="location.href='/web/vocastudy/test';">테스트</button>
	</div>
</body>
</html>