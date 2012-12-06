var Map = Class.create({

	

	initialize : function(service) {
		this.service = service;
		this.actualMarkerOnSelection = null;
		this.tmpMarker = null;
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

		var self = this;

		/*MapMoved - event*/

		google.maps.event.addListener(this.map, 'dragend', function() {

		console.log(self.getVisbleBounds(self.map));
		var url = 'http://trauma.backend.cloudfoundry.com/markers';

      	var data = {
            "central-lng" : self.map.getCenter().lng(),
            "central-lan" : self.map.getCenter().lat(),
            "central-rad" : self.getVisbleBounds(self.map)
        };

        
      	new Ajax.Request(url, {
      	method: 'GET',
      	parameters: Object.toJSON(data),
      	contentType: 'application/json',
      	onSuccess: function(transport) {        
     	 $('response').update(transport.responseText);

      	var xMarkerArray = JSON.parse($('response').innerHTML);


      	for(var i in xMarkerArray){

            var xMarker = xMarkerArray[i];
            if( i=="each" ) break; 
            
            self.map.showMarker(xMarker);
            
          }

        }
      
      });


	});

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

				self.service.addMarkerListener(self, latLng);
				self.indicateMarkerPlacement(self,latLng);
				break;
			}

		});

	},

	showMarker : function(xMarkerInfo) {
		var marker = new google.maps.Marker({
			position: new google.maps.LatLng(xMarkerInfo.latitude,xMarkerInfo.longitude),
			ID: xMarkerInfo.id,
			map: this.map,
      		description: xMarkerInfo.desc
		});

		// To add the marker to the map, call setMap();

		marker.setMap(this.map);
		this.revGeocode(this.position);
		this.onMarkerClick(marker,this.revGeocode);
		return marker;
	},

	removeMarker : function() {
		this.actualMarkerOnSelection.setMap(null);
	},

	onMarkerClick : function (marker,geocoder){
		var self = this;
		google.maps.event.addListener(marker, 'click', function() {


      	var effect = new Effects();
		geocoder(this.position);
      	document.getElementById('editBoxDescfield').value = this.description;
        $('actualMarkerID').update(this.ID);

      	effect.hide('descbox', function(){});
      	effect.show('editbox', function() {
		effect.focus('editBoxDescfield');			
			});

      	if(self.actualMarkerOnSelection)
      	{self.actualMarkerOnSelection.setIcon('http://maps.google.com/mapfiles/ms/icons/red-dot.png');}
      	self.actualMarkerOnSelection = marker;
      	self.actualMarkerOnSelection.setIcon('http://maps.google.com/mapfiles/ms/icons/green-dot.png');

      	

    	});

		
	},


	revGeocode : function (latLng) {
		var geocoder = new google.maps.Geocoder();

		geocoder.geocode({'latLng': latLng}, function(results, status) {
      	if (status == google.maps.GeocoderStatus.OK) {
        	if (true) {
        	//Always true
        	var geoLocBox = document.getElementById('geoloc');
        	var editLocBox = document.getElementById('editBoxGeoloc');
				if (geoLocBox)
				{
				editLocBox.innerHTML = results[0].formatted_address;
				geoLocBox.innerHTML = results[0].formatted_address;
				}

				document.getElementById("lat").innerHTML=latLng.lat(); 
				document.getElementById("lng").innerHTML=latLng.lng(); 
        	}
      	}

      	else {
        //Geocoding failed
      	}

    });
  		
	},


	indicateMarkerPlacement : function (map,latLng){

		if(this.tmpMarker){
				this.tmpMarker.setMap(null);
				}

		var marker = new google.maps.Marker({
			position: latLng,
			map: this.map   
		});
		marker.setMap(this.map);
		this.tmpMarker = marker;
      	this.tmpMarker.setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png');
	},

	getVisbleBounds : function (map){

		return this.map.getBounds().getNorthEast().lat() - this.map.getBounds().getSouthWest().lat();

	}

	

});