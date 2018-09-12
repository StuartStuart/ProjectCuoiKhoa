<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet" type="text/css" />
<title>Insert title here</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/JapanZone.js"></script>
</head>
<body>
	<!-- Begin vung header -->
	<%@ include file="header.jsp"%>
	<!-- End vung header -->

	<!-- Begin vung input-->
	<form action="${pageContext.request.contextPath}/ChangePass.do"
		method="post">
		<center>
			<table class="tbl_input" cellpadding="4" cellspacing="0"
				width="400px">
				<tr>
					<th width="120px">&nbsp;</th>
					<th></th>
				</tr>
				<tr>
					<th colspan="2" align="left">Màn hình thay đổi password</th>
				</tr>
				<c:forEach items="${adm007message}" var="message">
					<tr>
						<td class="errMsg" colspan="2">${message}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="lbl_left"><font color="red">*</font> パスワード:</td>
					<td align="left"><input class="txBox" type="password"
						name="pass" value="" size="30"
						onfocus="this.style.borderColor='#0066ff';"
						onblur="this.style.borderColor='#aaaaaa';" /></td>
				</tr>
				<tr>
					<td class="lbl_left">パスワード（確認）:</td>
					<td align="left"><input class="txBox" type="password"
						name="repass" value="" size="30"
						onfocus="this.style.borderColor='#0066ff';"
						onblur="this.style.borderColor='#aaaaaa';" /></td>
				</tr>
				<tr>
					<td><input type="hidden" name="userId" value="${userId}" /></td>
					<td align="left"><input class="btn btn_wider" type="submit"
						value="ログイン" /></td>
				</tr>
			</table>
		</center>
	</form>
	<!-- End vung input -->

	<!-- Begin vung footer -->
	<%@ include file="footer.jsp"%>
	<!-- End vung footer -->
</body>
</html>