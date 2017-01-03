/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.filebrowserImageUploadUrl= "/uploadImgs";
	config.image_previewText=' '; //预览区域显示内容
	config.width = 850;
    config.height = 400;
	config.toolbar = 'MyToolbar';//修改默认工具栏按钮     

    config.toolbar_MyToolbar =     
    [     
		['Source','Preview','Templates'],     
		['Cut','Copy','Paste','PasteText','PasteFromWord'],     
		['Undo','Redo','-','Find','Replace'],   
		['Image','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],     
		['NumberedList','BulletedList','-','Outdent','Indent','Blockquote','CreateDiv'],   
		'/',     
		   
		['Styles','Format','Font','FontSize'],   
		['Bold','Italic','Underline','Strike','-','Subscript','Superscript'], 
		['TextColor','BGColor'],  
		['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],     
		['Maximize', 'ShowBlocks']
    ]; 
   // 是否强制复制来的内容去除格式 plugins/pastetext/plugin.js 
    config.forcePasteAsPlainText =true; //去除
    config.fullPage= true;
    config.allowedContent= true;
};
