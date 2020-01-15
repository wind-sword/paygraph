

var JYtotal=0,JYtimes=0,JYmax=0,JYmin=0;//存储关系节点相关极值

var JYtotalm=Number.MAX_VALUE,JYtimesm=Number.MAX_VALUE,JYmaxm=Number.MAX_VALUE,JYminm=Number.MAX_VALUE;//储存关系节点极值最小
//带有数据的相关图形
var linkd,noded,linktextd,nodetextd,arcd,linka,nodea,nodetexta,linktexta,graph={"nodes":[],"links":[]},contract,zooma;
//是否第一次进入
var firstin=true;
//实际线条linkat,虚拟线条linkaf
var linkat,linkaf;
    //进入更新数据
 	var update=function(d){
	obj.innerHTML="固定";isFixed=true;
	
	var h=graph.nodes.length;
    var i=d.nodes.length;

	for(var j=0;j<i;j++){
	for(var k=0;k<h;k++){
	   if(d.nodes[j].id==graph.nodes[k].id){
       d.nodes[j]=graph.nodes[k];
	   break;}
	}
	}
	graph.nodes=d.nodes;
	graph.links=d.links;
	
	render();
	
	}
	//清空数据
	var exit=function(){
//	if(graph!=null){
//        linka.data(graph.links,function(d){return d.id;}).exit().remove();
//        nodea.data(graph.nodes,function(d){return d.id;}).exit().remove();
//		linktexta.data(graph.links,function(d){return d.id;}).exit().remove();
//		nodetexta.data(graph.nodes,function(d){return d.id;}).exit().remove();}
	    }

	 var width = 1400, height = 900;
	 var old;
	 //nodeflag 控制是否允许缩放  menuflag 控制是否允许菜单环刷新
	 //menucreate 控制菜单是否允许创建  mouseout 确认是否点击位于元素外  
	 var nodeflag=true,menuflag=false,menucreate=true,mouseout=false;
	 //鼠标按键按下时间    鼠标按键抬起时间
	 var first=0,last=0;
	 
	var force = d3.layout.force()
			.charge(-400).linkDistance(200).size([width, height])
			.friction(0.8)
			.gravity(0.05);
	
    //x方向比例尺
		var x=d3.scale.linear()
		     .domain([0,width])
			 .range([0,width]);
	//y方向比例尺		 
		var y=d3.scale.linear()
		     .domain([0,height])
			 .range([height,0]);

	//为了重置拿出来缩放
        var zooma=d3.behavior.zoom().x(x).y(y)
				    .scaleExtent([1/8,10])
				    .on("zoom",zoom);	
			 
	//设置svg元素并添加鼠标属性	
	    var svg = d3.select("#graph").append("svg")
	            .attr("width", "100%").attr("height", "100%")
	            .attr("pointer-events", "all")
				.on("click",function(d){ if(mouseout&&last-first<200) {contract();mouseout=false;menuflag=false;menucreate=true;}})
				.on("mousedown",function(d){first=new Date().getTime();})
				.on("mouseup",function(d){last=new Date().getTime();})
				.call(zooma);				
		
		var svg_g=svg.append("g");
		    svg_g.append("g").attr("class","layer links");
			svg_g.append("g").attr("class","layer nodes");
			svg_g.append("g").attr("class","layer nodetexts");
			svg_g.append("g").attr("class","layer linktexts");
		
     //缩放  
		function zoom(){
		if(nodeflag){
		    svg.select("g").selectAll("g").attr("transform","translate("+d3.event.translate+")scale("
			         +d3.event.scale+")");
			        }
		}

	//设置箭头	
		var marker=svg.select("g").selectAll("marker")
					  .data(["zjy1","zjy2","zjy3","总交易","ZDB","GL"])
					  .enter().append("marker")
					  .attr("markerUnits","userSpaceOnUse")
					  .attr("id",String)
					  .attr("viewBox","0 -5 10 10")
					  .attr("refX",25)
					  .attr("refY",-1)
					  .attr("markerWidth",8)
					  .attr("markerHeight",8)
					  .attr("orient","auto")
					  .attr("stroke-width",1)
					  .append("path")
					  .attr("d","M0,-5L10,0L0,5")
					  .attr("fill",function(d){
					  switch(d){
					    case "DB":
							return "#8B0000";
						case "JY":
							return "#757575";
						case "DK":
							return "#1F7CF3";
						case "ZDB":
							return "#20B2AA";
						case "DY":
							return "#EEAD0E";
						default:
							return "#7D26CD";
					  }
					  });

		//设置关系中文映射
		var maprel=function(d){
		    switch(d){
			case "DB":
			    return "担保";
			case "JY":
			    return "交易";
			case "DK":
			    return "贷款";
			case "ZDB":
				return "总担保";
			case "总交易":
				return "总交易";
			case "绑定":
				return "绑定";
			case "使用":
				return "使用";
			case "签约":
				return "签约";
			case "zjy1":
				return "支付账户交易";
			case "zjy2":
				return "银行账户交易";
			case "zjy3":
				return "跨域交易";
			default:
			    return "对应";
			}
		}					  

		//拖动就锁定
			var  drag=force.drag()
					.on("dragstart",function(d,i){
						d.fixed=true;
					});
		//样条曲线生成器			
	        var lineFunction = d3.svg.line()
	                         .interpolate("bundle")
							 .x(function(d){return d.x})
							 .y(function(d){return d.y})
							 .tension(.75);	

        //按键按下设置不允许缩放
	        var mousedown=function(d){
			    nodeflag=false;
			}						 
		//按键抬起设置允许缩放	
			var mouseup=function(d){
			    nodeflag=true;
			}
		//根据时间判断是否点击并创建环菜单	
			var click=function(d){
				
				document.getElementById("jyinput").reset();
				
			    if(last-first<200){
				if(menucreate) 
				{
				mouseout=false;
				expand(d);
				menuflag=true;
				menucreate=false;}
				}
			}

		//设置环生成器				  
		    var arc =d3.svg.arc();
        //设置扩张菜单
		    var expand=function(d){
			    var a=[];
				var r=(d.label=="B")?15:(d.label=="P"||d.label=="C")?12.5:10;
			    for(var i=0;i<3;i++)
				    a.push({"startAngle":(i*120+2)*Math.PI/180,"endAngle":(i*120+118)*Math.PI/180,"innerRadius":r+5,"outerRadius":r+25});
 
                var b=[];
				b.push(d);//制作单个数据
				//数据绑定组g
				arcd=svg.select("g").select(".layer.nodes").data(b).append("g")
				        .attr("transform",function(d){return "translate("+d.x+","+d.y+")";});
                //添加环
			      arcd.selectAll(".arc").data(a).enter()
			          .append("path").attr("class","arc").attr("d",function(d){return arc(d);})
					  .on("click",function(d,i){if(i==0) nodedelete(); if(i==1) nodeexpand(); if(i==2) nodefix();});
				//添加文本	  
				  arcd.selectAll(".arctext")
			          .data(a).enter()
			          .append("text").attr("class","arctext")
					  .attr("text-anchor","middle")
					  .attr("pointer-events", "none")
					  .attr("transform",function(d){return "translate("+arc.centroid(d)+")";})
					  .attr("fill","white")
					  .text(function(d,i){if(i==0) return "✖"; if(i==1) return "↔"; return "🔓"});
					  
			}
		//菜单再见
			var nodefix=function(){
			    arcd.attr("godbye",function(d){d.fixed=false;});
			}

		//菜单删除节点
            var nodedelete=function(){
			    var ID;
			    arcd.attr("godbye",function(d){ID=d.id;});

				var k=graph.links.length;
				for(var j=0;j<k;j++){
				    if(graph.links[j].source.id==ID||graph.links[j].target.id==ID){
					graph.links.splice(j,1);
					j--;k--;
					}
				}

			    for(var i in graph.nodes){
				   if(graph.nodes[i].id==ID){
                   graph.nodes.splice(i,1);
				   break;
				   }
				}
//更新实际线条
			linkat.data(graph.links,function(d){return d.id;})
				 .exit()
				 .remove();
//更新虚拟线条
			linkaf.data(graph.links,function(d){return d.id;})
				 .exit()
				 .remove();
			
			nodea.data(graph.nodes,function(d){return d.id;})
				 .exit()
				 .remove();

		nodetexta.data(graph.nodes,function(d){return d.id;})
		         .exit()
                 .remove();
        linktexta.data(graph.links,function(d){return d.id;})
                 .exit()
                 .remove();	
        showInfo();				 
			}	

		 //设置清除数据收缩菜单   
		 contract=function(d){
			    svg.select("g").select(".layer.nodes").selectAll("g")
			          .remove();				
			}
		//设置拓展节点
            var nodeexpand=function(d){
            
            var lab;
            arcd.attr("godbye",function(d){lab=d.label;});
            
            if(lab=="Com"||lab=="Per"){
            	$("#jyfx").show();
            	return;
            }
            
			var ID;
			arcd.attr("godbye",function(d){ID=d.id;});
			var nodes=JSON.stringify(graph.nodes);
			var links=JSON.stringify(graph.links);

			jQuery.ajax({
			type: 'POST',
			data: {
				id:ID,
				nodes:nodes,
				links:links
				},
			url: 'extendById.do',
			dataType: 'json',
			beforeSend:function (){
		        showModal();  
		    }, 
			success: function(data){
			hideModal();
			update(data);
			showInfo();
			$("#dbchain").hide();
		    },
		    error: function(){hideModal();alert("连接失败");}
	        }); 
			
			}


