<!DOCTYPE html>
<html>
  <head>
    <title>Trauma MAP</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="frontend.css" />
	
    <script src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="effectsJS/jquery.ui.map.full.min.js" type="text/javascript"></script>
	<script src="effectsJS/geo-min.js" type="text/javascript"></script>
	<script src="core.js" type="text/javascript"></script>
	<script src="effectsJS/context.js"></script>

	
	<script>

	

	if(geo_position_js.init())
	{
		geo_position_js.getCurrentPosition(show_position,function(){/*Ide jon az errorhandling*/},{enableHighAccuracy:true});
	}
	else
	{
		//Ide jon az error handling
	}
	


	
	$(document).ready(function () {
		$('#logo').delay(1000).fadeIn(3000);
	});
	

	</script>
	
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

   
  </body>
</html>