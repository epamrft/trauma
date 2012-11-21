<!DOCTYPE html>
<html>
<head>
<title>Trauma MAP</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no">
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="frontend.css" />

<script type="text/javascript" src="http://script.aculo.us/prototype.js"></script>
<script type="text/javascript"
	src="http://script.aculo.us/scriptaculous.js"></script>
<script type="text/javascript" src="http://script.aculo.us/effects.js"></script>

<script src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script type="text/javascript" src="comms.js"></script>
<script src="miscJs/geo-min.js" type="text/javascript"></script>
<script src="miscJs/context.js"></script>
<script src="miscJs/map.js"></script>
<script src="service.js"></script>
<script src="effects.js"></script>


</head>

<body>

	<img id="logo" src="logo.png" alt="TRAUMA LOGO" height="60">

	<div id="map_canvas"></div>

	<div id="footer">
		<div id="traumaCaption">&lt;TraUMa&gt;</div>
	</div>

	<div id="descbox" style="display: none;">

		<div id="lat"></div>
		<div id="lng"></div>

		<input type="text" id="geoloc" readonly="readonly"/>
		<BR>
		<textarea id="descfield" class="ownWidth" cols="21"></textarea>
		<BR>
		<input onclick="saveInfo()" type="button" id="saveBtn" value="Save">
		<input onclick="cancelProcess()" type="button" id="cancelBtn" value="Cancel">
		
	</div>

	<script>



		function initMap(p) {

			var service = new TraumaService(new Effects());
			var com = new BackendComms();
			var map = new Map(service,com);
			map.init(document.getElementById("map_canvas"), p);
		}

		if (geo_position_js.init()) {
			geo_position_js.getCurrentPosition(initMap, function() {/*Ide jon az errorhandling*/
			}, {
				enableHighAccuracy : true
			});
		} else {
			//Ide jon az error handling
		}



		/*Gombok kezelése TODO*/
		
		
		function saveInfo()
  		{
  		
  		}

  		function cancelProcess()
  		{
  		var descEffects = new Effects();
    	descEffects.hide('descbox');  
  		}

	</script>

</body>
</html>