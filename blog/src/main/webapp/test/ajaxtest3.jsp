<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
<button onclick="idCheck();">아이디 있니?</button>
<div id="box"></div>
<script>
	function idCheck() {
		// fetch()는 순수 자바스크립트 함수이다.
		fetch("http://localhost:8080/blog/ajax").then(function(data){
			return data.text(); // fetch()는 파싱과정이 필요하다. 응답하는 데이터를 text로 파싱해준다.
		}).then(function(data){
			alert(data);
		})
	}

</script>
</body>
</html>