var render=function(){
								
		//带有数据绑定的点	 
	         noded =svg.select("g")
			        .select(".layer.nodes")
			        .selectAll(".node")
					.data(graph.nodes,function(d){return d.id;});
		//进入圆形			
		   var node=noded.enter()
	                .append("circle")
	                .attr("class", function (d) { if(d.xiaodai)return "node "+d.label+" xiaodai"; if(d.dichan) return "node "+d.label+" dichan"; return "node "+d.label ;})
	                .attr("r", function(d){
					  return (d.label=="B")?15:(d.label=="P"||d.label=="C")?12.5:10;
					  })
					.attr("fill",function(d){
					  return (d.label=="个人支付账户")?"#ff0000":(d.label=="单位支付账户")?"#ff68fb":(d.label=="支付账户")?"#37ff00":(d.label=="单位银行账户")?"#37ff00":(d.label=="个人银行账户")?"#8600FF":(d.label=="收单机构")?"#fff50d":(d.label=="对公商户")?"#7EC0EE":(d.label=="个体商户")?"#6A5ACD":"#32CD32";
					})
					.on("mouseup",function(d){mouseup(d);})
					.on("mousedown",function(d){
						mousedown(d);
						console.log(d.label);
						switch(d.label){
							case "其他商户":$("#nodeInfo").html("<tr><td width='75px'>商户名称</td><td width='120%'>"+d.Acc_name+"</td><td width='75px'>商户类型</td><td width='120%'>"+d.label+"</td></tr>" +
											"<tr><td>收单机构代码</td><td>"+d.Org_codeJoin_code1.substring(0,14)+"</td><td>内部商户编码</td><td>"+d.Org_codeJoin_code1.substring(14)+"</td></tr>" +
											"<tr><td>法定代表人姓名</td><td>"+d.Id_name+"</td><td>代表证件类型号码</td><td>"+d.Id_type_no+"</td></tr>" +
											"<tr><td>地区代码</td><td>"+d.Add_code+"</td><td>营业状态</td></tr>"+""+"</td></tr>" );
							break;
							case "个体商户":$("#nodeInfo").html("<tr><td width='75px'>商户名称</td><td width='120%'>"+d.Acc_name+"</td><td width='75px'>商户类型</td><td width='120%'>"+d.label+"</td></tr>" +
									"<tr><td>收单机构代码</td><td>"+d.Org_codeJoin_code1.substring(0,14)+"</td><td>内部商户编码</td><td>"+d.Org_codeJoin_code1.substring(14)+"</td></tr>" +
									"<tr><td>法定代表人姓名</td><td>"+d.Id_name+"</td><td>代表证件类型号码</td><td>"+d.Id_type_no+"</td></tr>" +
									"<tr><td>地区代码</td><td>"+d.Add_code+"</td><td>营业状态</td></tr>"+""+"</td></tr>" );
							break;
							case "对公商户":$("#nodeInfo").html("<tr><td width='75px'>商户名称</td><td width='120%'>"+d.Acc_name+"</td><td width='75px'>商户类型</td><td width='120%'>"+d.label+"</td></tr>" +
									"<tr><td>收单机构代码</td><td>"+d.Org_codeJoin_code1.substring(0,14)+"</td><td>内部商户编码</td><td>"+d.Org_codeJoin_code1.substring(14)+"</td></tr>" +
									"<tr><td>法定代表人姓名</td><td>"+d.Id_name+"</td><td>代表证件类型号码</td><td>"+d.Id_type_no+"</td></tr>" +
									"<tr><td>地区代码</td><td>"+d.Add_code+"</td><td>营业状态</td></tr>"+""+"</td></tr>" );
							break;
							case "小微商户":$("#nodeInfo").html("<tr><td width='75px'>商户名称</td><td width='120%'>"+d.Acc_name+"</td><td width='75px'>商户类型</td><td width='120%'>"+d.label+"</td></tr>" +
									"<tr><td>收单机构代码</td><td>"+d.Org_codeJoin_code1.substring(0,14)+"</td><td>内部商户编码</td><td>"+d.Org_codeJoin_code1.substring(14)+"</td></tr>" +
									"<tr><td>法定代表人姓名</td><td>"+d.Id_name+"</td><td>代表证件类型号码</td><td>"+d.Id_type_no+"</td></tr>" +
									"<tr><td>地区代码</td><td>"+d.Add_code+"</td><td>营业状态</td></tr>"+""+"</td></tr>" );
							break;
							case "收单机构":$("#nodeInfo").html("<tr><td width='75px'>机构名称</td><td width='120%'>"+"中移电商"+"</td><td width='75px'>机构代码</td><td width='120%'>"+d.Org_code+"</td></tr>" );
							break;
							case "P":$("#nodeInfo").html("<tr><td width='75px'>客户姓名</td><td width='120%'>"+d.khxm+"</td><td width='75px'>证件号码</td><td width='120%'>"+d.creditcode+"</td></tr>" +
											"<tr><td>国籍</td><td>"+d.gj+"</td><td>民族</td><td>"+d.mz+"</td></tr>" +
											"<tr><td>性别</td><td>"+d.xb+"</td><td>出生日期</td><td>"+d.csrq+"</td></tr>" +
											"<tr><td>职业</td><td>"+d.zy+"</td><td>是否银行员工</td><td>"+d.sfygbz+"</td></tr>" +
											"<tr><td>银行机构代码</td><td>"+d.yxjgdm+"</td><td>银行机构名称</td><td>"+d.yxjgmc+"</td></tr>" +
											"<tr><td>账户名称</td><td>"+d.zhmc+"</td></tr>");
							break;
                            case "C":$("#nodeInfo").html("<tr><td width='75px'>对公客户</td><td width='120%'>"+d.khmc+"</td><td width='75px'>机构代码</td><td width='120%'>"+d.creditcode+"</td></tr>" +
                            				"<tr><td>法人代表</td><td>"+d.frdb+"</td><td>注册资本</td><td>"+d.zczb+"</td></tr>" +
                            				"<tr><td>注册地址</td><td>"+d.zcdz+"</td><td>所属行业</td><td>"+d.ssxy+"</td></tr>" +
                            				"<tr><td>实收资本</td><td>"+d.sszb+"</td><td>总资产</td><td>"+d.zzc+"</td></tr>" +
                            				"<tr><td>净资产</td><td>"+d.jzc+"</td><td>行政区划代码</td><td>"+d.xzqhdm+"</td></tr>" +
                            				"<tr><td>风险预警信号</td><td>"+d.fxyjxh+"</td></tr>");
                            break;
                            case "单位银行账户":$("#nodeInfo").html("<tr><td width='75px'>账户名称</td><td width='120%'>"+d.Acc_name+"</td><td width='75px'>账户号码</td><td width='120%'>"+d.Acc_no+"</td></tr>" +
                    				"<tr><td>收单机构代码</td><td>"+d.Org_codeCst_code.substring(0,14)+"</td><td>内部编号</td><td>"+d.Org_codeCst_code.substring(14)+"</td></tr>"+
                    				"<tr><td>负责人姓名</td><td>"+d.Id_name+"</td></tr>");
                            break;
                 
                            default:$("#nodeInfo").html("<tr><td width='75px'>账户名称</td><td width='120%'>"+d.Acc_name+"</td><td width='75px'>账户号码</td><td width='120%'>"+d.Acc_no+"</td></tr>" +
                    				"<tr><td>收单机构代码</td><td>"+d.Org_codeCst_code.substring(0,14)+"</td><td>内部编号</td><td>"+d.Org_codeCst_code.substring(14)+"</td></tr>");
                            break;
						}
						/*switch(d.label){
						case "B":$("#nodeInfo").html("<tr><td width='75px'>银行</td><td width='120%'>"+d.yxjgmc+"</td></tr><tr><td>银行机构代码</td><td>"+d.yxjgdm+"</td></tr><tr><td>内部机构号</td><td>"+d.nbjgh
										+"</td></tr><tr><td>金融许可证号</td><td>"+d.jrxkzh+"</td></tr><tr><td>机构类别</td><td>"+d.jglb+"</td></tr><tr><td>邮政编码</td><td>"+d.yzbm
										+"</td></tr><tr><td>网点号</td></tr>"+d.wdh+"</td></tr><tr><td>营业状态</td></tr>"+d.yyzt+"</td></tr><tr><td>成立时间</td></tr>"+d.clsj
										+"</td></tr><tr><td>机构工作开始时间</td><td>"+d.jggzkssj+"</td></tr><tr><td>机构工作终止时间</td><td>"+d.jggzzzsj+"</td></tr><tr><td>机构地址</td><td>"+d.jgdz
										+"</td></tr><tr><td>负责人姓名</td><td>"+d.fzrxm+"</td></tr><tr><td>负责人职务</td><td>"+d.fzrzw+"</td></tr><tr><td>负责人联系电话</td><td>"+d.fzrlxdh
										+"</td></tr><tr><td>采集日期</td><td>"+d.cjrq+"</td></tr>");
						break;
						case "P":$("#nodeInfo").html("<tr><td width='75px'>客户姓名</td><td width='120%'>"+d.khxm+"</td></tr><tr><td>证件号码</td><td>"+d.creditcode+"</td></tr><tr><td>国籍</td><td>"+d.gj
										+"</td></tr><tr><td>民族</td><td>"+d.mz+"</td></tr><tr><td>性别</td><td>"+d.xb+"</td></tr><tr><td>出生日期</td><td>"+d.csrq
										+"</td></tr><tr><td>职业</td><td>"+d.zy+"</td></tr><tr><td>是否银行员工</td><td>"+d.sfygbz+"</td></tr><tr><td>银行机构代码</td><td>"+d.yxjgdm
										+"</td></tr><tr><td>银行机构名称</td><td>"+d.yxjgmc+"</td></tr><tr><td>账户名称</td><td>"+d.zhmc+"</td></tr>");
						break;
                        case "C":$("#nodeInfo").html("<tr><td width='75px'>对公客户</td><td width='120%'>"+d.khmc+"</td></tr><tr><td>机构代码</td><td>"+d.creditcode+"</td></tr><tr><td>法人代表</td><td>"+d.frdb
                            			+"</td></tr><tr><td>注册资本</td><td>"+d.zczb+"</td></tr><tr><td>注册地址</td><td>"+d.zcdz
                            			+"</td></tr><tr><td>所属行业</td><td>"+d.ssxy+"</td></tr><tr><td>实收资本</td><td>"+d.sszb+"</td></tr><tr><td>总资产</td><td>"+d.zzc
                            			+"</td></tr><tr><td>净资产</td><td>"+d.jzc+"</td></tr><tr><td>行政区划代码</td><td>"+d.xzqhdm+"</td></tr><tr><td>风险预警信号</td><td>"+d.fxyjxh+"</td></tr>");
                        break;
                        default:$("#nodeInfo").html("<tr><td width='75px'>存款账户</td><td width='120%'>"+d.ckzh+"</td></tr><tr><td>账户名称</td><td>"+d.zhmc+"</td></tr><tr><td>证件号码</td><td>"+d.creditcode+"</td></tr><tr><td>客户统一编号</td><td>"+d.khtybh+"</td></tr><tr><td>银行机构名称</td><td>"+d.yxjgmc
                            			+"</td></tr><tr><td>银行机构代码</td><td>"+d.yxjgdm+"</td></tr><tr><td>保证金账户标志</td><td>"+d.bzjzhbz+"</td></tr><tr><td>存款余额</td><td>"+(d.ckye/10000)
                            			+"万元</td></tr><tr><td>币种</td><td>"+d.bz+"</td></tr>");
                        break;	
					}*/
					})
					.on("click",function(d){if(d.label != "Com" && d.label != "Per") $("#jyfx").hide();click(d);})
	        		.on("mouseover",function(d,i){
						old=$("#nodeSize").text();
						switch (d.label){
							case "B":$("#nodeSize").html("银行： "+d.yxjgmc+" 银行机构代码："+d.yxjgdm);break;
							case "P":$("#nodeSize").html("个人客户： "+d.khxm+" 证件号码："+d.creditcode);break;
                            case "C":$("#nodeSize").html("对公客户： "+d.khmc+" 机构代码："+d.creditcode);break;
                            default:$("#nodeSize").html("结点： "+d.label);break;
						}
					})
					.on("mouseout",function(d,i){
					    mouseout=true; 
						$("#nodeSize").html(old);
					})
	                .call(drag);
		
	     //带有数据绑定的节点文本
		     nodetextd=svg.select("g")
			          .select(".layer.nodetexts")
			          .selectAll(".text")
			          .data(graph.nodes,function(d){return d.id;});
		//设置文本
		  var nodetext=nodetextd.enter()
					  .append("text")
					  .attr("dx",15)
					  .attr("dy",-5)
					  .attr("class", "text")
					  .attr("pointer-events", "none")
					  .text(function(d){
					  return (d.label=="个人银行账户")?d.Acc_no:(d.label=="支付账户")?d.Acc_no:(d.label=="收单机构")?d.Org_code:(d.label=="单位银行账户")?d.Id_name:d.Acc_name;
					  });

    //带有数据绑定的连接
	         linkd = svg.select("g")
			        .select(".layer.links")
			        .selectAll(".link")
					.data(graph.links,function(d){return d.id;});

		//进入线条路径
		   var link=linkd.enter()
	                .append("path").attr("class", function(d) { /*if(d.circle) return "link "+d.label+" circle";*/ return "link " + d.label+" T"; })
					.attr("id",function(d,i){return "link"+d.id})
					.attr("pointer-events","none")
					.style("stroke",function(d){
						switch(d.label){
							case "签约":
							return "#8B0000";
							case "总交易":
							return "#757575";
							case "使用":
							return "#1F7CF3";
							case "ZDB":
							return "#20B2AA";
							case "DY":
							return "#EEAD0E";
							default:
							return "#7D26CD";
						}
					})
//					.attr("pointer-events","auto")//自动确认鼠标点击位置防止有问题覆盖
					// .on("mouseover",function(d,i){
						// d3.select(this).attr("stroke-width","3");
						// old=$("#nodeSize").text();
							// switch(d.type){
								// case "JY":
									// $("#nodeSize").html("关系类型:"+maprel(d.type)+"&nbsp&nbsp&nbsp详细关系:"+d.type_cn+"&nbsp&nbsp&nbspNO:"+d.no+"&nbsp&nbsp&nbsp主体:"+d.source.name
									// +"&nbsp&nbsp&nbsp客体:"+d.target.name+"&nbsp&nbsp&nbsp总金额:"+d.total+"&nbsp&nbsp&nbsp交易次数:"+d.times+"&nbsp&nbsp&nbsp最大交易:"+d.max+"&nbsp&nbsp&nbsp最小交易:"+d.min+"&nbsp&nbsp&nbsp日期:"+d.data_date);
									// break;
								// case "KZ":
								// case "DB":
									// $("#nodeSize").html("关系类型:"+maprel(d.type)+"&nbsp&nbsp&nbsp详细关系:"+d.type_cn+"&nbsp&nbsp&nbspNO:"+d.no+"&nbsp&nbsp&nbsp主体:"+d.source.name
									// +"&nbsp&nbsp&nbsp客体:"+d.target.name+"&nbsp&nbsp&nbsp份额:"+d.proportion+"&nbsp&nbsp&nbsp日期:"+d.data_date);
									// break;
							    // case "QT":
									// $("#nodeSize").html("关系类型:"+maprel(d.type)+"&nbsp&nbsp&nbsp详细关系:"+d.type_cn+"&nbsp&nbsp&nbspNO:"+d.no+"&nbsp&nbsp&nbsp主体:"+d.source.name
									// +"&nbsp&nbsp&nbsp客体:"+d.target.name+"&nbsp&nbsp&nbsp日期:"+d.data_date);
									// break;
							// }
					// })
					// .on("mouseout",function(d,i){
						// d3.select(this).attr("stroke-width","none");
						// $("#nodeSize").html(old);
					// })
					.attr("marker-end",function(d){return "url(#"+d.label+")";});

		//隐藏的线条
		var linkshadow=svg.select("g")
			        .select(".layer.links")
			        .selectAll(".link.shadow")
					.data(graph.links,function(d){return d.id;}).enter()
	                .append("path").attr("class", function(d) { return "link shadow"})
					.attr("opacity","0")
					.attr("stroke-width","10")
					.attr("pointer-events","auto")//自动确认鼠标点击位置防止有问题覆盖
					.on("mousedown",function(d,i){
						mousedown(d);
						switch(d.label){
								case "DB":
									$("#nodeInfo").html("<tr><td width='75px'>关系类型</td><td width='120%'>"+maprel(d.label)+"</td><td width='75px'>代办人证件号码</td><td width='120%'>"+d.dbrzjhm+"</td></tr>" +
											"<tr><td>银行机构代码</td><td>"+d.yxjgdm+"</td><td>银行机构名称</td><td>"+d.yxjgmc+"</td></tr>" +
											"<tr><td>担保起始日期</td><td>"+d.dbqsrq+"</td><td>担保到期日期</td><td>"+d.dbdqrq+"</td></tr>" +
											"<tr><td>担保合同状态</td><td>"+d.dbhtzt+"</td><td>担保总金额</td><td>"+(d.dbzje/10000)+"万元</td></tr>" +
											"<tr><td>担保币种</td><td>"+d.dbbz+"</td></tr>");
									break;
								case "总交易":
                                    $("#nodeInfo").html("<tr><td width='75px'>交易金额</td><td width='120%'>"+d.jyje+"</td><td width='75px'>交易次数</td><td width='120%'>"+d.jycs+"</td></tr>");
                                    break;
								case "zjy1":
                                    $("#nodeInfo").html("<tr><td width='75px'>交易金额</td><td width='120%'>"+d.jyje+"</td><td width='75px'>交易次数</td><td width='120%'>"+d.jycs+"</td></tr>");
                                    break;
								case "zjy2":
                                    $("#nodeInfo").html("<tr><td width='75px'>交易金额</td><td width='120%'>"+d.jyje+"</td><td width='75px'>交易次数</td><td width='120%'>"+d.jycs+"</td></tr>");
                                    break;
								case "zjy3":
                                    $("#nodeInfo").html("<tr><td width='75px'>交易金额</td><td width='120%'>"+d.jyje+"</td><td width='75px'>交易次数</td><td width='120%'>"+d.jycs+"</td></tr>");
                                    break;
								case "DK":
									$("#nodeInfo").html("<tr><td width='75px'>关系类型</td><td width='120%'>"+maprel(d.label)+"</td><td width='75px'>银行机构代码</td><td width='120%'>"+d.yxjgdm+"</td></tr>" +
											"<tr><td>银行机构名称</td><td>"+d.yxjgmc+"</td><td>证件号码</td><td>"+d.zjhm+"</td></tr>" +
											"<tr><td>贷款入账账户</td><td>"+d.dkrzzh+"</td><td>信贷借据号</td><td>"+d.xdjjh+"</td></tr>" +
											"<tr><td>客户统一编号</td><td>"+d.khtybh+"</td><td>客户名称</td><td>"+d.khmc+"</td></tr>" +
											"<tr><td>信贷合同号</td><td>"+d.xdhth+"</td><td>信贷业务种类</td><td>"+d.xdywzl+"</td></tr>" +
											"<tr><td>币种</td><td>"+d.bz+"</td><td>金额</td><td>"+(d.je/10000)+"万元</td></tr>" +
											"<tr><td>借款余额</td><td>"+(d.jkye/10000)+"万元</td><td>贷款期限</td><td>"+d.dkqx+"</td></tr>" +
											"<tr><td>展期次数</td><td>"+d.zqcs+"</td><td>总期数</td><td>"+d.zqs+"</td></tr>" +
											"<tr><td>放款方式</td><td>"+d.fkfs+"</td><td>贷款实际发放日期</td><td>"+d.dksjffrq+"</td></tr>" +
											"<tr><td>贷款实际到期日期</td><td>"+d.dksjdqrq+"</td><td>贷款类型</td><td>"+d.dklx+"</td></tr>" +
											"<tr><td>贷款五级分类</td><td>"+d.dkwjfl+"</td><td>还款方式</td><td>"+d.hkfs+"</td></tr>" +
											"<tr><td>还款账号</td><td>"+d.hkzh+"</td><td>保证金金额</td><td>"+(d.bzjje/10000)+"万元</td></tr>" +
											"<tr><td>信贷员姓名</td><td>"+d.xdyxm+"</td><td>信贷员工号</td><td>"+d.xdygh+"</td></tr>" +
											"<tr><td>主要担保方式</td><td>"+d.zydbfs+"</td><td>贷款用途</td><td>"+d.dkyt+"</td></tr>" +
											"<tr><td>贷款投向行业</td><td>"+d.dktxxy+"</td></tr>");
									break;
							    case "DY":
									$("#nodeInfo").html("<tr><td>关系类型</td><td>"+maprel(d.label)+"</td></tr>");
									break;
							    case "ZDB":
							    	$("#nodeInfo").html("<tr><td width='75px'>关系类型</td><td width='120%'>"+maprel(d.label)+"</td><td width='75px'>总担保次数</td><td width='120%'>"+d.zdbcs+"</td></tr>" +
							    						"<tr><td>总担保金额</td><td>"+(d.zdbje/10000)+"万元</td></tr>");
							    	break;
							    case "GL":
							    	$("#nodeInfo").html("<tr><td width='75px'>关系类型</td><td width='120%'>"+maprel(d.label)+"</td><td width='75px'>具体关系</td><td width='120%'>"+d.gxlx+"</td></tr>");
							    	break;
							    
							}
/*						switch(d.label){
								case "DB":
									$("#nodeInfo").html("<tr><td width='75px'>关系类型</td><td width='120%'>"+maprel(d.label)+"</td></tr><tr><td>代办人证件号码</td><td>"+d.dbrzjhm+"</td></tr><tr><td>银行机构代码</td><td>"+d.yxjgdm
											+"</td></tr><tr><td>银行机构名称</td><td>"+d.yxjgmc+"</td></tr><tr><td>担保起始日期</td><td>"+d.dbqsrq+"</td></tr><tr><td>担保到期日期</td><td>"+d.dbdqrq
											+"</td></tr><tr><td>担保合同状态</td><td>"+d.dbhtzt+"</td></tr><tr><td>担保总金额</td><td>"+(d.dbzje/10000)+"万元</td></tr><tr><td>担保币种</td><td>"+d.dbbz+"</td></tr>");
									break;
								case "JY":
		                            $("#nodeInfo").html("<tr><td width='75px'>关系类型</td><td width='120%'>"+maprel(d.label)+"</td></tr><tr><td>交易账号</td><td>"+d.jyzh+"</td></tr><tr><td>对方行号</td><td>"+d.dfxh
		                            		+"</td></tr><tr><td>核心交易流水号</td><td>"+d.hxjylsh+"</td></tr><tr><td>笔次序号</td><td>"+d.bcxh+"</td></tr><tr><td>交易日期</td><td>"+d.jyrq
		                            		+"</td></tr><tr><td>银行机构代码</td><td>"+d.yxjgdm+"</td></tr><tr><td>交易机构名称</td><td>"+d.jyjgmc+"</td></tr><tr><td>交易机构总行</td><td>"+d.jyjgmc_zh+"</td></tr><tr><td>明细科目编号</td><td>"+d.mxkmbh
		                            		+"</td></tr><tr><td>交易时间</td><td>"+d.jysj+"</td></tr><tr><td>交易机构名称</td><td>"+d.jyjgmc+"</td></tr><tr><td>交易户名</td><td>"+d.jyhm
		                            		+"</td></tr><tr><td>对方机构名称</td><td>"+d.dfjgmc+"</td></tr><tr><td>对方账号</td><td>"+d.dfzh+"</td></tr><tr><td>对方户名</td><td>"+d.dfhm
		                            		+"</td></tr><tr><td>交易金额</td><td>"+(d.jyje/10000)+"万元</td></tr><tr><td>账户余额</td><td>"+(d.zhye/10000)+"万元</td></tr><tr><td>交易借贷标志</td><td>"+d.jyjdbz
		                            		+"</td></tr><tr><td>现转标志</td><td>"+d.xzbz+"</td></tr><tr><td>币种</td><td>"+d.bz+"</td></tr><tr><td>业务类型</td><td>"+d.ywlx
		                            		+"</td></tr><tr><td>交易类型</td><td>"+d.jylx+"</td></tr><tr><td>交易渠道</td><td>"+d.jyqd+"</td></tr><tr><td>交易介质号</td><td>"+d.jyjzh
		                            		+"</td></tr><tr><td>操作柜员号</td><td>"+d.czgyh+"</td></tr><tr><td>摘要</td><td>"+d.zy+"</td></tr><tr><td>账户标志</td><td>"+d.zhbz
		                            		+"</td></tr><tr><td>开销户标志</td><td>"+d.kxhbz+"</td></tr><tr><td>采集日期</td><td>"+d.cjrq+"</td></tr>");
		                            break;
								case "DK":
									$("#nodeInfo").html("<tr><td width='75px'>关系类型</td><td width='120%'>"+maprel(d.label)+"</td></tr><tr><td>银行机构代码</td><td>"+d.yxjgdm+"</td></tr><tr><td>银行机构名称</td><td>"+d.yxjgmc
											+"</td></tr><tr><td>证件号码</td><td>"+d.zjhm+"</td></tr><tr><td>贷款入账账户</td><td>"+d.dkrzzh+"</td></tr><tr><td>信贷借据号</td><td>"+d.xdjjh
											+"</td></tr><tr><td>客户统一编号</td><td>"+d.khtybh+"</td></tr><tr><td>客户名称</td><td>"+d.khmc+"</td></tr><tr><td>信贷合同号</td><td>"+d.xdhth
											+"</td></tr><tr><td>信贷业务种类</td><td>"+d.xdywzl+"</td></tr><tr><td>币种</td><td>"+d.bz+"</td></tr><tr><td>金额</td><td>"+(d.je/10000)
											+"万元</td></tr><tr><td>借款余额</td><td>"+(d.jkye/10000)+"万元</td></tr><tr><td>贷款期限</td><td>"+d.dkqx+"</td></tr><tr><td>展期次数</td><td>"+d.zqcs
											+"</td></tr><tr><td>总期数</td><td>"+d.zqs+"</td></tr><tr><td>放款方式</td><td>"+d.fkfs+"</td></tr><tr><td>贷款实际发放日期</td><td>"+d.dksjffrq
											+"</td></tr><tr><td>贷款实际到期日期</td><td>"+d.dksjdqrq+"</td></tr><tr><td>贷款类型</td><td>"+d.dklx+"</td></tr><tr><td>贷款五级分类</td><td>"+d.dkwjfl
											+"</td></tr><tr><td>还款方式</td><td>"+d.hkfs+"</td></tr><tr><td>还款账号</td><td>"+d.hkzh+"</td></tr><tr><td>保证金金额</td><td>"+(d.bzjje/10000)
											+"万元</td></tr><tr><td>信贷员姓名</td><td>"+d.xdyxm+"</td></tr><tr><td>信贷员工号</td><td>"+d.xdygh+"</td></tr><tr><td>主要担保方式</td><td>"+d.zydbfs
											+"</td></tr><tr><td>贷款用途</td><td>"+d.dkyt+"</td></tr><tr><td>贷款投向行业</td><td>"+d.dktxxy+"</td></tr>");
									break;
							    case "DY":
									$("#nodeInfo").html("<tr><td>关系类型</td><td>"+maprel(d.label)+"</td></tr>");
									break;
							    case "ZDB":
							    	$("#nodeInfo").html("<tr><td width='75px'>关系类型</td><td width='120%'>"+maprel(d.label)+"</td></tr><tr><td>总担保次数</td><td>"+d.zdbcs+"</td></tr><tr><td>总担保金额</td><td>"+(d.zdbje/10000)+"万元</td></tr>");
							    	break;
							    case "GL":
							    	$("#nodeInfo").html("<tr><td width='75px'>关系类型</td><td width='120%'>"+maprel(d.label)+"</td></tr><tr><td>具体关系</td><td>"+d.gxlx+"</td></tr>");
							    	break;
					    
					}*/
					})
					.on("mouseover",function(d,i){
						old=$("#nodeSize").text();
							switch(d.label){
								case "DB":
									$("#nodeSize").html("关系类型:"+maprel(d.label)+"&nbsp&nbsp&nbsp银行机构名称:"+d.yxjgmc+"&nbsp&nbsp&nbsp总金额:"+(d.dbzje/10000)+"万元&nbsp&nbsp&nbsp起始日期:"+d.dbqsrq
									+"&nbsp&nbsp&nbsp到期日期:"+d.dbdqrq);
									break;
								case "JY":
                                    $("#nodeSize").html("关系类型:"+maprel(d.label)+"&nbsp&nbsp&nbsp交易机构名称:"+d.jyjgmc+"&nbsp&nbsp&nbsp交易金额:"+(d.jyje/10000) +"万元&nbsp&nbsp&nbsp交易日期:"+d.jyrq);
                                    break;
								case "DK":
									$("#nodeSize").html("关系类型:"+maprel(d.label)+"&nbsp&nbsp&nbsp贷款客户:"+d.khmc+"&nbsp&nbsp&nbsp贷款金额:"+d.je+"&nbsp&nbsp&nbsp贷款发放日期:"+d.dksjffrq
                                    +"&nbsp&nbsp&nbsp贷款到期日期:"+d.dksjdqrq+"&nbsp&nbsp&nbsp利息:"+d.dklx);
									break;
							    case "DY":
									$("#nodeSize").html("关系类型:"+maprel(d.label));
									break;
							    case "ZDB":
									$("#nodeSize").html("关系类型:"+maprel(d.label)+"&nbsp&nbsp&nbsp总担保次数:"+d.zdbcs+"&nbsp&nbsp&nbsp总担保金额:"+d.zdbje);
									break;
							    case "GL":
									$("#nodeSize").html("关系类型:"+maprel(d.label)+"&nbsp&nbsp&nbsp具体关系:"+d.gxlx);
									break;
							}
					})
					.on("mouseout",function(d,i){
						$("#nodeSize").html(old);
					});
		  
		//带有数据绑定的线条文本	
			 linktextd=svg.select("g")
			          .select(".layer.linktexts")
			          .selectAll(".text")
			          .data(graph.links,function(d){return d.id;});
		//设置文本
		  var linktext=linktextd.enter()
					  .append("text")
					  .attr("pointer-events", "none")
	                  .attr("text-anchor","middle")
					  .attr("dy",-5)
					  .attr("class", "text")
					  .attr("id",function(d,i){return "link"+d.id})
		//文本跟随路径		
			  linktext.append("textPath")
					  .attr("startOffset","50%")
					  .attr('xlink:href',function(d,i) {return '#link'+d.id})
					  .attr("pointer-events", "none")
					  .text(function(d){
					  return maprel(d.label);});
		
		//更新选择的连接节点
	         linka = svg.select("g").select(".layer.links")
			        .selectAll(".link");
			 nodea = svg.select("g").select(".layer.nodes")
			        .selectAll(".node");
		     nodetexta=svg.select("g").select(".layer.nodetexts")
			        .selectAll(".text");
			 linktexta=svg.select("g").select(".layer.linktexts")
			        .selectAll(".text");
			 //实际线条
			 linkat=svg.select("g").select(".layer.links")
		            .selectAll(".link.T");
			 //虚拟线条
			 linkaf=svg.select("g").select(".layer.links")
	                .selectAll(".link.shadow");
		//清理不需要的数据图形	
			linkat.data(graph.links,function(d){return d.id;})
			      .exit().remove();
			linkaf.data(graph.links,function(d){return d.id;})
			      .exit().remove();
			noded.exit().remove();
			linktextd.exit().remove();
			nodetextd.exit().remove();

		
	//力图开始模拟 
		force.nodes(graph.nodes).links(graph.links).start();
		
	    //图形随动作刷新
		force.on("tick", function() {
   
		//直线的时候link更新可以废弃
	            linka.attr("x1", function(d) { return d.source.x; })
	                .attr("y1", function(d) { return d.source.y; })
	                .attr("x2", function(d) { return d.target.x; })
	                .attr("y2", function(d) { return d.target.y; });
						
        //点移动时候坐标更新
	            nodea.attr("cx", function(d) { return d.x; })
	                    .attr("cy", function(d) { return d.y; });
		//点文本移动坐标跟新				
				nodetexta.attr("x",function(d){return d.x;})
				         .attr("y",function(d){return d.y});
        //连线文本翻转
		linktexta.attr("transform",function(d,i){
		        if(d.target.x<d.source.x){
				bbox = this.getBBox();
                rx = bbox.x+bbox.width/2;
                ry = bbox.y+bbox.height/2;
				return 'rotate(180 '+rx+' '+ry+')';
				}
		});		 
		//连线实时进行曲线运算			 
		linka.attr('d',function(d){
				
				if(d.source.x==d.target.x){
				   var avgangle=2*Math.PI/(d.sum+10),
				   r=125,
				   x=d.source.x,
				   y=d.source.y,
				   x1=x+Math.cos((d.no-1)*avgangle)*r,
				   y1=y+Math.sin((d.no-1)*avgangle)*r,
				   x2=x+Math.cos(d.no*avgangle)*r,
				   y2=y+Math.sin(d.no*avgangle)*r;
				   return lineFunction([{"x":x,"y":y},{"x":x1,"y":y1},{"x":x2,"y":y2},{"x":x,"y":y}]);
				}
			var center={
				x: (d.source.x + d.target.x) / 2,
				y: (d.source.y + d.target.y) / 2
			};
			

			var dx = d.target.x - d.source.x,
				dy = d.target.y - d.source.y,
				a = - dx / dy,
				mid=(d.sum/2);
				ndx = (d.no-mid-0.5) * 50 * Math.cos(Math.atan(a)),
				ndy = (d.no-mid-0.5) * 50 * Math.sin(Math.atan(a)),
				cx = center.x + ndx,
				cy = center.y + ndy;
	
			return lineFunction([{"x":d.source.x,"y":d.source.y},{"x":cx,"y":cy},{"x":d.target.x,"y":d.target.y}]); 
			});
		
    //如果可以允许菜单刷新
        if(menuflag){
		arcd.attr("transform",function(d){return "translate("+d.x+","+d.y+")";});	}
		
		});	

}
//根据类型和当前数据返回连线粗细
var strokelinear=function(string,d){
				 switch(string){
				 case "total":
				 return 5.5/(JYtotal-JYtotalm+0.01)*(d.total-JYtotalm+0.01)+0.5;
				 case "max":
				 return 5.5/(JYmax-JYmaxm+0.01)*(d.max-JYmaxm+0.01)+0.5;
				 case "min":
				 return 5.5/(JYmin-JYminm+0.01)*(d.min-JYminm+0.01)+0.5;
				 default:
				 return 5.5/(JYtimes-JYtimesm+0.01)*(d.times-JYtimesm+0.01)+0.5;}
				   }

