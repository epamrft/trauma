var Map = Class.create({

	initialize : function(service,comms) {
		this.service = service;
		this.comms = comms;
	},

	init : function(element, p) {

		var pos = new google.maps.LatLng(p.coords.latitude, p.coords.longitude);

		var mapOptions = {

			streetViewControl : false,
			panControl : false,
			zoomControl : true,
			zoom : 14,
			center : pos,
			mapTypeId : google.maps.MapTypeId.ROADMAP,
			zoomControlOptions : {
				style : google.maps.ZoomControlStyle.LARGE,
				position : google.maps.ControlPosition.TOP_RIGHT

			}

		};

		this.map = new google.maps.Map(element, mapOptions);

		var contextMenu = this.addContextMenu(this.map);

		this.addContextListeners(this.map, contextMenu);

		comms.getMatkers();

	},

	addContextMenu : function(map) {

		var contextMenuOptions = {};
		contextMenuOptions.classNames = {
			menu : 'context_menu',
			menuSeparator : 'context_menu_separator'
		};
		var menuItems = [];

		menuItems.push({
			className : 'context_menu_item',
			eventName : 'center_map_click',
			label : 'Center map here'
		});
		menuItems.push({
			className : 'context_menu_item',
			eventName : 'add_marker_map',
			label : 'Add marker here'
		});
		contextMenuOptions.menuItems = menuItems;

		return new ContextMenu(map, contextMenuOptions);

	},

	addContextListeners : function(map, contextMenu) {

		var self = this;

		google.maps.event.addListener(map, 'rightclick', function(mouseEvent) {
			contextMenu.show(mouseEvent.latLng);
		});

		google.maps.event.addListener(map, 'click', function(mouseEvent) {
			self.service.clickListener(self);
		});

		google.maps.event.addListener(contextMenu, 'menu_item_selected', function(latLng, eventName) {
			switch (eventName) {
			case 'center_map_click':
				map.panTo(latLng);
				break;
			case 'add_marker_map':
				console.log("Marker Added.");
				self.service.addMarkerListener(self, latLng);
				break;
			}

		});

	},

	showMarker : function(latLng) {
		var marker = new google.maps.Marker({
			position : latLng
		});

		// To add the marker to the map, call setMap();

		marker.setMap(this.map);
		this.revGeocode(latLng);
		return marker;
	},

	removeMarker : function(marker) {
		marker.setMap(null);
	},


	revGeocode : function (latLng) {
		var geocoder = new google.maps.Geocoder();

		geocoder.geocode({'latLng': latLng}, function(results, status) {
      	if (status == google.maps.GeocoderStatus.OK) {
        	if (true) {
        	//Always true
        	var geoLocBox = document.getElementById('geoloc');
				if (geoLocBox)
				{
				geoLocBox.value = results[0].formatted_address;
				}

				document.getElementById("lat").innerHTML=latLng.lat(); 
				document.getElementById("lng").innerHTML=latLng.lng(); 
        	}
      	}

      	else {
        //Geocoding failed
      	}

    });
  		
	}

});