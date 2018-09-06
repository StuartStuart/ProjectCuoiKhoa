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
	<form action="${pageContext.request.contextPath }/AddUserValidate.do"
		method="post" name="inputform">
		<table class="tbl_input" border="0" width="75%" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="left">
					<div style="padding-left: 100px;">会員情報編集</div>
				</th>
			</tr>
			<tr>
				<td class="errMsg"><c:forEach items="${errmsg}" var="msg">
						<div style="padding-left: 120px">${msg}</div>
					</c:forEach></td>
			</tr>
			<tr>
				<td align="left">
					<div style="padding-left: 100px;">
						<table border="0" width="100%" class="tbl_input" cellpadding="4"
							cellspacing="0">
							<c:set value="${ adm003userinfor.userId}" var="userId"></c:set>
							<input type="hidden" name="loginName"
								value="${adm003userinfor.loginName }" />
							<tr>
								<td class="lbl_left"><font color="red">*</font> アカウント名:</td>
								<td align="left"><input class="txBox" type="text" name="id"
									value="<c:out value="${adm003userinfor.loginName}" escapeXml="true"/>"
									${(userId != null)?'disabled="disabled"':'' } size="15"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> グループ:</td>
								<td align="left"><select name="group_id">
										<option value="0">選択してください</option>
										<c:forEach items="${groups}" var="group">
											<option value="${group.groupId}"
												${(adm003userinfor.groupId == group.groupId)?'selected="selected"':'' }>
												${fn:escapeXml(group.groupName) }</option>
										</c:forEach>
								</select> <span>&nbsp;&nbsp;&nbsp;</span></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> 氏名:</td>
								<td align="left"><input class="txBox" type="text"
									name="full_name"
									value="${fn:escapeXml(adm003userinfor.fullName) }" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left">カタカナ氏名:</td>
								<td align="left"><input class="txBox" type="text"
									name="full_name_kana"
									value="${fn:escapeXml(adm003userinfor.fullNameKana) }"
									size="30" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> 生年月日:</td>
								<%-- convert userBirthDay sang dạng chung --%>
								<%-- 								<fmt:parseDate value="${adm003userinfor.birthDay}"
									pattern="yyyy-MM-dd" var="birthDay" /> --%>
								<c:set value="${adm003userinfor.birthDay}" var="birthDay"></c:set>
								<td align="left">
									<%-- convert tham số tạm birthDay sang year --%> <fmt:formatDate
										value="${birthDay}" pattern="y" var="birthYear" /> <select
									name="birth_day">
										<c:forEach items="${birthyears }" var="birthyear">
											<option value="${birthyear}"
												${(birthyear==birthYear)?'selected="selected"':'' }>${birthyear}</option>
										</c:forEach>
								</select>年 <%-- convert tham số tạm birthDay sang month --%> <fmt:formatDate
										value="${birthDay}" pattern="M" var="birthMonth" /> <select
									name="birth_day">
										<c:forEach items="${months }" var="month">
											<option value="${month}"
												${(month==birthMonth)?'selected="selected"':'' }>${month}</option>
										</c:forEach>
								</select>月 <%-- convert tham số tạm birthDay sang date --%> <fmt:formatDate
										value="${birthDay}" pattern="d" var="birthDate" /> <select
									name="birth_day">
										<c:forEach items="${dates }" var="date">
											<option value="${date}"
												${(date==birthDate)?'selected="selected"':'' }>${date}</option>
										</c:forEach>
								</select>日
								</td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> メールアドレス:</td>
								<td align="left"><input class="txBox" type="text"
									name="email" value="${fn:escapeXml(adm003userinfor.email) }"
									size="30" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font>電話番号:</td>
								<td align="left"><input class="txBox" type="text"
									name="tel" value="${fn:escapeXml(adm003userinfor.tel) }"
									size="30" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<c:choose>
								<c:when test="${userId == null}">
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
								</c:when>
								<c:otherwise>
									<tr>
										<td></td>
										<td align="left"><a href="#">change password</a></td>
									</tr>
								</c:otherwise>
							</c:choose>
							<tr>
								<th align="left" colspan="2"><a href="#"
									onclick="changeJapanZone()">日本語能力</a></th>
							</tr>
							<tbody style="display: none" id="japanzone">
								<tr>
									<td class="lbl_left">資格:</td>
									<td align="left"><select name="kyu_id">
											<option value="N0">選択してください</option>
											<c:forEach items="${japanlevels }" var="mstJapan">
												<option value="${mstJapan.codeLevel}"
													${(mstJapan.codeLevel==adm003userinfor.codeLevel)?
												'selected="selected"':'' }>

													${fn:escapeXml(mstJapan.nameLevel)}</option>
											</c:forEach>
									</select></td>
								</tr>
								<tr>
									<td class="lbl_left">資格交付日:</td>
									<%-- convert userStartDate sang dạng chung --%>
									<%-- <fmt:parseDate value="${adm003userinfor.startDate}"
										pattern="yyyy-MM-dd" var="startDate" /> --%>
									<c:set value="${adm003userinfor.startDate}" var="startDate"></c:set>
									<td align="left">
										<%-- convert tham số tạm startDate sang year --%> <fmt:formatDate
											value="${startDate}" pattern="y" var="startYear" /> <select
										name="start_date">
											<c:forEach items="${startyears }" var="startyear">
												<option value="${startyear}"
													${(startyear==startYear)?'selected="selected"':'' }>${startyear}</option>
											</c:forEach>
									</select>年 <%-- convert tham số tạm startDate sang month --%> <fmt:formatDate
											value="${startDate}" pattern="M" var="startMonth" /> <select
										name="start_date">
											<c:forEach items="${months }" var="month">
												<option value="${month}"
													${(month==startMonth)?'selected="selected"':'' }>${month}</option>
											</c:forEach>
									</select>月 <%-- convert tham số tạm startDate sang date --%> <fmt:formatDate
											value="${startDate}" pattern="d" var="startDay" /> <select
										name="start_date">
											<c:forEach items="${dates }" var="date">
												<option value="${date}"
													${(date==startDay)?'selected="selected"':'' }>${date}</option>
											</c:forEach>
									</select>日
									</td>
								</tr>
								<tr>
									<td class="lbl_left">失効日:</td>
									<%-- convert userStartDate sang dạng chung --%>
									<%-- <fmt:parseDate value="${adm003userinfor.endDate}"
										pattern="yyyy-MM-dd" var="endDate" /> --%>
									<c:set value="${adm003userinfor.endDate}" var="endDate"></c:set>
									<td align="left">
										<%-- convert tham số tạm endDate sang year --%> <fmt:formatDate
											value="${endDate}" pattern="y" var="endYear" /> <select
										name="end_date">
											<c:forEach items="${endyears }" var="endyear">
												<option value="${endyear}"
													${(endyear==endYear)?'selected="selected"':'' }>${endyear}</option>
											</c:forEach>
									</select>年 <%-- convert tham số tạm endDate sang month --%> <fmt:formatDate
											value="${endDate}" pattern="M" var="endMonth" /> <select
										name="end_date">
											<c:forEach items="${months }" var="month">
												<option value="${month}"
													${(month==endMonth)?'selected="selected"':'' }>${month}</option>
											</c:forEach>
									</select>月 <%-- convert tham số tạm endDate sang date --%> <fmt:formatDate
											value="${endDate}" pattern="d" var="endDay" /> <select
										name="end_date">
											<c:forEach items="${dates }" var="date">
												<option value="${date}"
													${(date==endDay)?'selected="selected"':'' }>${date}</option>
											</c:forEach>
									</select>日
									</td>
								</tr>
								<tr>
									<td class="lbl_left">点数:</td>
									<td align="left"><input class="txBox" type="text"
										name="total"
										value="${adm003userinfor.total!=null?adm003userinfor.total:'' }"
										size="5" onfocus="this.style.borderColor='#0066ff';"
										onblur="this.style.borderColor='#aaaaaa';" /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</td>
			</tr>
		</table>
		<div style="padding-left: 100px;">&nbsp;</div>
		<!-- Begin vung button -->
		<div style="padding-left: 45px;">
			<table border="0" cellpadding="4" cellspacing="0" width="300px">
				<tr>
					<th width="200px" align="center">&nbsp;</th>
					<td><input class="btn" type="submit" value="確認" /></td>
					<td><input class="btn" type="button" value="戻る"
						onclick="window.location.href='${pageContext.request.contextPath }/ListUser.do?type=back'" /></td>
				</tr>
			</table>
			<!-- End vung button -->
	</form>
	<!-- End vung input -->

	<!-- Begin vung footer -->
	<%@ include file="footer.jsp"%>
	<!-- End vung footer -->
</body>
</html>