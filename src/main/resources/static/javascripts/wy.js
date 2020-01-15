/**
 * 
 */

document.write("<script language=javascript src='dx.js'></script>");



//通过radioButton查询担保环
function searchDBCircle(skip){
	$("input[type='checkbox']").prop("checked","true");
	 var num=3;
/*	 if(document.getElementById('radiobutton1').checked){ num=1;}*/
	 if(document.getElementById('radiobutton2').checked){ num=2;}
	 if(document.getElementById('radiobutton3').checked){ num=3;}
	 if(document.getElementById('radiobutton4').checked){ num=4;}
	 if(document.getElementById('radiobutton5').checked){ num=5;}
	 
		jQuery.ajax({
			type: 'POST',
			url: 'searchDBcircle.do',
			dataType: 'json',
			data:{num:num,
				  skip:skip},
			beforeSend:function (){
		        showModal();  
		    }, 
			success: function(data){
				hideModal();
				var length=data.nodes.length;
				if(length==0){
					alert("没有查询结果！请重新输入。");
					$("#nex").attr('disabled',true);
				}else{
					$("#nex").attr('disabled',false);
					for(var i=0;i<length;i++)
					{
						if(data.nodes[i].label!="C") continue;
						if(data.nodes[i].khmc.indexOf("小额贷款")>=0||data.nodes[i].khmc.indexOf("小贷")>=0)
						data.nodes[i].xiaodai=true;
						if(data.nodes[i].khmc.indexOf("地产")>=0)
							data.nodes[i].dichan=true;
					}
					
					svg.select("g").selectAll("g").attr("transform","translate(0,0)scale(1)");
					zooma.scale(1);
					zooma.translate([0,0]);
					update(data);
					showInfo();
				}
			},
			error: function(){
				hideModal();
				alert("连接失败！");
			}
		}); 
}
//通过输入框查询担保环
function searchDBCircleByInput(skip){
	var num=document.getElementById("searchDBchain").value;
	
	jQuery.ajax({
		type: 'POST',
		url: 'searchDBcircle.do',
		dataType: 'json',
		data:{num:num,skip:skip},
		beforeSend:function(){
	        showModal();  
	    }, 
		success: function(data){
			hideModal();
			if(data.nodes.length==0){
				alert("没有这种类型的担保环！请重新输入。");
				$("#nex").attr('disabled',true);
			}else{
				$("#nex").attr('disabled',false);
				 svg.select("g").selectAll("g").attr("transform","translate(0,0)scale(1)");
				zooma.scale(1);
				zooma.translate([0,0]);
				update(data);
				showInfo();
			}
		},
		error: function(){
			hideModal();
			alert("连接失败！");
		}
	}); 
	
}
//显示摘要图
function show(){
	
		jQuery.ajax({
			type: 'POST',
			url: 'graph.do',
			dataType: 'json',
//12.24			beforeSend:function(){
//		        showModal();  
//		    }, 
			success: function(data){
				alert("更新数据库完成！");
				hideModal();
//				console.log(data);
//				update(data);
//				$("#shownode").prop("checked",false);
//				$("#showlink").prop("checked",false);
//				showNodetext($("#shownode"));
//				showLinktext($("#showlink"));
//				showInfo();
			
		},
		error: function(){
			hideModal();
			alert("连接失败！");
		}
	}); 
 }

//通过姓名模糊查找节点
function search(){

	var name=document.getElementById("searchAcount").value;
	var options = $("#searchSort option:selected");
	console.log(options.val());
	switch(options.val()){
		case "accName":url = 'findByCountName.do';break;
		case "accNum":url = 'findByCountNumber.do';break;
		case "comName":url = 'findByCompanyName.do';break;
		case "comId":url = 'findByCompanyJgdm.do';break;
	}
	console.log(url);
	jQuery.ajax({
		type: 'POST',
		data: {name:name},
		url: url,
		dataType: 'json',
		beforeSend:function (){
        	showModal();  
    	}, 
		success: function(data){
			hideModal();
			if(data.nodes.length==0){
				alert("没有查询结果！请重新输入。");
			}else{
				update(data);
			    showInfo();}
		},
		error: function(){
			hideModal();
			alert("连接失败");
		}
	}); 
}

