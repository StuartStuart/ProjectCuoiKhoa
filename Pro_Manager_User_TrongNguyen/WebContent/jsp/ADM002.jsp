<%@page import="utils.CommonUtil"%>
<%@page import="utils.ConstantUtil"%>
<%@page import="properties.ConfigProperties"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<link href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet" type="text/css" />
<title>Insert title here</title>
<script type="text/javascript">
	function addNewUser() {
		window.location = "${pageContext.request.contextPath}/jsp/ADM003.jsp";
	}
</script>
</head>
<body>
	<%@ include file="header.jsp"%>
	<!-- Begin vung dieu kien tim kiem -->
	<form action="${pageContext.request.contextPath}/ListUser.do"
		method="get" name="mainform">
		<table class="tbl_input" border="0" width="90%" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>会員名称で会員を検索します。検索条件無しの場合は全て表示されます。</td>
			</tr>
			<tr>
				<td width="100%">
					<table class="tbl_input" cellpadding="4" cellspacing="0">
						<tr>
							<td class="lbl_left">氏名:</td>
							<td align="left"><input class="txBox" type="text"
								name="adm002fullname"
								value="${fn:escapeXml(ADM002_Textbox) }" size="20"
								onfocus="this.style.borderColor='#0066ff';"
								onblur="this.style.borderColor='#aaaaaa';" /></td>
							<td></td>
						</tr>
						<tr>
							<td class="lbl_left">グループ:</td>
							<td align="left" width="80px"><select
								name="adm002groupid">
									<c:forEach items="${adm002groupid }" var="group">
										<option value="${group.groupId }">${group.groupName }
										</option>
									</c:forEach>
							</select></td>
							<td align="left"><input class="btn" type="submit" value="検索" />
								<input class="btn" type="button" value="新規追加"
								onclick="addNewUser()" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<!-- End vung dieu kien tim kiem -->
	</form>
	<!-- Begin vung hien thi danh sach user -->
	<table class="tbl_list" border="1" cellpadding="4" cellspacing="0"
		width="80%">
		<c:choose>
			<c:when test="${fn:length(userInfors) gt 0}">
				<tr class="tr2">
					<th align="center" width="20px">ID</th>
					<th align="left">氏名 <a
						href=<%="ListUser.do?type=" + ConstantUtil.ADM002_SORT + "&priority="
							+ ConstantUtil.ADM002_SORT_TYPE_URL[0] + "&sort="
							+ CommonUtil.convertSymbol((String) request.getAttribute("symbolFullName"))%>>
							<%=request.getAttribute("symbolFullName")%></a>
					</th>
					<th align="left">生年月日</th>
					<th align="left">グループ</th>
					<th align="left">メールアドレス</th>
					<th align="left" width="70px">電話番号</th>
					<th align="left">日本語能力 <a
						href=<%="ListUser.do?type=" + ConstantUtil.ADM002_SORT + "&priority="
							+ ConstantUtil.ADM002_SORT_TYPE_URL[1] + "&sort="
							+ CommonUtil.convertSymbol((String) request.getAttribute("symbolCodeLevel"))%>>
							<%=request.getAttribute("symbolCodeLevel")%></a>
					</th>
					<th align="left">失効日 <a
						href=<%="ListUser.do?type=" + ConstantUtil.ADM002_SORT + "&priority="
							+ ConstantUtil.ADM002_SORT_TYPE_URL[2] + "&sort="
							+ CommonUtil.convertSymbol((String) request.getAttribute("symbolEndDate"))%>>
							<%=request.getAttribute("symbolEndDate")%></a>
					</th>
					<th align="left">点数</th>
				</tr>
				<c:forEach items="${userInfors}" var="userInfors">
					<tr>
						<td align="right"><a href="#">${userInfors.userId}</a></td>
						<td>${userInfors.fullName}</td>
						<td align="center">${userInfors.birthDay}</td>
						<td>${userInfors.groupName}</td>
						<td>${userInfors.email}</td>
						<td>${userInfors.tel}</td>
						<td>${userInfors.nameLevel}</td>
						<td align="center">${userInfors.endDate}</td>
						<td align="right">${userInfors.total}</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<p align="center">検索条件に該当するユーザが見つかりません。</p>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
	<!-- End vung hien thi danh sach user -->

	<!-- Begin vung paging -->
	<table>
		<tr>
			<%-- <td class="lbl_paging"><c:forEach items="${adm002paging}"
					var="adm002paging">
				${adm002paging}
			</c:forEach></td> --%>
			<c:forEach items="${adm002paging}" var="page">
				<c:if test="${adm002paging[0] > limitPage}">
					<a href="ListUser.do?type=paging&page=pageback"><<</a>&nbsp;
			</c:if>
				<a href="ListUser.do?type=paging&page=${page }">${page}</a>&nbsp;
			<c:if test="${adm002paging[0]*limitPage < totalPage}">
					<a href="ListUser.do?type=paging&page=pagenext">>></a>&nbsp;
			</c:if>
			</c:forEach>
		</tr>
	</table>
	<!-- End vung paging -->

	<!-- Begin vung footer -->
	<%@ include file="footer.jsp"%>
	<!-- End vung footer -->

</body>
</html>