//根据返回连接类型依次修改各个连接粗细
function changewidth(linktype){
   //求出交易关系相应极值
	extreme(graph.links);
    linkd.each(function(d,i){
	   if(d.label=="JY"){
	   d3.select(this).style({"stroke-width":strokelinear(linktype,d),"stroke":"#757575"});
//	   console.log(strokelinear(linktype,d));
       }
	   });
}

//遍历links数据求出所有相关数据极值
function extreme(d){
    for(var i in d){
	    if(d[i].total>JYtotal)
		JYtotal=d[i].total;
		if(d[i].total<JYtotalm)
		JYtotalm=d[i].total;
		if(d[i].max>JYmax)
		JYmax=d[i].max;
		if(d[i].max<JYmaxm)
		JYmaxm=d[i].max;
		if(d[i].min>JYmin)
		JYmin=d[i].min;
		if(d[i].min<JYminm)
		JYminm=d[i].min;
		if(d[i].times>JYtimes)
		JYtimes=d[i].times;
		if(d[i].times<JYtimesm)
		JYtimesm=d[i].times;
	}
}
//显示节点文字
function showNodetext(n){
	if(n.checked){
		var count=[];
	    linkd.each(function(d){if(d3.select(this).attr("visibility")=="hidden") return;if(count.indexOf(d.source.id)<0) count.push(d.source.id);if(count.indexOf(d.target.id)<0) count.push(d.target.id);});
		d3.select("#graph").select(".layer.nodetexts").selectAll(".text").each(function(d){if(count.indexOf(d.id)>=0) d3.select(this).attr("visibility","visible");});
	}else{
		d3.select("#graph").select(".layer.nodetexts").selectAll(".text").attr("visibility","hidden");
	}
	
}