//查询交易链
function searchJYChain(){
	var min=document.getElementById("minJY").value;
	var max=document.getElementById("maxJY").value;
		jQuery.ajax({
			type: 'POST',
			url: 'findJYChain.do',
			dataType: 'json',
			data:{
				min:min,
				max:max
				},
			success: function(data){
			if(data.nodes.length==0){
				alert("没有查询结果！请重新输入。");
			}else{
				update(data);
			    showInfo();}
			},
			error: function(){
			}
		}); 
 }

/*交易分析*/
function searchJYByFD(){
	var min = document.getElementById("minJYM").value;
	var max = document.getElementById("maxJYM").value;

	var hop = $("#hop option:selected").val();
	

	var date = document.getElementById("dateRange").value.replace(/-/g,'').replace(/\s+/g, "");
	var startDate = date.substring(0,8);
	var endDate = date.substring(8,16);
	
	var ID;
	arcd.attr("godbye",function(d){ID=d.id;});
	var nodes=JSON.stringify(graph.nodes);
	var links=JSON.stringify(graph.links);
	
	console.log("hop="+hop);
	console.log("ID="+ID);
	
	jQuery.ajax({
		type: 'POST',
		url: 'extendByInput.do',
		dataType: 'json',
		data:{
			id:ID,
			nodes:nodes,
			links:links,
			minJYJE:min,
			maxJYJE:max,
			hop:hop,
			minJYRQ:startDate,
			maxJYRQ:endDate
		},
		beforeSend:function (){
	        showModal();  
	    }, 
		success: function(data){
			hideModal();
			if(data.nodes.length==0){
				alert("没有查询结果！请重新输入。");
			}else{
				update(data);
			    showInfo();}
		},
		error: function(){
			hideModal();
			alert("连接失败");
		}
	});

}
/*查找前n的担保环*/
function searchTopDbcircleOflength(type){

	jQuery.ajax({
		type: 'POST',
		url: 'searchTopDbcircle.do',
		data:{type:type},
		dataType: 'json',
		beforeSend:function(){
	        showModal();  
	    }, 
		success: function(data){
			hideModal();
			update(data);
			showInfo();
		},
		error: function(){
			hideModal();
			alert("连接失败！");
		}
	}); 
}
/*
 * 输入机构名称或机构代码搜索其所在的担保圈,并以列表的形式显示
 */
var search_list;// = [{"itemName":"上海"},{"itemName":"上海"},{"itemName":"上海"},{"itemName":"上海"}];
function searchList(){
	var input=document.getElementById("searchAcount").value;
	var options = $("#searchSort option:selected");
	var url;
	console.log(options.val());
	switch(options.val()){
		case "comName":url = 'searchDBcircleByCompanyName.do';break;
		case "comId":url = 'searchDBcircleByCompanyJGDM.do';break;
	}
	
	jQuery.ajax({
		type: 'POST',
		data: {name:input},
		url: url,
		dataType: 'json',
		beforeSend:function (){
        	showModal();  
    	}, 
		success: function(data){
			hideModal();
			console.log(data);
			search_list=data.DbCircleInfo;
			showTable(4,true);	
		},
		error: function(){
			hideModal();
			alert("连接失败");
		}
	}); 
}
/*获取担保圈统计信息*/
var dbdata;
function getDbCircleInfo(){

	jQuery.ajax({
		type: 'POST',
		url: 'searchDBcircleInfo.do',
		async: false,
		dataType: 'json',
		success: function(data){
			dbdata = data;
			console.log(dbdata);
		},
		error: function(){
			alert("连接失败！");
		}
	}); 
}
/*获取担保列表*/
var dblist;
//dblist = [{"itemName":"北京"},{"itemName":"北京"},{"itemName":"北京"},{"itemName":"北京"}];
function getDBCircleList(){
	jQuery.ajax({
		type: 'POST',
		url: 'searchDBcircleListInfo.do',
		dataType: 'json',
		success: function(data){
			dblist = data.DbCircleListInfo;
		},
		error: function(){
			alert("连接失败！");
		}
	}); 
}
/*通过点击列表显示担保环*/
function searchDBCircleByList(num,skip){
	console.log("2");
	console.log(num+"------"+skip);
	jQuery.ajax({
		type: 'POST',
		url: 'searchDBcircle.do',
		async: false,
		dataType: 'json',
		data:{num:num,skip:skip},
		beforeSend:function(){
	        showModal();  
	    }, 
		success: function(data){
			console.log("w3");
			hideModal();
			svg.select("g").selectAll("g").attr("transform","translate(0,0)scale(1)");
			zooma.scale(1);
			zooma.translate([0,0]);
			update(data);
			showInfo();
		},
		error: function(){
			hideModal();
			console.log("f3");
			alert("连接失败！");
			
		}
	}); 
	console.log("e3");
}
/*获取各种分析模型的总条数*/
var totalNum;//存储各模型（除担保环模型）总条数的对象数组
//totalNum=[{"dkffzyg":8},{"dkhlzdbr":10},{"dkhlzfrdb":12},{"dkyjhx":14},{"dkzrfdc":16},{"dkzrrzdb":18},{"xfdkzrfdc":20},{"dkyyycbz":22}];
function getTotalNum(){
	jQuery.ajax({
		type: 'POST',
		url: 'searchPatternNumInfo.do',
		dataType: 'json',
		beforeSend:function (){
	        showModal();  
	    }, 
		success: function(data){
			hideModal();
			totalNum = data.PattenNumInfo;	
			console.log(totalNum);
		},
		error: function(){
			hideModal();
			alert("连接失败");
		}
	});
}
/**
 * 分析模型通用
 * kind:类型
 * skip:跳数
 * 点击上一页下一页时传入类型和条数
 */
