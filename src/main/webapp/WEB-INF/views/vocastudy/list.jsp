<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!DOCTYPE html>

<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<title>main</title>
	<link href="${path}/resources/css/list.css" rel="stylesheet"/>
	
	<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
	<script type="text/javascript">
	
		 if (self.name != 'reload') 
		 {
	         self.name = 'reload';
	         self.location.reload(true);
	     }
	     else
	    	 self.name = ''; 
	
		function check(page)
		{ 		
			let studyingPage = '${studyingPage}';
			if(studyingPage != "" && studyingPage != page)
			{
				let result = confirm(studyingPage + "장을 진행중입니다. \n포기하고 " + page + "장을 공부하실래요?");
				if(result == true)
				{
					data = 
				 	{
				 		diff: '${diff}',
				 		page: studyingPage,
				 		nextPage: page,
				 		mode: "out",
				 	};
					
					let jsonData = JSON.stringify(data);
					$.ajax({
						url : '/web/vocastudy/deleteStudy',
						type : 'POST',
						data: {"data": jsonData},
					});
					
					location.href='/web/vocastudy/study?diff=${diff}&page=' + page;	
				}
			}
			else
				location.href='/web/vocastudy/study?diff=${diff}&page=' + page;
		}
	</script>
	 	
</head>
<body>

	 <c:forEach items="${pageList}" var="page">
		<div>
			<c:if test="${studyingPage ne null && studyingPage eq page}">
	    		<div class="studying">진행중</div>
			</c:if>
			
			<button type="button" onClick="check('${page}');">${page}</button>
		</div>
	 	
     </c:forEach>
    
</body>
</html>