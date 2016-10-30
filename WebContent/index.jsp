<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    
    <head>
        <title></title>
        <!-- Bootstrap -->
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
        <link href="bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
        <link href="vendors/easypiechart/jquery.easy-pie-chart.css" rel="stylesheet" media="screen">
        <link href="assets/styles.css" rel="stylesheet" media="screen">
        
        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        
        <script src="vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
        
        <script src="script/E4E_functions.js" type="text/javascript"></script>
        <script src="script/jquery-2.0.3.js" type="text/javascript"></script>
        <script src="script/wz_jsgraphics.js" type="text/javascript"></script>
    </head>
    
    <body>
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container-fluid">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span>
                     <span class="icon-bar"></span>
                     <span class="icon-bar"></span>
                    </a>
                    <a class="brand" href="#">E4E</a>
                    <div class="nav-collapse collapse">
                        <ul class="nav pull-right">
                            <li class="dropdown">
                                <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"> <i class="icon-user"></i> Admin <i class="caret"></i>
                                </a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a tabindex="-1" href="#">Profile</a>
                                    </li>
                                    <li class="divider"></li>
                                    <li>
                                        <a tabindex="-1" href="#">Logout</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                        <ul class="nav">
                            <li class="active">
                                <a href="#">業務行銷</a>
                            </li>
                            <li>
                                <a href="#">物料</a>
                            </li>
                            <li>
                                <a href="#">品保</a>
                            </li>
                        </ul>
                    </div>
                    <!--/.nav-collapse -->
                </div>
            </div>
        </div>
        
        <div class="container-fluid">
            <div class="row-fluid">
                <div class="span4">
                                            
                    <div class="row-fluid" id="select">
                    
							<label class="control-label">Current Grade:</label>
							<select id="sourceGrade" class="form-control" style="width:200px;">
								<option value="A7">A7</option>
								<option value="S1/S2/S3">S1/S2/S3</option>
								<option value="S4/S5">S4/S5</option>
								<option value="P1/P2">P1/P2</option>
								<option value="P3/P4">P3/P4</option>
								<option value="M1+">M1+</option>
							</select>
							
							<label class="control-label">Current Job:</label>
							<select id="sourceJob" class="form-control" style="width:200px;">
								<option value="通讯">通讯</option>
								<option value="品牌与解决方案(TBC)">品牌与解决方案(TBC)</option>
								<option value="OEM/ODM">OEM/ODM</option>
								<option value="PM">PM</option>
							</select>
						
							<label class="control-label">Target Grade:</label>
							<select id="targetGrade" class="form-control" style="width:200px;">
								<option value="A7">A7</option>
								<option value="S1/S2/S3">S1/S2/S3</option>
								<option value="S4/S5">S4/S5</option>
								<option value="P1/P2">P1/P2</option>
								<option value="P3/P4">P3/P4</option>
								<option value="M1+">M1+</option>
							</select>
						
							<label class="control-label">Target Job:</label>
							<select id="targetJob" class="form-control" style="width:200px;">
								<option value="通讯">通讯</option>
								<option value="品牌与解决方案(TBC)">品牌与解决方案(TBC)</option>
								<option value="OEM/ODM">OEM/ODM</option>
								<option value="PM">PM</option>
							</select>
						
							<label class="control-label">Constraint:</label>
							<select id="planType" class="form-control" style="width:200px;">
								<option value="time">时间优先</option>
								<option value="project">项目数量优先</option>
							</select>
					</div>
					
					<div class="row-fluid">
						<button onclick="getTrainingRoad()" class="btn btn-info" style="width:200px; margin-bottom:10px">
							搜索训练路径
						</button>
					<div class="row-fluid">
					</div>
						<button onclick="selectCourse()" class="btn btn-info" style="width:200px; margin-bottom:10px">
							加入训练计划
						</button>
					<div class="row-fluid">
					</div>
						<button onclick="showAllContent()" class="btn btn-info" style="width:200px; margin-bottom:10px">
							显示训练内容
						</button>
					</div>
							
					<div class="block">
						<div class="navbar navbar-inner block-header">
							<div class="muted pull-left">Selections</div>
							<div class="pull-right"><span class="badge badge-warning"></span></div>
						</div>
						<div style="margin:10px; overflow:auto;" class="block-content collapse in">
							<table id="selected" class="table table-striped table-hover"></table>
						</div>
					</div>
					
                </div>
                
                <!--/span-->
                <div class="span8">
                    <div class="row-fluid">
                        <div class="alert alert-success">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
                            <h4 id="info">Info</h4>
                        </div>
                    </div>
	                
	                <div class="row-fluid">
                    	<div class="span12">   
                   		<!-- block -->
	                        <div class="block">
	                            <div class="navbar navbar-inner block-header">
	                                <div class="muted pull-left">Course Graph</div>
	                                <div class="pull-right"><span class="badge badge-warning"></span></div>
	                            </div>
	                            <div id="graph" style="width:100%; height:500px; clear:both; overflow:auto;" class="block-content collapse in">
	                                
	                            </div>
	                        </div>
	                	</div>
	                </div>
	                
	                <div class="row-fluid">
                    	<div class="span12">   
                   		<!-- block -->
	                        <div class="block">
	                            <div class="navbar navbar-inner block-header">
	                                <div class="muted pull-left">Course Content</div>
	                                <div class="pull-right"><span class="badge badge-warning"></span></div>
	                            </div>
	                            <div id="content" style="clear:both; margin:10px; overflow:auto;" class="block-content collapse in">
	                                
	                            </div>
	                        </div>
	                	</div>
	                </div>
                    
                </div>
            </div>
            <hr>
            <p>&copy; SEKE PKU & Delta 2015 </p>
        </div>
        <div id="graphScript"></div>
        <!--/.fluid-container-->
        
        <script src="bootstrap/js/bootstrap.min.js"></script>
        <script src="vendors/easypiechart/jquery.easy-pie-chart.js"></script>
        <script src="assets/scripts.js"></script>
