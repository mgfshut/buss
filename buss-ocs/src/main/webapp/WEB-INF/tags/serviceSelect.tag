<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="service" required="true" %>
<%@ attribute name="codeField" required="true" %>
<%@ attribute name="textField" required="true" %>
<%@ attribute name="name" required="false" %>
<%@ attribute name="defaultText" required="false" %>
<%@ attribute name="defaultValue" required="false" %>
<%@ attribute name="classes" required="false"%>
<%@ attribute name="value" required="false" %>


<c:import charEncoding="UTF-8" url="/module/ui-select/${service}?name=${name}&codefield=${codeField}&namefield=${textField}&defaultText=${defaultText}&classes=${classes}&value=${value}"/>