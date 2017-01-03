/**
 * 
 */

drag = 0;
move = 0;

var ie = document.all;
var nn6 = document.getElementById && !document.all;
var isdrag = false;
var y, x;
var oDragObj;

function moveMouse(e) {
	if (isdrag) {
		oDragObj.style.top = (nn6 ? nTY + e.clientY - y : nTY + event.clientY
				- y)
				+ "px";
		oDragObj.style.left = (nn6 ? nTX + e.clientX - x : nTX + event.clientX
				- x)
				+ "px";
		return false;
	}
}

function initDrag(e) {
	var oDragHandle = nn6 ? e.target : event.srcElement;
	var topElement = "HTML";
	while (oDragHandle.tagName != topElement
			&& oDragHandle.className != "dragAble") {
		oDragHandle = nn6 ? oDragHandle.parentNode : oDragHandle.parentElement;
	}
	if (oDragHandle.className == "dragAble") {
		isdrag = true;
		oDragObj = oDragHandle;
		nTY = parseInt(oDragObj.style.top + 0);
		y = nn6 ? e.clientY : event.clientY;
		nTX = parseInt(oDragObj.style.left + 0);
		x = nn6 ? e.clientX : event.clientX;
		document.onmousemove = moveMouse;
		return false;
	}
}
document.onmousedown = initDrag;
document.onmouseup = new Function("isdrag=false");

function MM_reloadPage(init) { // reloads the window if Nav4 resized
	if (init == true)
		with (navigator) {
			if ((appName == "Netscape") && (parseInt(appVersion) == 4)) {
				document.MM_pgW = innerWidth;
				document.MM_pgH = innerHeight;
				onresize = MM_reloadPage;
			}
		}
	else if (innerWidth != document.MM_pgW || innerHeight != document.MM_pgH)
		location.reload();
}
MM_reloadPage(true);

function bbimg(o) {
	var zoom = parseInt(o.style.zoom, 10) || 100;
	zoom += event.wheelDelta / 12;
	if (zoom < 50) {
		zoom = 50;
	}
	if (zoom > 300) {
		zoom = 300;
	}

	if (zoom > 0) {
		o.style.zoom = zoom + '%';
		$("#zoomd").css({
			'text-align' : 'center'
		}).html(zoom + "%").show().fadeOut();
	}
	return false;
}

jQuery(document).ready(function($) {
	document.getElementById("bgdiv").style.filter = "alpha(opacity=20%)";
	$('#a2').click(function() {
		$("#images1").attr("src", "${ctx }/uploads/20141001145632178319.JPG");
		$('.theme-popover-mask').fadeIn(100);
		$('.theme-popover').slideDown(200);
	});
	$('.theme-poptit .close').click(function() {
		$("#images1").attr("src", "");
		$('.theme-popover-mask').fadeOut(100);
		$('.theme-popover').slideUp(200);
	});
	$('.theme-popover-mask').click(function() {
		$("#images1").attr("src", "");
		$('.theme-popover-mask').fadeOut(100);
		$('.theme-popover').slideUp(200);
	});
});

function huanyuan() {
	if (oDragObj == null) {
		oDragObj = block1;
	}
	oDragObj.style.top = "0px";
	oDragObj.style.left = "0px";
	$("#images1").css("zoom", "100%");
	$("#zoomd").css({
		'text-align' : 'center'
	}).html("100%").show().fadeOut();
}

var nowPaths = null;
var nowIndex = 0;
var nowLength = 0;
function showDiv(paths, index) {
	nowPaths = paths;
	nowIndex = index;
	nowLength = nowPaths.length;
	changeImg(0);
}

function changeImg(n) {
	nowIndex = nowIndex + (n - 0);

	var filePath = nowPaths[nowIndex];
	$("#images1").attr("src", filePath);
	$('.theme-popover-mask').fadeIn(100);
	$('.theme-popover').slideDown(200);

	if (oDragObj == null) {
		oDragObj = block1;
	}
	oDragObj.style.top = "0px";
	oDragObj.style.left = "0px";

	$("#imgNum").text((nowIndex +1) + " / " + nowPaths.length);

	$("#images1").css("zoom", "100%");
}

function showChangeButton(event) {
	var top, left, oDiv;
	oDiv = document.getElementById("tbg");
	top = getY(oDiv);
	left = getX(oDiv);

	var l = (event.clientX - left + document.documentElement.scrollLeft) - 2;
	var t = (event.clientY - top + document.documentElement.scrollTop) - 2;

	// div总宽600 当鼠标移动到相对左侧200时 显示左 当鼠标移动到相对右侧200时显示右
	if (l > 0 && l < 300 && nowIndex > 0) {
		$("#zoomd")
				.css({
					'text-align' : 'left',
					'padding-left' : '50px'
				})
				.html(
						"<a href='javascript:void(0)' style='font-size:50px;' onclick='changeImg(-1)'>&lt;</a>")
				.show();
	} else if (l > 500 && l < 800 && nowIndex < (nowLength - 1)) {
		$("#zoomd")
				.css({
					'text-align' : 'right',
					'padding-right' : '50px'
				})
				.html(
						"<a href='javascript:void(0)' style='font-size:50px;' onclick='changeImg(1)'>&gt;</a>")
				.show();
	} else {
		$("#zoomd").css({
			'text-align' : 'center'
		}).html("").hide();
	}
}

function getX(obj) {
	var parObj = obj;
	var left = obj.offsetLeft;
	while (parObj = parObj.offsetParent) {
		left += parObj.offsetLeft;
	}
	return left;
}

function getY(obj) {
	var parObj = obj;
	var top = obj.offsetTop;
	while (parObj = parObj.offsetParent) {
		top += parObj.offsetTop;
	}
	return top;
}

function showImg(nowSrc) {
	if (nowSrc != null && nowSrc != "") {
		// 当前点击的img src
		var index = 0;
		var paths = new Array();
		var imgNums = 0;
		// 取得同批img的路径做批量查看
		$("img").each(
				function(i) {
						var src = $(this).attr("src");
						if (src != null && src != "" && $(this).hasClass("enlarge-img")) {
							paths[imgNums] = src;
							if (nowSrc == paths[imgNums]) {
								index = imgNums;
							}
							imgNums ++;
						}
				});

		/*
		 * for(var i = 0;i < objs.length;i++){ paths[i] = objs[i].value; if(src ==
		 * paths[i]){ index = i; } }
		 */
		if(imgNums != 0){
			imgNums = 0;
			nowPaths = null;
			nowIndex = 0;
			showDiv(paths, index);
		}
	}
}