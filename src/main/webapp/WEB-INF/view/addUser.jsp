<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>職員登録</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<c:import url="parts/header.jsp" />
	<div class="container">
		<h1>職員登録</h1>
		<div class="row">
			<div class="col">
				<form action="" method="post">
					<table border="1">
						<tr>
							<th>名前</th>
							<td><input type="text" name="name"
								value="<c:out value="${name}" />"></td>
						</tr>
						<tr>
							<th>所属部署</th>
							<td><select name="departmentId">
									<c:forEach var="dept" items="${departments}">
										<option value="${dept.id}">${dept.name}</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<th>権限種別</th>
							<td><select name="positionId">
									<c:forEach var="pos" items="${positions}">
										<option value="${pos.id}">${pos.name}</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<th>ログインID</th>
							<td><input type="text" name="loginId" placeholder="ログインID" required /></td>
						</tr>
						<tr>
							<th>パスワード</th>
							<td><input type="password" name="password" placeholder="パスワード" required /></td>
						</tr>
					</table>

					<input type="submit" value="登録">

				</form>
				<a href="listUser">職員一覧に戻る</a>
			</div>
		</div>
	</div>
</body>
</html>