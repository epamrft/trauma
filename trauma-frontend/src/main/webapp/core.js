function initMarker (latLng, title, desc, mapToPlace) {
  this.latLng = latLng;
  this.title = title;
  this.desc = desc;
  
    var infowindow = new google.maps.InfoWindow();
    var marker = new google.maps.Marker({

      position: latLng,
      map: mapToPlace  
    });
    
    google.maps.event.addListener(marker, 'click', function(){
    $("#descbox").html('<p>Location:<BR></p>'+desc+'<BR><p>Description:<BR></p>Bus driver eats McChicken.');
  
    document.getElementById('descbox').style.display = "block";        
      });

}

	

  function show_position(p)
{
	
  var pos=new google.maps.LatLng(p.coords.latitude,p.coords.longitude);
	
  var contextMenuOptions={}; 
  contextMenuOptions.classNames={menu:'context_menu', menuSeparator:'context_menu_separator'};
  var menuItems=[];
  
  menuItems.push({className:'context_menu_item', eventName:'center_map_click', label:'Center map here'});
  menuItems.push({className:'context_menu_item', eventName:'add_marker_map', label:'Add marker here'});
  contextMenuOptions.menuItems=menuItems;
  
 
 
  
  var map;
  var geocoder;
  geocoder = new google.maps.Geocoder();
  var marker;
  var infowindow = new google.maps.InfoWindow();
  
   
	
/*WORKS DONT MODIFY IF UR DRUNK */
    var latlng = pos;
    var mapOptions = {
	streetViewControl: false,
	panControl: false,
	zoomControl: true,
	zoomControlOptions: {
	style: google.maps.ZoomControlStyle.LARGE,
	position: google.maps.ControlPosition.TOP_RIGHT
},
	
/*WORKS DONT MODIFY IF UR DRUNK */
      zoom: 14,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    }
    map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
	
	var contextMenu=new ContextMenu(map, contextMenuOptions);
  
  google.maps.event.addListener(map, 'rightclick', function(mouseEvent){
		contextMenu.show(mouseEvent.latLng);
	});
	
/*WORKS DONT MODIFY IF UR DRUNK */
    geocoder.geocode({'latLng': pos}, function(results, status) {
      if (status == google.maps.GeocoderStatus.OK) {
        if (true) {
          map.setZoom(14);
        }
      } else {
        alert("Geocoder failed due to: " + status);
      }
    });
  
	  	google.maps.event.addListener(contextMenu, 'menu_item_selected', function(latLng, eventName){
		switch(eventName){
			case 'center_map_click':
				map.panTo(latLng);
				break;
				
				
			case 'add_marker_map':
				
		geocoder.geocode({'latLng': latLng}, function(results, status) {
      if (status == google.maps.GeocoderStatus.OK) {
        if (true) {
         
          initMarker (latLng, results[0].formatted_address, results[0].formatted_address, map);

        }
      } else {
        alert("Geocoder failed due to: " + status);
      }
    });

				break;
			
		}
		
	});
	
		<!-- WORKS DONT MODIFY IF UR DRUNK --> 
		 
  }
  
