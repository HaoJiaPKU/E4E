document.write("<script language='javascript' src='script/jquery-2.0.3.js'></script>");
document.write("<script language='javascript' src='script/wz_jsgraphics.js'></script>");
document.write("<script language='javascript' src='script/esl.js'></script>");
document.write("<script language='javascript' src='script/echarts.js'></script>");
document.write("<script language='javascript' src='script/Chart.min.js'></script>");

var selectedCourseName;

function getTrainingRoad(){
	var sourceGrade = document.getElementById("sourceGrade").value;
	var sourceJob = document.getElementById("sourceJob").value;
	var targetGrade = document.getElementById("targetGrade").value;
	var targetJob = document.getElementById("targetJob").value;
	var planType = document.getElementById("planType").value;
	
	var grade = new Array();
	var gradeCourse = new Array();
	var coursePair = new Array();
	
	//console.log(sourceGrade + " " + sourceJob + " " + targetGrade + " " + targetJob);
	$.ajax({
		url: "/E4E/TrainingRoadMap",
		data: {
			sourceGrade: sourceGrade,
			sourceJob: sourceJob,
			targetGrade: targetGrade,
			targetJob: targetJob,
			planType: planType,
		},
		type : "POST",
		async : false,
		dataType : "json",
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		success : function(json){
			
			console.log(json);
			$('#content').empty();
			$('#roadMap').empty();
			var script = document.createElement("script");
			script.innerHTML = json.script;
			
			grade = json.grade;
			gradeCourse = json.gradeCourse;
			coursePair = json.coursePair;
			
			$('#graphScript').empty();
			$('#graphScript').append(script);
			document.close();
		},
		error : function(xhr, status){
			return false;
		},
		complete : function(xhr, status){
			console.log("getTrainingRoad() complete");
		}
	});
	
	require.config({
		paths:{ 
			'echarts' : 'script/echarts',
	        'echarts/chart/force' : 'script/echarts',
	        'echarts/chart/chord' : 'script/echarts'
	    }
	});

	require(
		[
	     'echarts',
	     'echarts/chart/force',
	     'echarts/chart/chord'
	    ],
	    function (ec) {
	    	var myChart = ec.init(document.getElementById('graph')); 
	    	option = {
	    		title : {
	    			x:'right',
	    			y:'bottom'
	    		},
	    		tooltip : {
	    			trigger: 'item',
	    			formatter: '{a} : {b}'
	    		},
	    		toolbox: {
	    			show : false,
	    			feature : {
	    				restore : {show: true},
	    				magicType: {show: true, type: ['force', 'chord']},
	    				saveAsImage : {show: true}
	    			}
	    		},
	    		legend: {
	    			orient : 'vertical',
	    			x: 'left',
	    			data: grade
	    		},
	    		series : [
	    		    {
	    		    	type:'force',
	    		    	name : "先修课程",
	    		    	ribbonType: false,
	    		    	categories : function(){
	    		        	var categorySet = [];
	    		        	for(var i = 0; i < grade.length; i ++){
	    		        		var item = {name: grade[i]}
	    		        		categorySet.push(item);
	    		        	}
	    		        	return categorySet;
	    		        }(),
	    		    	itemStyle: {
	    		    		normal: {
	    		    			label: {
	    		    				show: true,
	    		    				textStyle: {
	    		    					color: '#333'
	    		    				}
	    		    			},
	    		                nodeStyle : {
	    		                	brushType : 'both',
	    		                	borderColor : 'rgba(255,215,0,0.4)',
	    		                	borderWidth : 1
	    		                }
	    		    		}
	    		        },
	    		        useWorker: false,
	    		        minRadius : 15,
	    		        maxRadius : 25,
	    		        gravity: 1.1,
	    		        scaling: 1.1,
	    		        roam: 'move',
	    		        nodes: function(){
	    		        	var nodeSet = [];
	    		        	for(var i = 0; i < gradeCourse.length; i ++){
	    		        		for(var j = 0; j < gradeCourse[i].length; j ++){
	    		        			var item = {category: i, name: gradeCourse[i][j],
	    		        				onclick : function(param){
	    		        					
	    		        					clickCourseNode(param.name);
	    		        				}
	    		        			};
	    		        			nodeSet.push(item);
	    		        		}
	    		        	}
	    		        	return nodeSet;
	    		        }(),
	    		        links: function(){
	    		        	var linkSet = [];
	    		        	for(var i = 0; i < coursePair.length; i ++){
	    		        		var item = {source: coursePair[i][1], target: coursePair[i][2], itemStyle : {normal : {lineWidth: coursePair[i][0]}}};
	    		        		linkSet.push(item);
	    		        	}
	    		        	return linkSet;
	    		        }()
	    		    }
	    		]
	    	};
	    	myChart.setOption(option);
	    	
	    	var ecConfig = require('echarts/config');
	    	function focus(param) {
	    	    clickCourseNode(param.data.name);
	    	    console.log(selectedCourseName);
	    	}
	    	myChart.on(ecConfig.EVENT.CLICK, focus);
	    }
	);
}

