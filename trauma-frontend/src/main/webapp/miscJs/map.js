var map = Class.create({
  initialize: function() {
   
  },
  init: function(element) {
    
  	var mapOptions = {

	streetViewControl: false,
	panControl: false,
	zoomControl: true,
	zoom: 14,
	mapTypeId: google.maps.MapTypeId.ROADMAP,
	zoomControlOptions: {
	style: google.maps.ZoomControlStyle.LARGE,
	position: google.maps.ControlPosition.TOP_RIGHT
		}	
      
    }

    var map = new google.maps.Map(element, mapOptions);

  }
});