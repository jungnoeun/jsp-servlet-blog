<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file = "../layout/header.jsp" %>


<div class = "container">
<!--  form태그에 데이터를 넣어서 보내면 디폴트 데이터타입은 x-www-form-urlencoded이다. enctype속성을 통해 바꿔줄 수 있다. -->
<!-- key값은 name값이고 value값은 내가 적은 값을 참고한다. -->
<form action="/blog/user?cmd=login"  method="post" >

<!-- input 칸에 값을 입력하면 input의 value속성값에 입력한 값이 들어간다. -->
  <div class="form-group">
    <input type="text" name="username" id="username"class="form-control" placeholder="Enter Username"  required/>
  </div>
  
  <div class="form-group">
	<input type="password" name="password" class="form-control" placeholder="Enter Password"  required/>	
  </div>

	<br/>

  <button type="submit" class="btn btn-primary">로그인완료</button>
</form>
 </div>


</body>
</html>