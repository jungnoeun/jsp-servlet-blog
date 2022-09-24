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
		// https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js 를 통해 ajax()함수를 사용할 수 있다.
		$.ajax("http://localhost:8080/blog/ajax").done(function(data){
			alert(data);
		});
	}

</script>
</body>
</html>