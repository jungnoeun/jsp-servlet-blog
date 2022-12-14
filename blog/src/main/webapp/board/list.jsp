<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>

<div class="container">

	<div class="m-2">
		<form class="form-inline d-flex justify-content-end" action="/blog/board">
			<input type="hidden" name="cmd" value="search" />
			<input type="hidden" name="page" value="0" />
			
			<input type="text" name="keyword" class="form-control mr-sm-2" placeholder="Search">			
			<button class="btn btn-primary m-1">검색</button>
		
		</form>
	</div>

	<div class="progress col-md-12 m-2">
		<div class="progress-bar" style="width: ${currentPosition}%"></div>
	</div>
	
	<!-- JSTL foreach문을 써서 뿌리세요. el표현식과 함께  -->
	<!-- items="${requestScope.boards}라고 안적어도 된다. 세션만 sessionScope.aa로 적어주면 된다. 일종의 약속 -->
	<!-- ${boards}는 컬렉션이다. for문을 한번 돌때마다 컬렉션 값이 var=board에 하나씩 담긴다. -->
	<c:forEach var="board" items="${boards}">
		<div class="card col-md-12 m-2">
			<div class="card-body">
				<h4 class="card-title">${board.title}</h4>
				<a href="/blog/board?cmd=detail&id=${board.id}" class="btn btn-primary">상세보기</a>
			</div>
		</div>
	</c:forEach>

		
	 <!--  disabled  -->
	<br />
	<ul class="pagination justify-content-center">
		<c:choose>
			<c:when test="${param.page == 0}">
				<li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="/blog/board?cmd=list&page=${param.page-1}">Previous</a></li>
			</c:otherwise>
		</c:choose>
		
		
		<c:choose>
			<c:when test="${lastPage == param.page}">
				<li class="page-item disabled"><a class="page-link" href="#">Next</a></li>		
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="/blog/board?cmd=list&page=${param.page+1}">Next</a></li>
			</c:otherwise>
		</c:choose>

	</ul>
</div>

</body>
</html>

