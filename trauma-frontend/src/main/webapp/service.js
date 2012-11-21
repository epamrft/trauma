var TraumaService = Class.create({

	initialize : function(effects) {
		this.effects = effects;
	},

	clickListener : function(map) {

	},

	addMarkerListener : function(map, latLng) {
		var self = this;

		if (this.marker) {
			// remove unsaved marker
			map.removeMarker(this.marker);
		}

		this.marker = map.showMarker(latLng);
		document.getElementById('descfield').value = '';
		this.effects.show('descbox', function() {
			self.effects.focus('descfield');
		});
	}


});