<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="codemap" required="true" %>
<%@ attribute name="value" required="true" rtexprvalue="true" %>

<c:import  charEncoding="UTF-8" url="/convert/codeValue/${codemap}-${value}?namefield=codeValueDescribe"/>