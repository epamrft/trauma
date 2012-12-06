/* Ajax communication utility class */
var BackendComms = Class.create({

  initialize : function(map) {
    this.map=map;
   
  },

/* Sending marker to backend */  
sendMarker : function () { 
      var self = this;   
      var url = 'http://trauma.backend.cloudfoundry.com/markers';

      var data = {
            longitude : document.getElementById("lng").innerHTML,
            latitude : document.getElementById("lat").innerHTML,
            desc : document.getElementById("descfield").value
        };

      new Ajax.Request(url, {
      method: 'post',
      contentType: 'application/json',
      postBody: Object.toJSON(data),  
      onSuccess: function(transport) {
        console.log(transport.responseText);
        $('response').update(transport.responseText);
        var xMarker = $('response').innerHTML.evalJSON();
        self.map.showMarker(xMarker);
        self.notifyUser('Marker Placement Success!');
        self.map.service.effects.hide($('descbox'));
        self.map.tmpMarker.setMap(null);
        }

      });

  },

/* Deleting marker from backend */
deleteMarker : function () {
    var self = this; 
    var url = 'http://trauma.backend.cloudfoundry.com/markers/'+$('actualMarkerID').innerHTML+'?_method=DELETE';
    
        new Ajax.Request(url, {
            method : 'POST',
            contentType : 'application/json',
            onSuccess : function(transport) {
                console.log(transport.responseText);
                $('response').update(transport.responseText);
                self.map.removeMarker();
                self.notifyUser('Marker Removal Successful!');
                self.map.service.effects.hide($('editbox'));


        }

    });

  },

/* Edit marker on backend */
updateMarker : function () {
    var self = this;
    var url = 'http://trauma.backend.cloudfoundry.com/markers/'+$('actualMarkerID').innerHTML+'?_method=PUT';
    
        new Ajax.Request(url, {
            method : 'POST',
            contentType : 'application/json',
            postBody : $('editBoxDescfield').value,
            onSuccess : function(transport) {
                console.log(transport.responseText);
                $('response').update(transport.responseText);
                var xMarker = $('response').innerHTML.evalJSON();
                document.getElementById('editBoxDescfield').value = xMarker.desc;
                self.notifyUser('Marker Updated Successfully!');
                self.map.removeMarker();
                self.map.showMarker(xMarker);
                self.map.service.effects.hide($('editbox'));
                
        }
    });

    },

/*Gets all markers from backend*/
getMarkers : function (lng,lat,rad,map) {

      var url = 'http://trauma.backend.cloudfoundry.com/markers';

      var data = {
            "central-lng" : lng,
            "central-lan" : lat,
            "central-rad" : rad
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
            
            map.showMarker(xMarker);
            
          }

        }
      
      });

    
  },

  notifyUser : function (notification){

    var notify = humane.create({container: $('map_canvas'), timeout: 2500, baseCls: 'humane-jackedup'});
    notify.log(notification);

  }

});