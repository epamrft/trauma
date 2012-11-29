var ToolKit = Class.create({

initialize : function() {
    
  },

	addCallbackMarker : function (xMarkerInfo,map) {
    
    console.log(xMarkerInfo.id+" | "+xMarkerInfo.latitude + " " +xMarkerInfo.longitude+" "+xMarkerInfo.desc + " added OK!");

    var marker = new google.maps.Marker({
      position: new google.maps.LatLng(xMarkerInfo.latitude,xMarkerInfo.longitude),
      ID: xMarkerInfo.id,
      description: xMarkerInfo.desc,
    });

    marker.setMap(this.map);
    
    google.maps.event.addListener(marker, 'click', function() {
      alert(this.ID);
    });
  }


});