castapp.init();
var marqueeInterval = new Array();
var sessionId = common.getPublicItem("sessionId");
var user = common.getPublicItem("sessionUser");
var userId = user;
var areaId='11111';
var sliders;

//字符串前后添加双引号
function doubleQuot(str) {
	return '"' + str + '"';
}

function getHomeSlider() {
	var requestPath = common.SERVICE_PREFIX_ADDRESS + 'manages/slider/getInnerHomeSlider.do';
	console.log(requestPath);
	mui.ajax(requestPath, {
		data: {
			'sessionId': sessionId
		},
		dataType: "json",
		type: "get",
		success: function(data) {
			sliders = data["data"];
			console.log("getHomeSlider ok :" + "number:" + sliders.length + JSON.stringify(data));
			var html = '<div class="mui-slider-group mui-slider-loop">';
			var htmlIndicator = '<div class="mui-slider-indicator mui-text-right">';

			if(sliders.length > 0) {

				var lastIndex = sliders.length - 1;
				var lastImgUrl = common.getImagePath(sliders[lastIndex]["sliderImg"]);
				var lastTitle = sliders[lastIndex]["sliderName"];

				//重复最后一个item
				html += '<div id=' + doubleQuot("sItem" + lastIndex) + ' class="mui-slider-item mui-slider-item-duplicate"><a href="#"><img class="slider-img" src=' + doubleQuot(lastImgUrl) + '><p class="mui-slider-title">' + lastTitle + '</p></a></div>';

				var firstIndex = 0;
				var firstImgUrl = common.getImagePath(sliders[firstIndex]["sliderImg"]);
				var firstTitle = sliders[firstIndex]["sliderName"];

				for(var i = 0; i < sliders.length; i++) {
					var tempUrl = common.getImagePath(sliders[i]["sliderImg"]);
					tempTitle = sliders[i]["sliderName"];

					//中间item
					html += '<div id=' + doubleQuot("sItem" + i) + ' class="mui-slider-item"><a href="#"><img class="slider-img" src=' + doubleQuot(tempUrl) + '><p class="mui-slider-title">' + tempTitle + '</p></a></div>';

					if(i == 0) {
						htmlIndicator += '<div class="mui-indicator mui-active"></div>';
					} else {
						htmlIndicator += '<div class="mui-indicator"></div>';
					}

				}
				//重复第一一个item
				html += '<div id=' + doubleQuot("sItem" + firstIndex) + ' class="mui-slider-item mui-slider-item-duplicate"><a href="#"><img class="slider-img" src=' + doubleQuot(firstImgUrl) + '><p class="mui-slider-title">' + firstTitle + '</p></a></div>';
				html += '</div>';

				htmlIndicator += '</div>';

				console.log(html + htmlIndicator);

				castapp.id("slider").innerHTML = html + htmlIndicator;

				//轮播
				mui("#slider").slider({
					interval: 3000
				});

			} else {
				castapp.id("slider").innerHTML = html + '</div>';
				//轮播
				mui("#slider").slider({
					interval: 3000
				});
			}
			var date = new Date();
			var month = date.getMonth() + 1;
			var title = month + '月实时数据';
			var sItems = castapp.className("mui-slider-item");
			for(var i = 0; i < sItems.length; i++) {
				castapp.click(sItems[i], function(resp) {

					var id = resp["id"];
					var index = id.substring(5);

					var url = sliders[index]["sliderLink"];

					console.log("click slider :" + index + " openURL:" + url);

					plus.runtime.openURL(url);

				});
			}

		},
		error: function(data) {
			console.log("getHomeSlider failed:" + JSON.stringify(data));
			alert("网络原因查询数据出错");
		}
	});
}

function getRecommendKbList() {
	var url = common.SERVICE_PREFIX_ADDRESS + 'app/kbList/getSysKbList.do';
	console.log("getRecommendKbList:" + url);
	mui.ajax(url, {
		data: {
			'sessionId': sessionId
		},
		dataType: "json",
		type: "post",
		success: function(data) {
			var kbList = data["rows"];
			console.log(kbList);
			setKblist(kbList);
		},
		error: function(data) {
			alert("网络原因查询数据出错");
		}
	});
}

