 <!--TRAUMA JSP-->

<!DOCTYPE html>
<html>
<head>
<title>Trauma MAP</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no">
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="frontend.css" />
<link rel="stylesheet" type="text/css" href="jackedup.css" />


<script type="text/javascript" src="http://script.aculo.us/prototype.js"></script>
<script type="text/javascript"
	src="http://script.aculo.us/scriptaculous.js"></script>
<script type="text/javascript" src="http://script.aculo.us/effects.js"></script>

<script src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script src="miscJs/geo-min.js" type="text/javascript"></script>
<script src="miscJs/humane.js" type="text/javascript"></script>
<script src="miscJs/context.js"></script>
<script src="miscJs/map.js"></script>
<script src="service.js"></script>
<script src="effects.js"></script>
<script src="miscJs/tools.js"></script>
<script src="comms.js"></script>
<script src="buttonActions.js"></script>


</head>

<body>

	<img id="logo" src="logo.png" alt="TRAUMA LOGO" height="60">

	<div id="map_canvas"></div>

	<div id="footer">
		<div id="traumaCaption">&lt;TraUMa&gt;</div>
	</div>

	<div id="descbox" style="display: none;">


		<div id = "geoloc"></div>
		<br>
		<textarea id="descfield" cols="21" spellcheck="false"></textarea>
		<br>
		<input type="button" id="saveBtn" value="Save">
		<input type="button" id="cancelBtn" value="Cancel">
		
	</div>


	<div id="editbox" style="display: none;">

		<div id = "editBoxGeoloc"></div>
		<br>
		<textarea id="editBoxDescfield" cols="21" spellcheck="false"></textarea>
		<br>
		<input type="button" id="editBtn" value="Edit">
		<input type="button" id="delBtn" value="Delete">
		
	</div>

	<div id="lat"></div>
	<div id="lng"></div>
	<div id="response"></div>
	<div id="actualMarkerID"></div>

	<script>

		var service = new TraumaService(new Effects());
		var map = new Map(service);
		var kom = new BackendComms(map);
		var actions = new buttonActions(kom);

		function initMap(p) {

			
			map.init(document.getElementById("map_canvas"), p);
			kom.getMarkers(p.coords.longitude,p.coords.latitude,2.0,map);

			
				

		}

		if (geo_position_js.init()) {
			geo_position_js.getCurrentPosition(initMap, function() {
				alert('This site needs active tracking of your location, please enable it and refresh the page!')
			}, {
				enableHighAccuracy : true
			});
		} else {
			alert('This site needs active tracking of your location, please enable it and refresh the page!');
		}

	</script>

</body>
</html>