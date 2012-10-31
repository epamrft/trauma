<!DOCTYPE html>
<html>
<head>
	<title>Trauma MAP</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="frontend.css" />
	
	<script src="miscJs/prototype.js" type="text/javascript"></script>

	<script src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>

	<script src="miscJs/geo-min.js" type="text/javascript"></script>	
	<script src="miscJs/context.js"></script>
	<script src="miscJs/map.js"></script>
		
</head>

<body>

	<img id="logo" src="logo.png" alt="TRAUMA LOGO" height="60">

	<div id="map_canvas"></div>

	<div id="footer">
		<div id="traumaCaption">
			&lt;TraUMa&gt;
		</div>
	</div>

	<div id="descbox">

		<input id="descfield" type="text" value="Here is the text" /> 

	</div>

	<script>
		var map = new Map();
		map.init(document.getElementById("map_canvas"));
	</script>
	
</body>
</html>