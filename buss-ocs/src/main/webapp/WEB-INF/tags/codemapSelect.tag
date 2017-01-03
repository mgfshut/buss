<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="codemap" required="true" %>
<%@ attribute name="name" required="false" %>
<%@ attribute name="defaultText" required="false" %>
<%@ attribute name="defaultValue" required="false" %>
<%@ attribute name="value" required="false" %>
<%@ attribute name="classes" required="false"%>
<%@ attribute name="style" required="false" %>
<%@ attribute name="disabled" required="false" %>
<%@ attribute name="required" required="false" %>
<%@ attribute name="dataerror" required="false"%>
<c:import charEncoding="UTF-8" url="/ui/codemapSelect/${codemap}?name=${name}&codefield=code&namefield=name&defaultText=${defaultText}&value=${value }&classes=${classes}&style=${style }&disabled=${disabled }&required=${required }&dataerror=${dataerror }"/>