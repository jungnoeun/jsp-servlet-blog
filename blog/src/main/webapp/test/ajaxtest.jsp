<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<button onclick="idCheck();">아이디 있니?</button>
<div id="box"></div>
<script>
	function idCheck() {
		const xhttp = new XMLHttpRequest();
		
		// 해당 함수는 통신이 끝나면 콜백 (요청에대한 응답이 끝나면 실행된다.)
		  xhttp.onload = function() {
		    //document.getElementById("demo").innerHTML = this.responseText;
		  	//AjaxTest.java에서 out.print('ok'); 를 통해 ok를 받는다.
		    if(this.response === 'ok') {
		  		var box = document.querySelector("#box");
		  		box.innerHTML = "다른 아이디를 사용해야겠군"; // html 웹 브라우저상의 id=box인 div위치에 통신성공이라고 그린다.
		  	}else {
		  		alert("해당 아이디를 사용할 수 있습니다.");
		  	}	
		  }
		
		//아래의 요청이 되고 응답이 되면 위의 스택(onload = function())이 실행된다.
		  xhttp.open("GET", "http://localhost:8080/blog/ajax", true);
		  xhttp.send();
	}

</script>
</body>
</html>