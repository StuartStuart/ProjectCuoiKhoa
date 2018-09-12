<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet" type="text/css" />
<title>Insert title here</title>
</head>
<body>
	<!-- Begin vung header -->
	<%@ include file="header.jsp"%>
	<!-- End vung header -->

	<!-- Begin vung input-->
		<table class="tbl_input" border="0" width="80%" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="center" colspan="2">
					<div style="height: 50px"></div>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2"><font color="red">${systemerrormessage}</font>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2">
					<div style="height: 70px"></div>
				</td>
			</tr>
		</table>
	<!-- End vung input -->

	<!-- Begin vung footer -->
	<%@ include file="footer.jsp"%>
	<!-- End vung footer -->
</body>
</html>