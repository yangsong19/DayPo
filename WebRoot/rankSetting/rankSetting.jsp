<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript" src="../assets/js/jquery.js"></script>
<link href="../assets/css/bootstrap.css" rel="stylesheet" />
<script type="text/javascript" src="../assets/js/google-code-prettify/prettify.js"></script>
<script type="text/javascript" src="../assets/js/bootstrap-transition.js"></script>
<script type="text/javascript" src="../assets/js/bootstrap-alert.js"></script>
<script type="text/javascript" src="../assets/js/bootstrap-modal.js"></script>
<script type="text/javascript" src="../assets/js/bootstrap-dropdown.js"></script>
<script type="text/javascript" src="../assets/js/bootstrap-scrollspy.js"></script>
<script type="text/javascript" src="../assets/js/bootstrap-tab.js"></script>
<script type="text/javascript" src="../assets/js/bootstrap-tooltip.js"></script>
<script type="text/javascript" src="../assets/js/bootstrap-popover.js"></script>
<script type="text/javascript" src="../assets/js/bootstrap-button.js"></script>
<script type="text/javascript" src="../assets/js/bootstrap-collapse.js"></script>
<script type="text/javascript" src="../assets/js/bootstrap-carousel.js"></script>
<script type="text/javascript" src="../assets/js/bootstrap-typeahead.js"></script>
<script type="text/javascript" src="../assets/js/bootstrap-affix.js"></script>
<script type="text/javascript" src="../assets/js/application.js"></script>
<script type="text/javascript" src="../assets/js/bootstrap.js"></script>
<script type="text/javascript">
	$(function () {
		$('#myTab a').click(function (e) {
		 	 e.preventDefault();
		  	 $(this).tab('show');
		  	 $('.dropdown-toggle').dropdown();
		});
    });
		document.getElementById('cid').onclick = function(){
			alert("why");
		}
    $(function(){
    	$('cid').focusin(function(){
			alert("cid");
		});
    });
	</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RankSetting</title>
</head>
<body>
		<div class="span11 offset8">
			<ul id="myTab" class="nav nav-tabs" >
				<li class="active"><a href="#VideoRank" data-toggle="tab">Video Rank</a></li>
				<li class=""><a href="#ChannelRank" data-toggle="tab">Channel Rank</a></li>
				<li class=""><a href="#SiteRank" data-toggle="tab">Site Rank</a></li>
				<li class=""><a href="#CarrierRank" data-toggle="tab">Carrier Rank</a></li>
				<li class=""><a href="#CountryRank" data-toggle="tab">Country Rank</a></li>
				<li class=""><a href="#RegionRank" data-toggle="tab">Region Rank</a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li class=""><a href="#dropdown1" data-toggle="tab">@fat</a></li>
						<li class=""><a href="#dropdown2" data-toggle="tab">@mdo</a></li>
					</ul>
				</li>
			</ul>
			<div class="tab-content" id="myTabContent">
				<div class="tab-pane active" id="VideoRank">
					<input type='text' value='search a cid' name='cid'/>
				</div>
				<div class="tab-pane fade" id="ChannelRank">
					<p>Channel </p>
				</div>
				<div class="tab-pane fade" id="SiteRank">
					<p>Site  </p>
				</div>
				<div class="tab-pane fade" id="CarrierRank">
					<p>Carrier  </p>
				</div>
				<div class="tab-pane fade" id="CountryRank">
					<p>Country  </p>
				</div>
				<div class="tab-pane fade" id="RegionRank">
					<p>Region  </p>
				</div>
				<div class="tab-pane fade" id="dropdown1">
					<p>@fat </p>
				</div>
				<div class="tab-pane fade" id="dropdown2">
					<p>@mdo </p>
				</div>
			</div>
		</div>
</body>
</html>