<%@page import="utils.ConstantUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<div>
			<table>
				<tr>
					<td width="80%"><img src="${pageContext.request.contextPath}/images/logo-manager-user.gif"
						alt="Luvina" />
					<td>
					<td align="left"><a href="${pageContext.request.contextPath}/logout.do">ログアウト</a> 
					&nbsp; 
					<a href="${pageContext.request.contextPath}/ListUser.do">トップ</a>
					<td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>