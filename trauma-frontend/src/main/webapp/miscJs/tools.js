var ToolKit = Class.create({

initialize : function() {
    
  },

	addCallbackMarker : function (xMarkerInfo) {
		
		console.log(xMarkerInfo.id+" | "+xMarkerInfo.latitude + " " +xMarkerInfo.longitude+" "+xMarkerInfo.desc + " added OK!");

		var marker = new google.maps.Marker({
    	position: new google.maps.LatLng(xMarkerInfo.latitude,xMarkerInfo.longitude),
    	ID: xMarkerInfo.id,
    	description: xMarkerInfo.desc,
    	map: map
		});

		google.maps.event.addListener(marker, 'click', function() {
			alert(this.ID);
		});
	}


});