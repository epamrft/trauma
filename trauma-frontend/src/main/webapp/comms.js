var BackendComms = Class.create({

  initialize : function(map) {
    this.map=map;
   
  },
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
        }

      });

  },


  deleteMarker : function () {
    var self = this; 
    var url = 'http://trauma.backend.cloudfoundry.com/markers/'+$('actualMarkerID').innerHTML+'?_method=DELETE';
    
        new Ajax.Request(url, {
            method : 'POST',
            contentType : 'application/json',
            onSuccess : function(transport) {
                console.log(transport.responseText);
                $('response').update(transport.responseText);
        }

    });

  },


  updateMarker : function () {
    
    var url = 'http://trauma.backend.cloudfoundry.com/markers/'+$('actualMarkerID').innerHTML+'?_method=PUT';
    
        new Ajax.Request(url, {
            method : 'POST',
            contentType : 'application/json',
            postBody : $('editBoxDescfield').value,
            onSuccess : function(transport) {
                console.log(transport.responseText);
                $('response').update(transport.responseText);
                var xMarker = $('response').innerHTML.evalJSON();
                console.log(xMarker.desc);
                return xMarker.desc;
        }
    });

    },


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



  /*Marker placing function*/

  

});