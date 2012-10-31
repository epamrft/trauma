var Map = Class.create({
  initialize: function() {
   
  },
  init: function(element,p) {
    
  	var pos=new google.maps.LatLng(p.coords.latitude,p.coords.longitude);}

  	var mapOptions = {

			streetViewControl: false,
			panControl: false,
			zoomControl: true,
			zoom: 14,
			center: pos,
			mapTypeId: google.maps.MapTypeId.ROADMAP,
			zoomControlOptions: {
			style: google.maps.ZoomControlStyle.LARGE,
			position: google.maps.ControlPosition.TOP_RIGHT
		}	
      
    }

    var map = new google.maps.Map(element, mapOptions);

  }
});