//显示链接文字
function showLinktext(l){

	if(l.checked){
		var count=[];
	    linkd.each(function(d){if(d3.select(this).attr("visibility")!="hidden") count.push(d.id);});
		d3.select("#graph").select(".layer.linktexts").selectAll(".text").each(function(d){if(count.indexOf(d.id)>=0) d3.select(this).attr("visibility","visible");});
	}else{
		d3.select("#graph").select(".layer.linktexts").selectAll(".text").attr("visibility","hidden");
	}
}

//显隐不同线条
function showlinetext(l){
    if(l.checked){
	    d3.select("#graph").select(".layer.links").selectAll(".link").each(function(d){if(d.label==l.value)d3.select(this).attr("visibility","visible")});
		if(($('#showlink').is(':checked')))
		d3.select("#graph").select(".layer.linktexts").selectAll(".text").each(function(d){ if(d.label==l.value)d3.select(this).attr("visibility","visible")});
	}else{
	    d3.select("#graph").select(".layer.links").selectAll(".link").each(function(d){if(d.label==l.value)d3.select(this).attr("visibility","hidden")});
		d3.select("#graph").select(".layer.linktexts").selectAll(".text").each(function(d){if(d.label==l.value)d3.select(this).attr("visibility","hidden")});
	}
    ordergraph();
}


