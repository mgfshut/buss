<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String agent = request.getHeader("User-Agent");
int iep = agent.indexOf("MSIE");
if(iep >= 0){
	String ver = agent.substring(iep + 5, agent.indexOf(';', iep));
	Float version = Float.parseFloat(ver);
	pageContext.setAttribute("iever", version);
}

%>
<shiro:authenticated>
	<jsp:forward page="/"></jsp:forward>
</shiro:authenticated>
<tiles:insertDefinition name="login">
	<tiles:putAttribute name="title" value="登录"></tiles:putAttribute>
	<tiles:putAttribute name="head">
		<!-- this page specific styles -->
		<link rel="stylesheet" href="css/detail/signin.css" type="text/css" media="screen" />
		<style>
			.support img{width:120px;}
			.login-wrapper h1{
				font-size: 35px;
				color: #990099;
				margin-bottom: 20px;
				margin-top: 10px;
			}
		</style>
		<script type="text/javascript">
			var bgIndex = 0;
			$(function(){
				/* $('#inputUsername').focus();
			        // bg switcher
			        var $btns = $(".bg-switch .bg");
			        $btns.click(function (e) {
			           e.preventDefault();
			           $btns.removeClass("active");
			           $(this).addClass("active");
			           var bg = $(this).data("img");
			           $("html").css("background-image", "url('img/bgs/" + bg + "')");
			        }); */
			    changeBg();
			});
			
			function changeBg(){
				$("html").css("background-image", "url('img/bgs/bg_"+bgIndex+".jpg')");
				if(bgIndex == 3){
					bgIndex = 0;
				}else{
					bgIndex ++;
				}
				window.setTimeout('changeBg()',100000);
			}
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
    <!-- background switcher -->
   <!--  <div class="bg-switch visible-desktop">
        <div class="bgs">
            <a href="#" data-img="landscape.jpg" class="bg active">
                <img src="img/bgs/landscape.jpg" />
            </a>
            <a href="#" data-img="blueish.jpg" class="bg">
                <img src="img/bgs/blueish.jpg" />
            </a>            
            <a href="#" data-img="7.jpg" class="bg">
                <img src="img/bgs/7.jpg" />
            </a>
            <a href="#" data-img="8.jpg" class="bg">
                <img src="img/bgs/8.jpg" />
            </a>
            <a href="#" data-img="9.jpg" class="bg">
                <img src="img/bgs/9.jpg" />
            </a>
            <a href="#" data-img="10.jpg" class="bg">
                <img src="img/bgs/10.jpg" />
            </a>
            <a href="#" data-img="11.jpg" class="bg">
                <img src="img/bgs/11.jpg" />
            </a>
        </div>
    </div> -->
    <div class="login-wrapper">
          <h1>网站管理系统</h1>
        <div class="box">
            <div class="content-wrap">
            	<form action="" method="post">
                <h6>登录</h6>
                <input class="form-control" type="text" value="${param.username}"  id="inputUsername" name="username"  placeholder="用户名">
                <input class="form-control" type="password" value="${param.password}" id="inputPassword" required="required" name="password" placeholder="密码">
                <!-- 
			                验证码
                <div class="row">
                	<div class="col-md-5">
		                <input class="form-control" type="text"  id="inputPassword" required="required" name="VERIFICATION_CODE" placeholder="验证码">
	                </div>
	                <div class="col-md-7" style="text-align: right;">
		                <img src="/Kaptcha.jpg" onclick="this.src='/Kaptcha.jpg?n='+new Date()" style="cursor: pointer;">
	                </div>
                </div> -->
                <button type="submit" class="btn-glow primary login">登录</button>
                </form>
        <c:if test="${ shiroLoginFailure ne null }">
				<div class="alert alert-error">${ shiroLoginFailure }</div>
		</c:if>
            </div>
        </div>
    </div>
	</tiles:putAttribute>
</tiles:insertDefinition>