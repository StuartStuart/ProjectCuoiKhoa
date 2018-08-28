<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<title>JSTL fmt:dateNumber Tag</title>
</head>

<body>
	<%-- <h3>Number Format:</h3>

	<p>
		<fmt:formatDate pattern="yyyy-MM-dd"
			value="<fmt:parseDate value="2018-09-08" pattern="y"/>" />
	</p> --%>

	<fmt:parseDate value="2018-09-08" pattern="yyyy-MM-dd" var="pD" />
	<p>
		<fmt:formatDate pattern="yyyy/MM/dd" value="${pD}" />
	</p>
	<fmt:formatDate value="${pD }" pattern="y" />
	<fmt:formatDate value="${pD }" pattern="M" />
	<fmt:formatDate value="${pD }" pattern="d" />
	<input type="text" value="test" disabled="disabled"/>
	<%-- <fmt:parseDate value="${pD }" pattern="y"></fmt:parseDate> --%>
	<%-- <p>
		<fmt:formatDate pattern="y" value=/>
	</p> --%>
	<%-- <p>
		<fmt:formatDate pattern="d" value="<fmt:parseDate value="2018-09-08" pattern="y"/>"/>
	</p> --%>

	<%-- <h3>Date Parsing:</h3>

	<c:set var="now" value="20-10-2010" />
	<fmt:parseDate value="${now}" var="parsedEmpDate" pattern="dd-MM-yyyy" />
	<p>
		Parsed Date:
		<c:out value="${parsedEmpDate}" />
	</p> --%>
</body>
</html>