//显隐数据源线条
function showSJYtext(l,txt){
    console.log(txt);
	if(l.checked){
	    d3.select("#graph").select(".layer.links").selectAll(".link").each(function(d){if(d.jyjgmc_zh==txt)d3.select(this).attr("visibility","visible")});
		if(($('#showlink').is(':checked')))
		d3.select("#graph").select(".layer.linktexts").selectAll(".text").each(function(d){if(d.jyjgmc_zh==txt)d3.select(this).attr("visibility","visible")});
	}else{
	    d3.select("#graph").select(".layer.links").selectAll(".link").each(function(d){if(d.jyjgmc_zh==txt)d3.select(this).attr("visibility","hidden")});
		d3.select("#graph").select(".layer.linktexts").selectAll(".text").each(function(d){if(d.jyjgmc_zh==txt)d3.select(this).attr("visibility","hidden")});
	}
	ordergraph();
}


//显隐不同节点
function showdottext(l){
	var tmp=l.value;
    if(l.checked){
        d3.select("#graph").select(".layer.links").selectAll(".link").each(function (d) {if(tmp.indexOf(d.source.label)>=0||tmp.indexOf(d.target.label)>=0 ) d3.select(this).attr("visibility","visible") });
        if(($('#showlink').is(':checked')))
            d3.select("#graph").select(".layer.linktexts").selectAll(".text").each(function(d){if(tmp.indexOf(d.source.label)>=0||tmp.indexOf(d.target.label)>=0 )d3.select(this).attr("visibility","visible")});
    }else{
        d3.select("#graph").select(".layer.links").selectAll(".link").each(function(d){if(tmp.indexOf(d.source.label)>=0||tmp.indexOf(d.target.label)>=0 )d3.select(this).attr("visibility","hidden")});
        d3.select("#graph").select(".layer.linktexts").selectAll(".text").each(function(d){if(tmp.indexOf(d.source.label)>=0||tmp.indexOf(d.target.label)>=0 )d3.select(this).attr("visibility","hidden")});
    }
    ordergraph();
}


