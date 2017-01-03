/*
	表格组件
	options:
		method: [read|postRead]
		title: 标题文本
		columns:列数组
			- format: 如果是字符串分两种格式，1.字段名 2.表达式(<a href='user/{id}'>{name}</a>)，如果是函数则函数处理内容,函数参数为record
			- style: class样式
		src: ajax加载URL
		autoload: 是否自动加载ajax数据
*/
(function( $ ) {
$.widget( "ui.dataTable", {
	options:{
		method:'postRead',
		pageSize:5,
		autoload:true
	},
	_create:function(){
		this._page = 1;
		this.element.append('<tbody/>');
		this._createFooter();
	},
	_createFooter:function(){
		this.footer = $('<tfoot><tr><td><div class="pagination">'
					+ '<ul class="ui-datatable-pager "></ul>'
					+ '</div></td></tr></tfoot>');
		this.element.append(this.footer);
		if(this.footer.length == 0) return;
		this._addEvent();
	},
	_init:function(){
		this.options.url = this.element.data('url');
		this._initColumns();
		this.footer.pager({
			pageSize:this.options.pageSize,
			autoload:this.options.autoload,
			change:$.proxy(function(event, data){
			this.options.pageSize = data.pageSize;
			this.setPage(data.page);
		},this)});
	},
	_addEvent:function(){
		this._on(true, this.element,{
			'click.sortable':function(event){
				this.element.find('th.sorting').removeClass('sorting');
				$(event.currentTarget).addClass('sorting');
				$(event.currentTarget).toggleClass('desc');
				this._reload();
			}
		});
		this._filterInit();
	},
	_filterInit:function(){
		if(this.options.filter){
			var filters;
			if(typeof(this.options.filter) == "string"){
				filters = $(this.options.filter);
			}
			else{
				filters =  this.options.filter;
			}
			this._filter = this._getFilters(filters);
			this._on(filters,{
				'click .btn-query':$.proxy(function(){
					this._query(filters);
				},this)
			});
			if(filters.data('auto') == true){
				this._on(filters,{
					'change input,select':$.proxy(function(){
						this._query(filters);
					},this)
				});
			}
		}
	},
	_query:function(filters){
		var filter = this._getFilters(filters);
		this.reload(filter);
	},
	_getFilters:function(filters){
		var filter = {};
		filters.find('input[name],select[name]').each(function(){
			filter[$(this).attr('name')] = $(this).val();
		});
		return filter;
	},
	_initColumns:function(){
		var cols = this.element.find('th[data-format],th[data-fill-method]');
		this.element.find('tfoot td').attr('colspan',cols.length);
		this._columns = [];
		for(var i = 0; i < cols.length; i++){
			var col = cols.eq(i);
			var column = {};
			column.format = col.data('format');
			column.type = col.data('type');
			column.fillMethod = col.data('fillMethod');
			this._columns.push($.extend({type:'data'},column));
			col.find('span.sorticon').remove();
			if(col.data('order')){
				col.append(' ');
				col.append('<span class="sorticon desc icon-angle-down"></span>');
				col.append('<span class="sorticon asc icon-angle-up"></span>');
			}
		}
	},
	setPage:function(page){
		this._page = page;
		this._reload();
	},
	_reload:function(){
		$[this.options.method]({url:this.options.url,traditional:true,
			data:$.extend(this._getParam(),this._filter),
			success:$.proxy(function(pager){
			this.footer.pager('totalCount',pager.totalElements);
			this._fillBody(pager);
		},this)});
	},
	reload:function(filter){
		this._filter = filter;
		this._reload();
	},
	_getParam:function(){
		return $.extend({'p:page':this._page-1,'p:size':this.options.pageSize},this._getOrderParams());
	},
	_getOrderParams:function(){
		var cols = this.element.find('th.sorting');
		var order = {'p:sort':[]};

		for(var i = 0; i < cols.length; i++){
			var col = cols.eq(i);
			var property = col.data('order');
			var sort = property;
			if(col.hasClass('desc')) sort += ',desc';
			else sort += ',asc';
			order['p:sort'][i] = sort;
		}
		return order;
	},
	_fillBody:function(pager){
		this.element.find('tbody').empty();
		for(var i = 0; i < pager.totalElements; i++){
			this._fillRow(pager.content[i]);
		}
	},
	_fillRow:function(record){
		if(!record) return;
		var html = '<tr>';
		for(var i = 0; i < this._columns.length; i++){
			html += '<td>' + this._fillCell(record,this._columns[i]) + '</td>';
		}
		html += '</tr>';
		this.element.find('tbody').append(html);
	},
	_fillCell:function(record,column){
		if(column.fillMethod && typeof(this.options[column.fillMethod]) == 'function'){
			return this.options[column.fillMethod].apply(this.element,[record,column]);
		}
		if(typeof(column.format) == 'string'){
			try{
				if(/^([0-9a-zA-Z_])+(\.[0-9-a-zA-Z_]+)*$/.test(column.format)){
					var val = this._getRecordValue(record,column.format);
					if(!val) val = '';
					return val;
				}
			}
			catch(e){}
			var text = this._propertyParser(record,column.format);
			text = this._convertParser(record,text);
			if(text == column.format);
			return text;
		}
	},
	_propertyParser:function(record,format){
		var el = format.match(/{([0-9a-zA-Z._]+)}/);
		if(el == null) return format;
		var result = format;
		do{
			result = result.replace(el[0], this._getRecordValue(record,el[1]));
			el = result.match(/{([0-9a-zA-Z._]+)}/);
		}while(el != null);
		return result;
	},
	_getRecordValue:function(record,name){
		return common.getRecordValue(record,name);
	},
	_convertParser:function(record,format){
		var regex = /convert\[([0-9a-zA-Z._]+)\]\[([0-9a-zA-Z._]+)\]/;
		var el = format.match(regex);
		if(el == null) return format;
		var result = format;
		do{
			result = result.replace(el[0], $.codemap[el[1]][this._getRecordValue(record,el[2])]||'');
			el = result.match(regex);
		}while(el != null);
		return result;
	}
});

}( jQuery ) );

