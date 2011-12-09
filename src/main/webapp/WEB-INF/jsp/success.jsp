<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Congratulations!</title>
</head>
<body>

<h1>Congratulations!</h1>

<div>
    <img src="<c:url value="/images/coconuts.jpg" />" />
</div>

<h2>The password is...</h2>
<p>"<%= request.getAttribute("password") %> coconuts"</p>

</body>
</html>