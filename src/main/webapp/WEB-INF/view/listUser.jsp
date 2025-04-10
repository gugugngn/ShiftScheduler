<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>職員一覧</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<c:import url="parts/header.jsp" />
<div class="container">
	<h1>職員一覧</h1>
	<div class="row">
		<div class="col">
			<table class="table table-bordered">
				<tr>
					<th>ID</th>
					<th>名前</th>
					<th>所属部署</th>
					<th>権限種別</th>
					<th>登録日</th>
					<th>更新日</th>
					<th>編集</th>
					<th>削除</th>
				</tr>
				<c:forEach items="${userList}" var="user">
					<tr>
						<td><c:out value="${user.id}" /></td>
						<td><c:out value="${user.name}" /></td>
						<td><c:out value="${user.departmentName}" /></td>
						<td><c:out value="${user.positionName}" /></td>
						<td><fmt:formatDate value="${user.created}" pattern="yyyy/M/d" /></td>
						<td><fmt:formatDate value="${user.updated}" pattern="yyyy/M/d" /></td>
						<td><a href="updateUser?id=<c:out value="${user.id}"/>">更新</a></td>
						<td>
						  <form action="deleteUser" method="post" >
						  	<input type="hidden" name="id" value="${user.id}" />
						    <input type="submit" class="btn btn-danger btn-sm" value="削除">
						  </form>
						</td>
					</tr>
				</c:forEach>
			</table>
			<p>
				<a href="addUser" class="btn btn-primary">職員の追加</a>
			</p>
		</div>
	</div>
</div>

<script src="https://code.jquery.com/jquery-3.6.1.slim.min.js"></script>
<script>
$(document).ready(function() {
	$("form").submit(function() {
		return confirm("本当に削除しますか？");
	});
});
</script>
</body>
</html>