function searchPattern(kind,skip){
	jQuery.ajax({
		type: 'POST',
		url: 'searchPattern.do',
		data:{skip:skip,kind:kind},
		dataType: 'json',
		beforeSend:function (){
	        showModal();  
	    }, 
		success: function(data){
			hideModal();
			if(data.nodes.length==0){
				alert("没有查询结果");
			}else{
				update(data);
				showInfo();
			}
		},
		error: function(){
			hideModal();
			alert("连接失败");
		}
	});
}
/**
 * 模式改变时获取当前模式的所有数据的部分字段，
 * 并以列表的形式展示在列表表格中
 * @returns
 */
var patternList;//当前模式列表形式的内容
function getPatternList(kind){
	jQuery.ajax({
		type: 'POST',
		url: 'searchPatternListInfo.do',
		data:{kind:kind},
		dataType: 'json',
		beforeSend:function (){
	        showModal();
	    }, 
		success: function(data){
			hideModal();
			patternList = data.PatternListInfo;
		},
		error: function(){
			hideModal();
			alert("连接失败");
		}
	});
}
/**
 * 点击列表传入当前行数据的参数，获取当前行对应的模式，并以可视化形式展示
 * @param id
 * @returns
 */
function getPatternByList(id){
	jQuery.ajax({
		type: 'POST',
		url: 'searchPatternByList.do',
		async: false,
		data:{id:id},
		dataType: 'json',
		beforeSend:function (){
	        showModal();  
	    }, 
		success: function(data){
			hideModal();
			update(data);
			showInfo();
		},
		error: function(){
			hideModal();
			alert("连接失败");
		}
	});
}

var search_pattern_list;// = [{"itemName":"上海"},{"itemName":"上海"},{"itemName":"上海"},{"itemName":"上海"}];
/**
 * 输入公司名称或机构代码获取该公司所在模式的列表
 * @returns
 */
function searchPatternList(){
	var input=document.getElementById("searchAcount").value;
	var options = $("#searchSort option:selected");
	var kind = ('0'+$("form option:selected")[0].index).toString();
	var url;
	console.log(options.val());
	switch(options.val()){
		case "comName":url = 'searchPatternByCompanyName.do';break;
		case "comId":url = 'searchPatternByCompanyJGDM.do';break;
	}
	console.log("name:"+input+"-- kind:"+kind);
	jQuery.ajax({
		type: 'POST',
		data: {name:input,kind:kind},
		url: url,
		dataType: 'json',
		beforeSend:function (){
        	showModal();  
    	}, 
		success: function(data){
			hideModal();
			console.log(data);
			search_pattern_list=data.PatternListInfo;
			showTable(6,true);	
		},
		error: function(){
			hideModal();
			alert("连接失败");
		}
	}); 
}





