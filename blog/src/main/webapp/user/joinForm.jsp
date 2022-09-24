<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file = "../layout/header.jsp" %>

<div class = "container">
<form action="/blog/user?cmd=join"  method="post" onsubmit="return valid()">

  <div class="d-flex justify-content-end">
  <button type="button" class="btn btn-info" onClick="usernameCheck()">중복체크</button>
  </div>

  <div class="form-group">
    <input type="text" name="username" id="username"class="form-control" placeholder="Enter Username"  required/>
  </div>
  
  <div class="form-group">
	<input type="password" name="password" class="form-control" placeholder="Enter Password"  required/>	
  </div>

  <div class="form-group">
	<input type="email" name="email" class="form-control" placeholder="Enter Email"  required/>
  </div>
  
  <div class="d-flex justify-content-end">
  <button type="button" class="btn btn-info" onClick="goPopup();">주소검색</button>
  </div>
  
  <div class="form-group">
	<input type="text" name="address"  id="address"  class="form-control" placeholder="Enter Aderess"  required readonly/>
  </div>
	<br/>

  <button type="submit" class="btn btn-primary">회원가입완료</button>
</form>
 </div>
 
 
 <script>
 	var isChecking = false; // false이면 submit 버튼을 눌러도 작동안하게 할 것이다.
 	
	function valid(){ //return onsubmit이 false이면 회원가입버튼을 눌러도 다음페이지로 넘어가지 않는다.
		if(isChecking == false){
			alert("아이디 중복체크를 해주세요");
		}
		return isChecking; //중복검사를 하지 않으면 제출이 안되게 한다.
	}
	
	function usernameCheck(){
		 //DB에서 확인해서 정상이면(중복이 아니면) isChecking=true
		 // 여기에서 ajax통신해야 한다. html을 받는것이 아니라 데이터만 받으면 된다.
		var username = $("#username").val();  //name이란 "속성"에 어떤 값이 담기는지 알아야 하므로 val()을 사용
		
		$.ajax({	//js에서는 {}가 객체임
			type: "POST",		//type은 데이터 통신 방법, GET으로 하면 하이퍼링크로 하면 안된다. 그러면 응답을 못받기 때문. login처럼 확인여부만 묻기때문에 POST로 해도됨
			url: "/blog/user?cmd=usernameCheck", 	 // usernameCheck에 해당하는 컨트롤러로 이동. 주소는 http://localhost8080의 contextpath는 생략가능하다. 이미 그안에 있기 때문
			 // type이 GET인 경우 url: "/blog/user?cmd=usernameCheck&username=ssar" 이어야 한다. 
			data: username, 	// 내가 요청할때 가져갈 http 바디 데이터이다. GET에는 http바디데이터가 없다. 그래서 GET은 쿼리스트링으로 보내야 함
			 // div에 id가 있으면 data를 찾기 쉽다(document.querySelector로 찾으면 된다.) 근데 여기서는 JQUery문법을 사용한다.
			contentType: "text/plain; charset=utf-8", 	 // 내가 지금 던지는 데이터가 어떻게 생겼는지 알려줌
			dataType: "text"  // 응답받을 데이터의 타입을 적으면 자바스크립트 오브젝트로 파싱해줌. json인 경우 {"result":"있어"}를 파싱해서 var a={result:"있어"} 와 같이 json을 js 오브젝트로 변환해준다.
		}).done(function(data){ 		//통신이 끝나면 이 함수를 실행시켜줌. callback되는 함수. 매개변수는 통신의 결과가 들어온다.
			if(data === 'ok'){ // 유저네임 있다는 것
				isChecking = false;
				alert('유저네임이 중복되었습니다.')
			}else{
				isChecking = true;
				alert("해당 유저네임을 사용할 수 있습니다.")
			}
		});
	}
 

 
 
 
// opener관련 오류가 발생하는 경우 아래 주석을 해지하고, 사용자의 도메인정보를 입력합니다. ("팝업API 호출 소스"도 동일하게 적용시켜야 합니다.)
//document.domain = "abc.go.kr";

function goPopup(){
	// 주소검색을 수행할 팝업 페이지를 호출합니다.
	// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
	var pop = window.open("/blog/user/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
	// window.open이면 브라우저가 하나 더 열린다는 의미이다. 
	
	// 모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다.
    //var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes"); 
}


function jusoCallBack(roadFullAddr){
		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
//		document.form.roadFullAddr.value = roadFullAddr; //이렇게 name값으로 찾는 방법은 옛날 방식이다.
	var addressEl = document.querySelector("#address");
	addressEl.value = roadFullAddr;
	 
}

</script>
</body>
</html>