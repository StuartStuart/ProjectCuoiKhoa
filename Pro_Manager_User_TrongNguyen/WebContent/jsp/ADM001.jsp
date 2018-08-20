<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet" type="text/css" />
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/Login" method="post">
		<center>
			<table class="tbl_input" cellpadding="4" cellspacing="0"
				width="400px">
				<tr>
					<th width="120px">&nbsp;</th>
					<th></th>
				</tr>
				<tr>
					<th colspan="2" align="left">アカウント名およびパスワードを入力してください</th>
				</tr>
				<c:forEach items="${message}" var="message">
					<tr>
						<td class="errMsg" colspan="2">${message}</td>
					</tr>
				</c:forEach>
				<tr align="left">
					<td class="lbl_left">アカウント名:</td>
					<td align="left"><input class="txBox" type="text"
						name="loginId"
						value="<%// tương tự message
			String loginId = (String) request.getAttribute("loginId");
			if (loginId != null && !"".equals(loginId)) {%>
						<%-- loginId ko null và đã được nhập --%>
							<%=loginId%> <%-- display loginId on textbox [login] --%>
						<%} else {%>""<%}%>"
						size="20" onfocus="this.style.borderColor='#0066ff';"
						onblur="this.style.borderColor='#aaaaaa';" /></td>
				</tr>
				<tr>
					<td class="lbl_left">パスワード:</td>
					<td align="left"><input class="txBox" type="password"
						name="password"
						value=<%// tương tự loginId
			String password = (String) request.getAttribute("password");
			if (password != null && !"".equals(password)) {%>
						<%=password%> <%} else {%>""<%}%> size="22"
						onfocus="this.style.borderColor='#0066ff';"
						onblur="this.style.borderColor='#aaaaaa';" /></td>
				</tr>
				<tr>
					<td></td>
					<td align="left"><input class="btn btn_wider" type="submit"
						value="ログイン" /></td>
				</tr>
			</table>
		</center>
	</form>
</body>
</html>