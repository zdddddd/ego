var TT = TAOTAO = {
	checkLogin : function(){
		//从cookie中获取token
		var _ticket = $.cookie("EGO_TOKEN");
		alert(_ticket);
		if(!_ticket){
			return ;
		}
		//调用SSO的根据token获取用户信息的方法
		$.ajax({
			url : "http://sso.ego.com/user/token/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				alert(JSON.stringify(data));
				if(data.status == 200){
					var username = data.data.username;
					var html = username + "，欢迎来到淘淘！<a href=\"http://www.taotao.com/user/logout.html\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				}
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});