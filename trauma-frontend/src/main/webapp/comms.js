var BackendComms = Class.create({

  initialize : function() {
    
  },


  sendMarker : function () {    
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
        var kit = new ToolKit();
        kit.addCallbackMarker(xMarker);
        //Fix a mapra rakást
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
        parameters: '{desc: ' + desc + '}',

        onSuccess: function(transport){
            alert("Marker deleted successfully!");  
        }

      });

  },


  getMarkers : function (lng,lat,rad) {

      var url = 'http://trauma.backend.cloudfoundry.com/markers';

      var data = {
            centralLng : lng,
            centralLan : lat,
            centralRad : rad
        };


      new Ajax.Request(url, {
      method: 'get',
      contentType: 'application/json',
      postBody: Object.toJSON(data),  
      onSuccess: function(transport) {        
      $('response').update(transport.responseText);

      var xMarkerArray = JSON.parse($('response').innerHTML);

      for(var i in xMarkerArray){

            var xMarker = xMarkerArray[i];
            console.log(xMarker.id + " | " + xMarker.desc);
            //ide jön a toolkites cucc
          }

        }
      
      });

    
  },

});