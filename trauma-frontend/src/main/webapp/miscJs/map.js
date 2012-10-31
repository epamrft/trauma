var Map = Class.create({
  initialize: function() {
   
  },
  init: function(element,p) {
    
  	var pos=new google.maps.LatLng(p.coords.latitude,p.coords.longitude);

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

	var contextMenu=this.addContextMenu(map);

	google.maps.event.addListener(map, 'rightclick', function(mouseEvent){
	contextMenu.show(mouseEvent.latLng);
	});

	this.addContextListeners(map,contextMenu);

	},




    addContextMenu: function(map){

    var contextMenuOptions={}; 
  	contextMenuOptions.classNames={menu:'context_menu', menuSeparator:'context_menu_separator'};
  	var menuItems=[];
  
  	menuItems.push({className:'context_menu_item', eventName:'center_map_click', label:'Center map here'});
  	menuItems.push({className:'context_menu_item', eventName:'add_marker_map', label:'Add marker here'});
  	contextMenuOptions.menuItems=menuItems; 	

		return new ContextMenu(map, contextMenuOptions);

	},


	addContextListeners: function(map,contextMenu){

		google.maps.event.addListener(contextMenu, 'menu_item_selected', function(latLng, eventName){
		switch(eventName){
			case 'center_map_click':
				map.panTo(latLng);
				break;
				
				
			case 'add_marker_map':
				
				console.log("Marker Added.")

				break;
			
		}
		
	});

	}


});