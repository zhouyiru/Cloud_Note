// scripts/note.js 编码一定是 utf-8

var SUCCESS = 0;
var ERROR = 1;

$(function(){
	
	//var userId = getCookie('userId');
	//console.log(userId);
	//初始的页号
	$(document).data("page",0);
	//网页加载后立即读取网页列表
	loadPageNotebooks();
	$("#notebook_list").on("click",".more",loadNotebook);
	//网页加载以后, 立即读取笔记本列表
	//loadNotebooks();
	//on() 方法绑定事件可以区别事件源
	//click() 方法绑定事件, 无法区别事件源
	//绑定笔记本列表区域的点击事件
	$('#notebook_list').on(
			'click','.notebook', loadNotes);
	//绑定笔记列表
	$("#note_list").on("click","li.note",loadNote);
	//绑定笔记添加事件
	$('#note_list').on('click', '#add_note', showAddNoteDialog);
	//关闭对话框
	$('#can').on('click','.close,.cancel',closeDialog);
	//监听新建笔记对话框
	$('#can').on('click','.create_note',addNote);
	//绑定笔记子菜单的触发事件
	$("#note_list").on("click",".btn_note_menu",showNoteMenu);
	//监听整体的文档区域, 任何位置点击都要关闭笔记子菜单
	$(document).click(hideNoteMenu);
	//监听回收站按钮被点击
	console.log($("#trash_button"));
	$("#trash_button").click(showTrashBin);
	startHeartbeat();
});
function loadNotebook(){
	var page=$(document).data("page");
	var url="notebook/page.do";
	var data={page:page};
	//从服务器拉去数据
	$.getJSON(url,data,function(result){
		if(result.state==SUCCESS){
			var notebooks=result.data;
			showPageNotebooks(notebooks,page);
			$(document).data("page",page+1);
		}else{
			alert(result.message);
		}
	});
}
/**/
function loadPageNotebooks(){
	var page=$(document).data("page");
	var url="notebook/page.do";
	var data={page:page};
	//从服务器拉去数据
	$.getJSON(url,data,function(result){
		if(result.state==SUCCESS){
			var notebooks=result.data;
			showPageNotebooks(notebooks,page);
			$(document).data("page",page+1);
		}else{
			alert(result.message);
		}
	});
}
function showPageNotebooks(notebooks,page){
	var ul=$("#notebook_list ul");
	if(page==0){
		ul.empty();
	}else{
		//删除more
		ul.find(".more").remove();
	}
	for(var i=0;i<notebooks.length;i++){
		var notebook=notebooks[i];
		var li=notebookTemplate.replace('[name]',notebook.name);
		li=$(li);
		li.data('notebookId',notebook.id);
		ul.append(li);
	}
	ul.append(moreTemplate);
}
var moreTemplate='<li class="online more">'+
'<a><i class="fa fa-plus" title="online" '+
'rel="tooltip-bottom"></i> More</a>'+
'</li>';