function setKblist(kbList) {
	var html = '';
	for(var i = 0; i < kbList.length; i++) {
		var o = kbList[i];
		var kbUrl=o['kbLink'];
		var title=o["kbTitle"];
		var createDate = common.formatterDateYMD(o['createDate']);
		html = html +
			' <li class="mui-table-view-cell mui-media" onclick="openLinkUrl(\'' + kbUrl + '\',\'' + title + '\')">' +
			' <a href="javascript:;">'+
		' <div class="mui-media-body">' +
		   "知识类型_"+o["kbTitle"] +
			'<p class="mui-ellipsis">' +
			o["content"] + '</p>' +
			'<p class="mui-ellipsis" id="kbMe">' +
		   "发布人:"+	o["createUser"] +'<span id="creT">'+"创建时间:"+createDate+'</span>' +'</p>'+
			'</div>' +
			'</a>' +
		'</li>';
	}
	castapp.id('kblist').innerHTML += html;
	console.log(html);
	//listClick();
}

function openLinkUrl(url,title){
	localStorage.setItem("oTitle", title);
	var httpHeader = "http://";
	if(url.indexOf(httpHeader) == -1) {
		url = httpHeader + url;
	}
	common.openUrl(url);
}

/*//知识列表点击
function listClick(){
	var knowledge_list = document.getElementById("kblist");
	var li_list = knowledge_list.getElementsByTagName("li");
	for (var i = 0; i < li_list.length; i++) {
		var ll = li_list[i];
		castapp.click(ll, function(o){
			knowledgeClick(o);
		});
}
}*/
/*//知识列表点击跳转
function knowledgeClick(o){
	var kburl = $(o).attr('kbLink');
	var kbTitle=o["kbTitle"];
	var userId=o["createUser"];
	var knContent=o["content"];
	alert(kburl);
	localStorage.setItem("kbList.KbTitle",kbTitle);
	localStorage.setItem("kbList.user",userId);
	localStorage.setItem("kbList.content",knContent);
	castapp.newInterface({
		url: 'kbContentDetail.html',
		id: 'kbContentDetail',
		showType: "pop-in",
		showTime: '200'
	});
}*/

function plusReady() {
	if(plus.os.name == "Android") {
		main = plus.android.runtimeMainActivity();
		Intent = plus.android.importClass("android.content.Intent");
		File = plus.android.importClass("java.io.File");
		Uri = plus.android.importClass("android.net.Uri");
		MimeTypeMap = plus.android.importClass("android.webkit.MimeTypeMap");
	}
}

//获取头条
function getHeadlines() {
	var urla = common.SERVICE_PREFIX_ADDRESS + 'app/news/getTopTenNews.do';
	mui.ajax(urla, {
		data: { 
			"userId": userId,
			"areaId": areaId,
			"sessionId": sessionId
		},
		dataType: "json",
		type: "post",
		success: function(data) {
			console.log("headline ok:" + JSON.stringify(data));
			setupHeadlines(data["data"]);
		},
		error: function(data) {
			console.log('headline failed:' + JSON.stringify(data));
		}
	});

}

//公告头条ui
function setupHeadlines(data) {
	var headlineData = data;
	var html = "";
	for(var i=0;i<data.length;i++){
		var o=data[i];
		var newsUrl=o["newsLink"];
		var title=o["newsTitle"];
		html=html+
		'<li  onclick="openLinkUrl(\'' + newsUrl + '\',\'' + title + '\')">'+o["newsTitle"]+'</li>';
	}
	castapp.id('headlines-table').innerHTML += html;
	if(data.length % 2 == 1) {
		html = html + '<li style="height:2.2rem;"></li>';
	}
	castapp.id('headlines-table').innerHTML += html;
	//castapp.id('scroll-content2').innerHTML = castapp.id('scroll-content1').innerHTML;

	if(data.length > 2) {
		sliderLoop();
	}

}
//头条滚动
function sliderLoop() {
	var marqueeHeight = castapp.id("scroll-content").offsetHeight;
	marqueeInterval[0] = setInterval(function() {
		clearInterval(marqueeInterval[1]);
		marqueeInterval[1] = setInterval(function() {
			if(castapp.id("scroll-content").scrollTop == castapp.id("scroll-content2").offsetHeight) {
				castapp.id("scroll-content").scrollTop = 0;
			} else {
				castapp.id("scroll-content").scrollTop++;
			}
			if(castapp.id("scroll-content").scrollTop % marqueeHeight == 0) {
				clearInterval(marqueeInterval[1]);
			}
		}, 20);

	}, 4000);
}

$(document).ready(function() {
	/*window.addEventListener('noticeReadEvent', function(event) {
		
		//重新获取公告
		
	});*/
	//清除定时器
		clearInterval(marqueeInterval[0]);
		clearInterval(marqueeInterval[1]);
		marqueeInterval = new Array();
	getHeadlines();
	getHomeSlider();
	getRecommendKbList();
	mui.plusReady(function() {
		plusReady();
	});
});