/*------以下淘汰-------------------------------------------------------------------------*/
/*贷款转入银承保证金*/
function searchYDZC(skip){
	alert("贷款转入银承保证金");
	jQuery.ajax({
		type: 'POST',
		url: 'dkyyycbz.do',
		data:{skip:skip},
		dataType: 'json',
		beforeSend:function (){
	        showModal();  
	    }, 
		success: function(data){
			hideModal();
			if(data.nodes.length==0){
				alert("没有查询结果");
			}else{
				update(data);
				showInfo();
			}
		},
		error: function(){
			hideModal();
			alert("连接失败");
		}
	});
}
/*贷款转入房地产*/
function searchDkzrfdc(skip){
	alert("贷款转入房地产");
	jQuery.ajax({
		type: 'POST',
		url: 'dkzrfdc.do',
		dataType: 'json',
		data:{skip:skip},
		beforeSend:function (){
	        showModal();  
	    }, 
		success: function(data){
			hideModal();
			if(data.nodes.length==0){
				alert("没有查询结果");
			}else{
				update(data);
				showInfo();
			}
		},
		error: function(){
			hideModal();
			alert("连接失败");
		}
	});
}
/*贷款转入融资担保类公司*/
function searchDkzrrzdb(skip){
	alert("贷款转入融资担保类公司");
	jQuery.ajax({
		type: 'POST',
		url: 'dkzrrzdb.do',
		dataType: 'json',
		data:{skip:skip},
		beforeSend:function (){
	        showModal();  
	    }, 
		success: function(data){
			hideModal();
			if(data.nodes.length==0){
				alert("没有查询结果");
			}else{
				update(data);
				showInfo();
			}
		},
		error: function(){
			hideModal();
			alert("连接失败");
		}
	});
}
/*贷款回流至担保人*/
function searchDkhlzdbr(skip){
	alert("贷款回流至担保人");
	jQuery.ajax({
		type: 'POST',
		url: 'dkhlzdbr.do',
		dataType: 'json',
		data:{skip:skip},
		beforeSend:function (){
	        showModal();  
	    }, 
		success: function(data){
			hideModal();
			if(data.nodes.length==0){
				alert("没有查询结果");
			}else{
				update(data);
				showInfo();
			}
		},
		error: function(){
			hideModal();
			alert("连接失败");
		}
	});
}
/*贷款以旧还新*/
function searchDkyjhx(skip){
	alert("贷款以旧还新");
	jQuery.ajax({
		type: 'POST',
		url: 'dkyjhx.do',
		dataType: 'json',
		data:{skip:skip},
		beforeSend:function (){
	        showModal();  
	    }, 
		success: function(data){
			hideModal();
			if(data.nodes.length==0){
				alert("没有查询结果");
			}else{
				update(data);
				showInfo();
			}
		},
		error: function(){
			hideModal();
			alert("连接失败");
		}
	});
}
/*贷款回流至法人代表账户*/
function searchDkhlzfrdb(skip){
	alert("贷款回流至法人代表账户");
	jQuery.ajax({
		type: 'POST',
		url: 'dkhlzfrdb.do',
		dataType: 'json',
		data:{skip:skip},
		beforeSend:function (){
	        showModal();  
	    }, 
		success: function(data){
			hideModal();
			if(data.nodes.length==0){
				alert("没有查询结果");
			}else{
				update(data);
				showInfo();
			}
		},
		error: function(){
			hideModal();
			alert("连接失败");
		}
	});
}
/*向本公司员工发放贷款*/
function searchDkffzyg(skip){
	alert("向本公司员工发放贷款");
	jQuery.ajax({
		type: 'POST',
		url: 'dkffzyg.do',
		dataType: 'json',
		data:{skip:skip},
		beforeSend:function (){
	        showModal();  
	    }, 
		success: function(data){
			hideModal();
			if(data.nodes.length==0){
				alert("没有查询结果");
			}else{
				update(data);
				showInfo();
			}
		},
		error: function(){
			hideModal();
			alert("连接失败");
		}
	});
}

/*消费贷资金违规进入房地产行业*/
function searchXfdjrfdc(skip){
	alert("消费贷资金违规进入房地产行业");
	jQuery.ajax({
		type: 'POST',
		url: 'xfdkzrfdc.do',
		dataType: 'json',
		data:{skip:skip},
		beforeSend:function (){
	        showModal();  
	    }, 
		success: function(data){
			hideModal();
			if(data.nodes.length==0){
				alert("没有查询结果");
			}else{
				update(data);
				showInfo();
			}
		},
		error: function(){
			hideModal();
			alert("连接失败");
		}
	});
}