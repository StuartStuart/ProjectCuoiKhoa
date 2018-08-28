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
	<%@include file="header.jsp"%>

	<!-- End vung header -->

	<!-- Begin vung input-->
	<form action="${pageContext.request.contextPath }/AddUserConfirm.do"
		method="post" name="inputform">
		<table class="tbl_input" border="0" width="75%" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="left">
					<div style="padding-left: 100px;">
						情報確認<br> 入力された情報をＯＫボタンクリックでＤＢへ保存してください
					</div>
					<div style="padding-left: 100px;">&nbsp;</div>
				</th>
			</tr>
			<tr>
				<td align="left">
					<div style="padding-left: 100px;">
						<table border="1" width="70%" class="tbl_input" cellpadding="4"
							cellspacing="0">
							<tr>
								<td class="lbl_left">アカウント名:</td>
								<td align="left">${fn:escapeXml(userinfor.loginName )}</td>
							</tr>
							<tr>
								<td class="lbl_left">グループ:</td>
								<td align="left">${fn:escapeXml(userinfor.mstGroup.groupName) }</td>
							</tr>
							<tr>
								<td class="lbl_left">氏名:</td>
								<td align="left">${fn:escapeXml(userinfor.fullName) }</td>
							</tr>
							<tr>
								<td class="lbl_left">カタカナ氏名:</td>
								<td align="left">${fn:escapeXml(userinfor.fullNameKana) }</td>
							</tr>
							<tr>
								<td class="lbl_left">生年月日:</td>
								<td align="left"><fmt:formatDate pattern="yyyy/MM/dd"
										value="${userinfor.birthDay }" /></td>
							</tr>
							<tr>
								<td class="lbl_left">メールアドレス:</td>
								<td align="left">${fn:escapeXml(userinfor.email) }</td>
							</tr>
							<tr>
								<td class="lbl_left">電話番号:</td>
								<td align="left">${userinfor.tel}</td>
							</tr>
							<tr>
								<th colspan="2"><a href="#" onclick="changeJapanZone()">日本語能力</a></th>
							</tr>
							<tbody
								${(userinfor.nameLevel == null || userinfor.nameLevel == '')?'style="display: none"':'' }
								id="japanzone">
								<tr>
									<td class="lbl_left">資格:</td>
									<c:set value="${userinfor.nameLevel }" var="nameLevel"></c:set>
									<td align="left">${fn:escapeXml(nameLevel) }</td>
								</tr>
								<tr>
									<td class="lbl_left">資格交付日:</td>
									<td align="left"><fmt:formatDate pattern="yyyy/MM/dd"
											value="${userinfor.startDate}" var="startDate" />
										${(nameLevel == null)?'':startDate }</td>
								</tr>
								<tr>
									<td class="lbl_left">失効日:</td>
									<td align="left"><fmt:formatDate pattern="yyyy/MM/dd"
											value="${userinfor.endDate}" var="endDate" /> ${(nameLevel == null)?'':endDate }</td>
								</tr>
								<tr>
									<td class="lbl_left">点数:</td>
									<c:set value="${userinfor.total}" var="total"></c:set>
									<td align="left">${(nameLevel == null)?'':total }</td>
								</tr>
							</tbody>
						</table>
					</div>
				</td>
			</tr>
		</table>
		<div style="padding-left: 100px;">&nbsp;</div>
		<!-- Begin vung button -->
		<div style="padding-left: 100px;">
			<table border="0" cellpadding="4" cellspacing="0" width="300px">
				<tr>
					<th width="200px" align="center">&nbsp;</th>
					<td><input class="btn" type="button" value="編集"
						onclick="window.location.href='${pageContext.request.contextPath}/AddUserInput.do?type=edit&userid=${userinfor.userId }'" />
					</td>
					<td><input class="btn" type="submit" value="削除" /></td>
					<td><input class="btn" type="button" value="戻る"
						onclick="window.location.href='${pageContext.request.contextPath}/ListUser.do?type=back'" />
					</td>
				</tr>
			</table>
			<!-- End vung button -->
	</form>
	<!-- End vung input -->

	<!-- Begin vung footer -->
	<%@include file="footer.jsp"%>>
	<!-- End vung footer -->
</body>
</html>