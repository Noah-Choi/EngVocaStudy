<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<title>로그인</title>
	<link href="${path}/resources/css/loginForm.css" rel="stylesheet"/>
	
	<script type="text/javascript">
	    var msg ='${msg}';
	    if(msg != "")
	    {
			alert(msg);
	    }
	    
	    function validate() 
		{
			if(memberId.value.trim() == "")
			{
				alert("아이디를 입력해 주세요.");
				memberId.focus();
				return false;
			}
			else if(password.value.trim() == "")
			{
				alert("비밀번호를 입력해 주세요.");
				password.focus();
				return false;
			}
		}
	    
	</script>
</head>
<body>
	<div class="wrap">
		<form action="/web/login" method="POST" onsubmit="return validate();">
			<input name="id" id="memberId" type="text" placeholder = "아이디">
			<br>
			
			<input name="pw" id="password" type="password" placeholder = "비밀번호">
			<button type="submit">로그인</button>
		</form>
	</div>
	
</body>
</html>