function clickCourseNode(courseName){
	$('#content').empty();
	console.log(current.innerHTML);
	selectedCourseName = courseName;
	$.ajax({
		url: "/E4E/CourseContent",
		data: {
			courseName: courseName,
		},
		type : "POST",
		async : false,
		dataType : "json",
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		success : function(json){
			
			console.log(json);
			if(json.indexOf("File Not Found") != -1){
				$('#info').empty();
				$('#info').append(json);
			}
			else{
				$('#info').empty();
				$('#info').append("操作完成");
				$('#content').empty();
				$('#content').append(json);
			}				
			document.close();
		},
		error : function(xhr, status){
			return false;
		},
		complete : function(xhr, status){
			console.log("click complete");
		}
	});
}

function selectCourse(){
	if($('#selected').html().indexOf(selectedCourseName) != -1){
		$('#info').empty();
		$('#info').append("请勿重复添加相同课程");
		return;
	}
	else{
		$('#info').empty();
		$('#info').append("操作完成");
	}
	//console.log($('#selected'));
	if($('#selected').html() == ""){
		var tdEleName = document.createElement("th");
		tdEleName.innerHTML = "课程名称";
		
		var tdEleHour = document.createElement("th");
		tdEleHour.innerHTML = "时数";
		
		var tdEleProject = document.createElement("th");
		tdEleProject.innerHTML = "项目数";
		
		var tdEleOperation = document.createElement("th");
		tdEleOperation.innerHTML = "操作";
		
		var trEle = document.createElement("tr");
		trEle.appendChild(tdEleName);
		trEle.appendChild(tdEleHour);
		trEle.appendChild(tdEleProject);
		trEle.appendChild(tdEleOperation);
		$('#selected').append(trEle);
	}
	
	var trEle = document.createElement("tr");
	
	//console.log(selectedCourseName);
	$.ajax({
		url: "/E4E/CourseBriefInfo",
		data: {
			courseName: selectedCourseName,
		},
		type : "POST",
		async : false,
		dataType : "json",
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		success : function(json){
			
			//console.log(json);
			if(json == "File Not Found")
				return;
			trEle.innerHTML = json;
			$('#selected').append(trEle);
		},
		error : function(xhr, status){
			return false;
		},
		complete : function(xhr, status){
			console.log("selectCourse() complete");
		}
	});
}

function deleteCourse(){
	$(event.target).parent().parent().remove();
}

function showAllContent(){
	console.log($('#selected').html());
	$.ajax({
		url: "/E4E/TrainingRoadPlan",
		data: {
			table: $('#selected').html(),
		},
		type : "POST",
		async : false,
		dataType : "json",
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		success : function(json){
			
			console.log(json);
			$('#content').empty();
			$('#content').append(json);
			document.close();
		},
		error : function(xhr, status){
			return false;
		},
		complete : function(xhr, status){
			console.log("showAllContent() complete");
		}
	});
}