/*实现心跳检查*/
function startHeartbeat(){
	 var url = "user/heartbeat.do";
	    setTimeout(function(){
	        $.getJSON(url, function(result){
	            console.log(result.data);
	        });
	    }, 5000);
}
/*监听回收站按钮被点击*/
function showTrashBin(){
	$("#trash_bin").show();
	$("#note_list").hide();
}
/* 关闭笔记子菜单事件处理方法 */
function hideNoteMenu(){
    $('.note_menu').hide();
}
//显示笔记子菜单
function showNoteMenu(){
	//找到菜单对象, 调用show() 方法
    var btn = $(this);
    //如果当前是被选定的 笔记项目 就弹出子菜单
    btn.parent('.checked').next().toggle();
    //btn.parent('.checked') 获取当前按钮的父元素
    //这个元素必须符合选择器'.checked', 如果不
    //符合就返回空的JQuery元素.  
    return false;//阻止点击事件的继续传播!避免传播到document对象时候, 触发关闭菜单事件
}
//添加一个新的笔记
function addNote(){
    var url = 'note/add.do';
    var notebookId=$(document).data('notebookId');
    var title = $('#can #input_note').val();

    var data = {userId:getCookie('userId'),
        notebookId:notebookId,
        title:title};
    //console.log(data);

    $.post(url, data, function(result){
        if(result.state==SUCCESS){
            var note=result.data;
            //console.log(note);
            showNote(note);
            //找到显示笔记列表的ul对象
            var ul = $('#note_list ul');
            //创建新新的笔记列表项目 li 
            var li = noteTemplate.replace(
                    '[title]', note.title);
            li = $(li);
            //设置选定效果
            ul.find('a').removeClass('checked');
            li.find('a').addClass('checked');
            //插入到笔记列表的第一个位置
            ul.prepend(li);
            //关闭添加对话框
            closeDialog();   
        }else{
            alert(result.message);
        }
    });
}
/*添加笔记事件*/
function showAddNoteDialog(){
	 var id = $(document).data('notebookId');
	    if(id){
	        $('#can').load('alert/alert_note.html', function(){
	            $('#input_note').focus();
	        });
	        $('.opacity_bg').show();
	        return;
	    }
	    alert('必须选择笔记本!');
}
/*关闭对话框*/
function closeDialog(){
	$(".opacity_bg").hide();
	$("#can").empty();
}
/*创建笔记点击事件*/
function loadNote(){
	//关闭回收站
	//显示笔记列表：关闭回收站，打开笔记列表
	$("#trash_bin").hide();
	$("#note_list").show();
	var li = $(this);//当前被点击的对象li
	//在被点击的笔记本li增加选定效果
	li.parent().find('a').removeClass('checked');
	li.find('a').addClass('checked');
	var url="note/load.do";
	var data={noteId:li.data("noteId")};
	$.ajax({
		url:url,
		data:data,
		type:"get",
		dataType:"json",
		success:function(result){
			var note=result.data;
			showNote(note);
		}
	});
}
/*显示所完成的笔记*/
function showNote(note){
	$("#input_note_title").val(note.title);
	um.setContent(note.body);
}
/** 笔记本项目点击事件处理方法, 加载全部笔记 */
function loadNotes(){
	var li = $(this);//当前被点击的对象li
	
	//在被点击的笔记本li增加选定效果
	li.parent().find('a').removeClass('checked');
	li.find('a').addClass('checked');
	$(document).data("notebookId",li.data('notebookId'));
	var url = 'note/list2.do';
	var data={notebookId:li.data('notebookId')};
	console.log(data);
	$.getJSON(url, data, function(result){
		if(result.state==SUCCESS){
			var notes = result.data;
			showNotes(notes);
		}else{
			alert(result.message);
		}
	});
}
/** 将笔记列表信息显示到屏幕上 */
function showNotes(notes){
	console.log(notes); 
	//将每个笔记对象显示到屏幕的ul区域
	var ul = $('#note_list ul');
	ul.empty();
	for(var i=0; i<notes.length; i++){
		var note = notes[i];
		var li = noteTemplate.replace(
				'[title]', note.title);
		li = $(li);
		li.data("noteId",note.id);
		ul.append(li);
	}
}

var noteTemplate = '<li class="online note">'+
	'<a>'+
	'<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> [title]<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down btn_note_menu"><i class="fa fa-chevron-down"></i></button>'+
	'</a>'+
	'<div class="note_menu" tabindex="-1">'+
	'<dl>'+
		'<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>'+
		'<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>'+
		'<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>'+
	'</dl>'+
	'</div>'+
	'</li>';

/** 加载笔记本列表数据 */
function loadNotebooks(){
	//利用ajax从服务器获取(get)数据, 使用getJSON方法
	var url = 'notebook/list.do';
	var data = {userId:getCookie('userId'),
			name:'demo'};
//	$.getJSON(url, data, function(result){
//		console.log(result);
//		if(result.state==SUCCESS){
//			var notebooks = result.data;
//			//在showNotebooks方法中将全部的
//			//笔记本数据 notebooks 显示到 
//			// notebook-list 区域
//			showNotebooks(notebooks);
//		}else{
//			alert(result.message);
//		}
//	});
	$.ajax({
		url:url,
		type:"post",
		data:data,
		dataType:"json",
		success:function(result){
			console.log(result);
			if(result.state==SUCCESS){
				var notebooks = result.data;
				//在showNotebooks方法中将全部的
				//笔记本数据 notebooks 显示到 
				// notebook-list 区域
				showNotebooks(notebooks);
			}else{
				alert(result.message);
			}
		}
	});
}
/** 在notebook-list区域中显示笔记本列表 */
function showNotebooks(notebooks){
	//找显示笔记本列表的区域的ul元素
	//遍历notebooks数组, 将为每个对象创建一个li
	//元素, 添加到 ul元素中.
	var ul = $('#notebook_list ul');
	ul.empty();
	for(var i=0; i<notebooks.length; i++){
		var notebook = notebooks[i];
		var li = notebookTemplate.replace(
				'[name]', notebook.name);
		li = $(li);
		//将 notebook.id 绑定到 li
		li.data('notebookId', notebook.id);
		
		ul.append(li);
	}
}
var notebookTemplate = 
	'<li class="online notebook">'+
	'<a><i class="fa fa-book" title="online" '+
	'rel="tooltip-bottom"></i> [name]</a>'+
	'</li>';










