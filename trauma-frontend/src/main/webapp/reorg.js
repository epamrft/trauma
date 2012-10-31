function initMarker (latLng, title, desc) {
	this.latLng = latLng;
	this.title = title;
	this.desc = des;
	alert("Hi");

	function showMarker () {
		
		var infowindow = new google.maps.InfoWindow();
		var marker = new google.maps.Marker({

			position: new google.maps.LatLng(latLng);
			map: map;			
		});
		marker.html = '<div>Ez itt van: ['latLng']</div>';
		google.maps.event.addListener(marker, 'click', function(){
        infoWindow.setContent(this.html);
        infoWindow.open(map, this);
        alert("Csa");
    });

	}


}