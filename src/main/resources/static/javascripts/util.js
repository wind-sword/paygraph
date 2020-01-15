/**
 * 
 */

$(function(){  
  $(document).keydown(function(event){  
    if(event.keyCode==13){  
       $("#search1").click();  
    }  
 });  
}); 

//定位节点--------------------------------------
function find(){
	var name=document.getElementById("searchAcount").value;
	noded.each(function(d,i){
		   if(d.name.indexOf(name)>=0){
		   d3.select(this).style("fill","#FFFF33");
	       }else{
		   d3.select(this).style("fill",function(d){ return d.label=="P"?"#37ff00":"#ff0000";});
		   }
	});

	
}

//清空数据
var exit=function(){
	$("#nodeSize").html(" ");
	$("input[type='checkbox']").prop("checked","true");
	 svg.select("g").selectAll("g").attr("transform","translate(0,0)scale(1)");
		zooma.scale(1);
		zooma.translate([0,0]);
	if(graph.nodes.length>0){
    	linka.data([]).exit().remove();
    	nodea.data([]).exit().remove();
    	linktexta.data([]).exit().remove();
    	nodetexta.data([]).exit().remove();
    	if(arcd){arcd.data([]).exit().remove();}
    	
    	}
    }


var isFixed=true;

var obj = document.getElementById("resetbtn");
obj.onclick = function(){
	noded.each(function(d){
		  d.fixed=isFixed;
	});	
	isFixed=!isFixed;
	obj.innerHTML = (obj.innerHTML =="固定"?"恢复":"固定");
}
