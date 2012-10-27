<!DOCTYPE html>
<html>
  <head>
    <title>Trauma MAP</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="frontend.css" />
    <script src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
    <script>
      var map;
      function initialize() {
        var mapOptions = {
          zoom: 8,
          center: new google.maps.LatLng(-34.397, 150.644),
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map(document.getElementById('map_canvas'),
            mapOptions);
      }

      google.maps.event.addDomListener(window, 'load', initialize);
    </script>
  </head>
  <body>
  

  	
  		<div id="header">
  		LOGO COMES HERE
  		</div>
  		

    	<div id="map_canvas"></div>
    	
    	<div id="footer">
    	Powered by pure awesomeness.
    	</div>
    	

   
  </body>
</html>