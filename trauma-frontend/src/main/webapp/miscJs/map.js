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

    var contextMenuOptions={}; 
  	contextMenuOptions.classNames={menu:'context_menu', menuSeparator:'context_menu_separator'};
  	var menuItems=[];
  
  	menuItems.push({className:'context_menu_item', eventName:'center_map_click', label:'Center map here'});
  	menuItems.push({className:'context_menu_item', eventName:'add_marker_map', label:'Add marker here'});
  	contextMenuOptions.menuItems=menuItems;

  	var contextMenu=new ContextMenu(element, contextMenuOptions);
  
  		google.maps.event.addListener(element, 'rightclick', function(mouseEvent){
		contextMenu.show(mouseEvent.latLng);
		
	});

  }
});