/*
 * YOUI bootstrap pager
 *
 * Copyright (c) 2013 zhouyang
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 *
 */
(function($) {
$.widget("ui.pager", {
	options:{
		pageSize:10,		// 每页记录数
		page:1,				// 当前面码
		pageCount:0,		// 最大页数
		pageIndexSize:5,	// 只显示５个页码
		change:$.noop,
		autoload:true
	},
	_create:function(){
		this._addClass();
		this._createHtml();
		this._addEvent();
	},
	_init:function(){
		if(this.options.autoload) this.reload();
	},
	_addEvent:function(){
		this._on(false,this.element,{
			'click li:has(a)':function(event){
				if($(event.currentTarget).is('.disabled,.active')) return false;
				this.page($(event.currentTarget).data('page'));
				return false;
			}
		});
	},
	_addClass:function(){
		if(!this.element.hasClass('pagination')){
			this.element.addClass('pagination');
		}
	},
	_createHtml:function(){
		var html = '';
		if(this.options.pageCount == 0){
			html += '<li><span>未找到记录</span></li>';
		}
		else{
			// 计算页码按钮个数,如果总页数小于可显示页码总数,则用总页数替代页码个数
			var count = this.options.pageIndexSize < this.options.pageCount?this.options.pageIndexSize:this.options.pageCount;
			// 计算第一个页面的索引值
			var startPage = this.options.page + this.options.pageIndexSize - 1 > this.options.pageCount ?
					this.options.pageCount - count + 1 : this.options.page;
			// 计算前进和后退按钮的可用状态
			var prevCls='',nextCls='';
			if(this.options.page == 1) firstCls = prevCls = ' class="disabled"';
			else if(startPage == 1) firstCls = ' class="disabled"';
			if(this.options.page == this.options.pageCount) nextCls = endCls = ' class="disabled"';
			else if(startPage + count > this.options.pageCount) endCls = ' class="disabled"';
			
			html += '<li' + prevCls + ' data-page="' + (this.options.page - 1) 
				+ '"><a href="#"><span class="icon-fast-backward"></span></a></li>';
			if(startPage != 1){
				html += '<li data-page="1"><a href="#">1</a></li>';
			}
			if(startPage > 2 && this.options.pageCount > 2){
				html += '<li><span>...</span></li>';
			}
			for(var i = 0; i < count; i++){
				var active = '';
				if(this.options.page == startPage + i) active = ' class="active"';
				html += '<li' + active + ' data-page="' + (startPage + i) + '"><a href="#">' + (startPage + i) + '</a></li>';
			}
			if(startPage + count < this.options.pageCount){
				html += '<li><span>...</span></li>';
			}
			if(startPage + count - 1< this.options.pageCount){
				html += '<li data-page="' + this.options.pageCount + '"><a href="#">' + this.options.pageCount + '</a></li>';
			}
			html += '<li' + nextCls + ' data-page="' + (this.options.page + 1) 
				+ '"><a href="#" ><span class="icon-fast-forward"></span></a></li>';
		}
		this.element.html(html);
	},
	/*pageCount:function(count){
		if(!count) return this.options.pageCount;
		this.options.pageCount = count;
		this._createHtml();
	},*/
	page:function(page){
		if(!page) return this.options.page;
		this.options.page = page;
		this.reload();
	},
	totalCount:function(count){
		var pageCount = 0;
		if(count > 0) pageCount = parseInt(count / this.options.pageSize) + (count % this.options.pageSize>0?1:0);
		this.options.pageCount = pageCount;
		this._createHtml();
	},
	reload:function(){
		this._trigger('change',{},{page:this.options.page,pageSize:this.options.pageSize});
	}
});
})(jQuery);
