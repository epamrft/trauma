var BackendComms = Class.create({

	initialize : function() {
		var url = 'http://trauma.backend.cloudfoundry.com/markers';
	},


	sendMarker : function (marker) {		
    	
		new Ajax.Request(this.url,
  		{
  			method:'POST',
  			parameters: 'lan: ' + marker.lan + ', lng: ' + marker.lng +  ', desc: ' + desc,

    		onSuccess: function(transport, json){
      			alert(json ? Object.inspect(json) : "no JSON object");
    		}

  		});

	},


	deleteMarker : function (markerId) {

		new Ajax.Request(this.url+'/'+markerId,
  		{
  			method:'DELETE',

    		onSuccess: function(transport){
      			alert("Marker deleted successfully!");
    		}

  		});

	},


	updateMarker : function (markerId, desc) {
		
		new Ajax.Request(this.url+'/'+markerId,
  		{
  			method:'EDIT',
  			parameters: 'desc: ' + desc,

    		onSuccess: function(transport){
      			alert("Marker deleted successfully!");	
    		}

  		});

	},


	getMatkers : function (argument) {
		
	}


});