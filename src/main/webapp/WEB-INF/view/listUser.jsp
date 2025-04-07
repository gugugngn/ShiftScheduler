<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
							<td><c:out value="${user.created}" /></td>
							<td><a href="updateUser?id=<c:out value="${user.id}"/>">更新</a></td>
							<td><a href="deleteUser?id=<c:out value="${user.id}" />">削除</a></td>
						</tr>
					</c:forEach>
				</table>
				<p>
					<a href="addUser" class="btn btn-primary">職員の追加</a>
				</p>
			</div>
		</div>
	</div>
</body>
</html>