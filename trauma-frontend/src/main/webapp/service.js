var TraumaService = Class.create({

	initialize : function(effects) {
		this.effects = effects;
	},

	clickListener : function(map) {

	},

	addMarkerListener : function(map, latLng) {
		var self = this;

	/*if (this.marker) {
			
			map.removeMarker(this.marker);
		};*/


		this.marker = map.showMarker(latLng);
		
		document.getElementById('descfield').value = '';
		this.effects.hide('editbox', function(){});
		this.effects.show('descbox', function() {
			self.effects.focus('descfield');
			map.revGeocode(latLng);
		});
	}


});