</body>

<script>
var lastItem;
var lastBackgroundColor;
var selectedCourseName;

$('DIV').mouseover(function(event){
	current = event.target;
	//console.log("mouseover op:" + current.tagName);
	//console.log("mouseover op:" + current.getAttribute('class'));
	
	if(current.getAttribute('style') != null && current.getAttribute('style').indexOf('position:absolute;overflow:hidden;') != -1){		
		if(lastItem != null)
			lastItem.style.backgroundColor = lastBackgroundColor;
		lastItem = current;
		lastBackgroundColor = current.style.backgroundColor;
		current.style.backgroundColor = '#dff0d8';
		event.stopPropagation();
	}
});

$('DIV').click(function(event){
	current = event.target;
	//console.log("click op:" + current.tagName);
	//console.log("click op:" + current.getAttribute('style'));
	
	/**
	current.tagName != 'DEL' && current.tagName != 'SPAN'
		&& current.tagName != 'TH' && current.tagName != 'TD'
			&& current.tagName != 'TR' && current.tagName != 'TABLE'
			&& current.tagName != 'BUTTON' && current.tagName != 'SELECT'
			&& current.tagName != 'LABEL'
			&& current.getAttribute('class') != 'plan'
			&& current.getAttribute('id') != 'select' && current.getAttribute('id') != 'roadMap'
			&& current.getAttribute('id') != 'name' && current.getAttribute('id') != 'content'
	**/
	
	if(current.tagName != 'DEL' && current.tagName != 'SPAN'
		&& current.tagName != 'TH' && current.tagName != 'TD'
			&& current.tagName != 'TR' && current.tagName != 'TABLE'
			&& current.tagName != 'BUTTON' && current.tagName != 'SELECT'
			&& current.tagName != 'LABEL'
			&& current.getAttribute('class') != 'plan'
			&& current.getAttribute('id') != 'select' && current.getAttribute('id') != 'roadMap'
			&& current.getAttribute('id') != 'name' && current.getAttribute('id') != 'content'
	){	
		$('#content').empty();
		//console.log(current.innerHTML);
		selectedCourseName = current.innerHTML;
		$.ajax({
			url: "/E4E/CourseContent",
			data: {
				courseName: current.innerHTML,
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
	event.stopPropagation();
	}
});

</script>
</html>