var ordergraph=function(){
	var count=[];
	linkd.each(function(d){if(d3.select(this).attr("visibility")=="hidden") return;if(count.indexOf(d.source.id)<0) count.push(d.source.id);if(count.indexOf(d.target.id)<0) count.push(d.target.id);});
	noded.each(function(d){var i=count.indexOf(d.id); if(i<0) d3.select(this).attr("visibility","hidden"); else  d3.select(this).attr("visibility","visible"); });
	if(($('#shownode').is(':checked')))
	nodetextd.each(function(d){var i=count.indexOf(d.id); if(i<0) d3.select(this).attr("visibility","hidden"); else  d3.select(this).attr("visibility","visible"); });
}

//svg 保存成Png  fuction
var downloadimg=function(){
   Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
    }	
	
	var date=(new Date).Format("yyyyMMddhhmmss");
//	var str=date.getFullYear()+""+(date.getMonth()+1)+""+date.getDate()+""+date.getHours()+""+date.getMinutes()+""+date.getSeconds();
      saveSvgAsPng(document.getElementsByTagName("svg")[0], date+".png",{backgroundColor:'#FFFFFF',height:4000,width:6000,left:-2500,top:-1500,scale:2});
}

var up = function(){
	var formData=new FormData($("#upfile")[0]);
	var start =new Date().getTime();
	$.ajax({
		url:"/upload.do",
		type:"POST",
		cache:false,
		dataType:"text",
		data:formData,
		processData:false,
		contentType:false
	}).done(function(data){
		$("#close").click();
        if(data=="空文件")
		alert("空文件！");
	    else{
		var time=(new Date().getTime()-start)/1000;
	    alert("数据上传完毕 "+"用时:"+Math.floor(time/60)+"分"+Math.floor(time%60)+"秒");}
	}).fail(function(XMLHttpRequest, textStatus, errorThrown) {  
          alert("数据上传出现错误");  
     });
	 	 
}

var del=function(){
	$.ajax({
		url:"/delete.do",
		type:"GET",
		dataType:"text",
	}).done(function(data){	
		alert(data+"数据库已清空");
	}).fail(function(XMLHttpRequest, textStatus, errorThrown) {  
          alert("删除错误");  
     });
}

