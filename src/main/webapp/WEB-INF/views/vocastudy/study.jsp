<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>study</title>
	<link href="${path}/resources/css/study.css" rel="stylesheet"/>
	<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
	<script src="${path}/resources/js/study.js"></script>
	
	<script type="text/javascript">
		let voArr = [];
		let diff = '${diff}';
		let page = '${page}';
		
		<c:forEach items="${voList}" var="vo">
			var temp = {};
			temp.eng = '${vo.eng}';
			temp.kor = '${vo.kor}';
			temp.iKnow = null;
			
			voArr.push(temp);
		</c:forEach>
	</script>
	
</head>
<body>
	<div class="wrap">
	  <div class="largeText1 viewNumber"> <span class="nowView"></span> / <span class="allView"></span></div>
	  <div class="viewBox">
	      <div class="eng"></div>
	      <div class="kor"></div>
	      <div class="speaker_wrap">
	      	<img src="${path}/resources/images/speaker.png" alt="음성출력" class="speaker" title="음성출력">
	      </div>
	  </div>
	
	  <div class="buttonWrap">
	      <div class="button" id="showKorean">Korean</div>
	      <div class="button" id="addVoca">단어장 추가</div>
	      <div class="button" id="keepGoing">공부하겠음</div>
	      <div class="button" id="iKnow">알고있음</div>
	      <div class="button" id="goOut">나가기</div>
	  </div>
	</div>
</body>
</html>