DWZ.regPlugins.push(function($p){
	$('input[data-provide="datepicker"],.datepicker',$p).each(function(){
		var $this=$(this);
		$(this).datepicker({todayHighlight:true,weekStart:1,format:$this.attr("dateFmt")?$this.attr("dateFmt"):"yyyy-mm-dd",autoclose:$this.attr("autoclose")?$this.attr("autoclose"):true});
		/*$(this).bind('focus',function(){
			$(this).datepicker('update');
		});*/
	});
	$('input[data-provide="datetimepicker"],.datepicker',$p).each(function(){
		var $this=$(this);
		$(this).datetimepicker({format:$this.attr('format')?$this.attr('format'):'yyyy-mm-dd hh:ii:ss',autoclose:$this.attr("autoclose")?$this.attr("autoclose"):true});
	});
	// grid checkbox/radio 的选择处理
	$('table tbody:has(:radio,:checkbox)', $p).on('click','tr',function(event){
		if($(event.target).is(':radio,:checkbox')) return;
		var input = $(event.target).closest('tr').find(':radio,:checkbox');
		if(input.is(':checkbox')) input.prop('checked',!input.prop('checked'));
		else input.prop('checked',true);
	});
	
	$('#dashboard-menu').on('click','a[target],a.dropdown-toggle',function(event){
		if($(this).hasClass('dropdown-toggle')){
			var $item = $('#dashboard-menu li.active>.dropdown-toggle').not(this).parent();
		    $item.toggleClass("active");
		    if ($item.hasClass("active")) {
		      $item.find(".submenu").slideDown("fast");
		    } else {
		      $item.find(".submenu").slideUp("fast");
		    }
		}
		else{
			$('#dashboard-menu li.selected').removeClass('selected');
			$('#dashboard-menu a.active').removeClass('active');
			$(this).addClass('active').parents('li').addClass('selected');
		}
	});
});
$(document).bind(DWZ.eventType.pageClear,function(event){
	var box = event.target;
	if ($.fn.xheditor) {
		$("textarea.editor", box).xheditor(false);
	}
	if($.fn.datepicker){
		$('input[data-provide="datepicker"],.datepicker',box).each(function(){
			$(this).datepicker('remove');
		});
	}
	if($.fn.datetimepicker){
		$('input[data-provide="datetimepicker"],.datetimepicker',box).each(function(){
			$(this).datetimepicker('remove');
		});
	}
});