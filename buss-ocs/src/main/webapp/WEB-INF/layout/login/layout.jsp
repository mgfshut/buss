<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 
<!DOCTYPE html>
<html class="login-bg">
<head>
<meta name="renderer" content="webkit"> 
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><tiles:getAsString name="title"/></title>
<!-- bootstrap -->
<link href="css/bootstrap/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet">
<!-- global styles -->
<link rel="stylesheet" type="text/css" href="css/detail/layout.css">
<link rel="stylesheet" type="text/css" href="css/detail/elements.css">
<link rel="stylesheet" type="text/css" href="css/detail/icons.css">

<!-- libraries -->
<link rel="stylesheet" type="text/css" href="css/lib/font-awesome.css">

<ys:script src="/js/dwz/jquery-1.11.1.min.js"></ys:script>
<script src="js/lib/bootstrap.min.js"></script>
<script src="js/lib/theme.js"></script>

<script type="text/javascript">
	var baseUrl = '${__baseUrl}';
</script>
<tiles:insertAttribute name="head" defaultValue=""/>
</head>
<body>
<tiles:insertAttribute name="body" />
</body>
</html>