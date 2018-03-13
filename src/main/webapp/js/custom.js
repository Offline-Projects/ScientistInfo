//这个函数只支持替换成不包含原字符的情况，包含原字符会陷入死循环
function ReplaceAll(str, sptr, sptr1) {
	while (str.indexOf(sptr) >= 0) {
		str = str.replace(sptr, sptr1);
	}
	return str;
}
//将转义字符串中所有需要转义的字符
function EscapeAll(str) {
	//转义所有反斜杠
	str = str.replace(/\\/g,"\\\\");
	//转义所有双引号
	var reg = new RegExp("\"","g");
	str = str.replace(reg,"\\\"");
	//反会处理完的字符串
	return str;
}
//搜索建议列表
function suggest() {
	var event = window.event || arguments.callee.caller.arguments[0];
	if(event.keyCode != 38 && event.keyCode != 40) {
		sgtKw = $("input#showKeyword").val();
		sgtCtg = $("select#category").val();
		query = sgtCtg + ":*\"" + EscapeAll(sgtKw) +"\"* OR " +sgtCtg + ":*" + EscapeAll(sgtKw) +"*";
		start = 0;
		rows = 10;				
		//推荐搜索选项列表
		$.ajax({
			type : "POST",
			url : "./webapp/ScientistInfo.v2/getScientistList",
			data : {query:query, start:start, rows:rows},
			success : function(data) {
				if (sgtCtg == "国家") {
					sgtCtg = "country";
				}
				
				if (sgtCtg == "姓名") {
					sgtCtg = "name";
				}
			
				if (sgtCtg == "序号") {
					sgtCtg = "order";
				}
				
				if (sgtCtg == "领域") {
					sgtCtg = "domain";
				}
				if (sgtCtg == "单位") {
					sgtCtg = "company";
				}
				var suggestion = [];
				for(i=0; i<Math.min(5, data.numFound); i++) {
					opt = data.scientistList[i][sgtCtg];
					if (suggestion.indexOf(opt) == -1) {
						suggestion.push(opt);
					}
				}				
				$('.searchIpt').AutoComplete({
					'data' : suggestion,
					'itemHeight' : 25,
					'width' : 564
					}).AutoComplete('show');
			},	
			error:function(XMLHttpRequest, textStatus, errorThrown){
				myChart.showLoading();
			}
		});		
	}
}

//提交表单
function toSubmit() {
	searchKw = $("#showKeyword").val();
	console.log(searchKw);
	if (searchKw != "") {
		//对搜索关键字进行一次编码
		$("#keyword").val(escape(searchKw));
		return true;
	} else {
		return true;
	}
}



